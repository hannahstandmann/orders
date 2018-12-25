package org.smbaiwsy.rest.controller;

import org.smbaiwsy.exception.BadRequestException;
import org.smbaiwsy.exception.InsufficientPermissionsException;
import org.smbaiwsy.exception.NotFoundException;
import org.smbaiwsy.rest.dto.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller for handling Rest exceptions.
 * 
 * @author anamattuzzi-stojanovic
 */
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Used to build UNAUTHORIZED error response
	 * 
	 * @param ex the exception
	 * @param request the request
	 * @return error response specifying the error type and the message
	 */
	@ExceptionHandler(value = { BadCredentialsException.class })
	public ResponseEntity<ErrorResponse> handleBadCredentialsExceptionException(Exception ex, WebRequest request) {
		log.info("Authorization exception. Message: {}", ex.getMessage());
		HttpHeaders headers = buildHttpHeadersResponse();
		headers.set("WWW-Authenticate", "Bearer realm=order");
		return new ResponseEntity<ErrorResponse>(new ErrorResponse("CREDENTIALS_REJECTED", ex.getMessage()), headers,
				HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Handle not found exception.
	 *
	 * @param ex
	 *            the exception
	 * @param request
	 *            the request
	 * @return the error response specifying the error type and the message
	 */
	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorResponse handleNotFoundException(NotFoundException e) {
		log.info("Not found exception. Error code: {} message: {}", e.getErrorCode(), e.getMessage());
		return new ErrorResponse(e.getErrorCode(), e.getMessage());
	}

	/**
	 * Handle not found exception.
	 *
	 * @param ex
	 *            the exception
	 * @param request
	 *            the request
	 * @return the error response specifying the error type and the message
	 */
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleBadRequestException(BadRequestException e) {
		log.info("Not found exception. Error code: {} message: {}", e.getErrorCode(), e.getMessage());
		return new ErrorResponse(e.getErrorCode(), e.getMessage());
	}

	/**
	 * Handle not found exception.
	 *
	 * @param ex
	 *            the exception
	 * @param request
	 *            the request
	 * @return the error response specifying the error type and the message
	 */
	@ExceptionHandler
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public ErrorResponse handleInsufficientPermissionsException(InsufficientPermissionsException e) {
		log.info("Not found exception. Error code: {} message: {}", e.getErrorCode(), e.getMessage());
		return new ErrorResponse(e.getErrorCode(), e.getMessage());
	}
	
	/**
	 * Handle not found exception.
	 * @param badRequestException the incoming exception
	 * @return the error response
	 */
	@ExceptionHandler
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public ErrorResponse handleAccessDeniedException(AccessDeniedException accessDeniedException) {
	    log.info("Not found exception. Error code: {} message: {}", 403, accessDeniedException.getMessage());
	   	return new ErrorResponse( "FORBIDDEN", accessDeniedException.getMessage());
	}

	/**
	 * Handles any uncaught exception as INTERNAL_SERVER_ERROR.
	 *
	 * @param ex
	 *            the exception
	 * @param request
	 *            the request
	 * @return the error response specifying the error type and the message
	 */
	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorResponse handleApiException(Exception ex, WebRequest request) {
		log.error("Runtime exception. Message: {}", ex.getMessage());
		log.error("RuntimeException. StackTrace: ", ex);
		return new ErrorResponse("SERVER_ERROR", ex.getMessage());
	}
	/**
	 * creates the content type header
	 * @return the header
	 */
	private HttpHeaders buildHttpHeadersResponse() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

}