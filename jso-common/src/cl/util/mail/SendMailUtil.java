/*
 * 创建日期 2007-2-28
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */

package cl.util.mail;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;



/**
 * @author Philip Huang
 * 
 * 2007-2-28
 */

public class SendMailUtil extends Thread{
	private final MailBox mailBox;
	
	final String fromName,tto,ttitle,tcontent;
	
	public SendMailUtil(String _fromName,String target,String title,String content){
		this(new SoGouMailBox(),_fromName,target,title,content);
	}
	
	public SendMailUtil(MailBox mailBox,String _fromName,String target,String title,String content){
		this.mailBox=mailBox;
		fromName=_fromName;
		tto= target;
		 ttitle= title;
		 tcontent= content;
	}
	
	public void send()throws MessagingException{
		
		Session s=Session.getInstance(mailBox.getProperties());
//		s.setDebug(true);
		
		MimeMessage message=new MimeMessage(s);


		InternetAddress fromInter=new InternetAddress(mailBox.getFrom());
		try {
			fromInter.setPersonal(fromName);
		} catch (UnsupportedEncodingException e) {
			// log error here
			e.printStackTrace();
		}
		message.setFrom(fromInter);
		InternetAddress to=new InternetAddress(tto);
		message.setRecipient(Message.RecipientType.TO,to);
		message.setSubject(ttitle);
		message.setSentDate(new Date());
		

		
		BodyPart mdp=new MimeBodyPart();
		mdp.setContent(tcontent,"text/html;charset="+mailBox.getCharSet());
		Multipart mm=new MimeMultipart();

		mm.addBodyPart(mdp);
		message.setContent(mm);
		
		message.saveChanges();
		Transport transport=s.getTransport(mailBox.getSmtpStr());
		transport.connect(mailBox.getHost(),mailBox.getFrom(),mailBox.getPwd());
		transport.sendMessage(message,message.getAllRecipients());
		transport.close();
	}
	
	public void run(){
		try {
			send();
		} catch (MessagingException e) {
			// log error here
			e.printStackTrace();
		}
	}

}
