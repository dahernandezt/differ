package com.waes.services;

import com.waes.controller.response.DataDiffResponse;

public interface DiffService {

	public DataDiffResponse getDiff(byte[] left, byte[] right);
}
