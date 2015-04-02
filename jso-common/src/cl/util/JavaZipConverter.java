/*
 * 创建日期 2006-12-30
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package cl.util;

import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * @author Allen Huang
 *
 * 2006-12-30
 */
public class JavaZipConverter {
    private static int maxByteSize=1000000;
    
    public static byte[] compress(byte[] input){
        if (input==null||input.length>maxByteSize)
            return null;
        
        // Compress the bytes
        byte[] output = new byte[input.length];
        Deflater compresser = new Deflater();
        compresser.setInput(input);
        compresser.finish();
        int compressedDataLength = compresser.deflate(output);
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        baos.write(output, 0, compressedDataLength);
        byte[] content = baos.toByteArray();
        return content;
    }

    public static byte[] decompress(byte[] output){
        if (output==null||output.length>maxByteSize)
            return null;
        // Decompress the bytes
        Inflater decompresser = new Inflater();
        decompresser.setInput(output);
        byte[] result = new byte[output.length*15];
        byte[] content=null;
        try {
            int resultLength = decompresser.inflate(result);
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            baos.write(result, 0, resultLength);
            content = baos.toByteArray();
        } catch (DataFormatException e) {
            // TODO 自动生成 catch 块
            e.printStackTrace();
        }
        decompresser.end();
        return content;
    }
}
