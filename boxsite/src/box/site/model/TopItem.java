package box.site.model;

import java.util.Date;

public class TopItem implements Comparable{
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getCat() {
		return cat;
	}
	public void setCat(int cat) {
		this.cat = cat;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	private String url;
	private int cat;
	private String content;
	private Date crDate;
	private int id;
	private String sitekey;
	private long contentTime;
	private String ctitle;
	private String htmlContent;
	public String getHtmlContent() {
		return htmlContent;
	}
	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}
	public String getCtitle() {
		return ctitle;
	}
	public void setCtitle(String ctitle) {
		this.ctitle = ctitle;
	}
	public int getLike() {
		return like;
	}
	public void setLike(int like) {
		this.like = like;
	}
	public String getShortImg() {
		return shortImg;
	}
	public void setShortImg(String shortImg) {
		this.shortImg = shortImg;
	}
	private int like;
	private String shortImg;

	public long getContentTime() {
		return contentTime;
	}
	public void setContentTime(long contentTime) {
		this.contentTime = contentTime;
	}
	public String getSitekey() {
		return sitekey;
	}
	public void setSitekey(String sitekey) {
		this.sitekey = sitekey;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getCrDate() {
		return crDate;
	}
	public void setCrDate(Date crDate) {
		this.crDate = crDate;
	}
	@Override
	public int compareTo(Object o) {
		TopItem item = (TopItem)o;
		Long crd = item.getContentTime();
		return crd.compareTo(this.getContentTime());
	}
}
