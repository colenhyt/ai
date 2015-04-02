package es.webref.model;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

public class PageRefTest extends TestCase {
	public void test(){
		Set<JSPageRef> a=new HashSet<JSPageRef>();
		JSPageRef ref=new JSPageRef("ddd","aa");
		
		for (int i=0;i<2;i++)
			System.out.println(a.add(ref));
	}
}
