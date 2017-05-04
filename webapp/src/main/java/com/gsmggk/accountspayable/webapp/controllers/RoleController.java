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

import com.gsmggk.accountspayable.datamodel.Role;
import com.gsmggk.accountspayable.services.IRoleService;
import com.gsmggk.accountspayable.webapp.models.IdModel;
import com.gsmggk.accountspayable.webapp.models.RoleModel;
import com.gsmggk.accountspayable.webapp.validate.ParameterErrorResponse;
import com.gsmggk.accountspayable.webapp.validate.ValidationErrorRestonse;

@RestController
@RequestMapping("/{prefix}/roles")
public class RoleController {
	private static final String NOT_FOUND_MES = "Role not found";

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
	public ResponseEntity<?> getById(@PathVariable(value = "id") Integer roleIdParam

	) {

		Role role = roleService.get(roleIdParam);
		if (role == null) {
			return ParameterErrorResponse.getNotFoundResponse(NOT_FOUND_MES);
		}

		RoleModel roleModel = entity2model(role);
		return new ResponseEntity<RoleModel>(roleModel, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createRole(@Valid @RequestBody RoleModel roleModel, Errors e

	) {

		if (e.hasErrors()) {
			return new ValidationErrorRestonse().getValidationErrorRestonse(e);
		}
		Role role = model2entity(roleModel);
		roleService.save(role);
		return new ResponseEntity<IdModel>(new IdModel(role.getId()), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateRole(@Valid @RequestBody RoleModel roleModel, Errors e,
			@PathVariable(value = "id") Integer roleIdParam) {

		if (e.hasErrors()) {
			return new ValidationErrorRestonse().getValidationErrorRestonse(e);
		}

		Role role = roleService.get(roleIdParam);
		if (role == null) {
			return ParameterErrorResponse.getNotFoundResponse(NOT_FOUND_MES);
		}
		role.setRoleName(roleModel.getRoleName());
		role.setLayer(roleModel.getLayer());
		roleService.save(role);
		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteRole(@PathVariable(value = "id") Integer roleIdParam) {

		Role role = new Role();

		role = roleService.get(roleIdParam);
		if (role == null) {
			return ParameterErrorResponse.getNotFoundResponse(NOT_FOUND_MES);
		}
		roleService.delete(role);
		return new ResponseEntity<IdModel>(HttpStatus.OK);
	}

	private Role model2entity(RoleModel model) {
		Role role = new Role();
		role.setId(model.getId());
		role.setRoleName(model.getRoleName());
		role.setLayer(model.getLayer());
		return role;
	}

	private RoleModel entity2model(Role role) {
		RoleModel model = new RoleModel();
		model.setId(role.getId());
		model.setRoleName(role.getRoleName());
		model.setLayer(role.getLayer());
		return model;
	}
}
