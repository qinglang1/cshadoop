package com.oldboy.spring.aop;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("aop.xml");
        WelcomService ws = (WelcomService) ac.getBean("proxy");
        ws.sayhello("tomas");

    }
}
