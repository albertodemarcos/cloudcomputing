package es.uah.facturasprocesadorms.controllers;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import es.uah.facturasprocesadorms.infraestructure.utils.ErrorMessage;


@RestControllerAdvice
public class GlobalExceptionHandlerController {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage globalExceptionHandler(Exception ex, WebRequest request) {
		int error = HttpStatus.INTERNAL_SERVER_ERROR.value();
		ErrorMessage message = new ErrorMessage(error, new Date(), ex.getMessage(), request.getDescription(false));
		return message;
	}

}
