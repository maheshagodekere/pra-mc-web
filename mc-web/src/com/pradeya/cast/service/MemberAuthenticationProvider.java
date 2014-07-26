package com.pradeya.cast.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pradeya.cast.domain.Organization;
import com.pradeya.cast.util.CastUtil;

/*
 Extend AbstractUserDetailsAuthenticationProvider when you want to
 prehandle authentication, as in throwing custom exception messages,
 checking status, etc. 
 */
@Component
public class MemberAuthenticationProvider extends
		AbstractUserDetailsAuthenticationProvider {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	OrganizationService organizationService;

	// @Autowired
	// private PasswordEncoder encoder;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication
				.equals(MemberUsernamePasswordAuthenticationToken.class);
	}

	@Override
	public UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {

		String password = (String) authentication.getCredentials();
		String user = CastUtil.getUser(username);
		String userDomain = CastUtil.getUserDomain(username);

		long orgId = organizationService.findOrganizationId(userDomain);

		if (orgId == 0) {
			logger.warn("Username {} user not found", username);
			throw new UsernameNotFoundException("Invalid Login");
		}
		
		Organization org = organizationService
				.findMember(orgId, user);
		
		if(org == null) throw new UsernameNotFoundException("Username user not found");
		
		String fpPassword = org.getMember().get(0).getPassword();
		
		if (!password.equalsIgnoreCase(fpPassword)) {
			logger.warn("Username {} : Invalid Login: ", username);
			throw new BadCredentialsException("Invalid Login");
		}

		// if (!encoder.matches(password, user.getPassword())) {
		// logger.warn("Username {} password {}: invalid password", username,
		// password);
		// throw new BadCredentialsException("Invalid Login");
		// }
		//
		// if
		// (!(UserAccountStatus.STATUS_APPROVED.name().equals(user.getStatus())))
		// {
		// logger.warn("Username {}: not approved", username);
		// throw new BadCredentialsException("User has not been approved");
		// }
		// if (!user.getEnabled()) {
		// logger.warn("Username {}: disabled", username);
		// throw new BadCredentialsException("User disabled");
		// }
		//
		// final List<GrantedAuthority> auths;
		// if (!user.getRoles().isEmpty()) {
		// auths = AuthorityUtils.commaSeparatedStringToAuthorityList(user
		// .getRolesCSV());
		// } else {
		// auths = AuthorityUtils.NO_AUTHORITIES;
		// }

		final List<GrantedAuthority> auths = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_ADMIN");
		return new User(username, password, true, // enabled
				true, // account not expired
				true, // credentials not expired
				true, // account not locked
				auths);
	}

}