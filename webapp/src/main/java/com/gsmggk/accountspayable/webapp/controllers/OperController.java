package com.gsmggk.accountspayable.webapp.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gsmggk.accountspayable.datamodel.Oper;
import com.gsmggk.accountspayable.services.IOperService;
import com.gsmggk.accountspayable.webapp.models.OperModel;
import com.gsmggk.accountspayable.webapp.models.IdModel;
import com.gsmggk.accountspayable.webapp.validate.ValidationErrorRestonse;

@RestController
@RequestMapping("/opers")
public class OperController {

	@Inject
	private IOperService operService;
	
	

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<OperModel>> getAll() {
		List<Oper> allOpers;
		allOpers = operService.getAll();

		List<OperModel> converterModel = new ArrayList<>();
		for (Oper oper : allOpers) {
			converterModel.add(entity2model(oper));
		}

		return new ResponseEntity<List<OperModel>>(converterModel, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable(value = "id") Integer operIdParam) {
		Oper oper = operService.get(operIdParam);
		if (oper==null){
			   String bodyOfResponse = "{\"error\":\"Oper not exists.\"}";
				return new ResponseEntity<String>(bodyOfResponse,HttpStatus.BAD_REQUEST);
		}
		OperModel operModel = entity2model(oper);
		return new ResponseEntity<OperModel>(operModel, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "content-type= application/json; charset=UTF-8")

	public ResponseEntity<?> createOper(@Valid @RequestBody OperModel operModel, Errors e) {
		if (e.hasErrors()) {
			return new ValidationErrorRestonse().getValidationErrorRestonse(e);
		}
		Oper oper = model2entity(operModel);
		operService.save(oper);
		return new ResponseEntity<IdModel>(new IdModel(oper.getId()), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateOper(@Valid @RequestBody OperModel operModel, Errors e,
			@PathVariable(value = "id") Integer operIdParam) {
		if (e.hasErrors()) {
			return new ValidationErrorRestonse().getValidationErrorRestonse(e);
		}

		Oper oper = operService.get(operIdParam);

		loadFromModel(operModel, oper);
		
		
		operService.save(oper);
		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	private void loadFromModel(OperModel operModel, Oper oper) {
		oper.setDebtorId(operModel.getDebtorId());
		oper.setClerkId(operModel.getClerkId());
		oper.setActionId(operModel.getActionId());
		oper.setActionDate(operModel.getActionDate());
		oper.setControlDate(operModel.getControlDate());
		oper.setOperDesc(operModel.getOperDesc());
	
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteOper(@PathVariable(value = "id") Integer operIdParam) {
		Oper oper = new Oper();
		oper = operService.get(operIdParam);
		operService.delete(oper);
		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	private Oper model2entity(OperModel model) {
		Oper oper = new Oper();
		oper.setId(model.getId());
		loadFromModel(model, oper);

		return oper;
	}

	private OperModel entity2model(Oper oper) {
		OperModel model = new OperModel();
		model.setId(oper.getId());
		model.setDebtorId(oper.getDebtorId());
		model.setClerkId(oper.getClerkId());
		model.setActionId(oper.getActionId());
		model.setActionDate(oper.getActionDate());
		model.setControlDate(oper.getControlDate());
		model.setOperDesc(oper.getOperDesc());

		return model;
	}

}
