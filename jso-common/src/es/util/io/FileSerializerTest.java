package es.util.io;

import java.io.File;

import junit.framework.TestCase;

public class FileSerializerTest extends TestCase {

	public void testGetDirectoryStringString() {
		File oriDirect=new File("g:/bkpictures/100");
		File oriDirect2=new File("g:/bkpicturesaa/100");
		System.out.println(oriDirect.canRead());
		System.out.println(oriDirect.canWrite());
		FileUtils.copyDirectory(oriDirect, oriDirect2,true);
	}

}
