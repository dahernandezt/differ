package com.waes.controller.response;

import java.util.ArrayList;

public class DataDiffResponse {
	private boolean isEqualSize;
	private boolean isEqualData;
	private ArrayList<Difference> differences = new ArrayList<>();
	
	public boolean isEqualSize() {
		return isEqualSize;
	}
	
	public void setEqualSize(boolean isEqualSize) {
		this.isEqualSize = isEqualSize;
	}
	
	public boolean isEqualData() {
		return isEqualData;
	}
	
	public void setEqualData(boolean isEqualData) {
		this.isEqualData = isEqualData;
	}
	
	public ArrayList<Difference> getDifferences() {
		return differences;
	}
	
	public void setDifferences(ArrayList<Difference> differences) {
		this.differences = differences;
	}
	
	
}
