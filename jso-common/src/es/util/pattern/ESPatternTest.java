package es.util.pattern;

import junit.framework.TestCase;

public class ESPatternTest extends TestCase {

	public void testIsISBN() {
		System.out.println(ESPattern.isISBN("12929191"));
		System.out.println(ESPattern.isISBN("129291912"));
		System.out.println(ESPattern.isISBN("12929191-"));
		System.out.println(ESPattern.isISBN("12929191a."));
		System.out.println(ESPattern.isISBN("129d29191a."));
		System.out.println(ESPattern.isISBN("1292919111111"));
		System.out.println(ESPattern.isISBN("1292919133333f"));
	}

}
