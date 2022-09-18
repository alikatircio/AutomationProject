/**
 * 
 */
package com.test.project.utilites;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class LoggerManager {

	private static Logger logger = LogManager.getLogger(LoggerManager.class);

	public static void info(String log) {
		logger.info(log);
	}

}
