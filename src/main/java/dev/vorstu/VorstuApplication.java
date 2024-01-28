package dev.vorstu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class VorstuApplication {

	private static Initializer initiator;

	@Autowired
	public void setInitialLoader(Initializer initiator) {
		VorstuApplication.initiator = initiator;
	}

	public static void main(String[] args) throws IOException {
		SpringApplication.run(VorstuApplication.class, args);

		initiator.initial();
	}
}

