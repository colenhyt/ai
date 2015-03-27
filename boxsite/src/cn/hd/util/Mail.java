package cn.hd.util;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import com.sun.mail.smtp.SMTPTransport;
public class Mail {
	private final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	private boolean mailUserName = true;
	private String host = null;
	private int port = 25;
	private boolean auth = true;
	private String username = null;
	private String password = null;	private String charset = null;	private String from = null;
	private Map<String, Object> config = null;
	private Session session = null;
	ThreadPoolExecutor executor = null;
	{
		executor = new ThreadPoolExecutor(1, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(5));
	}
	@SuppressWarnings("unchecked")
	public Mail(Map<String, Object> config) {
		host = config.get("mailServer")!=null ? config.get("mailServer").toString() :"";		try {			port = Integer.parseInt(config.get("mailPort").toString());		} catch (Exception e) {			port=25;		}
		if (port == 0) {
			port = 25;
		}
		if (!"是".equals(config.get("mailAuth"))) {
			auth = false;
		}
		username = config.get("mailUsername")!=null ? config.get("mailUsername").toString() :"";
		password = config.get("mailPwd")!=null ? config.get("mailPwd").toString() :"";		charset = config.get("mailCharset")!=null ? config.get("mailCharset").toString() :"";		from = config.get("mailFrom")!=null ? config.get("mailFrom").toString() :"";	}
	private synchronized void createSession() {
		Properties mailProps = new Properties();
		mailProps.setProperty("mail.transport.protocol", "smtp");
		mailProps.setProperty("mail.smtp.host", host);
		mailProps.setProperty("mail.smtp.port", String.valueOf(port));
		if ("smtp.gmail.com".equals(host)) {
			mailProps.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
			mailProps.setProperty("mail.smtp.socketFactory.fallback", "false");
			mailProps.setProperty("mail.smtp.socketFactory.port", String.valueOf(port));
		}
		if (auth) {
			mailProps.put("mail.smtp.auth", "true");
		}
		session = Session.getDefaultInstance(mailProps, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
	}
	private MimeMessage createMimeMessage() {
		if (session == null) {
			createSession();
		}
		return new MimeMessage(session);
	}	public boolean sendMessage(String toEmail, String subject, String body) {		return sendMessage(from, toEmail, subject, body);	}
	public boolean sendMessage(String from, String toEmail, String subject, String body) {
		try {
			subject = MimeUtility.encodeText((subject).replaceAll("[\r|\n]", ""),
					charset, "B");
			body = body.replaceAll("\n\r", "\r").replaceAll("\r\n", "\n").replaceAll("\r", "\n").replaceAll(
					"\n", "\r\n");
			String encoding = MimeUtility.mimeCharset(charset);
			String toEmails[] = toEmail.split(",");
			Address to[] = new Address[toEmails.length];
			for (int i = 0; i < toEmails.length; i++) {
				String sTo = toEmails[i];
				if (sTo.matches("^.*<.*>$")) {
					int index = sTo.indexOf("<");
					to[i] = new InternetAddress(sTo.substring(index + 1, sTo.length() - 1),
							mailUserName ? sTo.substring(0, index) : "", encoding);
				} else {
					to[i] = new InternetAddress(sTo, "", encoding);
				}
			}
			String fromName = "";
			String fromEmail;
			if (from.matches("^.*<.*>$")) {
				int index = from.indexOf("<");
				if (mailUserName) {
					fromName = from.substring(0, index);
				}
				fromEmail = from.substring(index + 1, from.length() - 1);
			} else {
				fromEmail = from;
			}
			Address fromAddress = new InternetAddress(fromEmail, fromName, encoding);
			MimeMessage message = createMimeMessage();
			message.setHeader("X-Priority", "3");
			message.setHeader("Date", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			message.setHeader("Content-Transfer-Encoding", "8bit");
			message.setRecipients(Message.RecipientType.TO, to);
			message.setFrom(fromAddress);
			message.setSubject(subject, encoding);
			MimeMultipart content = new MimeMultipart("alternative");
			if (body != null) {
				MimeBodyPart html = new MimeBodyPart();
				html.setContent(body, "text/html;charset=" + encoding);
				html.setDisposition(Part.INLINE);
				content.addBodyPart(html);
			}
			message.setContent(content);
			message.setDisposition(Part.INLINE);
			sendMessages(message);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	private void sendMessages(MimeMessage message) {
		Collection<MimeMessage> messages = Collections.singletonList(message);
		if (messages.size() == 0) {
			return;
		}
		executor.execute(new EmailTask(messages));
	}
	private class EmailTask implements Runnable {
		private Collection<MimeMessage> messages;
		private int timestamp;
		private String timeoffset;
		private String onlineIP;
		private int supe_uid;
		private String requestURI;
		public EmailTask(Collection<MimeMessage> messages) {
			this.messages = messages;
		}
		public void run() {
			try {
				sendMessages();
			} catch (MessagingException me) {
				String message = me.getMessage();
				if (message == null) {
					message = "";
				}
				message = "(" + host + ":" + port + ") 无法连接到邮件服务器"
						+ message.replace("\t", "");
			}
		}
		public void sendMessages() throws MessagingException {
			Transport transport = null;
			try {
				URLName url = new URLName("smtp", host, port, "", username, password);
				transport = new SMTPTransport(session, url);
				transport.connect(host, port, username, password);
				for (MimeMessage message : messages) {
					transport.sendMessage(message, message.getRecipients(MimeMessage.RecipientType.TO));
				}
			} finally {
				if (transport != null) {
					transport.close();
				}
			}
		}
	}
}