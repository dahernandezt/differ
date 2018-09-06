package waes.differ.model.dto;

import java.util.ArrayList;

/**
 * 
 * DTO used as diff endpoint response body.
 *
 */
public class DataDiffResponse {
	
	private boolean isEqualSize;
	private boolean isEqualData;
	private ArrayList<Difference> differences = new ArrayList<>();
	
	/**
	 * Get the size comparison result
	 * @return true if both pieces of data are of the same size.
	 */
	public boolean isEqualSize() {
		return isEqualSize;
	}
	
	/**
	 * Set the size comparison result.
	 * @param isEqualSize
	 */
	public void setEqualSize(boolean isEqualSize) {
		this.isEqualSize = isEqualSize;
	}
	
	/**
	 * Get the data content comparison result.
	 * @return true if both pieces of data have the same content.
	 */
	public boolean isEqualData() {
		return isEqualData;
	}
	
	/**
	 * Set the content comparison result.
	 * @param isEqualData
	 */
	public void setEqualData(boolean isEqualData) {
		this.isEqualData = isEqualData;
	}
	
	/**
	 * Get the differences between pieces of data of the same size but different content.
	 * @return
	 */
	public ArrayList<Difference> getDifferences() {
		return differences;
	}
	
	/**
	 * Set the differences between pieces of data of the same size but different content.
	 * @param differences List of {@link Difference}
	 */
	public void setDifferences(ArrayList<Difference> differences) {
		this.differences = differences;
	}
	
	
}
