package org.fleet.modules.deve.autocode.generate.util;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;

public class NonceUtils {
	private static final SimpleDateFormat INTERNATE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	private static final String[] SPACES = new String[]{"0", "00", "0000", "00000000"};

	private static Date lastTime;

	private static int counter = 0;

	public static String randomString(int length) {
		return RandomStringUtils.randomAlphanumeric(length);
	}

	public static int randomInt() {
		return (new SecureRandom()).nextInt();
	}

	public static String randomHexInt() {
		return Integer.toHexString(randomInt());
	}

	public static long randomLong() {
		return (new SecureRandom()).nextLong();
	}

	public static String randomHexLong() {
		return Long.toHexString(randomLong());
	}

	public static String randomUUID() {
		return UUID.randomUUID().toString();
	}

	public static String currentTimestamp() {
		Date now = new Date();
		return INTERNATE_DATE_FORMAT.format(now);
	}

	public static long currentMills() {
		return System.currentTimeMillis();
	}

	public static String currentHexMills() {
		return Long.toHexString(currentMills());
	}

	public static synchronized String getCounter() {
		Date currentTime = new Date();

		if (currentTime.equals(lastTime)) {
			counter++;
		} else {
			lastTime = currentTime;
			counter = 0;
		}
		return Integer.toHexString(counter);
	}

	public static String format(String source, int length) {
		int spaceLength = length - source.length();
		StringBuilder buf = new StringBuilder();

		while (spaceLength >= 8) {
			buf.append(SPACES[3]);
			spaceLength -= 8;
		}

		for (int i = 2; i >= 0; i--) {
			if ((spaceLength & 1 << i) != 0) {
				buf.append(SPACES[i]);
			}
		}

		buf.append(source);
		return buf.toString();
	}

	public static void main(String[] args) throws IOException {
		System.out.println(randomLong() + currentMills());
	}
}
