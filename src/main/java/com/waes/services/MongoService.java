package com.waes.services;

import com.waes.controller.response.DataDiffResponse;
import com.waes.exceptions.IdNotFoundException;

public interface MongoService {
	
	public String saveLeft(byte[] left, String uniqueId);
	
	public String saveRight(byte[] right, String uniqueId);
	
	public DataDiffResponse getDiff(String uniqueId) throws IdNotFoundException;

}
