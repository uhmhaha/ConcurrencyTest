package com.kakao.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class KakaoInvestApplication {

	public static void main(String[] args) {
		SpringApplication.run(KakaoInvestApplication.class, args);
	}

}
