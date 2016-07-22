package easyshop.html;

import java.io.Serializable;

public class TagDNA implements Serializable{
	public final static int TAG_TYPE_TAG = 0;			//<article />形态
	public final static int TAG_TYPE_TAG_KEY = 1;		//<article>key</article>形态
	public final static int TAG_TYPE_TAG_AND_PROP = 2;	//<div class=''>形态
	public final static int TAG_TYPE_TAG_AND_PROP_KEY = 3; //<div class=''>key</div>形态
	public final static int TAG_TYPE_TAG1START_AND_TAG2END = 4;		//<div ></div><div ></div>形态
	public final static int TAG_TYPE_TAG_AND_PROPS = 5;	//<div class='' id=''>形态,多个属性
	
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
	String props;
	public String getProps() {
		return props;
	}
	public void setProps(String props) {
		this.props = props;
	}
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
	
	public String toString(){
		return this.type+","+this.tag+","+this.propName+","+this.propValue+","+this.keyword;
	}
}
