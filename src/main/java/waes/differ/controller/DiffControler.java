package waes.differ.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import waes.differ.model.dto.DataDiffResponse;
import waes.differ.services.MongoService;

@RestController
@RequestMapping(value = "/v1/diff")
public class DiffControler {
	
	@Autowired
	MongoService mongoService;
	
	@ApiOperation(value = "Diff Strings", tags = {"Diff"})
	@RequestMapping(value = "/{id}/left", method = RequestMethod.POST)
	@ResponseBody
	public void saveLeft(@PathVariable("id") String id, @RequestBody byte[] data) {
		mongoService.saveLeft(data, id);
	}
	
	@ApiOperation(value = "Diff Strings", tags = {"Diff"})
	@RequestMapping(value = "/{id}/right", method = RequestMethod.POST)
	@ResponseBody
	public void saveRight(@PathVariable("id") String id, @RequestBody byte[] data) {
		mongoService.saveRight(data, id);
	}
	
	@ApiOperation(value = "Diff Strings", tags = {"Diff"})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataDiffResponse getDiff(@PathVariable("id") String id) throws NotFoundException {
		return mongoService.getDiff(id);
	}
	
}
