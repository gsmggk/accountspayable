package com.gsmggk.accountspayable.webapp.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gsmggk.accountspayable.datamodel.Action;
import com.gsmggk.accountspayable.services.IActionService;
import com.gsmggk.accountspayable.webapp.models.ActionModel;
import com.gsmggk.accountspayable.webapp.models.IdModel;

@RestController
@RequestMapping("/actions")
public class ActionController {
	@Inject
	private IActionService actionService;

	
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
	        ActionModel actionModel = entity2model(action);
	        return new ResponseEntity<ActionModel>(actionModel, HttpStatus.OK);
	    }
	 
	 @RequestMapping(method = RequestMethod.POST)
	    public ResponseEntity<?> createAction(@RequestBody ActionModel actionModel) {
	        Action action = model2entity(actionModel);
	        actionService.save(action);
	        return new ResponseEntity<IdModel>(new IdModel(action.getId()), HttpStatus.CREATED);
	    }
	 @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	    public ResponseEntity<?> updateAction(@RequestBody ActionModel actionModel,
	            @PathVariable(value = "id") Integer actionIdParam) {
	        Action action = actionService.get(actionIdParam);

	        action.setActionName(actionModel.getActionName());
	        action.setDuration(actionModel.getDuration());

	        actionService.save(action);
	        return new ResponseEntity<IdModel>(HttpStatus.OK);
	    }

	 @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	    public ResponseEntity<?> deleteAction(@PathVariable(value = "id") Integer actionIdParam) {
		    Action action=new Action();
		    action= actionService.get(actionIdParam);
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
		model.setActionName(action.getActionName());
		model.setDuration(action.getDuration());
		model.setId(action.getId());
		return model;
	}

}
