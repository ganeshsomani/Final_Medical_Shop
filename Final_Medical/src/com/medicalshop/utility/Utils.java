package com.medicalshop.utility;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * 
 * @author 
 *
 */
public class Utils {
	
	public static final SimpleDateFormat indianDateFormat = new SimpleDateFormat("dd/MM/yy");
	
	public static String getAlphaNumString(int lenalfa, int numlen ){
		String randomString="";
		randomString=genarateRandom(lenalfa,"abcdefghijklmnpqrstvwxyz");
		randomString=randomString+genarateRandom(numlen, "123456789");
		return randomString;
	}
	
	public static String  genarateRandom(int len, String chars){
        final int PW_LENGTH = len;
        Random rnd = new SecureRandom();
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < PW_LENGTH; i++)
        	randomString.append(chars.charAt(rnd.nextInt(chars.length())));
        return randomString.toString();
	}
	
	/**
	 * Generate random number.
	 */
	public static String generateRandomNumber(int len) {
//        String chars = "1234567890";
//        final int PW_LENGTH = len;
//        Random rnd = new SecureRandom();
//        StringBuilder pass = new StringBuilder();
//        for (int i = 0; i < PW_LENGTH; i++)
//            pass.append(chars.charAt(rnd.nextInt(chars.length())));
//        return pass.toString();
		int number = 0000000001 ;
		System.out.println(String.format("%010d", number));
		String chars = "1234567890";
        final int PW_LENGTH = len;
        Random rnd = new SecureRandom();
        StringBuilder pass = new StringBuilder();
        for (int i = 0; i < PW_LENGTH; i++)
            pass.append(chars.charAt(rnd.nextInt(chars.length())));
        	//System.out.println(pass);
        return pass.toString();
    }
	
	public static String randomStr(int length){
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(length);
	}
	
	public static Date addDays(Date date, Long days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, days.intValue());
		return c.getTime();
	}
}
