package waes.differ.services;

import waes.differ.model.dto.DataDiffResponse;

/**
 * 
 * Service in charge of extracting differences between json strings. 
 *
 */
public interface DiffService {

	/**
	 * 
	 * @param left decoded json string.
	 * @param right decoded json string.
	 * @return {@link DataDiffResponse}
	 */
	public DataDiffResponse getDiff(byte[] left, byte[] right);
}
