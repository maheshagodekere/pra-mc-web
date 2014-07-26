package com.pradeya.cast.ui.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.pradeya.cast.service.LoginService;

@Component
@ManagedBean
public class LoginUIBean implements Serializable {

	public static final String ANONYMOUS_USER = "anonymousUser";

	@Autowired
	LoginService loginService;

	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String logout() {
		return "logout.xhtml";
	}

	public boolean getLoggedIn() {
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			if (ANONYMOUS_USER.equalsIgnoreCase(SecurityContextHolder
					.getContext().getAuthentication().getName()))
				return false;
		}
		return true;
	}

	public void setLoggedIn(boolean dummy) {
	}

	
	//public String login() throws java.io.IOException {
//	public String loginFP() throws java.io.IOException {
//		return login(LoginService.LOGIN_TYPE_FP);
//	}
//
//	public String loginClient() throws java.io.IOException {
//		return login(LoginService.LOGIN_TYPE_CLIENT);
//	}

	public String login() {
		try {

			if (loginService.authenticate(this.getUsername(),
					this.getPassword()))
				return "upload";
		} catch (AuthenticationException ex) {
			System.out.println("Login Failed on AuthenticationException");
			System.out.println(ex.getMessage());
			FacesContext.getCurrentInstance()
					.addMessage(
							"formLogin",
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"Login Failed",
									"User Name and Password Not Match!"));
		}
		return "upload";
	}

}
