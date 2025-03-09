package org.example.starter.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.example.starter.config.LoggingProperties;

@Aspect
@Slf4j
public class LoggingAspect {

    private final LoggingProperties properties;

    public LoggingAspect(LoggingProperties properties) {
        this.properties = properties;
        log.info("LoggingAspect создан через конфигурацию");
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void taskControllerMethods() {
    }

    @Before("taskControllerMethods()")
    public void logBefore(JoinPoint joinPoint) {
        if (!properties.isEnabled()) {
            return;
        }
        logWithLevel("Запуск метода {} начался...", joinPoint.getSignature());
    }

    @AfterThrowing(pointcut = "taskControllerMethods()", throwing = "exception")
    public void logAfterThrowing(Exception exception) {
        if (!properties.isEnabled()) {
            return;
        }
        logWithLevel("Произошла ошибка: {}", exception.getMessage());
    }

    @AfterReturning(pointcut = "taskControllerMethods()", returning = "result")
    public void logAfterReturning(Object result) {
        if (!properties.isEnabled()) {
            return;
        }
        logWithLevel("Метод выполнен успешно, результат: {}", result);
    }

    @Around("taskControllerMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!properties.isEnabled()) {
            return joinPoint.proceed();
        }

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        logWithLevel("Метод {} выполнен за {} ms", joinPoint.getSignature(), (endTime - startTime));

        return result;
    }

    private void logWithLevel(String message, Object... args) {
        if (!properties.isEnabled()) {
            return;
        }

        switch (properties.getLevel()) {
            case DEBUG -> log.debug(message, args);
            case WARN -> log.warn(message, args);
            case ERROR -> log.error(message, args);
            default -> log.info(message, args);
        }
    }
}