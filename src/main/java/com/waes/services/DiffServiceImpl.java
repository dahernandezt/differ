package com.waes.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.waes.controller.response.DataDiffResponse;
import com.waes.controller.response.Difference;

@Service
public class DiffServiceImpl implements DiffService {

	@Override
	public DataDiffResponse getDiff(byte[] left, byte[] right) {
		DataDiffResponse diffResult = new DataDiffResponse();
		
		if(Objects.isNull(left) || Objects.isNull(right)) {
			diffResult.setEqualSize(false);
			diffResult.setEqualData(false);
		} else if (left.length != right.length) {
			diffResult.setEqualSize(false);
			diffResult.setEqualData(false);
		} else if (Arrays.equals(left, right)) {
			diffResult.setEqualSize(true);
			diffResult.setEqualData(true);
		} else {
			diffResult.setEqualSize(true);
			diffResult.setEqualData(false);
			diffResult.setDifferences(getOffsets(left, right));
		}
		return diffResult;
	}

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
