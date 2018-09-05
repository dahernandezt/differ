package com.waes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No data for such Id")  // 404
public class IdNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;	
}
