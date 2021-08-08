package cz.kojotak.arx.util;

import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/** @see https://newbedev.com/printing-thread-name-using-java-util-logging */
public class ArxLogFormatter extends SimpleFormatter {
	
    private static final MessageFormat messageFormat = new MessageFormat("{3,date,hh:mm:ss} [{2}] {0}: {4}\n");

	@Override
	public String format(LogRecord record) {
		Object[] arguments = new Object[6];
		arguments[0] = stripPackageName(record.getLoggerName());
		arguments[1] = record.getLevel();
		arguments[2] = Thread.currentThread().getName();
		arguments[3] = new Date(record.getMillis());
		arguments[4] = record.getMessage();
		arguments[5] = record.getSourceMethodName();
		return messageFormat.format(arguments);
	}
	
	String stripPackageName(String loggerName) {
		if(loggerName!=null) {
			int lastDot = loggerName.lastIndexOf(".");
			if(lastDot>0 && lastDot< loggerName.length()) {
				return loggerName.substring(lastDot+1);
			}
		}
		return loggerName;
	}
}
