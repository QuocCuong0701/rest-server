package com.example.restdemo2.exception;

import com.example.restdemo2.validation.ValidationError;
import com.example.restdemo2.validation.ValidationErrorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class ExceptionTranslator {
    @Autowired
    private MessageSource messageSource;

//    @ExceptionHandler({BindException.class})
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    public ValidationError validationError(BindException e) {
//        return ValidationErrorBuilder.fromBindingErrors(e.getBindingResult(), messageSource);
//    }

    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Object> validationError(BindException e) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : e.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("errors", errors);
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", "Co loi xay ra!");
        return ResponseEntity.badRequest().body(response);
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("errors", errors);
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", "Co loi xay ra!");
        return ResponseEntity.badRequest().body(response);
    }
//
//    @ExceptionHandler({ConstraintViolationException.class})
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    public ValidationError onConstraintViolationException(ConstraintViolationException e) {
//        return ValidationErrorBuilder.fromBindingErrors(e, messageSource);
//    }
}
