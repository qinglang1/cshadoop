package com.oldboy.spring.aop;

public class WelcomServiceimpl implements WelcomService {
    public void sayhello(String str) {
        System.out.println("hello: " + str);
    }
}
