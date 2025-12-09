package com.projectOne.Logger;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class LoggerService {

    private static final Logger logger = LogManager.getLogger("ProjectOneLogger");

    // Info Level Logging
    public void info(String message) {
        logger.info(message);
    }

    // Warn Level Logging
    public void warn(String message) {
        logger.warn(message);
    }

    // Error Level Logging
    public void error(String message, Throwable t) {
        logger.error(message, t);
    }

    // Debug Level Logging
    public void debug(String message) {
        logger.debug(message);
    }
}
