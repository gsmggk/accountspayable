package com.gsmggk.accountspayable.webapp.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.gsmggk.accountspayable.dao4api.modelmap.DebtorControl;
import com.gsmggk.accountspayable.dao4api.modelmap.DebtorState;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtor;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtors4Boss;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtors4Clerk;
import com.gsmggk.accountspayable.datamodel.Debtor;
import com.gsmggk.accountspayable.services.IDebtorService;
import com.gsmggk.accountspayable.webapp.models.DebtorControlModel;
import com.gsmggk.accountspayable.webapp.models.DebtorModel;
import com.gsmggk.accountspayable.webapp.models.DebtorStateModel;
import com.gsmggk.accountspayable.webapp.models.IdModel;
import com.gsmggk.accountspayable.webapp.validate.ValidationErrorRestonse;

@RestController
@RequestMapping("/debtors")
public class DebtorController {

	@Inject
	private IDebtorService debtorService;

	@RequestMapping(value = "/4clerk/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> getDebtors4Clerk(@Valid @RequestBody ParamsDebtors4Clerk params, Errors e,
			@PathVariable(value = "id") Integer clerkIdParam) {
		List<DebtorControl> allDebtors;
		if (params == null) {
			allDebtors = debtorService.getDebtors4Clerk(clerkIdParam);
		} else {
			allDebtors = debtorService.getDebtors4Clerk(clerkIdParam, params);
		}
		List<DebtorControlModel> converterModel = new ArrayList<>();
		for (DebtorControl debtor : allDebtors) {
			converterModel.add(entity2DCmodel(debtor));
		}
		return new ResponseEntity<List<DebtorControlModel>>(converterModel, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/4boss", method = RequestMethod.POST)
	public ResponseEntity<?> getDebtors4Boss(@Valid @RequestBody ParamsDebtors4Boss params, Errors e
			) {
		List<DebtorState> allDebtors;
		if (params == null) {
			allDebtors = debtorService.getDebtors4Boss();
		} else {
			allDebtors = debtorService.getDebtors4Boss( params);
		}
		List<DebtorStateModel> converterModel = new ArrayList<>();
		for (DebtorState debtor : allDebtors) {
			converterModel.add(entity2DSmodel(debtor));
		}
		return new ResponseEntity<List<DebtorStateModel>>(converterModel, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/alloc/{flag}", method = RequestMethod.POST)
	public ResponseEntity<?> getAllocatedDebtors(@Valid @RequestBody ParamsDebtor params, Errors e,
			@PathVariable(value = "flag") Boolean flag) {
		List<Debtor> allDebtors;
		if (params == null) {
			allDebtors = debtorService.getAllocatedDebtors(flag);
		} else {
			allDebtors = debtorService.getAllocatedDebtors(flag, params);
		}
		List<DebtorModel> converterModel = new ArrayList<>();
		for (Debtor debtor : allDebtors) {
			converterModel.add(entity2model(debtor));
		}
		return new ResponseEntity<List<DebtorModel>>(converterModel, HttpStatus.OK);
	}
	
	
	
	
	

	// ---------------- defaults-------------------------------------

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<DebtorModel>> getAll() {
		List<Debtor> allDebtors;
		allDebtors = debtorService.getAll();

		List<DebtorModel> converterModel = new ArrayList<>();
		for (Debtor debtor : allDebtors) {
			converterModel.add(entity2model(debtor));
		}

		return new ResponseEntity<List<DebtorModel>>(converterModel, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable(value = "id") Integer debtorIdParam) {
		Debtor debtor = debtorService.get(debtorIdParam);
		if (debtor == null) {
			String bodyOfResponse = "{\"error\":\"Debtor not exists.\"}";
			return new ResponseEntity<String>(bodyOfResponse, HttpStatus.BAD_REQUEST);
		}
		DebtorModel debtorModel = entity2model(debtor);
		return new ResponseEntity<DebtorModel>(debtorModel, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "content-type= application/json; charset=UTF-8")

	public ResponseEntity<?> createDebtor(@Valid @RequestBody DebtorModel debtorModel, Errors e) {
		if (e.hasErrors()) {
			return new ValidationErrorRestonse().getValidationErrorRestonse(e);
		}
		Debtor debtor = model2entity(debtorModel);
		debtorService.save(debtor);
		return new ResponseEntity<IdModel>(new IdModel(debtor.getId()), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateDebtor(@Valid @RequestBody DebtorModel debtorModel, Errors e,
			@PathVariable(value = "id") Integer debtorIdParam) {
		if (e.hasErrors()) {
			return new ValidationErrorRestonse().getValidationErrorRestonse(e);
		}

		Debtor debtor = debtorService.get(debtorIdParam);

		loadFromModel(debtorModel, debtor);

		debtorService.save(debtor);
		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	private void loadFromModel(DebtorModel debtorModel, Debtor debtor) {
		debtor.setShortName(debtorModel.getShortName());
		debtor.setFullName(debtorModel.getFullName());
		debtor.setAddress(debtorModel.getAddress());
		debtor.setPhones(debtorModel.getPhones());
		debtor.setFamily(debtorModel.getFamily());
		debtor.setJobe(debtorModel.getJobe());
		debtor.setOther(debtorModel.getOther());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteDebtor(@PathVariable(value = "id") Integer debtorIdParam) {
		Debtor debtor = new Debtor();
		debtor = debtorService.get(debtorIdParam);
		debtorService.delete(debtor);
		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	private Debtor model2entity(DebtorModel model) {
		Debtor debtor = new Debtor();
		debtor.setId(model.getId());
		loadFromModel(model, debtor);

		return debtor;
	}

	private DebtorModel entity2model(Debtor debtor) {
		DebtorModel model = new DebtorModel();
		model.setId(debtor.getId());
		model.setShortName(debtor.getShortName());
		model.setFullName(debtor.getFullName());
		model.setAddress(debtor.getAddress());
		model.setPhones(debtor.getPhones());
		model.setFamily(debtor.getFamily());
		model.setJobe(debtor.getJobe());
		model.setOther(debtor.getOther());

		return model;
	}

	private DebtorControlModel entity2DCmodel(DebtorControl debtor) {
		DebtorControlModel model = new DebtorControlModel();
		model.setId(debtor.getDebtorId());
		model.setShortName(debtor.getShortName());
		model.setFullName(debtor.getFullName());
		Date dateFrom = debtor.getControl();
		if (dateFrom == null) {
			model.setControl("Не установлена");
			return model;
		}
		DateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");

		model.setControl(dFormat.format(dateFrom));

		return model;
	}
	private DebtorStateModel entity2DSmodel(DebtorState debtor) {
		DebtorStateModel model = new DebtorStateModel();
		model.setId(debtor.getDebtorId());
		model.setShortName(debtor.getShortName());
		model.setFullName(debtor.getFullName());
		model.setActive(debtor.getActive().toString());
		return model;
	}
}
