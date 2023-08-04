package com.bank.history.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.SimpleTimeZone;

@Component
@Aspect
@Slf4j
public class MyAspect {
    @Around("execution(* com.bank.history.servise.HistoryServise.getHistoryList(..))")
    public Object aroundGetHistoryList(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String start = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss sec").format(Calendar.getInstance().getTime());
        Object proceed = proceedingJoinPoint.proceed();
        log.info("At this time - " + start + " the method was called - \"getHistoryList\". The method returns a list all History");
        return proceed;
    }

    @Around("execution(* com.bank.history.servise.HistoryServise.getHistoryById(..))")
    public Object aroundGetHistoryById(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String start = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss sec").format(Calendar.getInstance().getTime());
        Object proceed = proceedingJoinPoint.proceed();
        log.info("At this time - " + start + " the method was called - \"getHistoryById\". The method returns the  History by id");
        return proceed;
    }

    @Around("execution(* com.bank.history.servise.HistoryServise.delete(..))")
    public Object aroundDeleteHistoryById(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String start = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss sec").format(Calendar.getInstance().getTime());
        Object proceed = proceedingJoinPoint.proceed();
        log.info("At this time - " + start + " the method was called - \"delete\". The method deletes the history by id");
        return proceed;
    }

    @Around("execution(* com.bank.history.servise.HistoryServise.create(..))")
    public Object aroundCreateHistory(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String start = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss sec").format(Calendar.getInstance().getTime());
        Object proceed = proceedingJoinPoint.proceed();
        log.info("At this time - " + start + " the method was called -\" create\". The method adds a new history to the database");
        return proceed;
    }

    @Around("execution(* com.bank.history.servise.HistoryServise.update(..))")
    public Object aroundUpdateHistoryById(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String start = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss sec").format(Calendar.getInstance().getTime());
        Object proceed = proceedingJoinPoint.proceed();
        log.info("At this time - " + start + " the method was called - \"update\". The method calls history by id and saves the changes made");
        return proceed;
    }
}

