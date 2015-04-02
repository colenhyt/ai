package easyshop.html;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import easyshop.html.jericho.Element;



/*
 ** JSO1.0, by Allen Huang,2007-5-24
 */
public class TableElementComparator implements Comparator {
	static Logger log = Logger.getLogger("TableElementComparator.java");

	public int compare(Object o1, Object o2) {
		Element tag=(Element)o1;
		Element tag2=(Element)o2;
		List trs=tag.findAllElements("tr");
		List trs2=tag2.findAllElements("tr");
		if (trs.size()>trs2.size())
			return -1;
		else if (trs.size()<trs2.size())
			return 1;
		
		return 0;
	}
}

