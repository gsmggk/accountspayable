package com.gsmggk.accountspayable.webapp.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gsmggk.accountspayable.dao4api.modelmap.DebtorRepo;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtor;
import com.gsmggk.accountspayable.services.IDebtorService;
import com.gsmggk.accountspayable.services.util.SoftCach;
import com.gsmggk.accountspayable.services.util.cach.CachKey;
import com.gsmggk.accountspayable.webapp.models.DebtorRepoModel;

@RestController
@RequestMapping("/{prefix}/research")
public class ResearchController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ResearchController.class);

	@Inject
	private IDebtorService debtorService;

	@Inject
	private SoftCach cach;

	// ============================================================================

	@RequestMapping(value = "/cache2disk", method = RequestMethod.POST)
	public ResponseEntity<?> cache2disk() {
		cach.cache2disk();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/disk2cache", method = RequestMethod.POST)
	public ResponseEntity<?> disk2cache() {
		cach.disk2cache();
		return new ResponseEntity<String>(HttpStatus.OK);

	}

	@RequestMapping(value = "/flush", method = RequestMethod.DELETE)
	public ResponseEntity<?> flush() {
		cach.flush();
		return new ResponseEntity<String>(HttpStatus.OK);

	}

	// =================================================================================

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/repo", method = RequestMethod.POST)
	public ResponseEntity<?> getDebtorRepo(@Valid @RequestBody ParamsDebtor params, Errors e,
			@RequestParam("from") Date from, @RequestParam("to") Date to) {
		LOGGER.info("Generate KEY for SoftCache");
		CachKey key = request2key(from, to, params); // Generate KEY

		List<DebtorRepoModel> converterModel;
		// Search in cache for key, if found return response data
		converterModel = (List<DebtorRepoModel>) cach.getCache(key);

		if (converterModel != null) {
			LOGGER.info("Return VALUE from SoftCache");
			return new ResponseEntity<List<DebtorRepoModel>>(converterModel, HttpStatus.OK);
		}

		// IF in cache not found. Load from database. Put it in cache.
		List<DebtorRepo> allDebtors;
		allDebtors = debtorService.getDebtorRepo(from, to, params);

		converterModel = getResponseModel(allDebtors);
		// Put object into cache
		cach.putCache(key, 180, converterModel);
		LOGGER.info("Return VALUE from PostgreSQL");
		return new ResponseEntity<List<DebtorRepoModel>>(converterModel, HttpStatus.OK);
	}

	private List<DebtorRepoModel> getResponseModel(List<DebtorRepo> allDebtors) {
		List<DebtorRepoModel> converterModel = new ArrayList<>();
		for (DebtorRepo debtor : allDebtors) {
			converterModel.add(entity2DRmodel(debtor));
		}
		return converterModel;
	}

	private CachKey request2key(Date from, Date to, ParamsDebtor params) {
		CachKey key = new CachKey();
		key.setFrom(from);
		key.setTo(to);
		key.setParams(params);

		return key;
	}

	private DebtorRepoModel entity2DRmodel(DebtorRepo debtor) {
		DebtorRepoModel model = new DebtorRepoModel();

		model.setShortName(debtor.getShortName());
		model.setFullName(debtor.getFullName());
		model.setCount(debtor.getCount());
		return model;
	}

}
