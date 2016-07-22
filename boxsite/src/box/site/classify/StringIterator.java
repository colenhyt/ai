package box.site.classify;

import java.util.ArrayList;
import java.util.Iterator;

import cc.mallet.types.Instance;

public class StringIterator implements Iterator<Instance>{
	ArrayList<StringBuffer> fileArray;
	Iterator<StringBuffer> subIterator;
	String targetName;
	
	public StringIterator(String string,String _targetName){
		this.fileArray = new ArrayList<StringBuffer>();
		this.fileArray.add(new StringBuffer(string));
		
		this.subIterator = fileArray.iterator();
		
		this.targetName = _targetName;
	}
	
	@Override
	public boolean hasNext() {
		return subIterator.hasNext();
	}

	@Override
	public Instance next() {
		StringBuffer nextStr = this.subIterator.next();
		return new Instance (nextStr, targetName, 0, null);
	}

	@Override
	public void remove() {
		throw new IllegalStateException ("This Iterator<Instance> does not support remove().");
		
	}

}
