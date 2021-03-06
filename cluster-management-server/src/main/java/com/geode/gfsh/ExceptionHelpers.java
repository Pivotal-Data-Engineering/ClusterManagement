package com.geode.gfsh;

import org.apache.geode.LogWriter;
import org.apache.geode.cache.execute.ResultSender;

/**
 * Packages an exception and ships it back to the caller
 * @author wwilliams
 *
 */
public class ExceptionHelpers {
  public static void sendStrippedException(
    	final ResultSender<Object> resultSender, 
    	final Exception exception,
    	final LogWriter logWriter) {
    RuntimeException serializableException = new RuntimeException(exception.getMessage());
    serializableException.setStackTrace(exception.getStackTrace());
    if (logWriter != null)
    	logWriter.error(exception.getMessage());
    resultSender.lastResult(serializableException.toString());
  }
}
