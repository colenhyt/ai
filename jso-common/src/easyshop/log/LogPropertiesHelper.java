/*
 * Created on 2005-10-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package easyshop.log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Allenhuang
 *
 * created on 2005-10-26
 */
public class LogPropertiesHelper {
    
	public static final Properties getConfigProperties(String path) {
			Properties properties = new Properties();
			try {
                properties.load( getConfigStream(path) );
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
			return properties;

	}
	
	public static final InputStream getConfigStream(final String path)  {
			return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);

	}	
}
