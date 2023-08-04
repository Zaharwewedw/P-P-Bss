package com.bank.history.hendler;

import com.bank.history.exeption.HistoryException;
import com.bank.history.exeption.HistoryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerHistory {
    @ExceptionHandler
    private ResponseEntity<HistoryException> historyException(HistoryNotFoundException e) {
        HistoryException exception = new HistoryException("History not found", System.currentTimeMillis());
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);

    }
}
