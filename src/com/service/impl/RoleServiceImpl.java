package com.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dao.RoleMapper;
import com.entity.Role;
import com.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Resource
	private RoleMapper roleMapper;
	public List<Role> getRoleList() {
		
		return roleMapper.getRoleList();
	}

}
