package tn.springmvc_mongodb.web.exceptions;

public class FailureResponse {

	public String method;
	public String uri;
	public String reason;

	public FailureResponse(String method, String uri, String message) {
		super();
		this.method = method;
		this.uri = uri;
		this.reason = message;
	}

	public FailureResponse(String url, String message) {
		super();
		this.uri = url;
		this.reason = message;
	}

	public FailureResponse(String message) {
		super();
		this.reason = message;
	}

}
