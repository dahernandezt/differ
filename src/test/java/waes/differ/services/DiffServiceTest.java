package waes.differ.services;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import waes.differ.model.dto.DataDiffResponse;
import waes.differ.services.DiffService;
import waes.differ.services.DiffServiceImpl;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes =  DiffServiceImpl.class)
public class DiffServiceTest {
	
	@Autowired
	DiffService diffService;
	
	private static final byte[] DATA_LEFT_TEST_DIFFERENT_SIZE = new String("Different Size").getBytes();
	private static final byte[] DATA_RIGHT_TEST_DIFFERENT_SIZE = new String("Different Size++").getBytes();
	
	private static final byte[] DATA_EQUALS = new String("Same Content Both").getBytes();
	
	private static final byte[] DATA_LEFT_TEST_SAME_SIZE_DIFF_CONTENT = new String("Different Content 1").getBytes();
	private static final byte[] DATA_RIGHT_TEST_SAME_SIZE_DIFF_CONTENT = new String("Different Content 2").getBytes();
	
	private static final byte[] DATA_LEFT_TEST_SAME_SIZE_DIFF_CONTENT_LENGTH_5 = new String("Different Content 11111").getBytes();
	private static final byte[] DATA_RIGHT_TEST_SAME_SIZE_DIFF_CONTENT_LENGTH_5 = new String("Different Content 22222").getBytes();
	
	private static final byte[] DATA_LEFT_TEST_SAME_SIZE_DIFF_CONTENT_2_DIFFERENCES = new String("Different Content 111 11 11 11").getBytes();
	private static final byte[] DATA_RIGHT_TEST_SAME_SIZE_DIFF_CONTENT_2_DIFFERENCES = new String("Different Content 222 11 22 11").getBytes();
	
	
	@Test
	public void testDifferentSizeData() {
		DataDiffResponse result = diffService.getDiff(DATA_LEFT_TEST_DIFFERENT_SIZE, DATA_RIGHT_TEST_DIFFERENT_SIZE);
		assertEquals(false, result.isEqualSize());
		assertEquals(false, result.isEqualData());
		assertEquals(0, result.getDifferences().size());
	}
	
	@Test
	public void testEqualData() {
		DataDiffResponse result = diffService.getDiff(DATA_EQUALS, DATA_EQUALS);
		assertEquals(true, result.isEqualData());
		assertEquals(true, result.isEqualSize());
		assertEquals(0, result.getDifferences().size());
	}
	
	@Test
	public void testDifferentDataOneDifferenceSingleChar() {
		DataDiffResponse result = diffService.getDiff(DATA_LEFT_TEST_SAME_SIZE_DIFF_CONTENT, DATA_RIGHT_TEST_SAME_SIZE_DIFF_CONTENT);
		assertEquals(false, result.isEqualData());
		assertEquals(true, result.isEqualSize());
		assertEquals(1, result.getDifferences().size());
		assertEquals(new BigInteger("18"), result.getDifferences().get(0).getOffset());
		assertEquals(new BigInteger("1"), result.getDifferences().get(0).getLength());
	}
	
	@Test
	public void testDifferentDataOneDifferenceFiveCharacters() {
		DataDiffResponse result = diffService.getDiff(DATA_LEFT_TEST_SAME_SIZE_DIFF_CONTENT_LENGTH_5, DATA_RIGHT_TEST_SAME_SIZE_DIFF_CONTENT_LENGTH_5);
		assertEquals(false, result.isEqualData());
		assertEquals(true, result.isEqualSize());
		assertEquals(1, result.getDifferences().size());
		assertEquals(new BigInteger("18"), result.getDifferences().get(0).getOffset());
		assertEquals(new BigInteger("5"), result.getDifferences().get(0).getLength());
	}
	
	@Test
	public void testDifferentDataTwoDifferences() {
		DataDiffResponse result = diffService.getDiff(DATA_LEFT_TEST_SAME_SIZE_DIFF_CONTENT_2_DIFFERENCES, DATA_RIGHT_TEST_SAME_SIZE_DIFF_CONTENT_2_DIFFERENCES);
		assertEquals(false, result.isEqualData());
		assertEquals(true, result.isEqualSize());
		assertEquals(2, result.getDifferences().size());
		assertEquals(new BigInteger("18"), result.getDifferences().get(0).getOffset());
		assertEquals(new BigInteger("3"), result.getDifferences().get(0).getLength());
		assertEquals(new BigInteger("25"), result.getDifferences().get(1).getOffset());
		assertEquals(new BigInteger("2"), result.getDifferences().get(1).getLength());
	}
	
	@Test
	public void testDifferentDataLeftSideNull() {
		DataDiffResponse result = diffService.getDiff(null, DATA_EQUALS);
		assertEquals(false, result.isEqualData());
		assertEquals(false, result.isEqualSize());
		assertEquals(0, result.getDifferences().size());
	}
	
	@Test
	public void testDifferentDataRightSideNull() {
		DataDiffResponse result = diffService.getDiff(DATA_EQUALS, null);
		assertEquals(false, result.isEqualData());
		assertEquals(false, result.isEqualSize());
		assertEquals(0, result.getDifferences().size());
	}

}
