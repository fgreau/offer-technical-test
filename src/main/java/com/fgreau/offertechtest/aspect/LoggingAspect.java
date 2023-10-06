package com.fgreau.offertechtest.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Logging through AOP.
 */
@Aspect
@Component
public class LoggingAspect {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Logging informations from calling an endpoint.
     *
     * @param joinPoint joinpoint
     */
    @Before("execution(* com.fgreau.offertechtest.controllers.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        AspectUtils.startTime.set(System.currentTimeMillis());
        final String endpoint = joinPoint.getSignature().toShortString();
        LOGGER.info("Calling endpoint: {}", endpoint);
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            LOGGER.info("Input: {}", arg);
        }
    }

    /**
     * Logging the output of the call as well as the processing time.
     *
     * @param joinPoint joinpoint
     * @param result    output
     */
    @AfterReturning(pointcut = "execution(* com.fgreau.offertechtest.controllers.*.*(..))", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        final String endpoint = joinPoint.getSignature().toShortString();
        LOGGER.info("Successfully returned from endpoint: {}", endpoint);
        LOGGER.info("Output: {}", result);

        long elapsedTime = System.currentTimeMillis() - AspectUtils.startTime.get();
        LOGGER.info("Processing time: {}ms", elapsedTime);

    }

}
