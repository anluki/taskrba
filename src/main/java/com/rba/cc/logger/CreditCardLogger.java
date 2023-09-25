package com.rba.cc.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreditCardLogger {
	
	private static Logger logger;
	
	private void initializeLogger(Class<?> clazz) {	 
		this.logger = LoggerFactory.getLogger(clazz);
	}
	
	public void logError(String message, Class<?> clazz) {
		initializeLogger(clazz);
		logger.error(message);
	}
	
	public void logInfo(String message, Class<?> clazz) {
		initializeLogger(clazz);
		logger.info(message);
	}
	
	public void logWarn(String message, Class<?> clazz) {
		initializeLogger(clazz);
		logger.warn(message);
	}

}
