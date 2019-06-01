package com.trading.app.security;

import org.springframework.security.core.AuthenticationException;

public class TradingAppAuthenticationException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public TradingAppAuthenticationException(String msg) {
		super(msg);
	}

}
