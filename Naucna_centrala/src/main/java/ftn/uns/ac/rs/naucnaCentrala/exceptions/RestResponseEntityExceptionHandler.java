package ftn.uns.ac.rs.naucnaCentrala.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = { MyNotFoundExeption.class })
    protected ResponseEntity<Object> notExistHandle(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        bodyOfResponse = Optional.ofNullable(bodyOfResponse).orElse("The object not found");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { MyAlreadyExistsException.class })
    protected ResponseEntity<Object> alreadyExistsHandle(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        bodyOfResponse = Optional.ofNullable(bodyOfResponse).orElse("Already exists in database");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }


}
