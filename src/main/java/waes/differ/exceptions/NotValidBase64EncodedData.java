package waes.differ.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception threw when data from left or right side is not encoded as base64 string.
 *
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Data is not a valid base64 encoded string")  // 404
public class NotValidBase64EncodedData extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;

}
