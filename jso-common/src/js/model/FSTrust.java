package js.model;
import org.apache.log4j.Logger;


/*
 ** JSO1.0, by Allen Huang,2007-6-4
 */
public class FSTrust {
	static Logger log = Logger.getLogger("FSTrust.java");
	public final static int TYPE_ALI_TRUSTTONG=0;
	public final static int TYPE_HC_MAIMAITONG=1;
	public final static int TYPE_DNB=2;
	
	public final static String PATTERN_TRUST_DNB="[\\d]{9}";
	private final int type;
	private final String value;
	
	public FSTrust(int _type,String _value){
		type=_type;
		value=_value;
	}
	
	public int getType() {
		return type;
	}
	public String getValue() {
		return value;
	}

	public String toString(){
		return "type:"+type+"; value:"+value;
	}

	public String getTypeName() {
		switch (type){
		case FSTrust.TYPE_ALI_TRUSTTONG:
			return "阿里巴巴诚信通";
		case FSTrust.TYPE_HC_MAIMAITONG:
			return "慧聪买卖通";
		case FSTrust.TYPE_DNB:
			return "邓白氏指数";
		}		
		return null;
	}
}

