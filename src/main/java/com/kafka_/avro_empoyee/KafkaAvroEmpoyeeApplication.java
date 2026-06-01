package com.kafka_.avro_empoyee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.kafka_.avro_empoyee")
public class KafkaAvroEmpoyeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaAvroEmpoyeeApplication.class, args);
	}

}
