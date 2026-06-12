package com.auth.service.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


/*GlobalExceptionHandler
* Purpose: Centralized exception handling for the entire Spring Boot application
* Interview point: This is a common pattern in SB applications for handling cross-cutting concerns like exception handling,
logging, and validation.
* Benefits:
* 1. Single Responsibility: All exception handling logic is centralized in one place.
* 2. Helps to avoid repetitive try-catch blocks in every controller
* 3.Clean controller code
*/

//GLobalExceptionHandler uses @RestControllerAdvice annotation, which is a specialized version of @ControllerAdvice.
//GlobalException Handler handles exceptions thrown from all controllers in the entire Spring Boot application.
@RestControllerAdvice
public class GlobalExceptionHandler
{

    /*
     * Handles database constraint violation exceptions.
     *
     * Example:
     * - Duplicate email
     * - Duplicate username
     * - Unique constraint violation
     *
     * Whenever DataIntegrityViolationException occurs,
     * this method will automatically execute.
     */

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateException(DataIntegrityViolationException ex)
    {
        // Creating response body map
        Map<String, Object> response = new HashMap<>();
        // Adding current timestamp
        response.put("timestamp", LocalDateTime.now());
        // HTTP status code -> 409
        response.put("status", HttpStatus.CONFLICT.value());
        // Error title/message
        response.put("error", "Duplicate Entry");

        // Custom user-friendly message
        response.put("message", "Email or username already exists");


        /*
         * Returning response with:
         * Body  -> response map
         * Status -> 409 CONFLICT
         */
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /*
     * Generic Exception Handler
     *
     * Handles all remaining exceptions
     * which are not handled specifically.
     *
     * Example:
     * - NullPointerException
     * - ArithmeticException
     * - RuntimeException
     * - Any unknown exception
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex)
    {
        //Creating response body map
        Map<String, Object> response = new HashMap<>();

        //Adding timestamp
        response.put("timestamp", LocalDateTime.now());
        //Http status code -> 500
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        //Error title
        response.put("error", "Internal Server Error");
        //Actual exception message
        response.put("message", ex.getMessage());

        /*
         * Returning response with:
         * Body  -> response map
         * Status -> 500 INTERNAL SERVER ERROR
         */
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
