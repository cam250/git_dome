package com.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.Role;
import com.entity.User;

public interface UserService {
	/**
	 * 实现用户登录
	 * @param userCode  用户名
	 * @param userPassword  用户密码
	 * @return
	 */
	User login(String userCode,String userPassword);

	/**
	 * 通过用户名模糊查询、角色id精确查询，得到查询后的总记录条数
	 * 
	 * @param userName
	 *            用户名
	 * @param roleId
	 *            角色id
	 * @return 返回总记录条数
	 */
	int queryCount(String userName, Integer roleId);

	/**
	 * 分页并按条件查询用户列表
	 * 
	 * @param userName
	 *            用户名(按用户名模糊查询)
	 * @param roleId
	 *            角色id(按角色id精确查询)
	 * @param from
	 *            位置偏移量(从0开始)
	 * @param pageSize
	 *            每页显示的数据行数
	 * @return 返回分页查询到的结果
	 */
	List<User> queryUserListPage(String userName, Integer roleId, Integer from, Integer pageSize);

	// 查询所有的角色列表信息，用于在用户列表页面中进行显示
	List<Role> queryAllRole();
	
	/**
	 * 修改密码
	 * @param user
	 * @return
	 */
	int pwdSave(User user);
	
	/**
	 * 根据用户名查询
	 * @param userCode   用户名
	 * @return
	 */
	User queryByuserpwd(String userCode);
}
