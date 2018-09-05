package com.waes.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.waes.controller.response.DataDiffResponse;

@Document
public class ComparableData {
	
	@Id
	private String uniqueId;
	private byte[] left;
	private byte[] right;
	private DataDiffResponse diffResponse;
	

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public byte[] getLeft() {
		return left;
	}

	public void setLeft(byte[] left) {
		this.left = left;
	}

	public byte[] getRight() {
		return right;
	}

	public void setRight(byte[] right) {
		this.right = right;
	}

	public DataDiffResponse getDiffResponse() {
		return diffResponse;
	}

	public void setDiffResponse(DataDiffResponse diffResponse) {
		this.diffResponse = diffResponse;
	}

}
