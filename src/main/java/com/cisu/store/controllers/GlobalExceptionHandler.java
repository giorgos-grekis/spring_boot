package com.cisu.store.controllers;


import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle invalid body
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleUnreadableMessage(
            HttpMessageNotReadableException ex) {
        // Init  general message
        String errorMessage = "Invalid request body format or type error.";

        // We check if there is a 'cause' to get more details. Jackson (JsonMappingException)
        Throwable rootCause = ex.getRootCause();

        if (rootCause instanceof InvalidFormatException) {
            // Handle error and display right message
            InvalidFormatException ife = (InvalidFormatException) rootCause;

            // get the failed field
            String fieldName = ife.getPath().stream()
                    .map(reference -> reference.getFieldName())
                    .filter(java.util.Objects::nonNull)
                    .findFirst()
                    .orElse("N/A");

            // create a more specific message
            errorMessage = String.format(
                    "Invalid value '%s' for field '%s'. Expected type: %s",
                    ife.getValue(),
                    fieldName,
                    ife.getTargetType().getSimpleName()
            );

        } else if (rootCause != null) {
            // If the root is something else (e.g., JsonParseException), we use its message.
            errorMessage = "Malformed JSON: " + rootCause.getLocalizedMessage();
        }


        return ResponseEntity.badRequest().body(
                Map.of("error", errorMessage)
        );
    }

    // Handle @Valid validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException exception
    ) {
        var errors = new HashMap<String, String>();

        exception.getBindingResult()
                .getAllErrors()
                .forEach((error) -> {
                    String fieldName = error instanceof FieldError
                            ? ((FieldError) error).getField()
                            : error.getObjectName();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        return ResponseEntity.badRequest().body(errors);
    }

    // Handle invalid UUID in @PathVariable or @RequestParam
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        if (ex.getRequiredType() != null && ex.getRequiredType().equals(UUID.class)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Cart was not found."));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Invalid request parameter."));
    }

}
