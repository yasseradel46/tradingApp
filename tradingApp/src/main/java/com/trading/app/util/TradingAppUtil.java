package com.trading.app.util;

import java.beans.FeatureDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

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

	@Value("${app.email.encryptionAlgorithmLength}")
	private Integer encryptionAlgorithmLength;

	@Value("${app.email.encryptionPassword}")
	private String encryptionPassword;

	@Value("${app.sendsms.auth.token}")
	private String smsToken;

	@Value("${app.sendsms.accout.sid}")
	private String smsAccountSid;

	@Value("${app.sendsms.phoneNumber}")
	private String smsAppPhoneNumber;

	@Value("${app.sendsms.activationMsg}")
	private String smsActivationMsg;

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

	public void sendActivationSMS(String toNumber, String activationCode) {
		Twilio.init(smsAccountSid, smsToken);
		Message.creator(new PhoneNumber(toNumber), new PhoneNumber(smsAppPhoneNumber),
				smsActivationMsg + activationCode).create();
	}

	public String generateRandomPhoneActivation() {
		return String.valueOf(((int) (Math.random() * 9000) + 1000));
	}

	public String decryptEmail(String emailCode) {
		MessageCrypter = new MessageCrypter(encryptionPassword, encryptionAlgorithmLength, encryptionAlgorithm);
		return MessageCrypter.decrypt(emailCode);
	}

	private String buildEmailContent(String email) {
		MessageCrypter = new MessageCrypter(encryptionPassword, encryptionAlgorithmLength, encryptionAlgorithm);
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

	public String getLoggedInUserEmail() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = null;
		if (principal instanceof UserDetails) {
			email = ((UserDetails) principal).getUsername();
		} else {
			email = principal.toString();
		}
		return email;
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

	public String[] getIgnorePropertyNames(Object source, String... ignoreProperties) {
		List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties)
				: new ArrayList<String>());

		final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
		return Stream.of(wrappedSource.getPropertyDescriptors()).map(FeatureDescriptor::getName)
				.filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null
						|| ignoreList.contains(propertyName))
				.toArray(String[]::new);
	}
}
