package com.trading.app.util;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class TradingAppUtil {
	private Logger logger = LoggerFactory.getLogger(TradingAppUtil.class);

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private TemplateEngine templateEngine;

	@Value("${app.email.subject}")
	private String emailSubject;

	@Value("${app.name}")
	private String appName;

	@Value("${app.email.verificationApi}")
	private String verificationApi;

	@Value("${app.email.encryptionAlgorithm}")
	private String encryptionAlgorithm;

	@Value("${app.email.encryptionPassword}")
	private String encryptionPassword;

	private ObjectMapper mapper = new ObjectMapper();
	private MessageCrypter MessageCrypter = null;

	public void sendActivationEmail(String toEmail) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			messageHelper.setTo(toEmail);
			messageHelper.setSubject(appName + " " + emailSubject);
			mimeMessage.setContent(buildEmailContent(toEmail), "text/html");
		};
		try {
			emailSender.send(messagePreparator);
		} catch (MailException e) {
			logger.error("Error Parsing API Response", e);
		}
	}

	public String decryptEmail(String emailCode) {
		MessageCrypter = new MessageCrypter(encryptionPassword, 16, encryptionAlgorithm);
		return MessageCrypter.decrypt(emailCode);
	}

	private String buildEmailContent(String email) {
		MessageCrypter = new MessageCrypter(encryptionPassword, 16, encryptionAlgorithm);
		Context context = new Context();
		String verificationLink = verificationApi + MessageCrypter.encrypt(email);
		context.setVariable("appName", appName);
		context.setVariable("verificationLink", verificationLink);
		return templateEngine.process("mailTemplate", context);
	}

	public String convertObjectToString(Object result) {
		String json = null;
		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
		} catch (JsonProcessingException e) {
			logger.error("Error Parsing API Response", e);
		}
		return json;
	}

	public <T> T convertJsonToObject(String json, Class<T> classType) {
		T targetObject = null;
		try {
			targetObject = mapper.readValue(json, classType);
		} catch (IOException e) {
			logger.error("Error Parsing API Response", e);
		}
		return targetObject;
	}
}
