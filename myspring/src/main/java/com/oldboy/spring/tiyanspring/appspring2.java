package com.oldboy.spring.tiyanspring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class appspring2 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("bean2.xml");
        welcomservice ws = (welcomservice) ac.getBean("welcomeservice");
        ws.sayhello();

    }
}
