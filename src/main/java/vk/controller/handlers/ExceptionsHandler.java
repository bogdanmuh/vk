package vk.controller.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vk.controller.pojo.Error;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(Exception exception) {
        return ResponseEntity
                .badRequest()
                .body(new Error(exception.getMessage()));
    }

}
