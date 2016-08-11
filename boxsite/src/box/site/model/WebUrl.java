package box.site.model;

public class WebUrl {
	private String url;
	private String text;
	private int id;
	private int cat;
	private String catStr;
	public String getCatStr() {
		return catStr;
	}
	public void setCatStr(String catStr) {
		this.catStr = catStr;
	}
	public int getCat() {
		return cat;
	}
	public void setCat(int cat) {
		this.cat = cat;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	
	public int getId(){
		return this.url.hashCode();
	}
	
	public int hashCode(){
		return this.url.hashCode();
	}
}
