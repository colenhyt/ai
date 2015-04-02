package es.util;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;


/*
 ** JSO1.0, by Allen Huang,2007-5-24
 */
public class CoolectionHelper {
	static Logger log = Logger.getLogger("CoolectionHelper.java");
	
	//根据collection的size对其进行升序的排序:
	public static void sortBySize(List list){
		CollectionComparator comparator=new CollectionComparator();
		Collections.sort(list,comparator);
	}
}

