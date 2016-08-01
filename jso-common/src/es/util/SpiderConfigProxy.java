/*
 * Created on 2005-10-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import es.util.FileUtil;

/**
 * @author Allenhuang
 *
 * created on 2005-10-12
 */
public class SpiderConfigProxy {
    static SpiderContext context;
    
    private static Properties props(){
        String propsFile="config/spider.properties";
        Properties props = null;
        try
        {
        	FileInputStream propsIn = null;
    		URL  res = Thread.currentThread().getContextClassLoader().getResource("/");
			if (res!=null){
				propsIn = new FileInputStream(res.getPath()+propsFile);
			}else {
				propsIn = new FileInputStream(propsFile);
			}
            props = new Properties();
            props.load(propsIn);
            propsIn.close();
        }
        catch(FileNotFoundException fnfe)
        {
            fnfe.printStackTrace();
            System.exit(1);
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
            System.exit(1);
        }       
        return props;
    }
    
    public static SpiderContext getSpiderContext(){
        if (context==null)
            context=new SpiderContext(props());
        return context;
    }

}
