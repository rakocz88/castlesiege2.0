package com.pilaf.cs.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.http.HTTPException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class AbstractRestController {

	@ExceptionHandler({ Exception.class })
	public void handleException(HttpServletRequest request, HttpServletResponse response, Exception ex)
			throws IOException {
		if (ex instanceof HTTPException) {
			response.sendError(((HTTPException) ex).getStatusCode(), ex.getMessage());
			return;
		} else if (ex instanceof AccessDeniedException) {
			response.sendError(HttpStatus.FORBIDDEN.value(), ex.getMessage());
			return;
		} else {
			response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
			return;
		}
	}

}
