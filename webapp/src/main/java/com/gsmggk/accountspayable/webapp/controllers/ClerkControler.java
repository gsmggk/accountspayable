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

import com.gsmggk.accountspayable.dao4api.modelmap.ClerkRepo;
import com.gsmggk.accountspayable.datamodel.Clerk;
import com.gsmggk.accountspayable.services.IClerkService;
import com.gsmggk.accountspayable.services.util.MemClient;
import com.gsmggk.accountspayable.webapp.models.ClerkModel;
import com.gsmggk.accountspayable.webapp.models.ClerkRepoModel;
import com.gsmggk.accountspayable.webapp.models.IdModel;
import com.gsmggk.accountspayable.webapp.models.PasswordModel;
import com.gsmggk.accountspayable.webapp.validate.ParameterErrorResponse;
import com.gsmggk.accountspayable.webapp.validate.ValidationErrorResponse;

@RestController
@RequestMapping("/{prefix}/clerks")
public class ClerkControler {
	private static final String NOT_FOUND_MES = "Clerk not found";

	@Inject
	private IClerkService clerkService;
	@Inject
	private MemClient client;
	
	/**
	 * Get clerks list with allocated debtors count. Use in Boss layer. 
	 * @return 
	 */
	@RequestMapping(value = "/repo",method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<ClerkRepoModel>> getClerkRepo() {
		
		List<ClerkRepo> allClerks;
		allClerks = clerkService.getClerkRepo();

		List<ClerkRepoModel> converterModel = new ArrayList<>();
		for (ClerkRepo clerk : allClerks) {
			converterModel.add(entity2CRmodel(clerk));
		}

		return new ResponseEntity<List<ClerkRepoModel>>(converterModel, HttpStatus.OK);
	}
	
	
	
	



	/**
	 * Get clerks for debtor.Use in Boss layer
	 * @param debtorIdParam debtor id.
	 * @return List of clerks
	 */
	@RequestMapping(value = "/4debtor/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getClerks4Ddebor(@PathVariable(value = "id") Integer debtorIdParam
			) {
		
		List<Clerk> allClerks;
		allClerks = clerkService.getClerks4Debtor(debtorIdParam);

		List<ClerkModel> converterModel = new ArrayList<>();
		for (Clerk clerk : allClerks) {
			converterModel.add(entity2model(clerk));
		}

		return new ResponseEntity<List<ClerkModel>>(converterModel, HttpStatus.OK);

	}
	

	/**
	 * Set password to clerk
	 * @param model crypted password
	 * @param e
	 * @param clerkIdParam  clerks id to allocate password
	 * @return
	 */
	@RequestMapping(value = "/apassword/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> allocatePassword(@Valid @RequestBody PasswordModel model, Errors e,
			@PathVariable(value = "id") Integer clerkIdParam) {
		

		if (e.hasErrors()) {
			return new ValidationErrorResponse().getValidationErrorResponse(e);
		}

		Clerk clerk = clerkService.get(clerkIdParam);
		if (clerk == null) {
			return ParameterErrorResponse.getNotFoundResponse(NOT_FOUND_MES);
		}
		clerkService.allocatePassword(clerkIdParam, model.getValue());

		client.deleteMemCached(clerkIdParam);// delete memcached link

		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	// ==========================default controllers=================
	@RequestMapping(value = {"","/getall"},method = RequestMethod.GET)
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
	public ResponseEntity<?> getById(@PathVariable(value = "id") Integer clerkIdParam
			) {
		

		Clerk clerk = clerkService.get(clerkIdParam);
		if (clerk == null) {
			return ParameterErrorResponse.getNotFoundResponse(NOT_FOUND_MES);
		}
		ClerkModel clerkModel = entity2model(clerk);
		return new ResponseEntity<ClerkModel>(clerkModel, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "content-type= application/json; charset=UTF-8")

	public ResponseEntity<?> createClerk(@Valid @RequestBody ClerkModel clerkModel, Errors e) {
		
		if (e.hasErrors()) {
			return new ValidationErrorResponse().getValidationErrorResponse(e);
		}
		Clerk clerk = model2entity(clerkModel);
		clerkService.save(clerk);
		return new ResponseEntity<IdModel>(new IdModel(clerk.getId()), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateClerk(@Valid @RequestBody ClerkModel clerkModel, Errors e,
			@PathVariable(value = "id") Integer clerkIdParam) {
		

		if (e.hasErrors()) {
			return new ValidationErrorResponse().getValidationErrorResponse(e);
		}

		Clerk clerk = clerkService.get(clerkIdParam);
		if (clerk == null) {
			return ParameterErrorResponse.getNotFoundResponse(NOT_FOUND_MES);
		}
		clerk.setClerkFullName(clerkModel.getClerkFullName());
		clerk.setClerkLoginName(clerkModel.getClerkLoginName());
		clerk.setRoleId(clerkModel.getRoleId());

		clerkService.save(clerk);
		client.deleteMemCached(clerkIdParam);// delete memcached link
		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteClerk(@PathVariable(value = "id") Integer clerkIdParam
		) {
	
		Clerk clerk = new Clerk();
		clerk = clerkService.get(clerkIdParam);
		if (clerk == null) {
			return ParameterErrorResponse.getNotFoundResponse(NOT_FOUND_MES);
		}
		clerkService.delete(clerk);
		client.deleteMemCached(clerkIdParam);// delete memcached link
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
	private ClerkRepoModel entity2CRmodel(ClerkRepo clerk) {
		ClerkRepoModel model = new ClerkRepoModel();
		model.setId(clerk.getId());
		model.setClerkFullName(clerk.getClerkFullName());
		model.setDebtors(clerk.getDebtors());

		return model;
	}
}
