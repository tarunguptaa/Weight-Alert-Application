package com.egen.egen_be_challenge;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import com.egen.egen_be_challenge.utilities.MorphiaMongo;

@ComponentScan
@EnableAutoConfiguration
public class Application implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	public void run(String... arg0) throws Exception {
		new MorphiaMongo().cleanDatabase();
	}
}