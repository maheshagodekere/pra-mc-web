package com.pradeya.cast.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtil {
	public static final String DATE_FORMAT = "dd/MM/yy HH:mm:ss a, z";
	public static final String DATE_FORMAT_APPEND = " 00:00:00 AM, IST";

	//public static final String  greaterThan=">";
	public static Date getCurrentDate() {
		long ms = System.currentTimeMillis();
		Date current = new Date();
		current.setTime(ms);
		return current;
	}

	public static Date getParsedDate(String date) {
		SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
		Date pdate = null;
		if(date.indexOf(":") == -1)
			date = date + DATE_FORMAT_APPEND;
		try {
			pdate = df.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pdate;
	}

	public static void main(String[] args) {
		TestUtil t = new TestUtil();
//		System.out.println("-----" + TestUtil.getCurrentDate());
//		System.out.println("-----" + TestUtil.getParsedDate("12/4/13"));
	    String txt="100000@fp.domain.in";
	    
	    System.out.println("-----" +t.getUser(txt));
	}
	

	public boolean isUsernameVaid(String username){
		if(username.split("\\@").length==2){
			return username.split("\\@")[1].split("\\.").length>=3;
		}
		return false;
	}

	public String getWhichUser(String username){
		
		return username.split("\\@")[1].split("\\.")[0];
	}
	
	public String getUserDomain(String username){
		return  username.split("\\@")[1].split("\\.", username.split("\\@")[1].indexOf("."))[1];
	}
	public static String getUser(String username) {
		return username.split("\\@")[0];
	}
}