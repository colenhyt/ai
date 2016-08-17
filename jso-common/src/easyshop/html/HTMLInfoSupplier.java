package easyshop.html;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import cl.html.helper.NekoHTMLDelegate;
import easyshop.html.jericho.Attribute;
import easyshop.html.jericho.Attributes;
import easyshop.html.jericho.Element;
import easyshop.html.jericho.Source;
import easyshop.html.jericho.StartTag;
import es.Constants;
import es.util.CollectionComparator;
import es.util.FileUtil;
import es.util.html.HTMLContentHelper;
import es.util.pattern.ESPattern;
import es.util.string.StringHelper;
import es.util.url.URLStrHelper;
import es.webref.model.PageRef;


public class HTMLInfoSupplier implements Serializable{
	private String encoding=Constants.CHARTSET_DEFAULT;
	private Source totalJerio;
	private String strContent;
	private byte[] content;
	private String urlStr;
	private Document jsoupDoc;
	
	private List<Element> linkElements,trElements,tableElements,divElements;
	private List<Element> distinctLinkElements=new ArrayList<Element>();
	private List<StartTag> linkTags,imgTags;
	
	public HTMLInfoSupplier()
	{
		
	}
	
	public static void main(String[] args){
		String s = FileUtil.readFile("c:/boxsite/test/1.html");
		HTMLInfoSupplier helper = new HTMLInfoSupplier(null,s);
		Vector<String> vv = new Vector<String>();
		vv.add("link?");
		vv.add("百度快照");
		Vector<String> vv2 = new Vector<String>();
		String kk = helper.findMaxSizeDivClassValue(vv,vv2);
		List<Element> divs = null;
		System.out.println(kk);
		long start = System.currentTimeMillis();
		for (int i=0;i<1;i++){
			List<Element> els = helper.getDivElementsByClassValue(kk);
			System.out.println(els.toString());
		}
		System.out.println(System.currentTimeMillis()-start);
	}
	
	public HTMLInfoSupplier(String _urlstr,String context){
		this.urlStr=_urlstr;
		strContent=context;
		this.totalJerio=new Source(context);
		init();
	}
	
	public HTMLInfoSupplier(String _urlstr,byte[] content,String encode){
		init(_urlstr,content,encode);
	}
	
	public void init(byte[] content){
		init(null,content,"utf-8");
	}
	
	public void init(byte[] content,String encode){
		init(null,content,encode);
	}
	
	public void init(String url,String strContent){
		this.urlStr=url;
		this.content=content;
		this.strContent= strContent;
		this.totalJerio=new Source(strContent);
		
		jsoupDoc = Jsoup.parse(this.strContent);		
	}
	
	public void init(String strContent){
		this.content=content;
		this.strContent= strContent;
		this.totalJerio=new Source(strContent);
		
		jsoupDoc = Jsoup.parse(this.strContent);	
		init();
	}
	
	public void init(String _urlstr,byte[] content,String encode){
		try {
		this.urlStr=_urlstr;
		this.content=content;
		this.encoding=encode;		
		this.strContent=new String(content,encoding);
		this.totalJerio=new Source(strContent);
		
		jsoupDoc = Jsoup.parse(this.strContent);
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		init();		
	}
	
	private void init(){
		linkElements=totalJerio.findAllElements("a");
		Set<String> links=new HashSet<String>();
		for (Iterator it=linkElements.iterator();it.hasNext();){
			Element e=(Element)it.next();
			String href=e.getAttributes().getValue("href");
			if (href!=null){
				if (links.add(href))
					distinctLinkElements.add(e);
			}
		}
		linkTags=totalJerio.findAllStartTags("a");
		imgTags=totalJerio.findAllStartTags("img");
		tableElements=totalJerio.findAllElements("table");
		trElements=totalJerio.findAllElements("tr");
		divElements=totalJerio.findAllElements("div");
	}
	
	
	public static List<String> getMainTerms(Document doc){
		List<String> words = new ArrayList<String>();
		Elements all = doc.getAllElements();
		for (org.jsoup.nodes.Element item:all){
			words.add(item.text());
		}
		Elements els = doc.select("meta[name=description]");
		for (int i=0;i<els.size();i++){
			words.add(els.get(i).attr("content").toString());
		}		
		return words;
	}
	
	public Set<WebTerm>	getWords(){
		Elements els = jsoupDoc.getAllElements();
		Set<WebTerm> words = new HashSet<WebTerm>();
		for (org.jsoup.nodes.Element e:els){
			if (e.text()!=null&&e.text().trim().length()>0){
				WebTerm item = new WebTerm();
				item.setTagName(e.tagName());
				item.setText(e.text());
				words.add(item);
			}
		}
		return words;
	}
	
	public List<PageRef> getIFrameUrls(){
		return HTMLContentHelper.getIFrameUrls(strContent);
	}
	
	public String[] getBlocksByTagNameAndRefKey(String tagName,String refKey){
		List<Element> list=bGtMinEsByRefWord(tagName,refKey);
		String[] blocks=new String[list.size()];
		Element[] es=list.toArray(new Element[list.size()]);
		for (int i=0;i<es.length;i++){
			blocks[i]=es[i].toString();
		}
		return blocks;
	}

	public String[] getBlocksByTagName(String tagName){
		List<Element> list=totalJerio.findAllElements(tagName);
		String[] blocks=new String[list.size()];
		Element[] es=list.toArray(new Element[list.size()]);
		for (int i=0;i<es.length;i++){
			blocks[i]=es[i].getContentText();
		}
		return blocks;
	}
	public List<Element> bGtMinEsByRefWord(String tagName,String refKey){
		List<Element> lList=new ArrayList<Element>();
		List<Element> list=mGetMinEsHasLink(tagName);
		for (Iterator it=list.iterator();it.hasNext();){
			Element e=(Element)it.next();
//			String context=e.getContentText();
//			if (context.indexOf("下一页")>0){
//				System.out.println(context);
//			}
			List as=e.findAllElements("a");
			for (Iterator it2=as.iterator();it2.hasNext();){
				Element e2=(Element)it2.next();
				String refWord=URLStrHelper.getAnchorText(e2.getContentText());
				if (refKey!=null&&refWord!=null&&refWord.equalsIgnoreCase(refKey)){
					lList.add(e);
					break;
				}
			}
		}
		return lList;
	}
	
	//取anchorText的链接
	public PageRef bGtPageRefByRefWord(String anchor){
		for (Iterator it=linkElements.iterator();it.hasNext();){
			Element e=(Element)it.next();
			List as=e.findAllElements("a");
			for (Iterator it2=as.iterator();it2.hasNext();){
				Element e2=(Element)it2.next();
				String refWord=URLStrHelper.getAnchorText(e2.getContentText());
				String url=e2.getAttributes().getValue("href");
				if (anchor!=null&&refWord!=null&&refWord.equalsIgnoreCase(anchor)&&url!=null){
					return new PageRef(URLStrHelper.legalUrl(urlStr, url),refWord);
				}
			}
		}
		return null;
	}	
	
	public Element bGetMaxLinksDIVInMinByRefWord(String tagName,String refKey){
		Element maxLinks=null;
		List<Element> list=bGtMinEsByRefWord(tagName,refKey);
		if (list.size()>0){
		Element[] divs=list.toArray(new Element[list.size()]);
		maxLinks=divs[0];
		for (int i=1;i<divs.length;i++){
			List links=divs[i].findAllStartTags("a");
			if (links.size()>=maxLinks.findAllStartTags("a").size())
				maxLinks=divs[i];
			
		}
		}
		return maxLinks;
	}
	
	//对所有的容器元素(内部有url)进行排序,从高到低,:
	public List<Element> bGetAllSortedEsByLinkCount(){
		List<Element> ess=new ArrayList<Element>();
		List<Element> divEs=mGetSortedEsByLinkCount("div");
		List<Element> tableEs=mGetSortedEsByLinkCount("table");
		ess.addAll(divEs);
		ess.addAll(tableEs);
		RefCountElementComparator comparator=new RefCountElementComparator();
		Collections.sort(ess,comparator);
		return ess;
		
	}
	
	public Set<PageRef> bGetMaxRefsByUrlBranch(){
		Collection refs=mGetRefsByUrlBranch();
		List<PageRef> list=new ArrayList<PageRef>();
		list.addAll(refs);
		CollectionComparator comp=new CollectionComparator();
		Collections.sort(list, comp);
		return (Set)(list.get(0));
	}
	
	public Collection mGetRefsByUrlBranch(){
		List list=distinctLinkElements;
		Map tagMap=new HashMap();
		for (Iterator it=list.iterator();it.hasNext();){
			Element tag=(Element)it.next();
			if (tag!=null){
				Attribute url=tag.getAttributes().get("href");
				if (url!=null){
				String wUrl=URLStrHelper.legalUrl(urlStr,url.getValue());
				if (wUrl!=null){
				String keystr=URLStrHelper.getUrlBran(wUrl);
				Set<PageRef> refs=null;
				if (tagMap.containsKey(keystr)){
					refs=(Set)tagMap.get(keystr);
				}else {
					refs=new HashSet<PageRef>();
					tagMap.put(keystr, refs);
				}
				refs.add(new PageRef(wUrl,URLStrHelper.getAnchorText(tag.getContentText())));
				}}
			}
		}
		return tagMap.values();
	}
	
	//对有链接的这些单元按照链接的数量进行排序，从高到低,为了不影响原来的排序,必须另外实例化新的list:
	public List<Element> mGetSortedEsByLinkCount(String tagName){
		List<Element> ess=new ArrayList<Element>();
		ess.addAll(mGetMinEsHasLink(tagName));
		RefCountElementComparator comparator=new RefCountElementComparator();
		Collections.sort(ess,comparator);
		return ess;
	}
	
	//找出有链接的div,并且div是最小化的,即div里面再没有子的有链接的div,
	public List<Element> mGetMinEsHasLink(String tagName){
		List<Element> mList=new ArrayList<Element>();
		List<Element> list=bGetMinEsHasLink(tagName);
		for (Iterator it=list.iterator();it.hasNext();){
			Element e=(Element)it.next();
			List tags=e.findAllElements(tagName);
			if (tags.size()==1){//没有相同的tag,这个linkTag就是自己
				mList.add(e);
			}else{//有相同的tag但全部div都没有链接
				boolean noLink=true;
				Element[] ets=(Element[])tags.toArray(new Element[tags.size()]);
				//从第2个开始查找:
				for (int i=1;i<ets.length;i++){
					List a=(ets[i]).findAllStartTags("a");
					if (a.size()>0){
						noLink=false;
						break;
					}
				}
				if (noLink)mList.add(e);
			}
		}
		return mList;
	}
	
	//找出有链接的elements:
	public List<Element> bGetMinEsHasLink(String tagName){
		List<Element> elements=null;
		if (tagName.equalsIgnoreCase("div"))
			elements=divElements;
		else if (tagName.equalsIgnoreCase("table"))
			elements=tableElements;
		else
			elements=totalJerio.findAllElements(tagName);
		
		List aa=totalJerio.findAllElements("a");
		List aaa=totalJerio.findAllElements("A");
		List<Element> linkDivs=new ArrayList<Element>();
		for (Iterator it=elements.iterator();it.hasNext();){
			Element e=(Element)it.next();
//			String context=e.getContentText();
//			if (context!=null&&context.indexOf("下一页")>0){
//				System.out.println(context);
//			}
			List links=e.findAllStartTags("a");
			if (links.size()>0){
				linkDivs.add(e);
			}
		}
		return linkDivs;
	}
	
	public List<Element> getTablesSortByRows(){
		List<Element> tables=mGetMinEsHasLink("table");
		TableElementComparator comparator=new TableElementComparator();
		Collections.sort(tables,comparator);
		return tables;
	}
	
	
	//两个标签是否相同,内容不同,但结构(只进行了第一层的子元素)完全相同,在第一层中,a,input的标签只比较顺序，不比较属性名,img不进行任何比较:
	public boolean equalsStructureTag(Element tag1,Element tag2){
		if (tag1.getName().equalsIgnoreCase(tag2.getName())){
			boolean equalsAtts=equalsAttributeNames(tag1.getAttributes(),tag2.getAttributes());
			
			Element[] fChildTags=(Element[])tag1.findAllElements().toArray(new Element[tag1.findAllElements().size()]);
			Element[] nChildTags=(Element[])tag2.findAllElements().toArray(new Element[tag2.findAllElements().size()]);
			boolean equlasChildTag=false;
			if (fChildTags.length==nChildTags.length&&nChildTags.length==0){
				equlasChildTag=true;
			}else if (fChildTags.length==nChildTags.length){
				boolean like=true;
				for (int i=0;i<fChildTags.length;i++){
					String n1=fChildTags[i].getName();
					String n2=nChildTags[i].getName();
					//比较标签名
					
					if ((n1==null&&n2!=null)||(n1!=null&&n2==null)){
						like=false;
						break;
					}else if ((n1.equalsIgnoreCase("img")||n2.equalsIgnoreCase("img"))||(n1.equalsIgnoreCase(n1)&&(n1.equalsIgnoreCase("a")||n1.equalsIgnoreCase("input")))){
						continue;
					}else if (!equalsAttributeNames(fChildTags[i].getAttributes(),nChildTags[i].getAttributes())){//比较属性值:
						like=false;
						break;
					}
				}
				equlasChildTag=like;
			}
			return (equalsAtts&&equlasChildTag);
		}
		return false;
	}
	
	//属性个数相同，属性名相同,顺序相同，对应的属性值不进行比较:
	public boolean equalsAttributeNames(Attributes attributes1,Attributes attributes2){
		Attribute[] atts1=(Attribute[])attributes1.toArray(new Attribute[attributes1.size()]);
		Attribute[] atts2=(Attribute[])attributes2.toArray(new Attribute[attributes2.size()]);
		boolean equalsAtts=false;
		if (atts1.length==atts2.length&&atts2.length==0)
			equalsAtts=true;
		else if (atts1.length==atts2.length){
			boolean b=true;
			for (int i=0;i<atts1.length;i++){
				Attribute at=atts1[i];
				Attribute at2=atts2[i];
				if (!at.getName().equalsIgnoreCase(at2.getName())){
					b=false;
					break;					
				}
			}
			equalsAtts=b;
		}
		return equalsAtts;
	}	
	//属性个数相同，属性名相同,顺序相同，对应的属性值(除id）也相同:
	public boolean equalsAttributesAll(Attributes attributes1,Attributes attributes2){
		Attribute[] atts1=(Attribute[])attributes1.toArray(new Attribute[attributes1.size()]);
		Attribute[] atts2=(Attribute[])attributes2.toArray(new Attribute[attributes2.size()]);
		boolean equalsAtts=false;
		if (atts1.length==atts2.length&&atts2.length==0)
			equalsAtts=true;
		else if (atts1.length==atts2.length){
			boolean b=true;
			for (int i=0;i<atts1.length;i++){
				Attribute at=atts1[i];
				Attribute at2=atts2[i];
				if (at.getName().equalsIgnoreCase(at2.getName())){
					String v1=at.getValue();
					String v2=at2.getValue();
					if (!at.getName().equalsIgnoreCase("id")&&((v1==null&&v2!=null)||(v1!=null&&v2==null)||!v1.equalsIgnoreCase(v2))){
						b=false;
						break;
					}
				}else{
					b=false;
					break;
				}
			}
			equalsAtts=b;
		}
		return equalsAtts;
	}
	
	public String getBlockByPosAndTagName(int position,String tagName){
		Element e=totalJerio.findEnclosingElement(position, tagName);
		if (e!=null)
			return e.getContentText();
		return null;
	}
	
	public String getContentByTagDnas(List<TagDNA> dnas){
		String context = null;
		for (TagDNA dna:dnas){
			context = this.getContentByTagDna(dna);
			if (context!=null&&context.trim().length()>0)
				break;
		}
		return context;
	}
	
	public String getContentByTagDna(TagDNA dna){
		if (dna.getType()==TagDNA.TAG_TYPE_TAG_AND_PROP){
			return this.getBlockByOneProp(dna.getTag(), dna.getPropName(), dna.getPropValue());
		}else if (dna.getType()==TagDNA.TAG_TYPE_TAG){
			return this.getBlock(dna.getTag());
		}else if (dna.getType()==TagDNA.TAG_TYPE_TAG_AND_PROP_KEY){
			return this.getBlockByOnePropAndKey(dna.getTag(), dna.getPropName(), dna.getPropValue(),dna.getKeyword());
		}else if (dna.getType()==TagDNA.TAG_TYPE_TAG_KEY){
			return this.getBlock(dna.getTag(), dna.getKeyword());
		}else if (dna.getType()==TagDNA.TAG_TYPE_TAG1START_AND_TAG2END){
			String[] strs = dna.getStartTagStr().split(",");
			String[] strs2 = dna.getEndTagStr().split(",");
			easyshop.html.jericho.Element e = getElementByOneProp(strs[0], strs[1],strs[2]);
			easyshop.html.jericho.Element e2 = getElementByOneProp(strs2[0], strs2[1],strs2[2]);
			if (e!=null&&e2!=null){
				return this.strContent.substring(e.getBegin(),e2.getEnd());
			}
		}
		return null;
	}
	public String getBlock(String tagName){
        List list = totalJerio.findAllStartTags(tagName);
        for (Iterator it=list.iterator();it.hasNext();) {
            StartTag tag = (StartTag) it.next();
            if (tag.getElement().getContentText()!=null){{
            	return tag.getElement().getContentText();
            }
            }
        }
        return null;
		
	}	
	public String getBlock(String tagName,String key){
        List list = totalJerio.findAllStartTags(tagName);
        for (Iterator it=list.iterator();it.hasNext();) {
            StartTag tag = (StartTag) it.next();
            if (key==null||(key!=null&&tag.getElement().getContentText()!=null&&tag.getElement().getContentText().indexOf(key)>=0)){{
            			return tag.getElement().getContentText();
            }
            }
        }
        return null;
		
	}
	
	//找到相同标签的后一个标签内容
	public String getNextBlock(String tagName,String preTagKey){
        List list = totalJerio.findAllStartTags(tagName);
        for (Iterator it=list.iterator();it.hasNext();) {
            StartTag tag = (StartTag) it.next();
            if (preTagKey==null||(preTagKey!=null&&tag.getElement().getContentText()!=null&&tag.getElement().getContentText().indexOf(preTagKey)>=0)){{
            		if (it.hasNext())
            			return ((StartTag)it.next()).getElement().getContentText();
            }
            }
        }
        return null;
	}
	
	//找到某标签的后一个标签内容
	public String getNextBlock(String tagName,String propName,String propValue,String nextTagName){
		Element e = getElementByOneProp(tagName,propName,propValue);
		StartTag tag = totalJerio.findNextStartTag(e.getEnd(), nextTagName);
		if (tag!=null)
			return tag.getElement().getContentText();
        return null;
	}
	
	public int getBlockPosition(int position,String tagName){
		Element e=totalJerio.findEnclosingElement(position, tagName);
		if (e!=null)
			return e.getStartTag().getBegin();
		return -1;		
	}
	
	public int[] getPositions(String key){
		int index=0;
		Set pSet=new HashSet();
		while (strContent.indexOf(key, index)>=0){
			int j=strContent.indexOf(key, index);
			pSet.add(new Integer(j));
			index=j+key.length();
		}
		Integer[] ipos=(Integer[])pSet.toArray(new Integer[pSet.size()]);
		int[] poss=new int[ipos.length];
		for (int i=0;i<poss.length;i++){
			poss[i]=ipos[i].intValue();
		}
		return poss;
	}
	
	public String getBlockByPropsAndKey(String tagName,Map props,String key){
		String div=null;
	        List list = totalJerio.findAllStartTags(tagName);
	        for (Iterator it=list.iterator();it.hasNext();) {
	            StartTag tag = (StartTag) it.next();
	            Attributes attrs=tag.getAttributes();
	            boolean isThis=true;
	            for (Iterator it2=props.keySet().iterator();it2.hasNext();){
	            	String propName=(String)it2.next();
	            	String propValue=(String)props.get(propName);
	            	if (attrs.get(propName)==null||!attrs.get(propName).getValue().equalsIgnoreCase(propValue))
	            		isThis=false;
	            }
	            if (isThis&&tag.getElement().getContentText().indexOf(key)>=0){
	            	div=tag.getElement().getContentText();
	            	break;
	            }
	            
	        }			
		return div;
		
	}
	
	public String getDivByClassValue(String classValue)
	{
		String[] strs = getBlocksByOneProp("div","class",classValue);
		if (strs.length>0)
			return strs[0];
		
		return null;
	}
	
	public String[] getDivsByClassValue(String classValue)
	{
		return getBlocksByOneProp("div","class",classValue);
	}
	
	public String getBlockByOneProp(String tagName,String propName,String propValue){
		return getBlockByOnePropAndKey(tagName,propName,propValue,null);
	}

	public String getFullBlockByOneProp(String tagName,String propName,String propValue){
		return getFullBlockByOnePropAndKey(tagName,propName,propValue,null);
	}
	
	public String getNextText(String posKey,String tagName,String endKey) throws SAXException, IOException{
		int index=strContent.indexOf(posKey);
		if (index>=0){
		Element t=totalJerio.findEnclosingElement(index,tagName );
		if (t!=null){
			String content= t.getContentText();
			HTMLContentHelper del=new HTMLContentHelper();
			List list;
				list = del.getTextValues(content.getBytes(), encoding);
			if (list!=null&&list.size()>0){
				String[] texts=(String[])list.toArray(new String[list.size()]);
				int startIndex=0,endIndex=0;
				for (int i=0;i<texts.length;i++){
					if (texts[i].equalsIgnoreCase(posKey)&&i<=texts.length-1){
						startIndex=content.indexOf(texts[i+1]);
						if (startIndex>0){
							endIndex=content.indexOf(endKey,startIndex);
							break;
						}
					}
				}
				if (endIndex>0)
					return content.substring(startIndex,endIndex);
			}
		}
		}
		return null;		
	}
	public String getNextBlock(String posKey,String propName,String propValue){
		int index=strContent.indexOf(posKey);
		if (index>=0){
		StartTag t=totalJerio.findNextStartTag(index, propName, propValue, false);
		if (t!=null)
			return t.getElement().getContentText();
		}
		return null;
	}
	
	public String[] getBlocksByTagNameAndKey(String tagName,String key){
		List<String> blocks=new ArrayList<String>();
		
		List<Element> es=totalJerio.findAllElements(tagName);
		for (Iterator it=es.iterator();it.hasNext();){
			String bb=((Element)it.next()).toString();
			if (bb.indexOf(key)>=0)
				blocks.add(bb);
		}	
		return blocks.toArray(new String[blocks.size()]);
		
	}
	
	public String[] getParentBlocksByTagNameAndKey(String tagName,String key){
		String[] divs=null;
		
		int[] pp=getPositions(key);
		int[] childBlokcPp=new int[pp.length];
		for (int i=0;i<childBlokcPp.length;i++){
			childBlokcPp[i]=getBlockPosition(pp[i], tagName);
		}
		divs=new String[childBlokcPp.length];
		for (int i=0;i<childBlokcPp.length;i++){
			divs[i]=getBlockByPosAndTagName(childBlokcPp[i]-1, tagName);
		}
		return divs;
		
	}
	
	//biggerCount: 比这个数量少的最多的内容块,-1时找最大数量的内容块
	public String[] getMaxCountBlock(String tagName,int simuLstLength,int biggerCount){
		Map tagMap=settleByTagName(tagName,simuLstLength);
		int maxCount=-1;
		Collection values=tagMap.values();
		List maxTags=null;
		for (Iterator it=values.iterator();it.hasNext();){
			List list=(List)it.next();
			int size=list.size();
			if (size>=maxCount&&(biggerCount<=0||biggerCount>size)){
				maxCount=list.size();
				maxTags=list;
			}
		}
		if (maxTags!=null){
		for (Iterator it2=maxTags.iterator();it2.hasNext();){
			StartTag tag=(StartTag)it2.next();
//			System.out.println("---------------");
//			System.out.println(tag.getElement().getContentText());
		}
		String[] blocks=new String[maxTags.size()];
		StartTag[] tags=(StartTag[])maxTags.toArray(new StartTag[maxTags.size()]);
		for (int i=0;i<blocks.length;i++){
			blocks[i]=tags[i].getElement().getContentText();
		}
		return blocks;
		}
		return new String[0];
	}
	
	private List maxTables;
	//按标签名对内容块进行归类,相同标签名(包括最少相似长度相同)被归为一类,leastLengh是最少相识长度:
	public Map settleByTagName(String tagName,int leastLengh){
		List list = totalJerio.findAllStartTags(tagName);
		Set startTags=new HashSet();
		Map tagMap=new HashMap();
		//建索引
		for (Iterator it=list.iterator();it.hasNext();){
			StartTag tag=(StartTag)it.next();
			if (tag!=null&&tag.getElement().getContentText()!=null){
			if (tag.getElement().getContentText().length()>leastLengh)
			startTags.add(tag.toString()+tag.getElement().getContentText().substring(0,leastLengh));
			else
				startTags.add(tag.toString());
			}
		}
		
		for (Iterator it2=startTags.iterator();it2.hasNext();){
			tagMap.put(it2.next(), new ArrayList());
		}
		
		//存放值:
		for (Iterator it3=list.iterator();it3.hasNext();){
			StartTag tag=(StartTag)it3.next();
			Set keys=tagMap.keySet();
			if (tag!=null&&tag.getElement().getContentText()!=null){
			for (Iterator it4=keys.iterator();it4.hasNext();){
				String key=(String)it4.next();
				if (tag.getElement().getContentText().length()>leastLengh&&(tag.toString()+tag.getElement().getContentText().substring(0,leastLengh)).equalsIgnoreCase(key)){
					List tables=(List)tagMap.get(key);
					tables.add(tag);
					break;
				}else if (tag.toString().equalsIgnoreCase(key)){
					List tables=(List)tagMap.get(key);
					tables.add(tag);
					break;					
				}
			}
			}
		}
		return tagMap;
	}

	public StartTag getNextStartTag(int pos,String nextTagName){
		return totalJerio.findNextStartTag(pos, nextTagName);
	}
	
	//按标签名对内容块进行归类,相同标签名(包括最少相似长度相同)被归为一类,leastLengh是最少相识长度:
	public Collection settleTagListByTagAndProps(String tagName){
		List list = totalJerio.findAllElements(tagName);
		Map tagMap=new HashMap();
		for (Iterator it=list.iterator();it.hasNext();){
			Element tag=(Element)it.next();
			if (tag!=null&&tag.getContentText()!=null){
				Attributes atts=tag.getAttributes();
				StringBuffer buffer=new StringBuffer();
				//把全部属性和属性值加起来,变成一个key:
				for (Iterator it2=atts.iterator();it2.hasNext();){
					Attribute att=(Attribute)it2.next();
					if (att.getName().equalsIgnoreCase("id"))
						buffer.append("id");
					else
						buffer.append(att.getName()+att.getValue());
				}
				List<Element> tags=null;
				String keystr=buffer.toString();
				if (tagMap.containsKey(keystr)){
					tags=(List)tagMap.get(keystr);
				}else {
					tags=new ArrayList<Element>();
					tagMap.put(keystr, tags);
				}
				tags.add(tag);
			}
		}
		return tagMap.values();
	}

	/*按标签结构对内容块进行归类，有相同的标签和一样数量的子标签
	 * 被归为一类,必须有子标签, 没有的不管,同时,当查找div时,根标签必须有class或者id属性
	 * 这个方法适用于查找table和div
	 */
	public Collection<ArrayList<Element>> settleEsByStructure(String tagName){
		List<Element> elements=null;
		if (tagName.equalsIgnoreCase("div"))
			elements=divElements;
		else if (tagName.equalsIgnoreCase("table"))
			elements=tableElements;
		else
			elements=totalJerio.findAllElements(tagName);
		
		Map tagMap=new HashMap();
		for (Iterator it=elements.iterator();it.hasNext();){
			Element tag=(Element)it.next();
			if (tag!=null&&tag.findAllElements(tagName).size()>1){//必须有相同的子标签
				List child=tag.findAllElements(tagName);
				String key=null;
				List<Element> tags=null;
				if (tagName.equals("div")){
				Attribute cAtt=tag.getAttributes().get("class");
				if (cAtt==null)
					cAtt=tag.getAttributes().get("id");
				if (cAtt!=null){
					key=tagName+child.size()+cAtt.getName();
				}
				}else if (tagName.equals("table"))
					key=tagName+child.size();
				if (key!=null){
				if (tagMap.containsKey(key)){//标签名+相同子标签+class的size作为key
					tags=(List)tagMap.get(key);
				}else {
					tags=new ArrayList<Element>();
					tagMap.put(key, tags);
				}
				tags.add(tag);
				}
			}
		}	
		
		return (Collection<ArrayList<Element>>)tagMap.values();
	}

	/*按标签结构对内容块进行归类，有相同的标签和一样数量的子标签
	 * 被归为一类,必须有子标签, 没有的不管
	 * 这个方法适用于查找table和div
	 */
	public Collection<ArrayList<Element>> mGetTableEsByStruct(){
		List<Element> elements=null;
			elements=tableElements;
		
		Map tagMap=new HashMap();
		for (Iterator it=elements.iterator();it.hasNext();){
			Element tag=(Element)it.next();
			if (tag!=null&&tag.findAllElements("tr").size()>1&&tag.findAllElements("td").size()>0){//必须有相tr,td
				String key=null;
				List<Element> tags=null;
				NekoHTMLDelegate neko=new NekoHTMLDelegate(tag.getContentText());
					key=neko.findTableSruct();
				if (key!=null){
				if (tagMap.containsKey(key)){//标签名+相同子标签+class的size作为key
					tags=(List)tagMap.get(key);
				}else {
					tags=new ArrayList<Element>();
					tagMap.put(key, tags);
				}
				tags.add(tag);
				}
			}
		}	
		
		return (Collection<ArrayList<Element>>)tagMap.values();
	}

	/*对该类标签按照序列化字符串进行规类,序列化信息包括所有的属性(属性值),所有的子标签,以及所有的文本:
	 */
	public Map mIncludeEsByAllStr(String tagName){
		List<Element> elements=null;
		if (tagName.equalsIgnoreCase("div"))
			elements=divElements;
		else if (tagName.equalsIgnoreCase("table"))
			elements=tableElements;
		else
			elements=totalJerio.findAllElements(tagName);
		
		Map tagMap=Collections.synchronizedMap(new HashMap());
		for (Iterator it=elements.iterator();it.hasNext();){
			Element tag=(Element)it.next();
				Set<Element> tags=null;
				
				NekoHTMLDelegate neko=new NekoHTMLDelegate(tag.toString());
				synchronized (neko){
				String key=neko.toAllString();
				if (tagMap.containsKey(key)){
					tags=(Set)tagMap.get(key);
				}else {
					tags=Collections.synchronizedSet(new HashSet());
					tagMap.put(key, tags);
				}
				tags.add(tag);
				}
		}
		
		return tagMap;
	}

	/*对table按标签结构对内容块进行归类，都是table,假如table有width,height属性,这些属性值一样,
	 * 假如有tbody,则都有, 行数一样,列数一样,
	 * 被归为一类,必须有子标签, 没有的不管,同时,当查找div时,根标签必须有class或者id属性
	 * 这个方法适用于查找规范的table
	 */
	public Collection<ArrayList<Element>> settleTableEsByStructure(){
		List<Element> elements=tableElements;
		
		Map tagMap=new HashMap();
		for (Iterator it=elements.iterator();it.hasNext();){
			Element tag=(Element)it.next();
				StringBuffer key=new StringBuffer();
				List<Element> tags=null;
				Attribute wAtt=tag.getAttributes().get("width");
				if (wAtt!=null)
					key.append("w"+wAtt.getValue());
				
				Attribute hAtt=tag.getAttributes().get("height");
				if (hAtt!=null)
					key.append("h"+hAtt.getValue());				
				
				List tb=tag.findAllElements("tbody");
				if (tb.size()>0)
					key.append("tb"+tb.size());
				
				List tr=tag.findAllElements("tr");
				if (tr.size()>0)
					key.append("tr"+tr.size());

				List td=tag.findAllElements("td");
				if (td.size()>0)
					key.append("td"+td.size());

				List div=tag.findAllElements("div");
				if (div.size()>0)
					key.append("div"+div.size());

				List t=tag.findAllElements("table");
				if (t.size()>1)
					key.append("t"+t.size());

				if (tagMap.containsKey(key.toString())){//相同structure作为key
					tags=(List)tagMap.get(key.toString());
				}else {
					tags=new ArrayList<Element>();
					tagMap.put(key.toString(), tags);
				}
				tags.add(tag);
		}	
		
		return (Collection<ArrayList<Element>>)tagMap.values();
	}
	
	/*按标签名对内容块进行归类,相同的标签(标签名相同以及属性名相同)
	 * 和相同的子标签(相同的标签名字和顺序,以及对应的相同的属性名)被归为一类,必须有子标签, 没有的不管:
	 */
	public Collection settleTagListByTagAndChildTag(String tagName){
		List list = totalJerio.findAllElements(tagName);
		Map tagMap=new HashMap();
		for (Iterator it=list.iterator();it.hasNext();){
			Element tag=(Element)it.next();
			if (tag!=null&&tag.getContentText()!=null){
				List atts=tag.findAllElements();
				StringBuffer buffer=new StringBuffer();
				buffer.append(tag.getName());
				//把标签和全部子标签名加起来,变成一个key:
				for (Iterator it2=atts.iterator();it2.hasNext();){
					Element child=(Element)it2.next();
						buffer.append(child.getName());
				}
				List<Element> tags=null;
				String keystr=buffer.toString();
				if (tagMap.containsKey(keystr)){
					tags=(List)tagMap.get(keystr);
				}else {
					tags=new ArrayList<Element>();
					tagMap.put(keystr, tags);
				}
				tags.add(tag);
			}
		}
		return tagMap.values();
	}
	
	public List<String> getBlockListByOnePropAndKey(String tagName,String propName,String propValue,String key){
		String div=null;
		List blocks=new ArrayList();
        List list = totalJerio.findAllStartTags(tagName);
        for (Iterator it=list.iterator();it.hasNext();) {
            StartTag tag = (StartTag) it.next();
            Attributes attrs=tag.getAttributes();
            for (Iterator it2=attrs.iterator();it2.hasNext();){
            	Attribute attr=(Attribute)it2.next();
            	if (attr!=null&&attr.getName()!=null&&attr.getName().equalsIgnoreCase(propName)&&attr.getValue()!=null&&attr.getValue().equalsIgnoreCase(propValue)){
            		if (key==null||(key!=null&&tag.getElement().getContentText()!=null&&tag.getElement().getContentText().indexOf(key)>=0)){
            			blocks.add(tag.getElement().getContentText());
            		}
            	}
            }
        }			

        return blocks;		
	}
	
	public String[] getBlocksByOnePropAndKey(String tagName,String propName,String propValue,String key){
		String div=null;
		Set blocks=new HashSet();
        List list = totalJerio.findAllStartTags(tagName);
        for (Iterator it=list.iterator();it.hasNext();) {
            StartTag tag = (StartTag) it.next();
            Attributes attrs=tag.getAttributes();
            for (Iterator it2=attrs.iterator();it2.hasNext();){
            	Attribute attr=(Attribute)it2.next();
            	if (attr!=null&&attr.getName()!=null&&attr.getName().equalsIgnoreCase(propName)&&attr.getValue()!=null&&attr.getValue().equalsIgnoreCase(propValue)){
            		if (key==null||(key!=null&&tag.getElement().getContentText()!=null&&tag.getElement().getContentText().indexOf(key)>=0)){
            			blocks.add(tag.getElement().getContentText());
            		}
            	}
            }
        }			

        return (String[])blocks.toArray(new String[blocks.size()]);		
	}
	
	public String getBlockByTagName(String tagName){
		return getBlockByTagNameAndKey(tagName,null);
	}
	
	public String getBlockByTagNameAndKey(String tagName,String key){
		String div=null;
		List list = totalJerio.findAllElements(tagName);
	    for (Iterator it=list.iterator();it.hasNext();) {
	        Element tag = (Element) it.next();
	        if (tag.getContentText()!=null){
	        	if (key!=null&&tag.getContentText().indexOf(key)>=0)
	        		div=tag.getContentText();
	        	else if (key==null)
	        		div=tag.getContentText();
	        }
	        	
	    }
	    return div;
	}

	public String getBlockByTagNameAndKeys(String tagName,String[] keys){
		String div=null;
		List list = totalJerio.findAllElements(tagName);
	    for (Iterator it=list.iterator();it.hasNext();) {
	        Element tag = (Element) it.next();
	        if (tag.getContentText()!=null){
	        	if (StringHelper.containsAll(tag.getContentText(), keys)){
	        		div=tag.getContentText();
	        		break;
	        	}
	        }
	        	
	    }
	    return div;
	}

	public String getFullBlockByTagNameAndKeys(String tagName,String[] keys){
		String div=null;
		List list = totalJerio.findAllElements(tagName);
        for (Iterator it=list.iterator();it.hasNext();) {
            Element tag = (Element) it.next();
            if (tag.getContentText()!=null){
            	if (StringHelper.containsAll(tag.getContentText(), keys)){
            		div=tag.toString();
            		break;
            	}
            }
            	
        }
        return div;
	}
	
	public String getNextBlockByOnePropAndKey(String tagName,String propName,String propValue,String key){
		String div=null;
	        List list = totalJerio.findAllStartTags(tagName);
	        for (Iterator it=list.iterator();it.hasNext();) {
	            StartTag tag = (StartTag) it.next();
	            Attributes attrs=tag.getAttributes();
	            for (Iterator it2=attrs.iterator();it2.hasNext();){
	            	Attribute attr=(Attribute)it2.next();
	            	if (attr!=null&&attr.getName()!=null&&attr.getName().equalsIgnoreCase(propName)&&attr.getValue()!=null&&attr.getValue().equalsIgnoreCase(propValue)){
	            		if (key==null||(key!=null&&tag.getElement().getContentText()!=null&&tag.getElement().getContentText().indexOf(key)>=0)){
	            			if (it.hasNext()){
	            			div=((StartTag)it.next()).getElement().getContentText();
	            			break;
	            			}
	            		}
	            	}
	            }
	        }			

		return div;
		
	}	
	public Element getElementByOneProp(String tagName,String propName,String propValue){
		return getElementByOnePropAndKey(tagName,propName,propValue,null);
	}
	
	public Element getElementByKey(String tagName,String key){
		return getElementByOnePropAndKey(tagName,null,null,key);
	}
	
	public List<Element> getElementByNameAndProps(String tagName,Map props){
		List<Element> elements=new ArrayList<Element>();
        List list = totalJerio.findAllStartTags(tagName);
        for (Iterator it=list.iterator();it.hasNext();) {
            StartTag tag = (StartTag) it.next();
            Attributes attrs=tag.getAttributes();
            boolean isThis=true;
            for (Iterator it2=props.keySet().iterator();it2.hasNext();){
            	String propName=(String)it2.next();
            	String propValue=(String)props.get(propName);
            	if (attrs.get(propName)==null||!attrs.get(propName).getValue().equalsIgnoreCase(propValue))
            		isThis=false;
            }
            if (isThis){
            	elements.add(tag.getElement());
            }
            
        }			
		return elements;
	}
	
	public boolean existAllRefWords(List<Element> links,List<String> refWords){
		if (refWords!=null){
			List<String> words=new ArrayList<String>();
			for (Iterator it2=links.iterator();it2.hasNext();) {
				Element link = (Element) it2.next();
				String refWord=URLStrHelper.getAnchorText(link.getContentText());
				if (refWord!=null)
					words.add(refWord);
				}
		for (Iterator it=refWords.iterator();it.hasNext();) {
			String word=(String)it.next();
			if (!StringHelper.contains1atLeast(word, words))
				return false;

		}
		}			
		return true;
	}	
	public boolean existAllElements(Element parent,List<String> childNames){
		if (childNames!=null){
		for (Iterator it=childNames.iterator();it.hasNext();) {
			String tagName=(String)it.next();
			List list=parent.findAllStartTags(tagName);
			if (list==null||list.size()<0)
				return false;
		}
		}
		return true;
	}
	
	private static boolean containsKeys(String content,Vector<String> keys,Vector<String> nowords){
		if (keys!=null) {
			for (String key:keys){
				if (!content.contains(key))return false;
			}
		}
		
		if (nowords!=null){
			for (String key:nowords){
				if (content.contains(key))return false;
			}		
		}
		
		return true;
	}
	//找到页面中元素最多的div的class Value:
	public String findMaxSizeDivClassValue(Vector<String> keywords,Vector<String> nowords){
		ArrayList tags=new ArrayList();
        List<StartTag> list = totalJerio.findAllStartTags("div");
		Map<String,Integer> classValues = new HashMap<String,Integer>();
		for (StartTag tag:list){
			String content = tag.getElement().getContentText();
			if (content==null||!containsKeys(content,keywords,nowords)) continue;
			
            Attributes attrs=tag.getAttributes();
            for (Iterator it2=attrs.iterator();it2.hasNext();){
            	Attribute attr=(Attribute)it2.next();
            	if (attr!=null&&attr.getName().equalsIgnoreCase("class")&&attr.getValue()!=null&&attr.getValue().trim().length()>0){
            		int cc = 0;
            		if (classValues.containsKey(attr.getValue())){
            			Integer in = classValues.get(attr.getValue());
            			cc = in.intValue()+1;
            		}
            		classValues.put(attr.getValue(),cc);
            	}
            }
		}
		Object[] keys = classValues.keySet().toArray();
		if (keys.length<=0)
			return null;
		
		String maxSizeKey = (String)keys[0];
		for (Object k:keys){
			String kk = (String)k;
			if (classValues.get(maxSizeKey).intValue()<classValues.get(kk).intValue())
				maxSizeKey = kk;
		}
		return maxSizeKey;
	}
	
	public List<Element> getDivElementsByPropValueLike(String strPropName,String strPropValue)
	{
		ArrayList<Element> tags=new ArrayList();
        List list = totalJerio.findAllStartTags("div");
        for (Iterator it=list.iterator();it.hasNext();) {
            StartTag tag = (StartTag) it.next();
            Attributes attrs=tag.getAttributes();
            for (Iterator it2=attrs.iterator();it2.hasNext();){
            	Attribute attr=(Attribute)it2.next();
            	if (attr!=null&&attr.getName().equalsIgnoreCase(strPropName)&&attr.getValue().indexOf(strPropValue)>=0){
            		tags.add(tag.getElement());
            	}
            }
        }			
        return tags;
	}
	
	public String getTitleContent(){
		List list = totalJerio.findAllStartTags("title");
		if (list.size()>0){
			StartTag tag = (StartTag)list.get(0);
			return tag.getElement().getContentText();
		}
		return null;
	}
	
	public Element getElementByOnePropAndKey(String tagName,String propName,String propValue,String key){
	        List list = totalJerio.findAllStartTags(tagName);
	        for (Iterator it=list.iterator();it.hasNext();) {
	            StartTag tag = (StartTag) it.next();
	            
	            if (tag.getElement().toString()==null) continue;
	            
            	if (propName!=null){
    	            Attributes attrs=tag.getAttributes();
	            for (Iterator it2=attrs.iterator();it2.hasNext();){
	            	Attribute attr=(Attribute)it2.next();
	            	if (attr!=null&&attr.getName()!=null&&attr.getName().equalsIgnoreCase(propName)&&attr.getValue()!=null&&attr.getValue().equalsIgnoreCase(propValue)){
	            		if (key==null||(tag.getElement().getContentText().indexOf(key)>=0)){
	            			return tag.getElement();
	            		}
	            	}
	            	}
	            }
            	else if (key==null||(tag.getElement().toString().indexOf(key)>=0)){
            		return tag.getElement();
            	}
	        }			

		return null;
		
	}	
	
	public String getBlockByOnePropAndKey(String tagName,String propName,String propValue,String key){
		String div=null;
	        List list = totalJerio.findAllStartTags(tagName);
	        for (Iterator it=list.iterator();it.hasNext();) {
	        	if (div!=null)break;
	            StartTag tag = (StartTag) it.next();
	            Attributes attrs=tag.getAttributes();
	            for (Iterator it2=attrs.iterator();it2.hasNext();){
	            	Attribute attr=(Attribute)it2.next();
	            	if (attr!=null&&attr.getName()!=null&&attr.getName().equalsIgnoreCase(propName)&&attr.getValue()!=null&&attr.getValue().equalsIgnoreCase(propValue)){
	            		if (key==null||(key!=null&&tag.getElement().getContentText()!=null&&tag.getElement().getContentText().indexOf(key)>=0)){
							div=tag.getElement().getContentText();
	            			break;
	            		}
	            	}
	            }
	        }			
	
		return div;
		
	}

	public String getFullBlockByOnePropAndKey(String tagName,String propName,String propValue,String key){
		String div=null;
	        List list = totalJerio.findAllStartTags(tagName);
	        for (Iterator it=list.iterator();it.hasNext();) {
	            StartTag tag = (StartTag) it.next();
	            Attributes attrs=tag.getAttributes();
	            for (Iterator it2=attrs.iterator();it2.hasNext();){
	            	Attribute attr=(Attribute)it2.next();
	            	if (attr!=null&&attr.getName()!=null&&attr.getName().equalsIgnoreCase(propName)&&attr.getValue()!=null&&attr.getValue().equalsIgnoreCase(propValue)){
	            		if (key==null||(key!=null&&tag.getElement().getContentText()!=null&&tag.getElement().getContentText().indexOf(key)>=0)){
								div=tag.getElement().getContentText();
	            			break;
	            		}
	            	}
	            }
	        }			
	
		return div;
		
	}

	public String getBlockByOnePropAndKeys(String tagName,String propName,String propValue,String[] keys){
		String div=null;
	        List list = totalJerio.findAllStartTags(tagName);
	        for (Iterator it=list.iterator();it.hasNext();) {
	            StartTag tag = (StartTag) it.next();
	            Attributes attrs=tag.getAttributes();
	            for (Iterator it2=attrs.iterator();it2.hasNext();){
	            	Attribute attr=(Attribute)it2.next();
	            	if (attr!=null&&attr.getName()!=null&&attr.getName().equalsIgnoreCase(propName)&&attr.getValue()!=null&&attr.getValue().equalsIgnoreCase(propValue)){
	            		if (StringHelper.containsAll(tag.getElement().getContentText(), keys)){
	            			div=tag.getElement().getContentText();
	            			break;
	            		}
	            	}
	            }
	        }			

		return div;
		
	}	
	
	public String[] getImgUrls(String propName,String propValue){
	        Set imgset=new HashSet();
	        for (Iterator it=imgTags.iterator();it.hasNext();) {
	        	StartTag tag = (StartTag)it.next();
	        	if (propName!=null&&propValue!=null){
		            Attributes attrs=tag.getAttributes();
		            for (Iterator it2=attrs.iterator();it2.hasNext();){
		            	Attribute attr=(Attribute)it2.next();
		            	if (attr!=null&&attr.getName()!=null&&attr.getName().equalsIgnoreCase(propName)&&attr.getValue()!=null&&attr.getValue().equalsIgnoreCase(propValue)){
		            		imgset.add(tag.getAttributes().getValue("src"));
		            		}
		            	}	        		
	        	}
	        	else {
	        		imgset.add(tag.getAttributes().getValue("src"));
	        	}
	        }	
		return (String[])imgset.toArray(new String[imgset.size()]);
	}

	public String[] getImgUrlLists(){
		List imglist=new ArrayList();
	        for (Iterator it=imgTags.iterator();it.hasNext();) {
	    		StartTag tag = (StartTag)it.next();
	    		String src=tag.getAttributes().getValue("src");
	    		if (src!=null)
	    			imglist.add(src);
	        }	
		return (String[])imglist.toArray(new String[imglist.size()]);
	}
	
	public String[] getImgUrls(){
		Set imgset=new HashSet();
	        for (Iterator it=imgTags.iterator();it.hasNext();) {
	    		StartTag tag = (StartTag)it.next();
	    		String src=tag.getAttributes().getValue("src");
	    		if (src!=null)
	        		imgset.add(src);
	        }	
		return (String[])imgset.toArray(new String[imgset.size()]);
	}
	
	
	public String[] getImgUrlsByLinkKey(String key){
		Set imgset=new HashSet();
	        for (Iterator it=imgTags.iterator();it.hasNext();) {
	    		StartTag tag = (StartTag)it.next();
	    		String src=tag.getAttributes().getValue("src");
	    		if (src!=null&&src.indexOf(key)>=0)
	        		imgset.add(src);
	        }	
		return (String[])imgset.toArray(new String[imgset.size()]);
	}
	public String[] getImgPropValuesByEndValueKey(String propName){
	        Set imgset=new HashSet();
	        for (Iterator it=imgTags.iterator();it.hasNext();) {
	        	StartTag tag = (StartTag)it.next();
	        	if (propName!=null){
		            Attributes attrs=tag.getAttributes();
		            for (Iterator it2=attrs.iterator();it2.hasNext();){
		            	Attribute attr=(Attribute)it2.next();
		            	if (attr!=null&&attr.getName()!=null&&attr.getName().equalsIgnoreCase(propName)&&attr.getValue()!=null){
		            		imgset.add(attr.getValue());
		            		}
		            	}	        		
	        	}
	        }	
		return (String[])imgset.toArray(new String[imgset.size()]);
	}	
	
	public List<PageRef> getShortUrls(){
	        List<PageRef> urls=new ArrayList();
	        for (Iterator it=linkTags.iterator();it.hasNext();) {
	        	StartTag tag = (StartTag)it.next();
	            String url=tag.getAttributes().getValue("href");
	            if (url!=null){
	            	String refWord=URLStrHelper.getAnchorText(tag.getElement().getContentText());
	            	urls.add(new PageRef(url,refWord));	   
	            }
	            	
	        }
		return urls;		
	}
	
	public Set<String> getHrefValues(){
		Set<String> urls=new HashSet<String>();
	        for (Iterator it=linkTags.iterator();it.hasNext();) {
	        	StartTag tag = (StartTag)it.next();
	        	String href=tag.getAttributes().getValue("href");
	            urls.add(href);
	        }
		return urls;		
	}
	
	public List<PageRef> getNotDomainUrls(String domainName){
        List<PageRef> urls=new ArrayList();
        for (Iterator it=linkTags.iterator();it.hasNext();) {
        	StartTag tag = (StartTag)it.next();
        	String href=tag.getAttributes().getValue("href");
            String url=URLStrHelper.legalUrl(urlStr,href);
            if (url!=null&&domainName!=null&&url.indexOf(domainName)<0){
            	String refWord=URLStrHelper.getAnchorText(tag.getElement().getContentText());
            	urls.add(new PageRef(url,refWord));	   
            }
            	
        }
	return urls;		
	}
	
	public List<String> getUrls2(String domainName){
        List<String> urls=new ArrayList();
        for (Iterator it=linkTags.iterator();it.hasNext();) {
        	StartTag tag = (StartTag)it.next();
        	String href=tag.getAttributes().getValue("href");
            String url=URLStrHelper.legalUrl(urlStr,href);
            if (url!=null&&(domainName==null||url.indexOf(domainName)>0)){
            	urls.add(url);	   
            }
            	
        }
	return urls;		
	}
	
	public List<PageRef> getUrls(){
	    return getUrls(null);		
	}
	
	public List<String> getUrlWords(){
		List<String> urlWords = new ArrayList();
        for (Iterator it=linkTags.iterator();it.hasNext();) {
        	StartTag tag = (StartTag)it.next();
        	String href=tag.getAttributes().getValue("href");
            String url=URLStrHelper.legalUrl(urlStr,href);
            if (url!=null){
            	String refWord=URLStrHelper.getAnchorText(tag.getElement().getContentText(),this.encoding);
            	urlWords.add(refWord);	   
            }
            	
        }	
        return urlWords;
	}
	
	public PageRef[] getPageRefs(){
		List<PageRef> refs=getUrls();
		return refs.toArray(new PageRef[refs.size()]);
	}
	
	public Set<PageRef> getUrlSetByAchorPattern(String pattern){
	        Set urls=new HashSet();
	        for (Iterator it=linkTags.iterator();it.hasNext();) {
	        	StartTag tag = (StartTag)it.next();
	            Attributes attrs=tag.getAttributes();
	            String value=attrs.getValue("href");
	            if (value!=null){
		            String url=URLStrHelper.legalUrl(urlStr,value);
		            String word=URLStrHelper.getAnchorText(tag.getElement().getContentText());
		            if (url!=null&&pattern!=null&&ESPattern.matches(pattern,word)){
		            	urls.add(new PageRef(url,word));	   		            
		            	}
	            }
	            	
	        }
		return urls;		
	}
	
	public Set<PageRef> getUrlSetByAchorKey(String wordKey){
	        Set urls=new HashSet();
	        for (Iterator it=linkTags.iterator();it.hasNext();) {
	        	StartTag tag = (StartTag)it.next();
	            Attributes attrs=tag.getAttributes();
	            String value=attrs.getValue("href");
	            if (value!=null){
		            String url=URLStrHelper.legalUrl(urlStr,value);
		            String word=URLStrHelper.getAnchorText(tag.getElement().getContentText());
		            if (url!=null&&wordKey!=null&&word!=null&&word.indexOf(wordKey)>=0){
		            	urls.add(new PageRef(url,word));	   		            
		            	}
	            }
	            	
	        }
		return urls;		
	}	
	public Set<PageRef> getUrlSetByLinkKey(String linkKey){
	        Set urls=new HashSet();
	        for (Iterator it=linkTags.iterator();it.hasNext();) {
	        	StartTag tag = (StartTag)it.next();
	            Attributes attrs=tag.getAttributes();
	            String value=attrs.getValue("href");
	            if (value!=null){
		            String url=URLStrHelper.legalUrl(urlStr,value);
		            if (url!=null&&linkKey!=null&&url.indexOf(linkKey)>=0){
		            	String refWord=URLStrHelper.getAnchorText(tag.getElement().getContentText());
		            	urls.add(new PageRef(url,refWord));	   		            }
	            }
	            	
	        }
		return urls;		
	}

	public List<String> getUrlStrs(){
	        List<String> urls=new ArrayList<String>();
	        for (Iterator it=linkTags.iterator();it.hasNext();) {
	        	StartTag tag = (StartTag)it.next();
	            Attributes attrs=tag.getAttributes();
	            String value=attrs.getValue("href");
	            if (value!=null){
		            String url=URLStrHelper.legalUrl(urlStr,value);
		            if (url!=null){
		            	if (!urls.contains(url))
		            		urls.add(url);	   		            
		            	}
	            }
	            	
	        }
		return urls;		
	}
	public List<PageRef> getUrlsByLinkKey(String linkKey){
	        List<PageRef> urls=new ArrayList<PageRef>();
	        for (Iterator it=linkTags.iterator();it.hasNext();) {
	        	StartTag tag = (StartTag)it.next();
	            Attributes attrs=tag.getAttributes();
	            String value=attrs.getValue("href");
	            if (value!=null){
		            String url=URLStrHelper.legalUrl(urlStr,value);
		            if (url!=null&&linkKey!=null&&url.indexOf(linkKey)>=0){
		            	String refWord=URLStrHelper.getAnchorText(tag.getElement().getContentText());
		            	PageRef ref=new PageRef(url,refWord);
		            	if (!urls.contains(ref))
		            		urls.add(ref);	   		            
		            	}
	            }
	            	
	        }
		return urls;		
	}
	
	public Set<String> getUrlsInBlocksByKeys(String urlStr,String[] blocks,Set<String> urlKeys){
		Set<String> urls=new HashSet<String>();
		
		if (blocks==null||blocks.length<=0) return urls;
		
		for (int i=0;i<blocks.length;i++){
			Set<String> refs=HTMLContentHelper.getSiteUrlsInBlock(urlStr, blocks[i]);
			for (Iterator it=refs.iterator();it.hasNext();){
				String url=(String)it.next();
				if (StringHelper.containsAll(url, urlKeys.toArray(new String[urlKeys.size()]))){
					urls.add(url);
				}
			}
		}
		return urls;
	}
	
	public Set<PageRef> getUrlSetByKeys(String[] linkKeys,String[] keys){
        Set urls=new HashSet();
        for (Iterator it=linkTags.iterator();it.hasNext();) {
        	StartTag tag = (StartTag)it.next();
            Attributes attrs=tag.getAttributes();
            String value=attrs.getValue("href");
            if (value!=null){
	            String url=URLStrHelper.legalUrl(urlStr,value);
//	            System.out.println(url);
	            if (url==null||(linkKeys!=null&&linkKeys.length>0&&!StringHelper.containsAll(url, linkKeys)))continue;
	            
	            	String refWord=URLStrHelper.getAnchorText(tag.getElement().getContentText());
	            	if (keys==null||keys.length==0||StringHelper.contains1atLeast(refWord, keys))
	            		urls.add(new PageRef(url,refWord));	   		
	           }
            	
        }
	return urls;		
}	
	public Set<PageRef> getUrlSetByLinkKey(Set<String> linkKeys){
	        Set urls=new HashSet();
	        for (Iterator it=linkTags.iterator();it.hasNext();) {
	        	StartTag tag = (StartTag)it.next();
	            Attributes attrs=tag.getAttributes();
	            String value=attrs.getValue("href");
	            if (value!=null){
	//            	if(value.indexOf("")>=0){
	//            		System.out.println("?page=2&area=&key=");
	//            	}
	//	            	System.out.println(value);
		            String url=URLStrHelper.legalUrl(urlStr,value);
	//	            if (url.indexOf("usm_jgzx.asp")>0){
	//	            	System.out.println(url);
	//	            }
		            if (url==null||!StringHelper.containsAll(url, (String[])(linkKeys.toArray(new String[linkKeys.size()]))))
		            		continue;
		            
		            if (url!=null){
		            	String refWord=URLStrHelper.getAnchorText(tag.getElement().getContentText());
		            	urls.add(new PageRef(url,refWord));	   		            }
	            }
	            	
	        }
		return urls;		
		}

	public List<PageRef> getUrlsByLinkKeys(Set<String> linkKeys){
        List<PageRef> urls=new ArrayList<PageRef>();
        for (Iterator it=linkTags.iterator();it.hasNext();) {
        	StartTag tag = (StartTag)it.next();
            Attributes attrs=tag.getAttributes();
            String value=attrs.getValue("href");
            if (value!=null){
	            String url=URLStrHelper.legalUrl(urlStr,value);
	            if (url==null||!StringHelper.containsAll(url, (String[])(linkKeys.toArray(new String[linkKeys.size()]))))
	            		continue;
	            
	            if (url!=null){
	            	String refWord=URLStrHelper.getAnchorText(tag.getElement().getContentText());
	            	urls.add(new PageRef(url,refWord));	   		            }
            }
            	
        }
	return urls;		
	}
	
	public PageRef[] getUrls(String propName,String propValue){
		PageRef[] refs=null;
	        Set urls=new HashSet();
	        for (Iterator it=linkTags.iterator();it.hasNext();) {
	        	StartTag tag = (StartTag)it.next();
	            Attributes attrs=tag.getAttributes();
	            String value=attrs.getValue(propName);
	            if (value!=null&&propValue!=null&&value.equalsIgnoreCase(propValue)){
		            String url=URLStrHelper.legalUrl(urlStr,tag.getAttributes().getValue("href"));
		            if (url!=null){
		            	String refWord=URLStrHelper.getAnchorText(tag.getElement().getContentText());
		            	urls.add(new PageRef(url,refWord));	   		            }
	            }
	            	
	        }
	        refs=(PageRef[])urls.toArray(new PageRef[urls.size()]);
		return refs;		
	}

	public String getParentBlock(int childPosition,String parentTagName){
    	Element element=totalJerio.findEnclosingElement(childPosition,parentTagName);
    	return element.getContentText();
		
	}
	
	public int[] getBlocksPosition(String[] words){
		int[] poss=new int[words.length];
		for (int i=0;i<poss.length;i++){
			poss[i]=strContent.indexOf(words[i]);
		}
		return poss;
	}
	
	//抓取包含keys的最小化的那个标签,不允许有更大的包含标签，但是允许有同级或低一级的子标签
	public String[] getPureBlocks(String tagName,String[] keys){
		ArrayList tags=new ArrayList();
	        List list = totalJerio.findAllStartTags(tagName);
	        for (Iterator it=list.iterator();it.hasNext();) {
	            StartTag tag = (StartTag) it.next();
	            String text=tag.getElement().getContentText();
	            if (text==null)continue;
	            
	            int tag1=text.indexOf(tagName);
	            int tag2=0;
	            if (tag1>0)tag2=text.indexOf(tagName,tag1+tagName.length());
	            boolean has=true;
	            for (int i=0;i<keys.length;i++){
	            	int index=text.indexOf(keys[i]);
	            	if (index<0){
	            		has=false;
	            		break;
	            	}
	            	else if (tag2>0&&tag1<index&&index<tag2){//被包含在子标签里面;
	            		has=false;
	            		break;
	            	}
	            }
	            if (has){
//	            	System.out.println(text);
		            for (int i=0;i<keys.length;i++){
		            	int index=text.indexOf(keys[i]);
		            	if (index<0){
		            		has=false;
		            		break;
		            	}
		            	else if (tag2>0&&tag1<index&&index<tag2){//被包含在子标签里面;
		            		has=false;
		            		break;
		            	}
		            }	            	
	            	tags.add(text);
	            }

	        }			
		return (String[])tags.toArray(new String[tags.size()]);
		
	}	
	public String[] getBlocksByTagNameAndKeys(String tagName,String[] keys){
		ArrayList tags=new ArrayList();
	        List list = totalJerio.findAllStartTags(tagName);
	        for (Iterator it=list.iterator();it.hasNext();) {
	            StartTag tag = (StartTag) it.next();
	            String text=tag.getElement().getContentText();
	            boolean has=true;
	            for (int i=0;i<keys.length;i++){
	            	if (text.indexOf(keys[i])<0)
	            		has=false;
	            }
	            if (has)
	            	tags.add(text);
	        }			
		return (String[])tags.toArray(new String[tags.size()]);
		
	}
	
	public String[] getFullBlocksByOneProp(String tagName,String propName,String propValue){
		ArrayList tags=new ArrayList();
	        List list = totalJerio.findAllStartTags(tagName);
	        for (Iterator it=list.iterator();it.hasNext();) {
	            StartTag tag = (StartTag) it.next();
	            Attributes attrs=tag.getAttributes();
	            for (Iterator it2=attrs.iterator();it2.hasNext();){
	            	Attribute attr=(Attribute)it2.next();
	            	if (attr!=null&&attr.getName().equalsIgnoreCase(propName)&&attr.getValue().equalsIgnoreCase(propValue)){
	            		tags.add(tag.getElement().toString());
	            	}
	            }
	        }			
		return (String[])tags.toArray(new String[tags.size()]);
		
	}

	public String[] getBlocksByOneProp(String tagName,String propName,String propValue){
		ArrayList tags=new ArrayList();
	        List list = totalJerio.findAllStartTags(tagName);
	        for (Iterator it=list.iterator();it.hasNext();) {
	            StartTag tag = (StartTag) it.next();
	            Attributes attrs=tag.getAttributes();
	            for (Iterator it2=attrs.iterator();it2.hasNext();){
	            	Attribute attr=(Attribute)it2.next();
	            	if (attr!=null&&attr.getName().equalsIgnoreCase(propName)&&attr.getValue().equalsIgnoreCase(propValue)){
	            		tags.add(tag.getElement().getContentText());
	            	}
	            }
	        }			
		return (String[])tags.toArray(new String[tags.size()]);
		
	}

	public List<Element> getDivElementsByClassValue(String strClassValue)
	{
		ArrayList<Element> tags=new ArrayList();
	    List list = totalJerio.findAllStartTags("div");
	    for (Iterator it=list.iterator();it.hasNext();) {
	        StartTag tag = (StartTag) it.next();
	        Attributes attrs=tag.getAttributes();
	        for (Iterator it2=attrs.iterator();it2.hasNext();){
	        	Attribute attr=(Attribute)it2.next();
	        	if (attr!=null&&attr.getName().equalsIgnoreCase("class")&&attr.getValue().equalsIgnoreCase(strClassValue)){
	        		tags.add(tag.getElement());
	        	}
	        }
	    }			
	    return tags;
	}

	public List<String> getUrlStrsByLinkKey(String linkKey){
	        List<String> urls=new ArrayList<String>();
	        for (Iterator it=linkTags.iterator();it.hasNext();) {
	        	StartTag tag = (StartTag)it.next();
	            Attributes attrs=tag.getAttributes();
	            String value=attrs.getValue("href");
	            if (value!=null){
		            String url=URLStrHelper.legalUrl(urlStr,value);
		            if (url!=null&&linkKey!=null&&url.indexOf(linkKey)>=0){
		            	if (!urls.contains(url))
		            		urls.add(url);	   		            
		            	}
	            }
	            	
	        }
		return urls;		
	}

	public List<Element> getElementsByKeyTexts(String tagName,List<String> keyTexts){
		List<Element> list=null;
		if (tagName.equalsIgnoreCase("div"))
			list=divElements;
		else if (tagName.equalsIgnoreCase("table"))
			list=tableElements;
		else if (tagName.equalsIgnoreCase("tr"))
			list=trElements;
		else
			list=totalJerio.findAllElements(tagName);
		
		List<Element> elements=new ArrayList<Element>();
		
		for (Iterator it=list.iterator();it.hasNext();) {
			Element tag = (Element) it.next();
			if (tag.getContentText()!=null&&tag.getContentText().length()>0){
			String pureText=HTMLContentHelper.getPureText(tag.getContentText()).trim();
			if (keyTexts==null||StringHelper.containsAll(pureText, keyTexts)){
				elements.add(tag);
			}
			}
		}
		
		return elements;
	}

	public List<org.jsoup.nodes.Element> getElementsByKeyTexts(Document doc,String tagName,List<String> keyTexts){
		Elements els=null;
		if (tagName==null)
			els = doc.getAllElements();
		else
			els = doc.getElementsByTag(tagName);
		
		List<org.jsoup.nodes.Element> elements=new ArrayList<org.jsoup.nodes.Element>();
		
		for (Iterator it=els.iterator();it.hasNext();) {
			org.jsoup.nodes.Element tag = (org.jsoup.nodes.Element) it.next();
			if (tag.text()==null||tag.text().trim().length()<=0)continue;
			String attri = tag.attr("class");
			if (attri.equalsIgnoreCase("article-content")){
				int t = 10;
			}
			if (keyTexts==null||StringHelper.containsAll(tag.text(), keyTexts)){
				elements.add(tag);
			}
		}
		
		return elements;
	}

	public List<PageRef> getUrls(String domainName){
	    List<PageRef> urls=new ArrayList();
	    for (Iterator it=linkTags.iterator();it.hasNext();) {
	    	StartTag tag = (StartTag)it.next();
	    	String href=tag.getAttributes().getValue("href");
	        String url=URLStrHelper.legalUrl(urlStr,href);
	        if (url!=null&&(domainName==null||url.indexOf(domainName)>0)){
	        	String refWord=URLStrHelper.getAnchorText(tag.getElement().getContentText(),this.encoding);
	        	urls.add(new PageRef(url,refWord));	   
	        }
	        	
	    }
	return urls;		
	}

	//获取按同类聚合并根据数量多少进行排序的URL集合:
	public static Map<String,Vector<String>> findRegUrls(List<String> refs){
		Map<String,Vector<String>>  urlDirMap = new HashMap<String,Vector<String>>();
		for (String ref:refs){
			String regUrl = URLStrHelper.getUrlRex(ref);
			Vector<String> dirUrls = urlDirMap.get(regUrl);
			if (dirUrls==null){
				dirUrls = new Vector<String>();
				urlDirMap.put(regUrl, dirUrls);
			}
			dirUrls.add(ref);				
		}
	
		 //通过ArrayList构造函数把map.entrySet()转换成list 
		List<Map.Entry<String,Vector<String>>> mappingList = new ArrayList<Map.Entry<String,Vector<String>>>(urlDirMap.entrySet()); 
		  //通过比较器实现比较排序 
		  Collections.sort(mappingList, new Comparator<Map.Entry<String,Vector<String>>>(){ 
		   public int compare(Map.Entry<String,Vector<String>> mapping1,Map.Entry<String,Vector<String>> mapping2){ 
		    return mapping1.getValue().size()> mapping2.getValue().size()?1:0; 
		   } 
		  }); 
		  
		return urlDirMap;
	}

}
