package waes.differ.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception threw when decoded data from left or right side is not a valid json.
 *
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Data is not a valid Json string")  // 404
public class NotValidJsonException extends RuntimeException {
	private static final long serialVersionUID = 2L;	
}
