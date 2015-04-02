package es.security;

import junit.framework.TestCase;

public class DesSecurityTest extends TestCase {
	DesSecurity test=new DesSecurity();
	
	public void test1(){
		String a="xiao38";
		String b=test.encrypt(a);
		System.out.println(b);
		System.out.println(test.decrypt(test.encrypt(a)));
	}

}
