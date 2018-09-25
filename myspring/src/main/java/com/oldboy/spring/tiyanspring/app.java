package com.oldboy.spring.tiyanspring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class app {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
        welcomservice ws = (welcomservice) ac.getBean("welcomservice");
        ws.sayhello();
    }
}
