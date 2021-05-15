/**
 * 
 */
package com.southwicksstorage.southwicksstorage.models.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kyle
 *
 */
public class LogTask {

	private Class<?> clazz;
	private String taskName;
	private Logger log;
	
	public LogTask(Class<?> clazz, String taskName) {
		this.log = LoggerFactory.getLogger(clazz);
		this.taskName = taskName;
	}
	
	/**
	 * Log start task message
	 */
	public void startTaskLog() {
		log.info("Starting task \"" + taskName + "\"");
	}
	
	/**
	 * Log end task message
	 */
	public void endTaskLog() {
		log.info("Finished \"" + taskName + "\"");
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

}
