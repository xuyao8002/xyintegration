package com.xuyao.integration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xuyao.integration.dao")
public class XyintegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(XyintegrationApplication.class, args);
	}
}
