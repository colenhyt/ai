package estest;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

public class TestCollection extends TestCase {

	public void test1(){
		Set<String> strs=new HashSet<String>();
		
		strs.add("aa");
		strs.add("bb");
		for (String o:strs){
			String a=o;
			System.out.println(a);
		}
	}
}
