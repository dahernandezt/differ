package waes.differ.services;

import waes.differ.exceptions.NotValidBase64EncodedData;
import waes.differ.exceptions.NotValidJsonException;

public interface ValidationService {
	
	/**
	 * Validates if a given data has a valid json format. 
	 * @param data byte array to validate.
	 * @throws NotValidJsonException
	 */
	public boolean validateEncodedJsonData(byte[] data) throws NotValidJsonException , NotValidBase64EncodedData;
}
