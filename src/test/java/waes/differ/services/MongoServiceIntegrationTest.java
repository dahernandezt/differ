package waes.differ.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javassist.NotFoundException;
import waes.differ.model.dto.DataDiffResponse;
import waes.differ.model.mongo.ComparableData;
import waes.differ.services.DiffServiceImpl;
import waes.differ.services.MongoService;
import waes.differ.services.MongoServiceImpl;

@RunWith(SpringRunner.class)
@DataMongoTest
@ContextConfiguration(classes =  {MongoServiceImpl.class, DiffServiceImpl.class})
@EnableAutoConfiguration
public class MongoServiceIntegrationTest {
	
	@Autowired
	private MongoService mongoService;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Test
    public void testSaveLeft() {
		mongoService.saveLeft(("eyAidmFsaWQiOiAiZmFsc2UifQ==").getBytes(), "12");
        assertTrue(Arrays.equals(mongoTemplate.findOne(Query.query(Criteria.where("uniqueId").is("12")), ComparableData.class).getLeft(),("eyAidmFsaWQiOiAiZmFsc2UifQ==").getBytes()));
    }
	
	@Test
    public void testSaveRight() {
		mongoService.saveRight(("eyAidmFsaWQiOiAiZmFsc2UifQ==").getBytes(), "13");
        assertTrue(Arrays.equals(mongoTemplate.findOne(Query.query(Criteria.where("uniqueId").is("13")), ComparableData.class).getRight(),("eyAidmFsaWQiOiAiZmFsc2UifQ==").getBytes()));
    }
	
	@Test
    public void testGetDiff() throws NotFoundException {
		mongoService.saveRight(("eyAidmFsaWQiOiAiZmFsc2UifQ==").getBytes(), "14");
		mongoService.saveLeft(("eyAidmFsaWQiOiAiZmFsc2UifQ==").getBytes(), "14");
		DataDiffResponse response = mongoService.getDiff("14");
        assertTrue(response.isEqualData());
        assertTrue(response.isEqualSize());
        assertEquals(0, response.getDifferences().size());
    }

}
