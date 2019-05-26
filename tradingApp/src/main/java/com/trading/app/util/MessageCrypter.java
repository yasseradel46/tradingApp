package com.trading.app.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageCrypter {

	Logger logger = LoggerFactory.getLogger(MessageCrypter.class);
	private SecretKeySpec secretKey;
	private Cipher cipher;

	public MessageCrypter(String secret, int length, String algorithm) {
		try {
			byte[] key = new byte[length];
			key = fixSecret(secret, length);
			this.secretKey = new SecretKeySpec(key, algorithm);
			this.cipher = Cipher.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
		} catch (NoSuchPaddingException e) {
			logger.error(e.getMessage());
		} catch (UnsupportedEncodingException ex) {
			logger.error(ex.getMessage());
		}
	}

	private byte[] fixSecret(String s, int length) throws UnsupportedEncodingException {
		if (s.length() < length) {
			int missingLength = length - s.length();
			for (int i = 0; i < missingLength; i++) {
				s += " ";
			}
		}
		return s.substring(0, length).getBytes("UTF-8");
	}

	private static String encodeUsingDataTypeConverter(byte[] bytes) {
		return DatatypeConverter.printHexBinary(bytes);
	}

	private static byte[] decodeUsingDataTypeConverter(String hexString) {
		return DatatypeConverter.parseHexBinary(hexString);
	}

	public String encrypt(String email) {
		byte[] enc = new byte [16] ;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			enc = cipher.doFinal(email.getBytes());
		} catch (InvalidKeyException e) {
			logger.error(e.getMessage());
		} catch (IllegalBlockSizeException e) {
			logger.error(e.getMessage());
		} catch (BadPaddingException e) {
			logger.error(e.getMessage());
		}
		return encodeUsingDataTypeConverter(enc);
	}

	public String decrypt(String encryptedEmail) {
		byte[] decEmail = new byte [16] ;
		try {
			byte[] hexaEnc = decodeUsingDataTypeConverter(encryptedEmail);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			decEmail = cipher.doFinal(hexaEnc);
		} catch (InvalidKeyException e) {
			logger.error(e.getMessage());
		} catch (IllegalBlockSizeException e) {
			logger.error(e.getMessage());
		} catch (BadPaddingException e) {
			logger.error(e.getMessage());
		}
		return new String(decEmail);
	}
}