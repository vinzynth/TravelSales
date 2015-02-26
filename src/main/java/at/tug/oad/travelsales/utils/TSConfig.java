package at.tug.oad.travelsales.utils;

import at.chrl.nutils.configuration.ConfigUtil;
import at.chrl.nutils.configuration.Property;

/**
 * @author Leopold Christian - 1331948
 * 06.01.2015 - 12:57:39
 * 
 */
public class TSConfig {
	
	private TSConfig() {}
	
	static{
		ConfigUtil.loadAndExport(TSConfig.class);
	}
	
	@Property(key = "password.reset.expire.time", defaultValue = "5", description = "Password reset expire time in minutes")
	public static long PASSWORD_RESET_EXPIRE_TIME;
	
	@Property(key = "mail.smtp.host", defaultValue = "mail.vs-coding.com", description = "SMPT Host server")
	public static String SMPT_HOST;
	
	@Property(key = "mail.smtp.port", defaultValue = "587", description = "SMPT Port")
	public static int SMPT_PORT;
	
	@Property(key = "mail.smtp.auth", defaultValue = "true", description = "Enables SMPT authentification")
	public static String SMPT_AUTH;
	
	@Property(key = "mail.smtp.auth.starttls.enable", defaultValue = "false", description = "Enables STARTTLS")
	public static String SMPT_STARTTLS_ENABLE;
	
	@Property(key = "mail.smtp.sending.address", defaultValue = "xxx@xxx.xxx", description = "Sending Email Account")
	public static String SENDING_MAIL_ADDRESS;
	
	@Property(key = "mail.smtp.sending.address.password", defaultValue = "xxx", description = "Mail Account Password")
	public static String SENDING_MAIL_PASSWORD;

}
