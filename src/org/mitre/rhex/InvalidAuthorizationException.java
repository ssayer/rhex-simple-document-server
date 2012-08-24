package org.mitre.rhex;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidAuthorizationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3829671531715431310L;

}
