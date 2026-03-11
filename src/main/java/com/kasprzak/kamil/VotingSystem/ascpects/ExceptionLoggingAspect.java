package com.kasprzak.kamil.VotingSystem.ascpects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@Profile("logs")
public class ExceptionLoggingAspect {

    @AfterThrowing(pointcut = "execution(* com.kasprzak.kamil.VotingSystem..*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        log.error("Exception in method {} with args {}: {}",
                joinPoint.getSignature(),
                joinPoint.getArgs().length > 0 ? joinPoint.getArgs() : "no args",
                ex.getMessage(),
                ex);
    }
}
