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

import com.gsmggk.accountspayable.datamodel.Action;
import com.gsmggk.accountspayable.services.IActionService;
import com.gsmggk.accountspayable.services.IRoleService;
import com.gsmggk.accountspayable.webapp.models.ActionModel;
import com.gsmggk.accountspayable.webapp.models.IdModel;
import com.gsmggk.accountspayable.webapp.validate.ParameterErrorResponse;
import com.gsmggk.accountspayable.webapp.validate.ValidationErrorRestonse;

@RestController
@RequestMapping("/{prefix}/actions")
public class ActionController {
	private static final String NOT_FOUND_MES="Action not found";

	@Inject
	private IActionService actionService;
	@Inject
	private IRoleService roleService;
	
	@RequestMapping(value = "/{actionid}/add2role/{roleid}", method = RequestMethod.PUT)
	public ResponseEntity<?> addAction2Role(
			@PathVariable(value = "actionid") Integer actionIdParam,
			@PathVariable(value = "roleid") Integer roleIdParam,
			@PathVariable(value = "prefix") String prefix
			) {
	
	     roleService.addAction2Role(actionIdParam, roleIdParam);

	     return new ResponseEntity<>(HttpStatus.OK);
	}
	

	@RequestMapping(value = "/{actionid}/clear2role/{roleid}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteAction2Role(
			@PathVariable(value = "actionid") Integer actionIdParam,
			@PathVariable(value = "roleid") Integer roleIdParam,
			@PathVariable(value = "prefix") String prefix
			) {
	
	     roleService.deleteAction2Role(actionIdParam, roleIdParam);

	     return new ResponseEntity<>(HttpStatus.OK);
	}
	

	@RequestMapping(value = "/4role/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getActions4Role(
			@PathVariable(value = "prefix") String prefix,
			@PathVariable(value = "id") Integer roleIdParam) {
		List<Action> allActions;
		allActions = roleService.getActions4Role(roleIdParam);
		if (allActions.isEmpty()) {
			   String bodyOfResponse = "{\"error\":\"Role not exists.\"}";
			return new ResponseEntity<String>(bodyOfResponse,HttpStatus.BAD_REQUEST);
		}
		List<ActionModel> converterModel = new ArrayList<>();
		for (Action action : allActions) {
			converterModel.add(entity2model(action));
		}

		return new ResponseEntity<List<ActionModel>>(converterModel, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<ActionModel>> getAll(@PathVariable(value = "prefix") String prefix) {
		if (!prefix.toLowerCase().equals("admin")){ return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
		
		List<Action> allActions;
		allActions = actionService.getAll();

		List<ActionModel> converterModel = new ArrayList<>();
		for (Action action : allActions) {
			converterModel.add(entity2model(action));
		}

		return new ResponseEntity<List<ActionModel>>(converterModel, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable(value = "id") Integer actionIdParam,
			@PathVariable(value = "prefix") String prefix) {
		if (!prefix.toLowerCase().equals("admin")){ return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
		
		Action action = actionService.get(actionIdParam);
		if (action==null){
			   return ParameterErrorResponse.getNotFoundResponse(NOT_FOUND_MES);		}
		
		
		ActionModel actionModel = entity2model(action);
		return new ResponseEntity<ActionModel>(actionModel, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "content-type= application/json; charset=UTF-8")

	public ResponseEntity<?> createAction(@Valid @RequestBody ActionModel actionModel, Errors e,
			@PathVariable(value = "prefix") String prefix) {
		if (!prefix.toLowerCase().equals("admin")){ return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
		
		if (e.hasErrors()) {
			return new ValidationErrorRestonse().getValidationErrorRestonse(e);
		}
		Action action = model2entity(actionModel);
		actionService.save(action);
		return new ResponseEntity<IdModel>(new IdModel(action.getId()), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateAction(@Valid @RequestBody ActionModel actionModel, Errors e,
			@PathVariable(value = "prefix") String prefix,
			@PathVariable(value = "id") Integer actionIdParam) {
		if (!prefix.toLowerCase().equals("admin")){ return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
		
		if (e.hasErrors()) {
			return new ValidationErrorRestonse().getValidationErrorRestonse(e);
		}

		Action action = actionService.get(actionIdParam);
		if (action==null){
			 return ParameterErrorResponse.getNotFoundResponse(NOT_FOUND_MES);
		}
		action.setActionName(actionModel.getActionName());
		action.setDuration(actionModel.getDuration());

		actionService.save(action);
		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteAction(@PathVariable(value = "id") Integer actionIdParam,
			@PathVariable(value = "prefix") String prefix) {
		if (!prefix.toLowerCase().equals("admin")){ return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
		
		Action action = new Action();
		action = actionService.get(actionIdParam);
		if (action==null){
			 return ParameterErrorResponse.getNotFoundResponse(NOT_FOUND_MES);
		}
		actionService.delete(action);
		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	private Action model2entity(ActionModel model) {
		Action action = new Action();
		action.setActionName(model.getActionName());
		action.setDuration(model.getDuration());

		return action;
	}

	private ActionModel entity2model(Action action) {
		ActionModel model = new ActionModel();
		model.setId(action.getId());
		model.setActionName(action.getActionName());
		model.setDuration(action.getDuration());

		return model;
	}

}
