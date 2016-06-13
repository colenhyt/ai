package box.site.model;

import java.util.Date;

public class TopItem {
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
}
