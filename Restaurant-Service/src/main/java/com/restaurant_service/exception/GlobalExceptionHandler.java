package com.restaurant_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    /* Resource Not Found*/
    /*
     * Handle ResourceNotFoundException
     *
     * Returns:
     * 404 NOT FOUND
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex)
    {
        // Create response map
        Map<String, Object> response = new HashMap<>();

        // Add response details
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", "ResourceNotFound");
        response.put("message", ex.getMessage());

        // Return response with 404 status
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /* Duplicate Resource*/
    /*
     * Handle DuplicateResourceException
     *
     * Returns:
     * 409 CONFLICT
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateResourceException(DuplicateResourceException ex)
    {
        // Create response map
        Map<String, Object> response = new HashMap<>();

        // Add response details
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.CONFLICT.value());
        response.put("error", "Duplicate Resource");
        response.put("message", ex.getMessage());

        // Return response with 409 status
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /* Bad Request*/
    /*
     * Handle BadRequestException
     *
     * Returns:
     * 400 BAD REQUEST
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequestException(BadRequestException ex)
    {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Bad Request");
        response.put("message", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /* Handle Generic Exceptions
    * This handles all unexpected exceptions
    * Returns: 500 INTERNAL SERVER ERROR*/
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalExceptions(Exception ex)
    {
        //Create response map
        Map<String, Object> response = new HashMap<>();

        //Add response details
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "Internal Server Error");

        //Return response with 500 status
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* Validation Errors*/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex)
    {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Validation Failed");
        response.put("message", ex.getBindingResult().getFieldError().getDefaultMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Exception handler for malformed or invalid HTTP request bodies
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex)
    {
        // Initialize response map to hold error details
        Map<String, Object> response = new HashMap<>();

        //Add current timestamp when error occurred
        response.put("timestamp", LocalDateTime.now());

        //Add HTTP status code (400 BAD_REQUEST)
        response.put("status", HttpStatus.BAD_REQUEST);

        //Add error type/category
        response.put("error", "Invalid Request Payload");

        //Add detailed error message from the root cause exception
        response.put("message", ex.getMostSpecificCause().getMessage());

        //Return error response with BAD_REQUEST status code
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }
}
