package com.ml.solarsystem.exceptions;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

   @ExceptionHandler(ConstraintViolationException.class)
   public void springHandleConstraintViolation(HttpServletResponse response) throws IOException {
       response.sendError(HttpStatus.BAD_REQUEST.value());
   }

   
}