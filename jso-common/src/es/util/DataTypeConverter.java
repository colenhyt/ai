/*
 * Created on 2005-9-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.util;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import es.Constants;


/**
 * @author Allenhuang
 * 
 * created on 2005-9-17
 */
public class DataTypeConverter {
    public static String readerToString(Reader reader) {
        CharArrayWriter writer = new CharArrayWriter();
        char[] chas = new char[1024];
        try {
            int charRead = 0;
            while (charRead >= 0) {
                writer.write(chas, 0, charRead);
                charRead = reader.read(chas);
            }
            reader.close();
            return writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    

    public static String streamToString(InputStream in) {
        return streamToString(in, Constants.CHARTSET_DEFAULT);
    }

    public static String streamToString(InputStream in, String charset) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(10240);
        byte[] buf = new byte[1024];
        try {
            int bytesRead = 0;
            while (bytesRead >= 0) {
                baos.write(buf, 0, bytesRead);
                bytesRead = in.read(buf);
            }

            byte[] content = baos.toByteArray();
            return new String(content, charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}