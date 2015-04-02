package cl.util.mail;
import java.util.Properties;


/*
 ** JSO1.0, by Allen Huang,2007-7-4
 */
public interface MailBox {

	public String getFrom();

	public String getHost();

	public Properties getProperties();

	public String getPwd();

	public String getCharSet();

	public String getSmtpStr();

}

