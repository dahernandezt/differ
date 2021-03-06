package waes.differ.controller;

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

import waes.differ.controller.DiffControler;
import waes.differ.model.dto.DataDiffResponse;
import waes.differ.services.DiffServiceImpl;
import waes.differ.services.MongoServiceImpl;
import waes.differ.services.ValidationServiceImpl;


@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@WebAppConfiguration
@ContextConfiguration(classes =  {DiffControler.class, MongoServiceImpl.class, DiffServiceImpl.class, ValidationServiceImpl.class})
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
					.content("eyAidmFsaWQiOiAiNTY3OCJ9"))
			.andExpect(status().isOk());
	}
	
	@Test
    public void testRightEndpointInvalidJSON() throws Exception {
		mockMvc
			.perform(post("/v1/diff/2/right")
					.contentType(MediaType.TEXT_PLAIN)
					.content("VGhpcyBpcyBub3QgYSBqc29uIG9iamVjdA=="))
			.andExpect(status().isBadRequest());
	}
	
	@Test
    public void testLeftEndpointInvalidJSON() throws Exception {
		mockMvc
			.perform(post("/v1/diff/1/left")
					.contentType(MediaType.TEXT_PLAIN)
					.content("VGhpcyBpcyBub3QgYSBqc29uIG9iamVjdA=="))
			.andExpect(status().isBadRequest());
	}
	
	@Test
    public void testRightEndpoint() throws Exception {
		mockMvc
			.perform(post("/v1/diff/2/right")
					.contentType(MediaType.TEXT_PLAIN)
					.content("eyAidmFsaWQiOiAiZmFsc2UifQ=="))
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
				.content("eyAidmFsaWQiOiAiZmFsc2UifQ=="))
		.andExpect(status().isOk());
		
		mockMvc
		.perform(post("/v1/diff/4/right")
				.contentType(MediaType.TEXT_PLAIN)
				.content("eyAidmFsaWQiOiAiZmFsc2UifQ=="))
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
				.content("eyAidmFsaWQiOiAiZmFsc2UifQ=="))
		.andExpect(status().isOk());
		
		mockMvc
		.perform(post("/v1/diff/5/right")
				.contentType(MediaType.TEXT_PLAIN)
				.content("eyAidmFsaWQiOiAidHJ1ZSJ9"))
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
				.content("eyAidmFsaWQiOiAiMTIzNCJ9"))
		.andExpect(status().isOk());
		
		mockMvc
		.perform(post("/v1/diff/6/right")
				.contentType(MediaType.TEXT_PLAIN)
				.content("eyAidmFsaWQiOiAiNTY3OCJ9"))
		.andExpect(status().isOk());
		
		MvcResult result = mockMvc
			.perform(get("/v1/diff/6"))
			.andExpect(status().isOk())
			.andReturn();
		
		String content = result.getResponse().getContentAsString();
		DataDiffResponse response = objectMapper.readValue(content, DataDiffResponse.class);
		
		assertFalse(response.isEqualData());
		assertTrue(response.isEqualSize());
		assertEquals(new BigInteger("12"), response.getDifferences().get(0).getOffset());
		assertEquals(new BigInteger("4"), response.getDifferences().get(0).getLength());
	}


}
