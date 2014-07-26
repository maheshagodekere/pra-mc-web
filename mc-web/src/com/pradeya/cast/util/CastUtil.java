package com.pradeya.cast.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;


public class CastUtil {

	public static final String ID = "_id";
	public static final String WHO = "who";
	public static final String ROLE = "role";

	public static final String ORGANIZATION = "organization";
	public static final String ORG_NAME = "orgName";
	public static final String FINANCIAL_PLANNER = "financialPlanner";
	public static final String ORGANIZATION_DETAIL = "detail";

	public static final String CLIENT = "client";
	public static final String CLIENT_DETAIL = "clientDetail";
	public static final String REFERENCE = "reference";
	public static final String APPOINTMENT = "appointment";
	public static final String MEMBER = "member";
	public static final String GOAL = "goal";
	public static final String PROFILE = "profile";
	
	public static final String ROLE_CLIENT = "ROLE_CLIENT";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_FP_ADMIN = "ROLE_FP_ADMIN";
	public static final String ROLE_FP_USER = "ROLE_FP_USER";
	
	public static final String DATE_FORMAT = "dd/MM/yy HH:mm:ss a, z";
	public static final String DATE_FORMAT_APPEND = " 00:00:00 AM, IST";
	
	//Mediacast Server details
	//@Value("${mediacast.server.ip}")
	public static String SERVER_IP = "127.0.0.1";
	
	//@Value("${mediacast.server.port}")
	public static String SERVER_PORT = "8080";

    //Mediacast Server url
//    public static String WEB_SERVER_URL = "http://"+SERVER_IP+":"+SERVER_PORT+"/mc-web";
//    public static String WEBSERVICE_SERVER_URL = "http://"+SERVER_IP+":"+SERVER_PORT+"/mc-webservice";



	public static boolean isUsernameVaid(String username) {
		if (username.split("\\@").length == 2) {
			return username.split("\\@")[1].split("\\.").length >= 3;
		}
		return false;
	}

	public static String getWhichUser(String username) {

		return username.split("\\@")[1].split("\\.")[0];
	}

	public static String getUserDomain(String username) {
		return username.split("\\@")[1].split("\\.",
				username.split("\\@")[1].indexOf("."))[1];
	}
	
	public static String getUser(String username) {
		return username.split("\\@")[0];
	}

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
//		System.out.println("-----" + CastUtil.getCurrentDate());
//		System.out.println("-----" + CastUtil.getParsedDate("12/4/13"));

		System.out.println("-----isUsernameVaid: " + isUsernameVaid("admin@mc.pradeya.com"));
		System.out.println("-----getWhichUser: " + getWhichUser("admin@mc.pradeya.com"));
		System.out.println("-----getUserDomain: " + getUserDomain("admin@mc.pradeya.com"));
		System.out.println("-----getUser: " + getUser("admin@mc.pradeya.com"));
		
		
	}
	
}
