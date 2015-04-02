/*
 * Created on 2005-9-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.util;

import java.io.CharArrayReader;
import java.io.IOException;

import junit.framework.TestCase;

/**
 * @author Allenhuang
 *
 * created on 2005-9-17
 */
public class DataTypeConverterTest extends TestCase {

    public void testReaderToString() {
        char[] chas=new char[]{'1','b','d'};
        CharArrayReader reader=new CharArrayReader(chas);
        assertEquals("1bd",DataTypeConverter.readerToString(reader));
        CharArrayReader reader2=new CharArrayReader(chas);
        try {
            reader2.skip(5);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertEquals("1bd",DataTypeConverter.readerToString(reader2));        
    }

    /*
     * String streamToString 的正在测试的类(InputStream)
     */
    public void testStreamToStringInputStream() {
    }

    /*
     * String streamToString 的正在测试的类(InputStream, String)
     */
    public void testStreamToStringInputStreamString() {
    }

}
