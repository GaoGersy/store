package com.piesat.project.common.aop.exception.aspect;

import com.piesat.project.common.exception.StackTraceHandler;
import com.piesat.project.service.system.SysExceptionInfoService;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionAspect {

    @Autowired
    SysExceptionInfoService mExceptionService;

    @Pointcut("@annotation(com.piesat.project.common.aop.exception.annotation.HandleException)")
    public void handleExceptionPointCut() {
    }

    @Around("handleExceptionPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        try {
            return  point.proceed();
        }catch (Exception e) {
            mExceptionService.insert(StackTraceHandler.getStackTrace(e));
            throw e;
        }
    }
}
