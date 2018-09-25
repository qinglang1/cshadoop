package com.oldboy.spring.aop;

import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

public class MyThrowsAdvice implements ThrowsAdvice {
    public void afterThrowing(Method method,Object[] args,Object target,Exception ex){

        System.out.println("出错了！！" + ex.getMessage());
    }

}
