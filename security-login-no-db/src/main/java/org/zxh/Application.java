package org.zxh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class Application
{
    public static void main( String[] args )
    {
        System.out.println( "Application run!!!" );
        SpringApplication.run(Application.class,args);
    }
}
