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

import com.gsmggk.accountspayable.datamodel.Account;
import com.gsmggk.accountspayable.services.IAccountService;
import com.gsmggk.accountspayable.webapp.models.AccountModel;
import com.gsmggk.accountspayable.webapp.models.IdModel;
import com.gsmggk.accountspayable.webapp.validate.ValidationErrorRestonse;

@RestController
@RequestMapping("/{prefix}/accounts")
public class AccountController {
	@Inject
	private IAccountService accountService;
	
	@RequestMapping(value = "/4debtor/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getAccounts4Debtor(
			@PathVariable(value = "prefix") String prefix,
			@PathVariable(value = "id") Integer debtorIdParam) {
		if (!prefix.toLowerCase().equals("work")){ return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
		
		List<Account> allAccounts = accountService.getAccounts4Debtor(debtorIdParam);
		
		
		List<AccountModel> converterModel = new ArrayList<>();
		for (Account account : allAccounts) {
			converterModel.add(entity2model(account));
		}
	
		return new ResponseEntity<List<AccountModel> >(converterModel, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<AccountModel>> getAll(@PathVariable(value = "prefix") String prefix) {
		if (!prefix.toLowerCase().equals("admin")){ return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
		
		List<Account> allAccounts;
		allAccounts = accountService.getAll();

		List<AccountModel> converterModel = new ArrayList<>();
		for (Account account : allAccounts) {
			converterModel.add(entity2model(account));
		}

		return new ResponseEntity<List<AccountModel>>(converterModel, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable(value = "id") Integer accountIdParam) {
		Account account = accountService.get(accountIdParam);
		if (account==null){
			   String bodyOfResponse = "{\"error\":\"Account not exists.\"}";
				return new ResponseEntity<String>(bodyOfResponse,HttpStatus.BAD_REQUEST);
		}
		AccountModel accountModel = entity2model(account);
		return new ResponseEntity<AccountModel>(accountModel, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "content-type= application/json; charset=UTF-8")

	public ResponseEntity<?> createAccount(@Valid @RequestBody AccountModel accountModel, Errors e) {
		if (e.hasErrors()) {
			return new ValidationErrorRestonse().getValidationErrorRestonse(e);
		}
		Account account = model2entity(accountModel);
		accountService.save(account);
		return new ResponseEntity<IdModel>(new IdModel(account.getId()), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateAccount(@Valid @RequestBody AccountModel accountModel, Errors e,
			@PathVariable(value = "id") Integer accountIdParam) {
		if (e.hasErrors()) {
			return new ValidationErrorRestonse().getValidationErrorRestonse(e);
		}

		Account account = accountService.get(accountIdParam);

		account.setAccountName(accountModel.getAccountName());
		account.setSumm(accountModel.getSumm());
		account.setDebtorId(accountModel.getDebtorId());
		

		accountService.save(account);
		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteAccount(@PathVariable(value = "id") Integer accountIdParam) {
		Account account = new Account();
		account = accountService.get(accountIdParam);
		accountService.delete(account);
		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	private Account model2entity(AccountModel model) {
		Account account = new Account();
		account.setId(model.getId());
		account.setAccountName(model.getAccountName());
		account.setSumm(model.getSumm());
		account.setDebtorId(model.getDebtorId());

		return account;
	}

	private AccountModel entity2model(Account account) {
		AccountModel model = new AccountModel();
		model.setId(account.getId());
		model.setAccountName(account.getAccountName());
		model.setSumm(account.getSumm());
		model.setDebtorId(account.getDebtorId());

		return model;
	}

}
