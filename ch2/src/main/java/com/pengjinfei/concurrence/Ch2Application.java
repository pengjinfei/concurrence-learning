package com.pengjinfei.concurrence;

import com.pengjinfei.concurrence.Servlet.StatelessFactorizer;
import com.pengjinfei.concurrence.Servlet.UnsafeCountingFactorizer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Created by Pengjinfei on 16/9/24.
 * Description:
 */
@SpringBootApplication
public class Ch2Application {

    public static void main(String[] args) {
        SpringApplication.run(Ch2Application.class, args);
    }

    @Bean
    public ServletRegistrationBean statelessFactorizerBean() {
        return new ServletRegistrationBean(new StatelessFactorizer(), "/stateless");
    }

    @Bean
    public ServletRegistrationBean unsafeCountingFactorizerBean() {
        return new ServletRegistrationBean(new UnsafeCountingFactorizer(), "/unsafe");
    }
}
