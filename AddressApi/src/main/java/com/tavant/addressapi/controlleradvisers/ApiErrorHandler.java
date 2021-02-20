package com.tavant.addressapi.controlleradvisers;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.tavant.addressapi.errorresponse.BadCredentialsResponse;
import com.tavant.addressapi.errorresponse.ValidationErrorResponse;
import com.tavant.addressapi.exceptions.DuplicateEmailException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiErrorHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ex.printStackTrace();
		List<ValidationErrorResponse> errorResponses = ValidationErrorResponse
				.getValidationErrorResponse(ex.getFieldErrors());

		return new ResponseEntity<Object>(errorResponses, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(DuplicateEmailException.class)
	public ResponseEntity<Object> handleDataIntegrityViolationException(DuplicateEmailException ex,
			WebRequest request) {
		ex.getLocalizedMessage();
		ValidationErrorResponse errorResponse = new ValidationErrorResponse("User", "email",

				ex.getRejectedValue(), ex.getMessage());
		return new ResponseEntity<Object>(Arrays.asList(errorResponse), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {

		BadCredentialsResponse errorResponse = new BadCredentialsResponse();
		return new ResponseEntity<Object>(Arrays.asList(errorResponse), HttpStatus.BAD_REQUEST);
	}

}
