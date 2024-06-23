package dev.vorstu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class KovmakApplication {

	private static Initializer initiator;

	@Autowired
	public void setInitialLoader(Initializer initiator) {
		KovmakApplication.initiator = initiator;
	}

	public static void main(String[] args) throws IOException {
		SpringApplication.run(KovmakApplication.class, args);

		initiator.initial();
	}
}

