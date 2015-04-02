package cl.util;

import java.util.HashSet;
import java.util.Set;

public class CollectionBridge {
	public  Set toSet(Object[] objs){
		Set newObjs=new HashSet();
		if (objs!=null){
		for (int i=0;i<objs.length;i++){
			newObjs.add(objs[i]);
		}
		}
		return newObjs;
	}
}
