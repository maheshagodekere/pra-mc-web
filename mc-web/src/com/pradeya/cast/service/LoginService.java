package com.pradeya.cast.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.pradeya.cast.domain.Organization;
import com.pradeya.cast.util.CastUtil;

@SuppressWarnings("serial")
@Service
public class LoginService {
	private static Logger L = LoggerFactory.getLogger(LoginService.class);

	public static final String LOGIN_TYPE_MEMBER = "mc";

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	OrganizationService organizationService;

	public boolean authenticate(String username, String password)
			throws AuthenticationException {

		Authentication req = null;

		if (!CastUtil.isUsernameVaid(username)) {
			L.warn("Username {}: no password provided", username);
			throw new BadCredentialsException("Please enter valid username");
		}

		if (!StringUtils.hasText(password)) {
			L.warn("Username {}: no password provided", username);
			throw new BadCredentialsException("Please enter password");
		}

		String userType = CastUtil.getWhichUser(username);

		if (LOGIN_TYPE_MEMBER.equalsIgnoreCase(userType)) {
			req = new MemberUsernamePasswordAuthenticationToken(username, password);
		} else {
			throw new BadCredentialsException("Invalid login");
		}
		Authentication result = authenticationManager.authenticate(req);
		L.warn("Login Success! {}", result.getPrincipal()
				+ " is authenticated: ", result.isAuthenticated());
		L.warn("Authorities: {}:",result.getAuthorities().toString());
		SecurityContextHolder.getContext().setAuthentication(result);

		String userDomain = CastUtil.getUserDomain(username);
		long orgId = organizationService.findOrganizationId(userDomain);
		Organization org= organizationService.findOrganizationDetail(orgId);
		
		try {
			RequestContextHolder.currentRequestAttributes().setAttribute(
					CastUtil.ID, orgId, RequestAttributes.SCOPE_SESSION);
			RequestContextHolder.currentRequestAttributes().setAttribute(
					CastUtil.ORG_NAME, org==null?"Orginazation":org.getDetail().getOrgName(), RequestAttributes.SCOPE_SESSION);
			RequestContextHolder.currentRequestAttributes().setAttribute(
					CastUtil.WHO, CastUtil.getUser(username), RequestAttributes.SCOPE_SESSION);
			RequestContextHolder.currentRequestAttributes().setAttribute(
					CastUtil.ROLE, result.getAuthorities().toString(), RequestAttributes.SCOPE_SESSION);
		} catch (IllegalStateException ise) {
			L.error("Exception encountered {}: ", ise);
			SecurityContextHolder.getContext().setAuthentication(null);
			return false;
		}
		return true;

	}
}
