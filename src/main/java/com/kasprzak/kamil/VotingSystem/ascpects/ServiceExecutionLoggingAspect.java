package com.kasprzak.kamil.VotingSystem.ascpects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@Profile("logs")
public class ServiceExecutionLoggingAspect {

    @Around("execution(public * com.kasprzak.kamil.VotingSystem.services.impl..*(..))")
    public Object logPublicServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        log.info("Calling method: {} with args: {}", joinPoint.getSignature(), args.length > 0 ? args : "no args");
        return joinPoint.proceed();
    }
}
