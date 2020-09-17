package BurgerJointsInTartu.Services.aws;

import com.amazonaws.AmazonServiceException;

public class ApiGatewayException extends AmazonServiceException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7608637507416742457L;

	public ApiGatewayException(String errorMessage) {
        super(errorMessage);
    }

    public ApiGatewayException(String errorMessage, Exception cause) {
        super(errorMessage, cause);
    }
}
