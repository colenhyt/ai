package es.model;

import java.io.Serializable;

import bk.biz.memory.util.Cacheable;
import es.datamodel.ContentInfo;

public class SItem implements Serializable,Cacheable{
	private long id;
	private static final long serialVersionUID = 1L;

	public SItem(){
		
	}
	public SItem(long _id){
		id=_id;
	}
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
