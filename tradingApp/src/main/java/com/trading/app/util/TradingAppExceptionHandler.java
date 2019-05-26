package com.trading.app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import static com.trading.app.util.TradingAppConstant.RESPONSE_EXCEPTION_CODE;
import static com.trading.app.util.TradingAppConstant.RESPONSE_EXCEPTION_MSG;
import com.trading.app.dto.ResponseDTO;

@ControllerAdvice
public class TradingAppExceptionHandler {
	Logger logger = LoggerFactory.getLogger(TradingAppExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseDTO<String>> globleExcpetionHandler(Exception ex, WebRequest request) {
		logger.error(request.getDescription(false), ex);
		ResponseDTO<String> response = new ResponseDTO<String>(RESPONSE_EXCEPTION_CODE, RESPONSE_EXCEPTION_MSG);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
