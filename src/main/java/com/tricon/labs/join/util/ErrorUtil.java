package com.tricon.labs.join.util;

public class ErrorUtil {
	
	public static String formErrorMailBody(Exception e) {
		String stackTraceMessage = "stackTraceMessage \n";
		for (StackTraceElement stackTraceElement : e.getStackTrace()) {
			stackTraceMessage = stackTraceMessage + stackTraceElement.toString() + "\n";
		}
		return e.getMessage() + "\n stacktrace \n" + stackTraceMessage;
	}
	
	public static String stackTraceString(StackTraceElement[] stackTrace) {
		String stackTraceMessage = "stackTraceMessage \n";
		for (StackTraceElement stackTraceElement : stackTrace) {
			stackTraceMessage = stackTraceMessage + stackTraceElement.toString() + "\n";
		}
		return stackTraceMessage;
	}
	
}