package com.sepractice.lenovoshop.interceptors;

import com.sepractice.lenovoshop.utils.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<Result> exceptionHandler(Exception e){
        e.printStackTrace();
        Result result = Result.error(e.toString());
        return ResponseEntity.status(500).body(result);
    }
}
