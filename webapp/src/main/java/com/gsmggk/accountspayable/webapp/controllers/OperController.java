package com.gsmggk.accountspayable.webapp.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gsmggk.accountspayable.datamodel.Oper;
import com.gsmggk.accountspayable.services.IOperService;
import com.gsmggk.accountspayable.services.util.UserSessionStorage;
import com.gsmggk.accountspayable.webapp.models.OperModel;
import com.gsmggk.accountspayable.webapp.models.utils.ConvertUtils;
import com.gsmggk.accountspayable.webapp.models.IdModel;
import com.gsmggk.accountspayable.webapp.models.OperDebtorModel;
import com.gsmggk.accountspayable.webapp.models.OperInsertModel;
import com.gsmggk.accountspayable.webapp.validate.ParameterErrorResponse;
import com.gsmggk.accountspayable.webapp.validate.ValidationErrorResponse;

@RestController
@RequestMapping("/{prefix}/opers")
public class OperController {
	private static final String NOT_FOUND_MES = "Operation not found";
	@Inject
	private IOperService operService;
	@Inject
	private ApplicationContext appContext;
	
	/**
	 * Clear allocation debtor to clerk.
	 * @param debtorIdParam
	 * @param clerkIdParam
	 * @return
	 */
	@RequestMapping(value = "/debtor2clerk/del",method = RequestMethod.DELETE)

	public ResponseEntity<?> unlinkDebtor2ClerkOper(@RequestParam("debtorid") Integer debtorIdParam,
			@RequestParam("clerkid") Integer clerkIdParam	) {
		
		UserSessionStorage storage = appContext.getBean(UserSessionStorage.class);
	
		operService.unlinkDebtor2Clerk(storage.getId(), debtorIdParam, clerkIdParam);
		return new ResponseEntity<>( HttpStatus.OK);
	}
	
	/**
	 * Allocate debtor to clerk.
	 * @param debtorIdParam
	 * @param clerkIdParam
	 * @return
	 */
	@RequestMapping(value = "/debtor2clerk/add",method = RequestMethod.PUT)

	public ResponseEntity<?> linkDebtor2ClerkOper(@RequestParam("debtorid") Integer debtorIdParam,
			@RequestParam("clerkid") Integer clerkIdParam	) {
		
		UserSessionStorage storage = appContext.getBean(UserSessionStorage.class);
	
		operService.linkDebtor2Clerk(storage.getId(), debtorIdParam, clerkIdParam);
		return new ResponseEntity<>( HttpStatus.OK);
	}
	
	
	
	/**
	 * Clerk delete operation. If have access to operations action and debtor assigned to him.
	 * @param operIdParam operation id
	 * @return
	 */
	@RequestMapping(value = "/oper/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteOperLog(@PathVariable(value = "id") Integer operIdParam) {
		Oper oper = new Oper();
	
		oper = operService.get(operIdParam);
			if (oper == null) {	return ParameterErrorResponse.getNotFoundResponse(NOT_FOUND_MES);}
			UserSessionStorage storage = appContext.getBean(UserSessionStorage.class);	
		operService.deleteOper(storage.getId(), oper);
		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}
	
	
	/**
	 * 
	 * Clerk edit debtors operation. Used in Work layer.
	 * @param operModel
	 * @param e
	 * @param operIdParam
	 * @return
	 */
	@RequestMapping(value = "/oper/{id}",method = RequestMethod.PUT)

	public ResponseEntity<?> updateOper(@Valid @RequestBody OperInsertModel operModel, Errors e,
			@PathVariable(value = "id") Integer operIdParam) {
		if (e.hasErrors()) {
			return new ValidationErrorResponse().getValidationErrorResponse(e);
		}
		
		Oper oper = operService.getOper(operIdParam);
		if (oper == null) {	return ParameterErrorResponse.getNotFoundResponse(NOT_FOUND_MES);}
			
		oper.setActionId(operModel.getActionId());
		oper.setOperDesc(operModel.getOperDesc());
		oper.setControlDate(ConvertUtils.string2date(operModel.getControlDate()));
	
		UserSessionStorage storage = appContext.getBean(UserSessionStorage.class);
	
		operService.updateOper(storage.getId(),oper);
		return new ResponseEntity<>( HttpStatus.OK);
	}
	
	/**
	 * Clerk add operation for debtor. Debtor allocated with clerk.
	 * Clerk have access to action. Used in Work layer.
	 * @param operModel -operation detail
	 * @param e
	 * @return
	 */
	@RequestMapping(value = "/oper",method = RequestMethod.POST)

	public ResponseEntity<?> addOper(@Valid @RequestBody OperInsertModel operModel, Errors e) {
		if (e.hasErrors()) {
			return new ValidationErrorResponse().getValidationErrorResponse(e);
		}
		Oper oper = model2OIentity(operModel);
		UserSessionStorage storage = appContext.getBean(UserSessionStorage.class);
		oper.setClerkId(storage.getId());
		operService.addOper(oper);
		return new ResponseEntity<IdModel>(new IdModel(oper.getId()), HttpStatus.CREATED);
	}
	
	

	/**
	 * Get list of opers for debtor. Used by Work layer.
	 * @param debtorIdParam debtor id
	 * @return
	 */
	@RequestMapping(value = "/4debtor/{debtorId}", method = RequestMethod.GET)
	public ResponseEntity<?> getOpers4Debtor(@PathVariable(value = "debtorId") Integer debtorIdParam) {
		List<Oper> allOpers;
		allOpers = operService.getOpers4Debtor(debtorIdParam);

		List<OperDebtorModel> converterModel = new ArrayList<>();
		for (Oper oper : allOpers) {
			converterModel.add(entity2ODmodel(oper));
		}
		return new ResponseEntity<List<OperDebtorModel>>(converterModel, HttpStatus.OK);
	}

	
//============================default controlers=============================
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
		
		if (oper == null) {	return ParameterErrorResponse.getNotFoundResponse(NOT_FOUND_MES);}
		
		OperModel operModel = entity2model(oper);
		return new ResponseEntity<OperModel>(operModel, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)

	public ResponseEntity<?> createOper(@Valid @RequestBody OperModel operModel, Errors e) {
		if (e.hasErrors()) {
			return new ValidationErrorResponse().getValidationErrorResponse(e);
		}
		Oper oper = model2entity(operModel);
		operService.save(oper);
		return new ResponseEntity<IdModel>(new IdModel(oper.getId()), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateOper(@Valid @RequestBody OperModel operModel, Errors e,
			@PathVariable(value = "id") Integer operIdParam) {
		if (e.hasErrors()) {
			return new ValidationErrorResponse().getValidationErrorResponse(e);
		}

		Oper oper = operService.get(operIdParam);
		if (oper == null) {	return ParameterErrorResponse.getNotFoundResponse(NOT_FOUND_MES);}
		
		loadFromModel(operModel, oper);

		operService.save(oper);
		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteOper(@PathVariable(value = "id") Integer operIdParam) {
		Oper oper = new Oper();
	
		oper = operService.get(operIdParam);
			if (oper == null) {	return ParameterErrorResponse.getNotFoundResponse(NOT_FOUND_MES);}
		operService.delete(oper);
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
	private OperDebtorModel entity2ODmodel(Oper oper) {
		OperDebtorModel model = new OperDebtorModel();
		model.setId(oper.getId());
		
		model.setClerkId(oper.getClerkId());
		model.setActionId(oper.getActionId());
		model.setActionDate(ConvertUtils.timestmp2string(oper.getActionDate()));
		model.setControlDate(ConvertUtils.date2string(oper.getControlDate()));
		model.setOperDesc(oper.getOperDesc());

		return model;
	}
	
	private Oper model2OIentity(OperInsertModel model) {
		Oper oper = new Oper();
		oper.setDebtorId(model.getDebtorId());
		oper.setActionId(model.getActionId());
		oper.setOperDesc(model.getOperDesc());
		oper.setControlDate(ConvertUtils.string2date(model.getControlDate()));
		
		return oper;
	}
}
