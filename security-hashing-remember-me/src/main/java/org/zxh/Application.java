package org.zxh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by xh.zhi on 2018-2-5.
 */
@SpringBootApplication
// 当使用xml的方式配置时，开启此注解注释掉Java代码方式的配置
//@ImportResource("classpath:spring-security-config.xml")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
