package com.diploma.backend.error.advice;

import java.util.Enumeration;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class ExceptionsLoggerAdvice {

    @Pointcut(value = "@annotation(exceptionHandler)", argNames = "exceptionHandler")
    private void exceptionHandlerPointcut(ExceptionHandler exceptionHandler) {
        // not need to be implemented
    }

    @Before(value = "exceptionHandlerPointcut(exceptionHandler) && args(ex)", argNames = "exceptionHandler,ex")
    public void logExceptionAdvice(ExceptionHandler exceptionHandler, Exception ex) {
        HttpServletRequest request =
                Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .map(ServletRequestAttributes::getRequest).orElse(null);
        logExceptionAdvice(exceptionHandler, ex, request);
    }

    private void logExceptionAdvice(ExceptionHandler exceptionHandler, Exception ex, HttpServletRequest request) {
        if (request != null) {
            log.debug(requestToString(request));
        }
        log.debug("Exception successfully handled: ", ex);
    }

    private String requestToString(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder("HttpRequest{" + request.getMethod() + " " + request.getRequestURI() +
                "\nrequestHeaders: \n");
        Enumeration<String> iterator = request.getHeaderNames();
        String headerName;
        while (iterator.hasMoreElements()) {
            headerName = iterator.nextElement();
            sb.append(headerName);
            sb.append(" = ");
            if (!headerName.equalsIgnoreCase(HttpHeaders.AUTHORIZATION)) {
                sb.append(request.getHeader(headerName));
            } else {
                sb.append("***");
            }
            sb.append("\n");
        }
        sb.append("}");
        return sb.toString();
    }

}
