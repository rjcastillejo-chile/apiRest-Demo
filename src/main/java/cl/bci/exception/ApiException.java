package cl.bci.exception;

public class ApiException extends Exception {

	private static final long serialVersionUID = 5698255678606708916L;
	private String uri;
	private String returnCode; 

	public ApiException() {
		super();
	}

	public ApiException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ApiException(String arg0) {
		super(arg0);
	}

	public ApiException(Throwable arg0) {
		super(arg0);
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public String getUri() {
		return uri;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	
}
