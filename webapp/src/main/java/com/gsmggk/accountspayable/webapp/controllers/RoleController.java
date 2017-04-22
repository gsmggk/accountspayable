package com.gsmggk.accountspayable.webapp.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gsmggk.accountspayable.datamodel.Role;
import com.gsmggk.accountspayable.services.IRoleService;
import com.gsmggk.accountspayable.webapp.models.IdModel;
import com.gsmggk.accountspayable.webapp.models.RoleModel;

@RestController
@RequestMapping("/roles")
public class RoleController {
	@Inject
	private IRoleService roleService;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<RoleModel>> getAll() {
		List<Role> allRoles;
		allRoles = roleService.getAll();

		List<RoleModel> converterModel = new ArrayList<>();
		for (Role role : allRoles) {
			converterModel.add(entity2model(role));
		}

		return new ResponseEntity<List<RoleModel>>(converterModel, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable(value = "id") Integer roleIdParam) {
		Role role = roleService.get(roleIdParam);
		RoleModel roleModel = entity2model(role);
		return new ResponseEntity<RoleModel>(roleModel, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createAction(@RequestBody RoleModel roleModel) {
		Role role = model2entity(roleModel);
		roleService.save(role);
		return new ResponseEntity<IdModel>(new IdModel(role.getId()), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateAction(@RequestBody RoleModel roleModel,
			@PathVariable(value = "id") Integer roleIdParam) {
		Role role = roleService.get(roleIdParam);

		role.setRoleName(roleModel.getRoleName());
		roleService.save(role);
		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteAction(@PathVariable(value = "id") Integer roleIdParam) {
		Role role = new Role();
		role = roleService.get(roleIdParam);
		roleService.delete(role);
		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	private Role model2entity(RoleModel model) {
		Role role = new Role();
		role.setId(model.getId());
		role.setRoleName(model.getRoleName());
		return role;
	}

	private RoleModel entity2model(Role role) {
		RoleModel model = new RoleModel();
		model.setRoleName(role.getRoleName());
		model.setId(role.getId());
		return model;
	}
}
