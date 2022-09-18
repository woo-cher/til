package com.study.til.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ServiceAdviserExample {

    @Pointcut("execution(* com.study.til.aop.*Service.save*(..))")
    public void loggingPointcut() {}

    @Before("loggingPointcut()")
    public void beforeExample(JoinPoint joinPoint) {
        log.info("──────────────────── [Before] log, {}", joinPoint);
    }

    @After("loggingPointcut()")
    public void afterExample(JoinPoint joinPoint) {
        log.info("──────────────────── [After] log, {}", joinPoint);
    }

    @Around("@annotation(com.study.til.spring.aop.LoggingAspect)")
    public Object aroundAndAnnotationExample(ProceedingJoinPoint pjp) throws Throwable {
        log.info("──────────────────── [Around] before method proceed");
        Object retVal = pjp.proceed();
        log.info("──────────────────── [Around] after method proceed");
        return retVal;
    }

    @AfterReturning(value = "execution(* com.study.til.spring.aop.SomeService.getSomeMessage())", returning = "returnObject")
    public void afterReturnExample(Object returnObject) {
        log.info("──────────────────── [AfterReturning] log, {}", returnObject);
    }
}
