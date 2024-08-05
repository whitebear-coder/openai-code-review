package cn.sun.gaga.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    public void Test(){
        System.out.println(Integer.parseInt("aaaa"));
    }
}
