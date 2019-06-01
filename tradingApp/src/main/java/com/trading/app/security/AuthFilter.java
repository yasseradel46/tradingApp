package com.trading.app.security;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import static com.trading.app.util.TradingAppConstant.SIGN_IN_URL;

public class AuthFilter extends UsernamePasswordAuthenticationFilter {

	public AuthFilter() {
		super();
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(SIGN_IN_URL, "POST"));
	}
}
