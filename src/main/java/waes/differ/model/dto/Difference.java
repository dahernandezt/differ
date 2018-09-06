package waes.differ.model.dto;

import java.math.BigInteger;

/**
 * 
 * DTO for a content difference. 
 *
 */
public class Difference {
	
	
	private BigInteger offset;
	private BigInteger length;
	
	/**
	 * Get the 0 based index offset of a difference in content.
	 * @return {@link BigInteger}
	 */
	public BigInteger getOffset() {
		return offset;
	}
	
	/**
	 * Set the 0 based index offset of a difference in content.
	 * @param offset {@link BigInteger}
	 */
	public void setOffset(BigInteger offset) {
		this.offset = offset;
	}
	
	/**
	 * Get the length in characters of a difference.
	 * @return {@link BigInteger}
	 */
	public BigInteger getLength() {
		return length;
	}
	
	/**
	 * Set the length in characters of a difference.
	 * @param length {@link BigInteger}
	 */
	public void setLength(BigInteger length) {
		this.length = length;
	}
	
	
}
