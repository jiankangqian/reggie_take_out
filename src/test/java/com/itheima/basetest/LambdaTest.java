package com.itheima.basetest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class LambdaTest {
    @Test
    public void list(){
        System.out.println("列出所有清单");
    }

    public static void main(String[] args) {
        System.out.println("hello");
       /* Swimming s1 = new Swimming() {
            @Override
            public void swim() {
                System.out.println("我正在游泳");
            }
        };*/
        Swimming s1 = () -> {
            System.out.println("我正在游泳");
        };
        go(s1);
        go(()->{
            System.out.println("朋友在跑步");
        });
    }
    public static void go(Swimming s){
        System.out.println("开始-----");
        s.swim();
        System.out.println("结束------");

    }


}

