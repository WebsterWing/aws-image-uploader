package com.webster.awsimageupload.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="File is wrong content type")
public class BadFiletypeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
