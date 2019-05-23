package com.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.entity.Page;
import com.entity.Role;
import com.entity.User;
import com.mysql.jdbc.StringUtils;
import com.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	private Logger logger = Logger.getLogger(UserController.class);
	@Resource
	private UserService userService;

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/pwdModify")
	public String pwdModify() {
		return "pwdmodify";
	}
	@RequestMapping("/yhgl")
	public String yhgl(){
		return "useradd";
	}

	//判断用户编码是否存在
	@RequestMapping(value="/codeExist",method=RequestMethod.GET)
	@ResponseBody
	public Object userCodeIsExit(@RequestParam String userCode,HttpSession session){
		logger.debug("userCodeIsExit  :"+userCode);
		User user=(User)session.getAttribute("userSession");
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if (StringUtils.isNullOrEmpty(userCode)) {
			resultMap.put("userCode", "exist");    //为空的话
		}else {
			if (null!=user) {
				resultMap.put("userCode", "exist");   
			}else {
				resultMap.put("userCode", "noexist");
			}
		}
		return JSONArray.toJSONString(resultMap);
	}
	
	@RequestMapping(value="/ucExist")
	@ResponseBody
	public Object getPwdByUserId(HttpSession session,@RequestParam String oldpassword){
		User user=(User)session.getAttribute("userSession");
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if (StringUtils.isNullOrEmpty(oldpassword)) { // userPassword是空
			resultMap.put("userCode", "isNull");
			//JSON字符串数组格式
		} else {
			// 通过userPassword查询用户详细对象信息
			if (user.getUserPassword() == oldpassword) { // userPassword已存在
				resultMap.put("userCode", "exist"); // 用来标识当前userPassword已存在
			} else {
				resultMap.put("userCode", "noexist"); // 用来标识当前userPassword不存在，即userPassword可用
			}
		}
		// 将集合转换成JSON字符串格式
		return JSONArray.toJSONString(resultMap);
	}
	
	 //修改前输入原来的密码验证
    @RequestMapping(value="pwdmodify",method = RequestMethod.GET)
    @ResponseBody
    public Object pwdupdate(@RequestParam String oldpassword,HttpSession session) {
		HashMap<String, String> map=new HashMap<String,String>();
		User user=(User)session.getAttribute("userSession");
		if(user==null) {
			map.put("oldpassword", "sessionerror");
		}else {
            if(!user.getUserPassword().equals(oldpassword)) {
				map.put("oldpassword", "false");
			}else {
				map.put("oldpassword", "true");
			}
		}
		return map;
	}
    
	@RequestMapping(value="/pwdSave",method=RequestMethod.POST)
	public String pwdSave(@RequestParam String newpassword,HttpSession session){
		User user=(User)session.getAttribute("userSession");
		user.setUserPassword(newpassword);
		int row=userService.pwdSave(user);
		if(row>0) {
			return "login";
		}
		return "pwdmodify";
	} 	
	
	// doLogin是用于处理登录请求
	@RequestMapping("/doLogin")
	public String doLogin(@RequestParam("userCode") String userCode, @RequestParam("userPassword") String userPassword,
			HttpServletRequest request, Model model, HttpSession session) {
		User user = userService.login(userCode, userPassword);
		if (user == null) {
			// 手动抛出异常
			throw new RuntimeException("用户名不存在");
		} else if (user != null && !user.getUserPassword().equals(userPassword)) { // 登录成功，重定向到主页面
			request.getSession().setAttribute("error", "登录密码不正确，请确认后再输入");
			return "login"; // 登录失败再跳回登录页面
		} else {
			// 将登录成功后的用户信息保存到Session作用域
			session.setAttribute("userSession", user);
			// return "redirect:/user/frame";
			// return "/frame";
			return "/frame";
			// return "redirect:/user/list";
		}
	}

	@ExceptionHandler(value = { RuntimeException.class })
	public String handlerException(RuntimeException e, HttpServletRequest req) {
		req.setAttribute("exception", e);
		return "login";
	}

	@RequestMapping("/frame")
	public String frame() {
		return "frame"; // 跳转到超市订单管理系统的主页面
	}

	@RequestMapping("/list")
	public String list(@RequestParam(value = "queryUserRole", required = false) Integer queryUserRole,
			@RequestParam(value = "queryname", required = false) String queryname,
			@RequestParam(value = "currPageNo", required = false, defaultValue = "1") Integer currPageNo, Model model) {
		int totalCount = userService.queryCount(queryname, queryUserRole);
		Page page = new Page();
		page.setPageSize(3); // 每页显示的数据行数
		page.setCurrPageNo(currPageNo); // 当前页码
		page.setTotalCount(totalCount); // 总的记录数
		Integer from = (page.getCurrPageNo() - 1) * page.getPageSize();
		List<User> userList = userService.queryUserListPage(queryname, queryUserRole, from, page.getPageSize());
		List<Role> roleList = userService.queryAllRole();
		model.addAttribute("page", page); // Page对象
		model.addAttribute("userList", userList); // 用户列表
		model.addAttribute("roleList", roleList); // 角色列表
		model.addAttribute("queryname", queryname); // 用户名
		model.addAttribute("queryUserRole", queryUserRole); // 角色id保存到Model中
		return "userlist";
	}
}
