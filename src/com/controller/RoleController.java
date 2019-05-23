package com.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.entity.Role;
import com.service.RoleService;

@Controller
@RequestMapping("/role")
public class RoleController {
	@Resource
	private RoleService roleService;
	@RequestMapping("/rolelist")
	public String rolelist(Model model){
		List<Role>rolelist = roleService.getRoleList();
		model.addAttribute("rolelist",rolelist);
		return "rolelist";
	}
}
