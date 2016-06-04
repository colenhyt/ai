package box.site.model;

public class WebUrl {
	private String url;
	private String text;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String toString(){
		return this.url+","+this.text;
	}
}
