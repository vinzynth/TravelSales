package at.tug.oad.travelsales.utils;

/**
 * @author Leopold Christian - 1331948
 * 23.11.2014 - 23:04:09
 * 
 */
public class TravelSalesException extends RuntimeException{

	private static final long serialVersionUID = -3646062772812677822L;
	
	private TSExceptionType type;
	
	/**
	 * @param type
	 * @param message
	 */
	public TravelSalesException(final TSExceptionType type, final String message) {
		super(message);
		this.type = type;
	}
	
	/**
	 * @param type
	 * @param message
	 * @param throwable
	 */
	public TravelSalesException(final TSExceptionType type, final String message, final Throwable throwable) {
		super(message, throwable);
		this.type = type;
	}
	
	/**
	 * @return the type
	 */
	public TSExceptionType getType() {
		return type;
	}
}
