package at.tug.oad.travelsales.utils.mail;

import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import at.chrl.nutils.configuration.ConfigUtil;
import at.tug.oad.travelsales.model.IUser;
import at.tug.oad.travelsales.utils.TSConfig;


/**
 * @author Leopold Christian - 1331948
 * 06.01.2015 - 12:57:06
 * 
 */
public class MailService {

	private MailService() {
	}

	public void sendEmail(IUser user, String subject, String content) throws AddressException, MessagingException{
		Session session = Session.getDefaultInstance(ConfigUtil.getProperties(TSConfig.class));
		Message msg = new MimeMessage(session);
		
		msg.setFrom(new InternetAddress(TSConfig.SENDING_MAIL_ADDRESS));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getMailAddress()));
		msg.setSubject(subject);
		
		msg.setText(content);
		msg.setSentDate(new Date());
		
		
		Transport transport = session.getTransport("smtp");
		if(TSConfig.SMPT_PORT > 0)
			transport.connect(
					TSConfig.SMPT_HOST, 
					TSConfig.SMPT_PORT, 
					TSConfig.SENDING_MAIL_ADDRESS, 
					TSConfig.SENDING_MAIL_PASSWORD
			);
		else
			transport.connect(
					TSConfig.SMPT_HOST,
					TSConfig.SENDING_MAIL_ADDRESS, 
					TSConfig.SENDING_MAIL_PASSWORD
			);
		
		transport.sendMessage(msg, msg.getAllRecipients());
		transport.close();
	}
	
	
	private static class SingletonHolder {
		private static final MailService instance = new MailService();
	}

	public static MailService getInstance() {
		return SingletonHolder.instance;
	}
}
