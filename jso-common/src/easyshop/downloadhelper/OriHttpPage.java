/*
 * This is the MIT license, see also
 * http://www.opensource.org/licenses/mit-license.html
 * 
 * Copyright (c) 2001 Brian Pitcher
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

// $Header: /cvsroot/weblech/weblech/src/weblech/spider/URLObject.java,v 1.3
// 2002/06/02 08:00:48 weblech Exp $
package easyshop.downloadhelper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import cl.site.util.SiteStrConverter;
import easyshop.model.Page;


public class OriHttpPage extends HttpPage{
	static Logger _logClass = Logger.getLogger(OriHttpPage.class.getName());

	private Page originalPage;
	
	private int classId=-1,status=0;
	
	private int directId=-1,typeId=-1;
	
	private long docId;
	
	private Object param;
	
	private int paramInt=-1,code=-1,contentLength=-1,paramSortId=-1;
	
	private String paramTradeType;
	
	private String paramCatRoot;
	
	private String rootUrlStr,refWord,relWord;
	
	private int relPaging=-1,refId=-1;
	
	private int pageActionType=-1;
	
	private long serverDate=-1;
	
	private int downloadStatus=-101;
	
	private boolean doDownload=true,saveFile=false;
	
	private String newUrlStr=null;
	
	public Object getParam() {
		return param;
	}

	public void setParam(Object param) {
		this.param = param;
	}

	public int getDirectId() {
		return directId;
	}

	public void setDirectId(int directId) {
		this.directId = directId;
	}

	public void setSpecId(String sId){
		specId=sId;
	}
	
    public String getSpecId() {
		return specId;
	}
	
    private Set pageRefs=new HashSet();
    public Set getPageRefs() {
        return pageRefs;
    }
    public void addRefs(Set refs) {
        pageRefs.addAll(refs);
    }	

	private final int url_id;
	
	private String specId,siteId;
	
	private String urlKey;
	
	/**
	 * @return 返回 urlKey。
	 */
	public String getUrlKey() {
		return urlKey;
	}
	/**
	 * @param urlKey 要设置的 urlKey。
	 */
	public void setUrlKey(String urlKey) {
		this.urlKey = urlKey;
	}
	private final ConnResponse response;

	public OriHttpPage(URL url,byte[] content,String sId){
		this(-1,url.toExternalForm(),content,sId,null,null);
	}
	
	public OriHttpPage(byte[] content,String encoding){
		this(-1,null,content,null,null,encoding);
	}

	public OriHttpPage(String urlStr, byte[] content,ConnResponse resp,String _charSet) {
		super(urlStr,content,resp,_charSet);
		url_id = -1;
		response=resp;
	}
	public OriHttpPage(int dId, String urlStr, byte[] content, String sId,ConnResponse resp,String _charset) {
		super(urlStr,content,resp,_charset);
		url_id = dId;
		specId = sId;
		response=resp;
	}
	
	public int getUrlId() {
		return url_id;
	}

    public void setOriginalPage(Page originalPage) {
        this.originalPage = originalPage;
    }
    
    public Page getOriginalPage(){
    	return originalPage;
    }
    
	public int getClassId() {
		return classId;
	}
	
	public URL getURL(){
		try {
			return new URL(urlStr);
		} catch (MalformedURLException e) {
			// log error here
			
			System.out.println(e.getMessage());
			return null;
		}
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getSiteId() {
		if (siteId==null)
			return SiteStrConverter.getShopSiteId(urlStr);
		
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getParamInt() {
		return paramInt;
	}

	public void setParamInt(int paramInt) {
		this.paramInt = paramInt;
	}

	public boolean isDoDownload() {
		return doDownload;
	}

	public void setDoDownload(boolean doDownload) {
		this.doDownload = doDownload;
	}

	public String getParamTradeType() {
		return paramTradeType;
	}

	public void setParamTradeType(String paramTradeType) {
		this.paramTradeType = paramTradeType;
	}

	public String getParamCatRoot() {
		return paramCatRoot;
	}

	public void setParamCatRoot(String paramCatRoot) {
		this.paramCatRoot = paramCatRoot;
	}

	public int getParamSortId() {
		return paramSortId;
	}

	public void setParamSortId(int paramSortId) {
		this.paramSortId = paramSortId;
	}

	public long getDocId() {
		return docId;
	}

	public void setDocId(long docId) {
		this.docId = docId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getRootUrlStr() {
		return rootUrlStr;
	}

	public void setRootUrlStr(String rootUrlStr) {
		this.rootUrlStr = rootUrlStr;
	}

	public String getRefWord() {
		return refWord;
	}

	public void setRefWord(String refWord) {
		this.refWord = refWord;
	}

	public int getRelPaging() {
		return relPaging;
	}

	public void setRelPaging(int relPaging) {
		this.relPaging = relPaging;
	}

	public String getRelWord() {
		return relWord;
	}

	public void setRelWord(String relWord) {
		this.relWord = relWord;
	}

	public int getDownloadStatus() {
		return downloadStatus;
	}

	public void setDownloadStatus(int downloadStatus) {
		this.downloadStatus = downloadStatus;
	}

	public int getPageActionType() {
		return pageActionType;
	}

	public void setPageActionType(int catType) {
		this.pageActionType = catType;
	}

	public int getRefId() {
		return refId;
	}

	public void setRefId(int refId) {
		this.refId = refId;
	}

	public boolean isSaveFile() {
		return saveFile;
	}

	public void setSaveFile(boolean saveFile) {
		this.saveFile = saveFile;
	}


}