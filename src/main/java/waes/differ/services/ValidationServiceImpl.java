package waes.differ.services;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import waes.differ.exceptions.NotValidBase64EncodedData;
import waes.differ.exceptions.NotValidJsonException;

@Service
public class ValidationServiceImpl implements ValidationService {

	@Autowired
	ObjectMapper objectMapper;

	@Override
	public boolean validateEncodedJsonData(byte[] data) throws NotValidJsonException, NotValidBase64EncodedData {
		if(Objects.isNull(data)) {
			throw new NotValidJsonException();
		}
		try {
			byte[] decodedData = Base64.getDecoder().decode(data);
			final ObjectMapper mapper = new ObjectMapper();
			mapper.readTree(decodedData);
		} catch (IOException e) {
			throw new NotValidJsonException();
		} catch (IllegalArgumentException ex) {
			throw new NotValidBase64EncodedData();
		}
		return true;
	}

}
