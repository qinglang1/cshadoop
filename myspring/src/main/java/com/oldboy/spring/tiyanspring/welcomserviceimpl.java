package com.oldboy.spring.tiyanspring;

import com.oldboy.spring.tiyanspring.welcomservice;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service("welcomeservice")
@Scope("singleton")
public class welcomserviceimpl implements welcomservice {
    private  String name;
    @Resource(name="byeservice")
    private  byeservice bs;

    public byeservice getBs() {
        return bs;
    }

    public void setBs(byeservice bs) {
        this.bs = bs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void sayhello() {
        bs.saybye();
    }

}
