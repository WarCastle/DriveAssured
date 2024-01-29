package com.castle.drive;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author YuLong
 * @Date 2024/1/29 11:37
 * @Classname DriveAssuredApplication
 * @Description 启动类
 */
@EnableAsync
@SpringBootApplication(scanBasePackages = "com.castle.drive")
// @MapperScan("com.castle.drive.mapper")
// @EnableScheduling
public class DriveAssuredApplication {

    public static void main(String[] args) {
        SpringApplication.run(DriveAssuredApplication.class, args);
    }
}
