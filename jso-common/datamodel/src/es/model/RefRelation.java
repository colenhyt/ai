package es.model;
import java.util.Set;

import org.apache.log4j.Logger;

import es.webref.model.PageRef;



/*
 ** JSO1.0, by Allen Huang,2007-6-5
 */
public class RefRelation {
	static Logger log = Logger.getLogger("RefRelation.java");
	private final Set<PageRef> refs;
	private final String relation;
	
	public RefRelation(String rel,Set<PageRef> _refs){
		relation=rel;
		refs=_refs;
	}
	
	public Set<PageRef> getRefs() {
		return refs;
	}
	
	public String getRelation() {
		return relation;
	}

}

