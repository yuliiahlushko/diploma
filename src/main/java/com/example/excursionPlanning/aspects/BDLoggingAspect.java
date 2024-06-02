package com.example.excursionPlanning.aspects;

import com.example.excursionPlanning.services.interfaces.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.Principal;

@Aspect
@Component
public class BDLoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(BDLoggingAspect.class);

    @Pointcut("execution(* com.example.excursionPlanning.dao.*.save*(..))")
    public void saveMethods() {
    }


    @After("saveMethods() && args(entity)")
    public void logInsertion(JoinPoint joinPoint, Object entity) {
        logger.info("Inserting or changing entity {} into database", entity);
    }


}
