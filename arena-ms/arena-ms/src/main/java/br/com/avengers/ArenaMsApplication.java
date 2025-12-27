package br.com.avengers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class ArenaMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArenaMsApplication.class, args);
	}

}
