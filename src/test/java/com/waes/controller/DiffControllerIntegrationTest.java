package com.waes.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waes.controller.response.DataDiffResponse;
import com.waes.services.DiffServiceImpl;
import com.waes.services.MongoServiceImpl;


@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@WebAppConfiguration
@ContextConfiguration(classes =  {DiffControler.class, MongoServiceImpl.class, DiffServiceImpl.class})
public class DiffControllerIntegrationTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
    public void testLeftEndpoint() throws Exception {
		mockMvc
			.perform(post("/v1/diff/1/left")
					.contentType(MediaType.TEXT_PLAIN)
					.content("ABC"))
			.andExpect(status().isOk());
	}
	
	@Test
    public void testRightEndpoint() throws Exception {
		mockMvc
			.perform(post("/v1/diff/2/right")
					.contentType(MediaType.TEXT_PLAIN)
					.content("ABC"))
			.andExpect(status().isOk());
	}
	
	@Test
    public void testDiffEndpointNoDataFound() throws Exception {
		mockMvc
			.perform(get("/v1/diff/3"))
			.andExpect(status().isNotFound())
			.andReturn();
	}
	
	@Test
    public void testDiffEndpointEqualDataFound() throws Exception {
		mockMvc
		.perform(post("/v1/diff/4/left")
				.contentType(MediaType.TEXT_PLAIN)
				.content("ABC"))
		.andExpect(status().isOk());
		
		mockMvc
		.perform(post("/v1/diff/4/right")
				.contentType(MediaType.TEXT_PLAIN)
				.content("ABC"))
		.andExpect(status().isOk());
		
		MvcResult result = mockMvc
			.perform(get("/v1/diff/4"))
			.andExpect(status().isOk())
			.andReturn();
		
		String content = result.getResponse().getContentAsString();
		DataDiffResponse response = objectMapper.readValue(content, DataDiffResponse.class);
		
		assertTrue(response.isEqualData());
		assertTrue(response.isEqualSize());
		assertEquals(0, response.getDifferences().size());
	}
	
	@Test
    public void testDiffEndpointDifferentSizeDataFound() throws Exception {
		mockMvc
		.perform(post("/v1/diff/5/left")
				.contentType(MediaType.TEXT_PLAIN)
				.content("RGlmZmVyZW50IFNpemU="))
		.andExpect(status().isOk());
		
		mockMvc
		.perform(post("/v1/diff/5/right")
				.contentType(MediaType.TEXT_PLAIN)
				.content("RGlmZmVyZW50IFNpemUrKw=="))
		.andExpect(status().isOk());
		
		MvcResult result = mockMvc
			.perform(get("/v1/diff/5"))
			.andExpect(status().isOk())
			.andReturn();
		
		String content = result.getResponse().getContentAsString();
		DataDiffResponse response = objectMapper.readValue(content, DataDiffResponse.class);
		
		assertFalse(response.isEqualData());
		assertFalse(response.isEqualSize());
		assertEquals(0, response.getDifferences().size());
	}
	
	@Test
    public void testDiffEndpointSameSizeDifferentDataFound() throws Exception {
		mockMvc
		.perform(post("/v1/diff/6/left")
				.contentType(MediaType.TEXT_PLAIN)
				.content("RGlmZmVyZW50IENvbnRlbnQgMTExIDExIDExIDEx"))
		.andExpect(status().isOk());
		
		mockMvc
		.perform(post("/v1/diff/6/right")
				.contentType(MediaType.TEXT_PLAIN)
				.content("RGlmZmVyZW50IENvbnRlbnQgMjIyIDExIDIyIDEx"))
		.andExpect(status().isOk());
		
		MvcResult result = mockMvc
			.perform(get("/v1/diff/6"))
			.andExpect(status().isOk())
			.andReturn();
		
		String content = result.getResponse().getContentAsString();
		DataDiffResponse response = objectMapper.readValue(content, DataDiffResponse.class);
		
		assertFalse(response.isEqualData());
		assertTrue(response.isEqualSize());
		assertEquals(new BigInteger("18"), response.getDifferences().get(0).getOffset());
		assertEquals(new BigInteger("3"), response.getDifferences().get(0).getLength());
		assertEquals(new BigInteger("25"), response.getDifferences().get(1).getOffset());
		assertEquals(new BigInteger("2"), response.getDifferences().get(1).getLength());
	}


}
