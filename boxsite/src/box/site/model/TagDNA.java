package box.site.model;

public class TagDNA {
	public int getKeyType() {
		return keyType;
	}
	public void setKeyType(int keyType) {
		this.keyType = keyType;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getTagPropName() {
		return tagPropName;
	}
	public void setTagPropName(String tagPropName) {
		this.tagPropName = tagPropName;
	}
	public String getTagPropValue() {
		return tagPropValue;
	}
	public void setTagPropValue(String tagPropValue) {
		this.tagPropValue = tagPropValue;
	}
	int keyType;
	String tagName;
	String tagPropName;
	String tagPropValue;
	String keyword;
	int startIndex,endIndex;
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
}
