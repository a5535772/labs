package com.example.demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.demo.bean.Cat;
import com.example.demo.bean.People;

@SpringBootTest
public class DroolsApplicationTests {
    @Autowired
    private KieContainer kieContainer;


    @Test
    public void people() {
        KieSession session = kieContainer.newKieSession();
        try {
            People people = new People();
            people.setName("达");
            people.setSex(1);
            people.setDrlType("people");
            session.insert(people);// 插入
            session.fireAllRules();// 执行规则
        } catch (Exception e) {
            throw e;
        } finally {
            session.destroy();
        }

    }

    @Test
    public void tom() {
        KieSession session = kieContainer.newKieSession();
        try {
            Cat cat = new Cat();
            cat.setName("tom");
            session.insert(cat);// 插入
            session.fireAllRules();// 执行规则
        } catch (Exception e) {
            throw e;
        } finally {
            session.destroy();
        }

    }

    @AfterEach
    public void runDispose() {}
}
