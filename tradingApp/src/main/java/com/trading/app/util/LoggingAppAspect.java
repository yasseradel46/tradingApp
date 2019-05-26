package com.trading.app.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import static com.trading.app.util.TradingAppConstant.CONTROLLER_BASE_PACKAGE;;

@Aspect
@Configuration
public class LoggingAppAspect {
	private Logger logger = LoggerFactory.getLogger(LoggingAppAspect.class);

	@Autowired
	private TradingAppUtil tradingUtil;

	@AfterReturning(pointcut = "execution(* " + CONTROLLER_BASE_PACKAGE + ".*.*(..))", returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		logger.info("{} Api returned with payload {}", joinPoint.getSignature().getName(),
				tradingUtil.convertObjectToString(result));
	}

}
