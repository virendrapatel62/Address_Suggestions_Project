package com.tavant.addressapi.errorresponse;

import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorResponse extends ApiErrorResponse {
	private String objectName;
	private String field;
	private Object rejectedValue;
	private String defaultMessage;

	public static List<ValidationErrorResponse> getValidationErrorResponse(List<FieldError> fieldErrors) {
		List<ValidationErrorResponse> errorResponses = new ArrayList<ValidationErrorResponse>();

		fieldErrors.forEach(error -> {
			errorResponses.add(new ValidationErrorResponse(error.getObjectName(), error.getField(),
					error.getRejectedValue(), error.getDefaultMessage()));
		});
		
		return errorResponses;

	}
}
