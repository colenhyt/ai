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

import java.io.UnsupportedEncodingException;
import java.net.URL;

import org.apache.log4j.Logger;

import easyshop.model.ModelConstants;
import es.util.url.URLStrHelper;
import es.webref.model.WebLink;


public class HttpPage {
	static Logger _logClass = Logger.getLogger(HttpPage.class.getName());

	protected String urlStr;
	
	private String charSet;
	
	private int typeId;
	
	private int code=-1,contentLength=-1;
	
	private long serverDate=-1;
	
	private String newUrlStr=null;	
	public void setNewUrlStr(String newUrlStr) {
		this.newUrlStr = newUrlStr;
	}

	/**
	 * @return 返回 urlStr。
	 */
	public String getUrlStr() {
		return urlStr;
	}
	
	private final byte[] content;

	protected final ConnResponse response;

	public HttpPage(URL url,byte[] content){
		this(url.toExternalForm(),content,null,null);
	}
	
	public HttpPage(byte[] content,String encoding){
		this(null,content,null,encoding);
	}
	
	public HttpPage(String urlStr, byte[] content,ConnResponse resp,String _charSet) {
		this.urlStr=urlStr;
		this.content = content;
		response=resp;
		this.charSet=_charSet;
	}

	public String getStringContent() {
		try {
			if (content!=null)
				return new String(content,getCharSet());
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return null;
	}

	public byte[] getContent() {
		return content;
	}

	public String getCharSet(){
		if (urlStr.indexOf("haotushu.com")>0||urlStr.indexOf("joyo.com")>0||urlStr.indexOf("amazon.cn")>0)
			return "utf-8";
	    return ((charSet==null)?"GB2312":charSet);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("HttpPage: "+urlStr);

		return sb.toString();
	}

    /**
     * @return 返回 originalPage。
     */
	public int getCode() {
		if (response!=null)
			return response.getCode();
		return code;
	}

	public long getServerDate() {
		if (response!=null)
			return response.getServerDate();
		return serverDate;
	}

	public int getContentLength() {
		if (response!=null)
			return response.getContentLength();
		return contentLength;
	}

	public String getNewUrlStr() {
		if (response!=null)
			return response.getNewUrlStr();
		return newUrlStr;
	}
	
	public int getTypeId() {
    	int type=URLStrHelper.getFileType(urlStr);
    	if (type<=ModelConstants.PAGE_MAX_RESOURCE)
    		return WebLink.TYPE_SOURCE;
    	else 
    		return WebLink.TYPE_HTML;
	}

	public ConnResponse getResponse() {
		return response;
	}

}