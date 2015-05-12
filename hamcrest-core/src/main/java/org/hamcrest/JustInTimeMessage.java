package org.hamcrest;

/**
 * Wraps a function to deliver a string message just in time
 * allows failure messages to be calculated when assertions fail
 * rather than be provided up front
 */
public interface JustInTimeMessage {
	String getMessage();
}
