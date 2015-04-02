/*
 * 创建日期 2006-8-15
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package easyshop.downloadhelper;

/**
 * @author Allen Huang
 *
 * EasyShop 1.0
 * 
 * Date: 2006-8-15
 */
public class ConnResponse {
	private final String contentType;
	private final String charSet;
	private final String newUrlStr;
	private final int contentLength;
	private final long serverDate;
	private final int code;
	/**
	 * @return 返回 charSet。
	 */
	public String getCharSet() {
		return charSet;
	}
	/**
	 * @return 返回 code。
	 */
	public int getCode() {
		return code;
	}
	/**
	 * @return 返回 contentLength。
	 */
	public int getContentLength() {
		return contentLength;
	}
	/**
	 * @return 返回 contentType。
	 */
	public String getContentType() {
		return contentType;
	}
	/**
	 * @return 返回 newUrlStr。
	 */
	public String getNewUrlStr() {
		return newUrlStr;
	}
	/**
	 * @return 返回 serverDate。
	 */
	public long getServerDate() {
		return serverDate;
	}
	public ConnResponse(String cType,String newUrlStr,
			int length, long sDate, int code){
		this.contentType=HttpConnectionHelper.getContentType(cType);
		this.charSet=HttpConnectionHelper.getCharSet(cType);		
		this.newUrlStr=newUrlStr;
		this.contentLength=length;
		this.serverDate=sDate;
		this.code=code;
		
	}

}
