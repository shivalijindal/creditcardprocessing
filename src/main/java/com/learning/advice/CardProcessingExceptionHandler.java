package com.learning.advice;

import com.learning.model.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Handles Exception thrown from the Controller
 */
@RestControllerAdvice
public class CardProcessingExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CardProcessingExceptionHandler.class);

    private static final String X_CORRELATION_ID = "X-Correlation-Id";

    /**
     * Handles IllegalArgumentException thrown during Card Validation
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { IllegalArgumentException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, null,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Overrides default implementation to handle all kinds of exceptions and return an Error Response.
     * @param ex
     * @param body
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOG.info("Failure Occured during Credit Processing: ", ex);
        Error error = new Error();
        error.reasonCode("1"+status.value())
                .description(status.name() + ": " + ex.getMessage())
                .source("CCP");
        headers.set(X_CORRELATION_ID, request.getHeader(X_CORRELATION_ID));
        return new ResponseEntity(error, headers, status);
    }
}
