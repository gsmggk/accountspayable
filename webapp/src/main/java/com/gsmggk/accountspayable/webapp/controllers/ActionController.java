package com.gsmggk.accountspayable.webapp.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gsmggk.accountspayable.datamodel.Action;
import com.gsmggk.accountspayable.services.IActionService;
import com.gsmggk.accountspayable.webapp.models.ActionModel;

@RestController
public class ActionController {
	@Inject
	private IActionService actionService;

	@RequestMapping("/actions")
	//@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ActionModel>> getAll() {
		List<Action> allActions;
		allActions = actionService.getAll();

		List<ActionModel> converterModel = new ArrayList<>();
		for (Action action : allActions) {
			converterModel.add(entity2model(action));
		}

		return new ResponseEntity<List<ActionModel>>(converterModel, HttpStatus.OK);
	}

	@RequestMapping(value="/new", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getAllNew() {
	    List<Action> entityList = actionService.getAll();

	    List<ActionModel> entities = new ArrayList<ActionModel>();
	    for (Action action : entityList) {
	    	ActionModel actionM = new ActionModel();
	    	actionM.setActionName(action.getActionName());
	    	actionM.setDuration(action.getDuration());
	        entities.add(actionM);
	    }
	    return new ResponseEntity<Object>(entities, HttpStatus.OK);
	}
	
	@RequestMapping("/test2")
	@ResponseStatus(HttpStatus.OK)
	public String  test2() {

		List<Action> allActions;
		allActions = actionService.getAll();

		List<ActionModel> converterModel = new ArrayList<>();
		for (Action action : allActions) {
			converterModel.add(entity2model(action));
		}
		return converterModel.toString();
	}


	private ActionModel entity2model(Action action) {
		ActionModel model = new ActionModel();
		model.setActionName(action.getActionName());
		model.setDuration(action.getDuration());
		model.setCode(action.getId());
		return model;
	}

}
