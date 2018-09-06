package waes.differ.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import org.springframework.stereotype.Service;

import waes.differ.model.dto.DataDiffResponse;
import waes.differ.model.dto.Difference;

@Service
public class DiffServiceImpl implements DiffService {

	@Override
	public DataDiffResponse getDiff(byte[] left, byte[] right) {
		DataDiffResponse diffResult = new DataDiffResponse();
		
		if(Objects.isNull(left) || Objects.isNull(right)) {//if any side is null the comparison is considered as different content.
			diffResult.setEqualSize(false);
			diffResult.setEqualData(false);
		} else if (left.length != right.length) {//if both pieces of data have different size, differences are not calculated.
			diffResult.setEqualSize(false);
			diffResult.setEqualData(false);
		} else if (Arrays.equals(left, right)) {//if both pieces of data are equal, differences are not calculated.
			diffResult.setEqualSize(true);
			diffResult.setEqualData(true);
		} else { 
			diffResult.setEqualSize(true);
			diffResult.setEqualData(false);
			diffResult.setDifferences(getOffsets(left, right));
		}
		return diffResult;
	}

	/*
	 * Make a byte to byte comparison and store differences in a list. 
	 * 
	*/
	private ArrayList<Difference> getOffsets(byte[] left, byte[] right) {
		ArrayList<Difference> differences = new ArrayList<>();
		BigInteger diffLength = BigInteger.ZERO;
		boolean isNewDifference = true;
		BigInteger offset = null;
		for (int i = 0; i < left.length; i++) {
			if(left[i] != right[i]) {
				if (isNewDifference) {
					offset = BigInteger.valueOf(i);
					diffLength = diffLength.add(BigInteger.ONE);
					isNewDifference = false;
				} else {
					diffLength = diffLength.add(BigInteger.ONE);
				}
			} else if (!isNewDifference){
				Difference difference = new Difference();
				difference.setOffset(offset);
				difference.setLength(diffLength);
				differences.add(difference);
				diffLength = BigInteger.ZERO;
				isNewDifference = true;
				offset = null;
			}
		}
		
		if(Objects.nonNull(offset)) {
			Difference difference = new Difference();
			difference.setOffset(offset);
			difference.setLength(diffLength);
			differences.add(difference);
		}
		
		return differences;
	}
	
	

}
