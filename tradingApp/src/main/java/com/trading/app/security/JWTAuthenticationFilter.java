package com.trading.app.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.app.dto.UserProfileDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.trading.app.util.TradingAppConstant.EXPIRATION_TIME;
import static com.trading.app.util.TradingAppConstant.SECRET;
import static com.trading.app.util.TradingAppConstant.TOKEN_PREFIX;
import static com.trading.app.util.TradingAppConstant.HEADER_STRING;

public class JWTAuthenticationFilter extends AuthFilter {

	private Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {
			UserProfileDTO creds = new ObjectMapper().readValue(req.getInputStream(), UserProfileDTO.class);
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));

		} catch (Exception e) {
			throw new TradingAppAuthenticationException(e.getMessage());
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		String token = JWT.create().withSubject(((User) auth.getPrincipal()).getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).sign(HMAC512(SECRET.getBytes()));
		Map<String, String> response = new HashMap<String, String>();
		response.put(HEADER_STRING, TOKEN_PREFIX + token);
		res.getWriter().write(convertObjectToString(response));
	}

	public String convertObjectToString(Object result) {
		String json = null;
		try {
			json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(result);
		} catch (JsonProcessingException e) {
			logger.error("Error Parsing API Response", e);
		}
		return json;
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write(failed.getMessage());
	}

}