package com.example.excursionPlanning.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BDLoggingAspect {

    private final Logger logger = LoggerFactory.getLogger( BDLoggingAspect.class);

    @Pointcut("execution(* com.example.excursionPlanning.dao.*.save*(..))")
    public void saveMethods() {}

    @After("saveMethods() && args(entity)")
    public void logInsertion(JoinPoint joinPoint, Object entity) {
        logger.info("Inserting or changing entity {} into database", entity);
    }
}
