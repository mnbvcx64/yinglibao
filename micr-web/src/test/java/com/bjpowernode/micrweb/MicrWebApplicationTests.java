package com.bjpowernode.micrweb;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Scanner;

@SpringBootTest
class MicrWebApplicationTests {

    @Test
    void contextLoads() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入一个正整数：");
        int i = scanner.nextInt();
        if (is_prime(i)){
            System.out.println(i+"是质数");
        }else {
            System.out.println(i+"是偶数");
        }
    }

    boolean is_prime(int n){
        if (n<=1){
            return true;
        }
        for (int i=2; i * i <= n; i++){
            if (n % i == 0){
                return true;
            }
        }
        return false;
    }
}
