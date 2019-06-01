package com.trading.app.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.app.util.TradingAppException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import static com.trading.app.util.TradingAppConstant.HEADER_STRING;
import static com.trading.app.util.TradingAppConstant.SECRET;
import static com.trading.app.util.TradingAppConstant.TOKEN_PREFIX;
import static com.trading.app.util.TradingAppConstant.INVALID_TOKEN_MSG;;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	public JWTAuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		TradingAppException customException = null;
		try {
			String header = req.getHeader(HEADER_STRING);
			if (header == null || !header.startsWith(TOKEN_PREFIX)) {
				chain.doFilter(req, res);
				return;
			}
			UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(req, res);
		} catch (TokenExpiredException e) {
			customException = new TradingAppException(new Date(), HttpServletResponse.SC_UNAUTHORIZED,
					HttpStatus.UNAUTHORIZED.name(), e.getMessage());
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			res.getWriter().write(convertObjectToJson(customException));
		} catch (Exception e) {
			customException = new TradingAppException(new Date(), HttpServletResponse.SC_UNAUTHORIZED,
					HttpStatus.UNAUTHORIZED.name(), INVALID_TOKEN_MSG);
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			res.getWriter().write(convertObjectToJson(customException));
		}

	}

	private String convertObjectToJson(Object object) {
		if (object == null) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		String jsonResponse = null;
		try {
			jsonResponse = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonResponse;
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if (token != null) {
			String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, "")).getSubject();
			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
			}
			return null;
		}
		return null;
	}
}
