package exceptions;

public class MandatoryFieldException extends Exception {
	private static final long serialVersionUID = 8101036937806616334L;

	public MandatoryFieldException() {
		super();
	}
	
	public MandatoryFieldException(String message) {
		super(message);
	}

}
