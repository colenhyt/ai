package easyshop.helper;
import junit.framework.TestCase;

import org.apache.log4j.Logger;

import es.util.pattern.ESPattern;



/*
 ** JSO1.0, by Allen Huang,2007-5-29
 */
public class GetCJKValueHelperTest extends TestCase {
	static Logger log = Logger.getLogger("GetCJKValueHelperTest.java");
	GetCJKValueHelper helper=new GetCJKValueHelper();
	public void testFindTelStrString() {
		String pattern="([+]{0,1}[\\d]{2,3}[-\\s]{0,1}){0,1}[\\d]{3,4}[-\\s][\\d]{7,11}";
		pattern=GetCJKValueHelper.PATTERN_TEL_ALL;
		assertTrue(ESPattern.matches(pattern,"0574-87592025"));
		assertTrue(ESPattern.matches(pattern,"086-0574-8759205"));
		assertTrue(ESPattern.matches(pattern,"86-0574-87592025"));
		assertTrue(ESPattern.matches(pattern,"0574-87592025"));
		assertTrue(ESPattern.matches(pattern,"+86-21-87592025"));
		assertTrue(ESPattern.matches(pattern,"+86-0574-87592025"));
		assertTrue(ESPattern.matches(pattern,"+86-0574-87592025222"));
		assertTrue(ESPattern.matches(pattern,"086-0574-87592025"));
		assertTrue(ESPattern.matches(pattern,"86 0755 28112628"));
		assertTrue(ESPattern.matches(pattern,"86 0755 28112628111"));
//		assertTrue(ESPattern.matches(pattern,"0574-87592025"));
//		assertTrue(ESPattern.matches(pattern,"574-87592025"));
//		assertTrue(ESPattern.matches(pattern,"86-0574-87592025-01"));
//		assertTrue(ESPattern.matches(pattern,"86-0574-87592025-0"));
	}

	public void testFindFaxStrString() {
		fail("Not yet implemented");
	}

	public void testFindEmailStr() {
		assertTrue(ESPattern.matches(helper.PATTERN_EMAIL,"service@wood365.cn"));
		assertTrue(ESPattern.matches(helper.PATTERN_EMAIL,"allen@163.com"));
		assertTrue(ESPattern.matches(helper.PATTERN_EMAIL,"allen123@gmail.com"));
		assertTrue(!ESPattern.matches(helper.PATTERN_EMAIL,"all黄123@gmail.com"));
	}

	public void testFindPostCodeStr() {
		assertTrue(ESPattern.matches(helper.PATTERN_POSTCODE,"062151"));
	}

	public void testFindMobileStr() {
		assertTrue(ESPattern.matches(helper.PATTERN_MOBILE,"13858303317"));
		assertTrue(ESPattern.matches(helper.PATTERN_MOBILE,"15958303317"));
		assertTrue(!ESPattern.matches(helper.PATTERN_MOBILE,"138583317"));
		assertTrue(!ESPattern.matches(helper.PATTERN_MOBILE,"159583033171"));
		assertTrue(!ESPattern.matches(helper.PATTERN_MOBILE,"12858303317"));
		assertTrue(!ESPattern.matches(helper.PATTERN_MOBILE,"138583033171"));
		assertTrue(ESPattern.matches(helper.PATTERN_MOBILE,"15858303317"));
	}
}

