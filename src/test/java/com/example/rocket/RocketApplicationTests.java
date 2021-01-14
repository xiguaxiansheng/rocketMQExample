package com.example.rocket;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class RocketApplicationTests {

    @Test
    void contextLoads() {
    }

    //遍历数组组成字符串
    @Test
    void joinerListTest1(){
        List<String> lists = Lists.newArrayList("a","b","g","8","9");
        String result = Joiner.on(",").join(lists);
        System.out.println(result);
    }

    @Test
    void joinerListTest2(){
        List<String> lists = Lists.newArrayList("a","b",null,"8","9");
        String result = Joiner.on(",").skipNulls().join(lists);
        System.out.println(result);
    }


}
