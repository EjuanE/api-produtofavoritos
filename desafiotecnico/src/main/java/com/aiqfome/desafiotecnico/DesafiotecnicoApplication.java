package com.aiqfome.desafiotecnico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DesafiotecnicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafiotecnicoApplication.class, args);
	}

}
