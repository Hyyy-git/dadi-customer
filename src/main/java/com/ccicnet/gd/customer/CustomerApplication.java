package com.ccicnet.gd.customer;

import ch.qos.logback.classic.PatternLayout;
import com.ccicnet.gd.common.environment.log.LogBackThreadConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.ccicnet.gd.common", "com.ccicnet.gd.customer"})
@SpringBootApplication
@ServletComponentScan(basePackages = {"com.ccicnet.gd.common.environment.log"})
@EnableConfigurationProperties
public class CustomerApplication {
    public static void main(String[] args) {
        PatternLayout.defaultConverterMap.put("T", LogBackThreadConverter.class.getName());
        SpringApplication.run(CustomerApplication.class, args);
    }
}
