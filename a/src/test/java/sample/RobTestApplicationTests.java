package sample;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest (classes=StartApplication.class)
class RobTestApplicationTests {

	@Test
	void contextLoads() {
		Assertions.assertNotNull(this);
	}

}
