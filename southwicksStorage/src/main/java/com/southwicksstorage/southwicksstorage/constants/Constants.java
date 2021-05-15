package com.southwicksstorage.southwicksstorage.constants;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class Constants {
	
	/* System Constants */
	
	/* User Constants */
	public static final String DEFAULT_PASSWORD = "Password123!";
	public static final String MANAGER_ROLE = "MANAGER";
	public static final String TEAM_MEMBER_ROLE = "TEAM_MEMBER";
	public static final String SYSTEM_USERNAME = "system";
	
	/* Modal Constants */
	public static final String SHOW_MODAL = "showModal";
	public static final String MODAL_TYPE = "modalType";
	public static final String MODAL_TITLE = "modalTitle";
	public static final String MODAL_MESSAGE = "modalMessage";
	
	/* Security Constants */
	public static final String NOT_LOGGED_IN = "anonymousUser";
	
	/* Entity Constants */
	public static final String ADDITIONAL_INFO_EXCEED = "Additional Information can not exceed 500 characters";
	
	/* Error messages */
	public static final String ERROR_500 = "Sorry about that! It seems like something went wrong on our end";
	
	/* Date / Time Formats */
	public static final String DATE_TIME_FORMAT = "MM/dd/yyyy hh:mm a";
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
	
	/* Audit Log */
	// In how many days should we delete the audit log entries?
	public static final long DELETE_AUDIT_ENTRIES = 7;
	public static final TemporalUnit DELETE_AUDIT_ENTRIES_TIME_UNIT = ChronoUnit.DAYS;
	
	/* Task Constants */
	public static final String DELETE_OLD_LOGS_TASK_NAME = "Delete Old Logs";
	public static final String UPDATE_ORDER_REPORT = "Update Order Report";
	
	/* Cron Schedule Constants */
	public static final String EVERY_HOUR = "0 0 0/1 * * ?";
	public static final String EVERY_FIFTEEN_MIN = "0 0/15 * * * ?";
	public static final String EVERY_MINUTE = "0 0/1 * * * ?";
	
	/* System Settings Default Constants */
	public static final String LOW_THRESHOLD_DEFAULT = "0.25";
	public static final String OUT_THRESHOLD_DEFAULT = "0.0";

}
