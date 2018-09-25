package com.oldboy.spring.tiyanspring;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("byeservice")
@Scope("singleton")
public class byeserviceimpl implements byeservice {

    private  String bye;

    public byeserviceimpl() {
        System.out.println("byeserviceimpl的空参构造");
    }

    public String getBye() {
        return bye;
    }

    public void setBye(String bye) {
        this.bye = bye;
    }

    public void saybye() {
        System.out.println(bye);
    }
}
