package com.trading.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.xml.bind.DatatypeConverter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TradingAppApplicationTests {

	@Test
	public void contextLoads() {
	}

	public static void main(String[] args) throws IOException {
		File resource = new ClassPathResource("myphoto.jpg").getFile();
		String imageBase64 = DatatypeConverter.printHexBinary(Files.readAllBytes(resource.toPath()));
		System.out.println(imageBase64);
	}
}
