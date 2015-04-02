package cl.collection;
import java.util.Collection;

import org.apache.log4j.Logger;


/*
 ** JSO1.0, by Allen Huang,2007-6-10
 */
public class SizeCollection {
	static Logger log = Logger.getLogger("SizeCollection.java");
	private final int size;
	private final Collection collection;
	
	public SizeCollection(int _size,Collection _coll){
		size=_size;
		collection=_coll;
	}

	public Collection getCollection() {
		return collection;
	}

	public int size() {
		return size;
	}
}

