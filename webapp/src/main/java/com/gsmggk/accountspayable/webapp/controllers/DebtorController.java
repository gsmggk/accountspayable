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

import com.gsmggk.accountspayable.datamodel.Debtor;
import com.gsmggk.accountspayable.services.IDebtorService;
import com.gsmggk.accountspayable.webapp.models.DebtorModel;
import com.gsmggk.accountspayable.webapp.models.IdModel;
import com.gsmggk.accountspayable.webapp.validate.ValidationErrorRestonse;


@RestController
@RequestMapping("/debtors")
public class DebtorController {
	
	
		@Inject
		private IDebtorService debtorService;
		
		

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
			if (debtor==null){
				   String bodyOfResponse = "{\"error\":\"Debtor not exists.\"}";
					return new ResponseEntity<String>(bodyOfResponse,HttpStatus.BAD_REQUEST);
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

	}
