package cl.util.mail;
import java.util.Properties;

import org.apache.log4j.Logger;



/*
 ** JSO1.0, by Allen Huang,2007-7-4
 */
public class SoGouMailBox implements MailBox{
	static Logger log = Logger.getLogger("SoGouMailBox.java");
	private final String host,from,pwd,smtpStr,charSet;
	private final Properties properties;
	
	public SoGouMailBox(){
		host="smtp.mail.sogou.com";
		from="jarso@jarso.cn";
		pwd="jarso0418";
		charSet="gbk";
		smtpStr="smtp";
		properties=System.getProperties(); 
		properties.put("mail.smtp.host",host); 
		properties.put("mail.smtp.auth","true"); 
		properties.put("mail.from",from);
		properties.put("mail.smtp.starttls.enable","true"); 
		properties.put("mail.smtp.port", "25"); 
		
	}

	public String getFrom() {
		return from;
	}

	public String getHost() {
		return host;
	}

	public Properties getProperties() {
		return properties;
	}

	public String getPwd() {
		return pwd;
	}

	public String getCharSet() {
		return charSet;
	}

	public String getSmtpStr() {
		return smtpStr;
	}

	public int getPort() {
		return 25;
	}

}

