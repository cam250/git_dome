package com.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.dao.UserMapper;
import com.entity.Role;
import com.entity.User;
import com.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{
	@Resource
	private UserMapper userMapper;

	public User login(String userCode,String userPassword) {
		User user = null;
		user=userMapper.queryByuserpwd(userCode);
		if (null!=user) {
			if (!user.getUserPassword().equals(userPassword)) {
				user=null;
			}
		}
		return user;
	}
	public int queryCount(String userName, Integer roleId) {
		return userMapper.queryCount(userName, roleId);
	}

	public List<User> queryUserListPage(String userName, Integer roleId,
			Integer from, Integer pageSize) {
		return userMapper.queryUserListPage(userName, roleId, from, pageSize);
	}

	public List<Role> queryAllRole() {
		return userMapper.queryAllRole();
	}
	public int pwdSave(User user) {
		// TODO Auto-generated method stub
		return userMapper.pwdSave(user);
	}
	
	/**
	 * 根据用户名查询
	 * @param userCode   用户名
	 * @return
	 */
	public User queryByuserpwd(String userCode) {
		// TODO Auto-generated method stub
		return userMapper.queryByuserpwd(userCode);
	}
}
