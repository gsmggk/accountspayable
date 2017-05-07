package com.gsmggk.accountspayable.webapp.controllers;

import java.util.ArrayList;
import java.util.Date;
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

import com.gsmggk.accountspayable.dao4api.modelmap.DebtorControl;
import com.gsmggk.accountspayable.dao4api.modelmap.DebtorRepo;
import com.gsmggk.accountspayable.dao4api.modelmap.DebtorState;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtor;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtors4Boss;
import com.gsmggk.accountspayable.dao4api.params.ParamsDebtors4Clerk;
import com.gsmggk.accountspayable.datamodel.Debtor;
import com.gsmggk.accountspayable.services.IDebtorService;
import com.gsmggk.accountspayable.services.util.UserSessionStorage;
import com.gsmggk.accountspayable.webapp.models.DebtorControlModel;
import com.gsmggk.accountspayable.webapp.models.DebtorModel;
import com.gsmggk.accountspayable.webapp.models.DebtorRepoModel;
import com.gsmggk.accountspayable.webapp.models.DebtorStateModel;
import com.gsmggk.accountspayable.webapp.models.IdModel;
import com.gsmggk.accountspayable.webapp.models.utils.ConvertUtils;
import com.gsmggk.accountspayable.webapp.validate.ParameterErrorResponse;
import com.gsmggk.accountspayable.webapp.validate.ValidationErrorResponse;

@RestController
@RequestMapping("/{prefix}/debtors")
public class DebtorController {
	private static final String NOT_FOUND_MES = "Debrot not found";
	@Inject
	private ApplicationContext appContext;
	@Inject
	private IDebtorService debtorService;
	
	/**
	 * Get debtors report. Count all operations for each debtor, from-to date period. Use in Boss layer.
	 * @param params search, sort, pagination parameter
	 * @param e
	 * @param from date from
	 * @param to date to
	 * @return list debtors with operations count
	 */
	@RequestMapping(value = "/repo", method = RequestMethod.POST)
	public ResponseEntity<?> getDebtorRepo(@Valid @RequestBody ParamsDebtor params, Errors e,
			@RequestParam("from") Date from,@RequestParam("to") Date to) {
		List<DebtorRepo> allDebtors;

	
			allDebtors = debtorService.getDebtorRepo(from, to, params);
		
		List<DebtorRepoModel> converterModel = new ArrayList<>();
		for (DebtorRepo debtor : allDebtors) {
			converterModel.add(entity2DRmodel(debtor));
		}
		return new ResponseEntity<List<DebtorRepoModel>>(converterModel, HttpStatus.OK);
	}
	

	

	/**
	 * Reopen debtors act. Change act with action (9) to action (1).
	 * @param debtorIdParam
	 * @return
	 */
	@RequestMapping(value = "/reopen/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> reopenDebtorAct(@PathVariable(value = "id") Integer debtorIdParam) {

		UserSessionStorage storage = appContext.getBean(UserSessionStorage.class);
		debtorService.reopenDedtor(storage.getId(), debtorIdParam);

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * Close debtor act. Use in Boss layer.
	 * @param debtorIdParam debtor id
	 * @return
	 */
	@RequestMapping(value = "/close/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> closeDebtorAct(@PathVariable(value = "id") Integer debtorIdParam) {

		UserSessionStorage storage = appContext.getBean(UserSessionStorage.class);
		debtorService.closeDedtor(storage.getId(), debtorIdParam);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * Add new debtor. Insert operation with action (9) for new debtor. Clerk
	 * conduct this operation have access create debtors. Use in Boss layer.
	 * 
	 * @param debtorModel
	 * @param e
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<?> insertDebtor(@Valid @RequestBody DebtorModel debtorModel, Errors e) {
		if (e.hasErrors()) {
			return new ValidationErrorResponse().getValidationErrorResponse(e);
		}

		Debtor debtor = new Debtor();
		loadFromModel(debtorModel, debtor);
		UserSessionStorage storage = appContext.getBean(UserSessionStorage.class);
		Integer clerkId = storage.getId();
		debtorService.saveDebtor(clerkId, debtor);
		return new ResponseEntity<IdModel>(new IdModel(debtor.getId()), HttpStatus.CREATED);
	}

	/**
	 * 
	 * Clerk edit debtor contact information. Clerk allocated with debtor. Clerk
	 * have access to edit action (8). Used in Work layer.
	 * 
	 * @param debtorModel
	 *            debtor info
	 * @param e
	 * @param debtorIdParam
	 *            debtor id
	 * @return
	 */
	@RequestMapping(value = "/save/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> saveDebtor(@Valid @RequestBody DebtorModel debtorModel, Errors e,
			@PathVariable(value = "id") Integer debtorIdParam) {

		if (e.hasErrors()) {
			return new ValidationErrorResponse().getValidationErrorResponse(e);
		}

		Debtor debtor = debtorService.get(debtorIdParam);
		if (debtor == null) {
			return ParameterErrorResponse.getNotFoundResponse(NOT_FOUND_MES);
		}
		loadFromModel(debtorModel, debtor);
		UserSessionStorage storage = appContext.getBean(UserSessionStorage.class);
		Integer clerkId = storage.getId();
		debtorService.saveDebtor(clerkId, debtor);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * Get list debtors with control date sort by control date. Can sort,
	 * search, paginate list.Used be Work layer.
	 * 
	 * @author Gena
	 *
	 */
	@RequestMapping(value = "/4clerk", method = RequestMethod.POST)
	public ResponseEntity<?> getDebtors4Clerk(@Valid @RequestBody ParamsDebtors4Clerk params, Errors e) {

		UserSessionStorage storage = appContext.getBean(UserSessionStorage.class);
		Integer clerkIdParam = storage.getId();

		List<DebtorControl> allDebtors;

		if (params == null || params.nullable()) {
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

	/**
	 * Get debtors list for boss.Include open or close state of debtor. Use in
	 * Boss layer.
	 * 
	 * @param params
	 *            Search, sort, paginate parameters
	 * @param e
	 *            validation error
	 * @return DebtorStateModel list
	 */
	@RequestMapping(value = "/4boss", method = RequestMethod.POST)
	public ResponseEntity<?> getDebtors4Boss(@Valid @RequestBody ParamsDebtors4Boss params, Errors e) {
		List<DebtorState> allDebtors;
		if (params == null || params.nullable()) {
			allDebtors = debtorService.getDebtors4Boss();
		} else {
			allDebtors = debtorService.getDebtors4Boss(params);
		}
		List<DebtorStateModel> converterModel = new ArrayList<>();
		for (DebtorState debtor : allDebtors) {
			converterModel.add(entity2DSmodel(debtor));
		}
		return new ResponseEntity<List<DebtorStateModel>>(converterModel, HttpStatus.OK);
	}

	/**
	 * Get debtors list allocated or not to user. Use in Boss layer.
	 * 
	 * @param params
	 *            sort, search, paginate parameter
	 * @param e
	 * @param flag
	 *            allocated flag - if true select only debtors allocated to
	 *            clerk. If false - debtors without allocation to clerk
	 * @return
	 */
	@RequestMapping(value = "/alloc", method = RequestMethod.POST)
	public ResponseEntity<?> getAllocatedDebtors(@Valid @RequestBody ParamsDebtor params, Errors e,
			@RequestParam("allocated") Boolean flag) {
		List<Debtor> allDebtors;

		if (params == null || params.nullable()) {
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

	/**
	 * Get Debtor by id. Can be use in Admin and Work layers.
	 * 
	 * @param debtorIdParam
	 * @return
	 */
	@RequestMapping(value = { "/{id}", "/get/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable(value = "id") Integer debtorIdParam) {

		Debtor debtor = debtorService.get(debtorIdParam);
		if (debtor == null) {
			return ParameterErrorResponse.getNotFoundResponse(NOT_FOUND_MES);
		}
		DebtorModel debtorModel = entity2model(debtor);
		return new ResponseEntity<DebtorModel>(debtorModel, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "content-type= application/json; charset=UTF-8")

	public ResponseEntity<?> createDebtor(@Valid @RequestBody DebtorModel debtorModel, Errors e) {

		if (e.hasErrors()) {
			return new ValidationErrorResponse().getValidationErrorResponse(e);
		}
		Debtor debtor = model2entity(debtorModel);
		debtorService.save(debtor);
		return new ResponseEntity<IdModel>(new IdModel(debtor.getId()), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateDebtor(@Valid @RequestBody DebtorModel debtorModel, Errors e,
			@PathVariable(value = "id") Integer debtorIdParam) {

		if (e.hasErrors()) {
			return new ValidationErrorResponse().getValidationErrorResponse(e);
		}

		Debtor debtor = debtorService.get(debtorIdParam);
		if (debtor == null) {
			return ParameterErrorResponse.getNotFoundResponse(NOT_FOUND_MES);
		}
		loadFromModel(debtorModel, debtor);

		debtorService.save(debtor);
		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteDebtor(@PathVariable(value = "id") Integer debtorIdParam) {

		Debtor debtor = new Debtor();
		debtor = debtorService.get(debtorIdParam);
		if (debtor == null) {
			return ParameterErrorResponse.getNotFoundResponse(NOT_FOUND_MES);
		}
		debtorService.delete(debtor);
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
		model.setControl(ConvertUtils.date2string(debtor.getControl()));
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
	private DebtorRepoModel entity2DRmodel(DebtorRepo debtor) {
		DebtorRepoModel model = new DebtorRepoModel();
	
		model.setShortName(debtor.getShortName());
		model.setFullName(debtor.getFullName());
		model.setCount(debtor.getCount());
		return model;
	}

}
