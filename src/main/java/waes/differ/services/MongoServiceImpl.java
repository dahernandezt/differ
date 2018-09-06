package waes.differ.services;

import java.util.Base64;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import waes.differ.exceptions.IdNotFoundException;
import waes.differ.model.dto.DataDiffResponse;
import waes.differ.model.mongo.ComparableData;

@Service
public class MongoServiceImpl implements MongoService {
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	DiffService diffService;

	@Override
	public String saveLeft(byte[] left, String uniqueId) {
		ComparableData comparableData = mongoTemplate.findOne(Query.query(Criteria.where("uniqueId").is(uniqueId)), ComparableData.class);
		if (Objects.isNull(comparableData)) {
			comparableData = new ComparableData();
			comparableData.setLeft(left);
			comparableData.setUniqueId(uniqueId);
		} else {
			comparableData.setLeft(left);
		}
		setDifferences(comparableData);
		mongoTemplate.save(comparableData);
		return comparableData.getUniqueId();
	}

	@Override
	public String saveRight(byte[] right, String uniqueId) {
		ComparableData comparableData = mongoTemplate.findOne(Query.query(Criteria.where("uniqueId").is(uniqueId)), ComparableData.class);
		if (Objects.isNull(comparableData)) {
			comparableData = new ComparableData();
			comparableData.setRight(right);
			comparableData.setUniqueId(uniqueId);
		} else {
			comparableData.setRight(right);
		}
		setDifferences(comparableData);
		mongoTemplate.save(comparableData);
		return comparableData.getUniqueId();
	}

	@Override
	public DataDiffResponse getDiff(String uniqueId) throws IdNotFoundException {
		ComparableData comparableData = mongoTemplate.findOne(Query.query(Criteria.where("uniqueId").is(uniqueId)), ComparableData.class);
		if (Objects.isNull(comparableData)) {
			throw new IdNotFoundException();
		}
		return comparableData.getDiffResponse();
	}
	
	private void setDifferences(ComparableData comparableData) {
		byte[] decodedLeft = Objects.isNull(comparableData.getLeft()) ? null : Base64.getDecoder().decode(comparableData.getLeft());
		byte[] decodedRight = Objects.isNull(comparableData.getRight()) ? null : Base64.getDecoder().decode(comparableData.getRight());
		comparableData.setDiffResponse(diffService.getDiff(decodedLeft, decodedRight));
	}
	
	
}
