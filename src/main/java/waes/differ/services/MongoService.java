package waes.differ.services;

import waes.differ.exceptions.IdNotFoundException;
import waes.differ.model.dto.DataDiffResponse;

public interface MongoService {
	
	/**
	 * Save the encoded left side of a comparison into database.
	 * @param left base64 encoded data.
	 * @param uniqueId
	 * @return the uniqueId of the stored data.
	 */
	public String saveLeft(byte[] left, String uniqueId);
	
	/**
	 * Save the encoded right side of a comparison into database. 
	 * @param right base64 encoded data.
	 * @param uniqueId
	 * @return the uniqueId of the stored data.
	 */
	public String saveRight(byte[] right, String uniqueId);
	
	/**
	 * Retrieve the differences of a comparison from database.
	 * @param uniqueId
	 * @return [@link DataDiffResponse}
	 * @throws IdNotFoundException
	 */
	public DataDiffResponse getDiff(String uniqueId) throws IdNotFoundException;

}
