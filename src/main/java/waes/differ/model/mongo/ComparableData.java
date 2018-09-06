package waes.differ.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import waes.differ.model.dto.DataDiffResponse;

/**
 * 
 * Mongo document object to store a comparable data set and its difference.
 *
 */
@Document
public class ComparableData {
	
	@Id
	private String uniqueId;
	private byte[] left;
	private byte[] right;
	private DataDiffResponse diffResponse;
	
	/**
	 * Get the unique id used to identify a comparison.
	 * @return 
	 */
	public String getUniqueId() {
		return uniqueId;
	}

	/**
	 * Get the unique id used to identify a comparison.
	 * @param uniqueId
	 */
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	/**
	 * Get the encoded left content as byte array.
	 * @return
	 */
	public byte[] getLeft() {
		return left;
	}

	/**
	 * Set the encoded left content as byte array.
	 * @param left
	 */
	public void setLeft(byte[] left) {
		this.left = left;
	}

	/**
	 * Get the encoded right content as byte array.
	 * @return
	 */
	public byte[] getRight() {
		return right;
	}

	/**
	 * Set the encoded right content as byte array.
	 * @param right
	 */
	public void setRight(byte[] right) {
		this.right = right;
	}

	/**
	 * Get the differences between decoded content. 
	 * @return {@link DataDiffResponse}
	 */
	public DataDiffResponse getDiffResponse() {
		return diffResponse;
	}

	/**
	 * Set the differences between decoded content.
	 * @param diffResponse {@link DataDiffResponse}
	 */
	public void setDiffResponse(DataDiffResponse diffResponse) {
		this.diffResponse = diffResponse;
	}

}
