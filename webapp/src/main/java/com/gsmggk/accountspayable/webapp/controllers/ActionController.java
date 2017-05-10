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

import com.gsmggk.accountspayable.datamodel.Action;
import com.gsmggk.accountspayable.datamodel.Role;
import com.gsmggk.accountspayable.services.IActionService;
import com.gsmggk.accountspayable.services.IClerkService;
import com.gsmggk.accountspayable.services.IRoleService;
import com.gsmggk.accountspayable.services.util.UserSessionStorage;
import com.gsmggk.accountspayable.webapp.models.ActionModel;
import com.gsmggk.accountspayable.webapp.models.IdModel;
import com.gsmggk.accountspayable.webapp.models.RoleModel;
import com.gsmggk.accountspayable.webapp.validate.ParameterErrorResponse;
import com.gsmggk.accountspayable.webapp.validate.ValidationErrorResponse;

@RestController
@RequestMapping("/{prefix}/actions")
public class ActionController {
	private static final String NOT_FOUND_MES = "Action not found";

	@Inject
	private IActionService actionService;
	@Inject
	private IRoleService roleService;
	@Inject
	private IClerkService clerkService;
	@Inject
	private ApplicationContext appContext;

	/**
	 * Add link action to role. Use in Admin layer.
	 * @param actionIdParam
	 * @param roleIdParam
	 * @return
	 */
	@RequestMapping(value = "/add2role", method = RequestMethod.POST)
	public ResponseEntity<?> addAction2Role(@RequestParam("actionid") Integer actionIdParam,
			@RequestParam("roleid") Integer roleIdParam	) {

		roleService.addAction2Role(actionIdParam, roleIdParam);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	/**
	 * Clear lick action to role. Use in Admin layer.
	 * @param actionIdParam
	 * @param roleIdParam
	 * @return
	 */
	@RequestMapping(value = "/del2role", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteAction2Role(@RequestParam("actionid") Integer actionIdParam,
			@RequestParam("roleid")  Integer roleIdParam

	) {

		roleService.deleteAction2Role(actionIdParam, roleIdParam);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	/**
	 * Get actions for clerk.Used in Work layer. 
	 * @return
	 */
	@RequestMapping(value = "/4clerk", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getActions4Clerk() {
	
		List<Action> allActions;
		UserSessionStorage storage = appContext.getBean(UserSessionStorage.class);
		allActions = clerkService.getActions4Clerk(storage.getId());
		
		List<ActionModel> converterModel = new ArrayList<>();
		for (Action action : allActions) {
			converterModel.add(entity2model(action));
		}

		return new ResponseEntity<List<ActionModel>>(converterModel, HttpStatus.OK);
	}
	
	/**
	 * Get actions list for role. Used in Admin layer.
	 * @param roleIdParam role id
	 * @return
	 */
	@RequestMapping(value = "/4role/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getActions4Role(@PathVariable(value = "id") Integer roleIdParam	) {
		
		Role role = roleService.get(roleIdParam);
		if (role == null) {
			return ParameterErrorResponse.getNotFoundResponse("Role not found");
		}
		List<Action> allActions;
		allActions = roleService.getActions4Role(roleIdParam);
		List<ActionModel> converterModel = new ArrayList<>();
		for (Action action : allActions) {
			converterModel.add(entity2model(action));
		}
		
	
		return new ResponseEntity<List<ActionModel>>(converterModel, HttpStatus.OK);
	}
	
	
//==========================default actions===============================
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<ActionModel>> getAll() {

		List<Action> allActions;
		allActions = actionService.getAll();

		List<ActionModel> converterModel = new ArrayList<>();
		for (Action action : allActions) {
			converterModel.add(entity2model(action));
		}

		return new ResponseEntity<List<ActionModel>>(converterModel, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable(value = "id") Integer actionIdParam) {

		Action action = actionService.get(actionIdParam);
		if (action == null) {
			return ParameterErrorResponse.getNotFoundResponse(NOT_FOUND_MES);
		}

		ActionModel actionModel = entity2model(action);
		return new ResponseEntity<ActionModel>(actionModel, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "content-type= application/json; charset=UTF-8")

	public ResponseEntity<?> createAction(@Valid @RequestBody ActionModel actionModel, Errors e) {

		if (e.hasErrors()) {
			return new ValidationErrorResponse().getValidationErrorResponse(e);
		}
		Action action = model2entity(actionModel);
		actionService.save(action);
		return new ResponseEntity<IdModel>(new IdModel(action.getId()), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateAction(@Valid @RequestBody ActionModel actionModel, Errors e,

			@PathVariable(value = "id") Integer actionIdParam) {

		if (e.hasErrors()) {
			return new ValidationErrorResponse().getValidationErrorResponse(e);
		}

		Action action = actionService.get(actionIdParam);
		if (action == null) {
			return ParameterErrorResponse.getNotFoundResponse(NOT_FOUND_MES);
		}
		action.setActionName(actionModel.getActionName());
		action.setDuration(actionModel.getDuration());
		action.setActionDesc(actionModel.getActionDesc());

		actionService.save(action);
		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteAction(@PathVariable(value = "id") Integer actionIdParam) {

		Action action = new Action();
		action = actionService.get(actionIdParam);
		if (action == null) {
			return ParameterErrorResponse.getNotFoundResponse(NOT_FOUND_MES);
		}
		actionService.delete(action);
		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	private Action model2entity(ActionModel model) {
		Action action = new Action();
		action.setActionName(model.getActionName());
		action.setDuration(model.getDuration());
		action.setActionDesc(model.getActionDesc());
		return action;
	}

	private ActionModel entity2model(Action action) {
		ActionModel model = new ActionModel();
		model.setId(action.getId());
		model.setActionName(action.getActionName());
		model.setDuration(action.getDuration());
		model.setActionDesc(action.getActionDesc());
		return model;
	}

}
