package com.ps.patient_service.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationException(MethodArgumentNotValidException ex){
        Map<String,String> erros = new HashMap<String,String>();
        ex.getBindingResult().getAllErrors().forEach((err) -> {
            erros.put(((FieldError) err).getField(),err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(erros);
    }

}
