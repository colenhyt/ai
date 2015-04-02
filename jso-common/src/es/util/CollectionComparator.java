package es.util;
import java.util.Collection;
import java.util.Comparator;

import org.apache.log4j.Logger;


/*
 ** JSO1.0, by Allen Huang,2007-5-24
 */
public class CollectionComparator implements Comparator {
	static Logger log = Logger.getLogger("CollectionComparator.java");

	public int compare(Object o1, Object o2) {
		Collection c1=(Collection)o1;
		Collection c2=(Collection)o2;
		if (c1.size()>c2.size())
			return -1;
		else if (c1.size()<c2.size())
			return 1;
		return 0;
	}
}

