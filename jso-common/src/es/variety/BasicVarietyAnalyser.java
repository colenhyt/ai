/*
 * Created on 2005-7-6
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.variety;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import easyshop.html.jericho.Element;
import easyshop.html.jericho.Source;
import easyshop.model.ModelConstants;
import easyshop.model.ShoppingItem;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BasicVarietyAnalyser implements VarietyAnalyser{
	
	int getVarietyByTitle(String contentText){
		int var=ModelConstants.VARIETY_UNKNOWN;
		Source source=new Source(contentText);
		List list=source.findAllElements("title");
		for (Iterator it=list.iterator();it.hasNext();){
			String var1=((Element)it.next()).getContentText();
			if (var1==null)
				continue;
			if ((var=getVar(var1))>=0);
			 break;
		}
		return var;
		
	}

	private int getVar(String titleWord){
		if (isVar(titleWord,BOOK_TITLE_WORDS))
			return ModelConstants.VARIETY_BOOK;
		if (isVar(titleWord,MOVIE_TITLE_WORDS))
			return ModelConstants.VARIETY_MOVIE;
		if (isVar(titleWord,MUSIC_TITLE_WORDS))
			return ModelConstants.VARIETY_MUSIC;
		if (isVar(titleWord,SOFT_TITLE_WORDS))
			return ModelConstants.VARIETY_SOFT;
		if (isVar(titleWord,DIGIT_TITLE_WORDS))
			return ModelConstants.VARIETY_DIGIT;
		return -1;
	}

	public int getVarByBranchVote(ShoppingItem page){
//		PageReferer ref=ClassifyHelper.findUniqueBranchRef(page.getReferers());
//		if (ref==null)
//			return -1;
//    	BranchPage refBranch=(BranchPage)ClassifyHelper.getPage(ref.getLinkId(),ref.getRefType());		
//    	return refBranch.getVariety()	;	
    	return -1;
	}
	
	public int getVarByVote(Set refs,int pageType){
//		Set catas=ClassifyHelper.getPageRef(pageType,refs);
//		if (catas.size()==0)
//			return -1;
//		
//		Set bookDirect=findRefs(BranchPage.BOOK,pageType);
//		Set movieDirect=findRefs(BranchPage.MOVIE,pageType);
//		Set musicDirect=findRefs(BranchPage.MOVIE,pageType);
//		Set softDirect=findRefs(BranchPage.MOVIE,pageType);
//		Set bookVotes=new HashSet();
//		Set movieVotes=new HashSet();
//		Set musicVotes=new HashSet();
//		Set softVotes=new HashSet();
//		
//		for (Iterator it=catas.iterator();it.hasNext();){
//			PageReferer ref=(PageReferer)it.next();
//			for (Iterator it2=bookDirect.iterator();it2.hasNext();){
//				if (ref.getLinkId()==((Integer)it2.next()).intValue())
//					bookVotes.add(new Integer(ref.getLinkId()));
//			}
//			for (Iterator it3=movieDirect.iterator();it3.hasNext();){
//				if (ref.getLinkId()==((Integer)it3.next()).intValue())
//					bookVotes.add(new Integer(ref.getLinkId()));
//			}
//			for (Iterator it4=musicDirect.iterator();it4.hasNext();){
//				if (ref.getLinkId()==((Integer)it4.next()).intValue())
//					bookVotes.add(new Integer(ref.getLinkId()));
//			}
//			for (Iterator it5=softDirect.iterator();it5.hasNext();){
//				if (ref.getLinkId()==((Integer)it5.next()).intValue())
//					bookVotes.add(new Integer(ref.getLinkId()));
//			}
//		}
//		
//		if (bookVotes.size()>0)
//			return BranchPage.BOOK;
//		if (movieVotes.size()>0)
//			return BranchPage.MOVIE;
//		if (musicVotes.size()>0)
//			return BranchPage.MUSIC;
//		if (softVotes.size()>0)
//			return BranchPage.SOFT;


		return -1;
	}
	
	private Set findRefs(int variety,int pageType){
//		PagesClassifyDAO dao=PagesClassifyDAOFactory.get().getDAO();
//		return dao.getDocIDs(variety,pageType);
		return null;
	}
	private boolean isVar(String word,String[] keys){
		for (int i=0;i<keys.length;i++){
			if (word.indexOf(keys[i])>=0)
				return true;
		}
		return false;
	}
	

}
