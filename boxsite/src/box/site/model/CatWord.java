package box.site.model;

public class CatWord {
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getCat() {
		return cat;
	}
	public void setCat(int cat) {
		this.cat = cat;
	}
	private String word;
	private int level;
	private int cat;
	private String catstr;
	public String getCatstr() {
		return catstr;
	}
	public void setCatstr(String catstr) {
		this.catstr = catstr;
	}
}
