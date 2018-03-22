package com.etc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.service.impl.BaseService;
import com.etc.dao.impl.RoleDao;
import com.etc.module.Role;
import com.etc.service.IRoleService;

@Service
public class RoleService extends BaseService<Role, Integer> implements IRoleService {

	@Autowired
	private IRoleService roleService;
	
	@Autowired
	public RoleService(RoleDao roleDao) {
		super(roleDao);
		// TODO Auto-generated constructor stub
	}


}
