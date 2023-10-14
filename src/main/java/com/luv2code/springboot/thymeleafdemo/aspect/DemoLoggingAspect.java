package com.luv2code.springboot.thymeleafdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DemoLoggingAspect {

    //Setup Logger
    private Logger logger= LoggerFactory.getLogger(getClass());

    //Setup pointcut declarations
    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.controller.*.*(..))")
    public void forControllerPackage(){

    }

    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.service.*.*(..))")
    public void forServicePackage(){

    }

    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.dao.*.*(..))")
    public void forDaoPackage(){

    }

    @Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
    public void forAppFlow(){

    }

    @Before("forAppFlow()")
    public void beforeEveryMethodAdvice(JoinPoint theJoinPoint){
       //display the method we are calling
        MethodSignature methodSignature=(MethodSignature) theJoinPoint.getSignature();
        logger.info("Before the method: "+methodSignature.toShortString());

       //display the arguments to the method
        Object[] argsArray=theJoinPoint.getArgs();
        for(Object arg:argsArray){
            logger.info("Method argument: "+arg);
        }
    }

    @AfterReturning(pointcut="forAppFlow()",returning="theResult")
    public void afterEveryMethodReturnAdvice(JoinPoint theJoinPoint, Object theResult){
        //display the method we are calling
        MethodSignature methodSignature=(MethodSignature) theJoinPoint.getSignature();
        logger.info("After the method: "+methodSignature.toShortString());

        //display result
        logger.info("Returned value: "+(theResult!=null?theResult.toString():null));
    }
}
