package com.utcn.proiectScd;

import org.springframework.boot.SpringApplication;

public class TestProiectScdApplication {

	public static void main(String[] args) {
		SpringApplication.from(ProiectScdApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
