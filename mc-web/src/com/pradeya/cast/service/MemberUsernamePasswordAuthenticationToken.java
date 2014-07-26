package com.pradeya.cast.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@SuppressWarnings("serial")
public class MemberUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken{

	public MemberUsernamePasswordAuthenticationToken(Object principal,
			Object credentials) {
		super(principal, credentials);
		// TODO Auto-generated constructor stub
	}

}
