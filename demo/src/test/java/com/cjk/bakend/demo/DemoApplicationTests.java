package com.cjk.bakend.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
		String a = new String("haha");
		String b = new String("heihei");
		System.out.println(a.hashCode());
		System.out.println(b.hashCode());
		a = b;
		System.out.println(a.hashCode());
	}

}
