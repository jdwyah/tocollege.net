package com.apress.progwt.server.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

public class CryptUtils {
	private static final Logger log = Logger.getLogger(CryptUtils.class);

	public static String hashString(String password) {
		String hashword = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(password.getBytes("UTF-8"));
			BigInteger hash = new BigInteger(1, md5.digest());
			hashword = hash.toString(16);

		} catch (NoSuchAlgorithmException nsae) {
			log.error(nsae);
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}

		return pad(hashword, 32, '0');
	}

	private static String pad(String s, int length, char pad) {
		StringBuffer buffer = new StringBuffer(s);
		while (buffer.length() < length) {
			buffer.insert(0, pad);
		}
		return buffer.toString();
	}
}
