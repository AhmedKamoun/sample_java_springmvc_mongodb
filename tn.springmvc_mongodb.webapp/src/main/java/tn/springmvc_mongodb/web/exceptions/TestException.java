package tn.springmvc_mongodb.web.exceptions;

public class TestException extends RuntimeException {
	/**
	 * Unique ID for Serialized object
	 */
	private static final long serialVersionUID = 4657491283614755649L;

	public TestException(String msg) {
		super(msg);
	}

	public TestException(String msg, Throwable t) {
		super(msg, t);
	}
}
