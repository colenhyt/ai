package box.site.model;

public class TagDNA {
	public final static int TAG_TYPE_TAG = 0;			//<article />形态
	public final static int TAG_TYPE_TAG_KEY = 1;		//<article>key</article>形态
	public final static int TAG_TYPE_TAG_AND_PROP = 2;	//<div class=''>形态
	public final static int TAG_TYPE_TAG_AND_PROP_KEY = 3; //<div class=''>key</div>形态
	public final static int TAG_TYPE_TAG1START_AND_TAG2END = 4;		//<div ></div><div ></div>形态
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	int type;
	String tag;
	String propName;
	String propValue;
	public String getPropName() {
		return propName;
	}
	public void setPropName(String propName) {
		this.propName = propName;
	}
	public String getPropValue() {
		return propValue;
	}
	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}
	String keyword;
	int startIndex,endIndex;
	String startTagStr,endTagStr;
	public String getStartTagStr() {
		return startTagStr;
	}
	public void setStartTagStr(String startTagStr) {
		this.startTagStr = startTagStr;
	}
	public String getEndTagStr() {
		return endTagStr;
	}
	public void setEndTagStr(String endTagStr) {
		this.endTagStr = endTagStr;
	}
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
