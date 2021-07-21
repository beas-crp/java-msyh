package com.msyh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 13778    vue +swagger2(api文档)+ springboard + mybatisplus + mysql
 */
@SpringBootApplication
@EnableSwagger2
@MapperScan("com.msyh.mapper")
public class MsyhApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsyhApplication.class, args);
    }

}
