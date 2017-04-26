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

import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.services.IClerkService;
import com.gsmggk.accountspayable.webapp.models.ClerkModel;
import com.gsmggk.accountspayable.webapp.models.IdModel;
import com.gsmggk.accountspayable.webapp.validate.ValidationErrorRestonse;

@RestController
@RequestMapping("/clerks")
public class ClerkControler {
	@Inject
	private IClerkService clerkService;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<ClerkModel>> getAll() {
		List<Clerk> allClerks;
		allClerks = clerkService.getAll();

		List<ClerkModel> converterModel = new ArrayList<>();
		for (Clerk clerk : allClerks) {
			converterModel.add(entity2model(clerk));
		}

		return new ResponseEntity<List<ClerkModel>>(converterModel, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable(value = "id") Integer clerkIdParam) {
		Clerk clerk = clerkService.get(clerkIdParam);
		if (clerk==null){
			   String bodyOfResponse = "{\"error\":\"Clerk not exists.\"}";
				return new ResponseEntity<String>(bodyOfResponse,HttpStatus.BAD_REQUEST);
		}
		ClerkModel clerkModel = entity2model(clerk);
		return new ResponseEntity<ClerkModel>(clerkModel, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "content-type= application/json; charset=UTF-8")

	public ResponseEntity<?> createClerk(@Valid @RequestBody ClerkModel clerkModel, Errors e) {
		if (e.hasErrors()) {
			return new ValidationErrorRestonse().getValidationErrorRestonse(e);
		}
		Clerk clerk = model2entity(clerkModel);
		clerkService.save(clerk);
		return new ResponseEntity<IdModel>(new IdModel(clerk.getId()), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateClerk(@Valid @RequestBody ClerkModel clerkModel, Errors e,
			@PathVariable(value = "id") Integer clerkIdParam) {
		if (e.hasErrors()) {
			return new ValidationErrorRestonse().getValidationErrorRestonse(e);
		}

		Clerk clerk = clerkService.get(clerkIdParam);

		clerk.setClerkFullName(clerkModel.getClerkFullName());
		clerk.setClerkLoginName(clerkModel.getClerkLoginName());
		clerk.setRoleId(clerkModel.getRoleId());
		

		clerkService.save(clerk);
		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteClerk(@PathVariable(value = "id") Integer clerkIdParam) {
		Clerk clerk = new Clerk();
		clerk = clerkService.get(clerkIdParam);
		clerkService.delete(clerk);
		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	private Clerk model2entity(ClerkModel model) {
		Clerk clerk = new Clerk();
		clerk.setId(model.getId());
		clerk.setClerkLoginName(model.getClerkLoginName());
		clerk.setClerkFullName(model.getClerkFullName());
		clerk.setRoleId(model.getRoleId());

		return clerk;
	}

	private ClerkModel entity2model(Clerk clerk) {
		ClerkModel model = new ClerkModel();
		model.setId(clerk.getId());
		model.setClerkLoginName(clerk.getClerkLoginName());
		model.setClerkFullName(clerk.getClerkFullName());
		model.setRoleId(clerk.getRoleId());

		return model;
	}

}
