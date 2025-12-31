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
    public ResponseEntity<Map<String,String>> handelValidationException(MethodArgumentNotValidException ex){
        Map<String,String> erros = new HashMap<String,String>();
        ex.getBindingResult().getAllErrors().forEach((err) -> {
            erros.put(((FieldError) err).getField(),err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<Map<String,String>> handelEmailAlreadyExistException(EmailAlreadyExistException ex){
        Map<String,String> error = new HashMap<>();
        error.put("message","Email address already exist");
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    ResponseEntity<Map<String,String>> handelPatientNotFoundException(PatientNotFoundException ex){
        Map<String,String> error = new HashMap<>();
        error.put("message","Patient Not Found");
        return ResponseEntity.badRequest().body(error);
    }

}
