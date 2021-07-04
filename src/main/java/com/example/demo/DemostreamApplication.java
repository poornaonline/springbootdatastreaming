package com.example.demo;

import com.example.demo.model.UserDetail;
import com.example.demo.repository.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;

import java.util.UUID;

@SpringBootApplication
public class DemostreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemostreamApplication.class, args);
	}

	@Autowired
	private SampleRepository sampleRepository;


	// This generate sample records and store in the user

	@Bean
	public void runAtStartUp() {
		for (int i = 0; i <= 902003; i++) {

			String randomUUID = UUID.randomUUID().toString().replace("-", "");
			String firstName = UtilityClass.createRandomWord(10);
			String lastName = UtilityClass.createRandomWord(15);

			UserDetail ud = new UserDetail();
			ud.setFirstName(firstName);
			ud.setLastName(lastName);
			ud.setPassword(randomUUID);
			ud.setGender("Male");
			ud.setUserName(firstName + lastName);
			ud.setStatus(true);

			UserDetail savedUserDetail = sampleRepository.save(ud);
			System.err.println(savedUserDetail.getId());

		}
	}

}
