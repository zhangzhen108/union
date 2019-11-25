package com.union;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@ComponentScan("com.union")
@MapperScan("com.union.dao")
@EnableSwagger2
public class UnionApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnionApplication.class, args);
	}
}
