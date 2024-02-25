package com.generation.arcane;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ArcaneApplicationTests {

	@Test
	void contextLoads() {
		assertDoesNotThrow(() -> ArcaneApplication.main(new String[] {}));
	}

}
