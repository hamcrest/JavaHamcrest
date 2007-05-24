package org.hamcrest.text.pattern;

public class PatternMatchException extends Exception {
    private static final long serialVersionUID = 1L;

    public PatternMatchException(String message) {
	super(message);
    }

    public PatternMatchException(String message, Throwable cause) {
	super(message, cause);
    }
}
