package com.api.employee.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseObject> handleResourceNotFound(ResourceNotFoundException ex) {
        ErrorObject error = new ErrorObject();
        error.setCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        error.setMoreInfo("The requested resource was not found in the system.");

        ResponseObject response = new ResponseObject();
        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        response.setStatusMessage("FAILED");
        response.setErrorResponse(error);
        response.setServerResponseTime(System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseObject> handleBadRequest(IllegalArgumentException ex) {
        ErrorObject error = new ErrorObject();
        error.setCode(400);
        error.setMessage("Bad Request");
        error.setMoreInfo(ex.getMessage());

        ResponseObject response = new ResponseObject();
        response.setStatusCode(400);
        response.setStatusMessage("FAILURE");
        response.setErrorResponse(error);
        response.setServerResponseTime(System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
