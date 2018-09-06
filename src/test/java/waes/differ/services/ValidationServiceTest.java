package waes.differ.services;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import waes.differ.exceptions.NotValidBase64EncodedData;
import waes.differ.exceptions.NotValidJsonException;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes =  ValidationServiceImpl.class)
@EnableAutoConfiguration
public class ValidationServiceTest {
	
	@Value("classpath:waes/differ/services/json/test.json")
	File jsonTest;
	
	@Autowired
	ValidationService validationService;
	
	@Test
	public void testValidJsonString() {
		assertTrue(validationService.validateEncodedJsonData("eyJ2YWxpZCI6ICJ0cnVlIn0=".getBytes()));
	}
	
	@Test
	public void testValidComplexJsonsString() throws IOException {
		assertTrue(validationService.validateEncodedJsonData(FileUtils.readFileToString(jsonTest).getBytes()));
	}
	
	@Test(expected = NotValidJsonException.class) 
	public void testInValidJsonString() {
		validationService.validateEncodedJsonData("Tm90IGEgdmFsaWQganNvbiBvYmplY3Q=".getBytes());
	}
	
	@Test(expected = NotValidJsonException.class) 
	public void testNullString() {
		validationService.validateEncodedJsonData(null);
	}
	
	@Test(expected = NotValidBase64EncodedData.class) 
	public void testNotBase64EncodedString() {
		validationService.validateEncodedJsonData("Not valid bas64 encoded data".getBytes());
	}
}
