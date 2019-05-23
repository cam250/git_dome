package com.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.entity.Page;
import com.entity.Provider;
import com.entity.User;
import com.service.ProviderService;

@Controller
@RequestMapping("/provider")
public class ProviderController {

	@Resource
	private ProviderService providerService;

	private Logger logger = Logger.getLogger(ProviderController.class);

	@RequestMapping("/providerlist")
	public String providerlist(@RequestParam(value = "queryProCode", required = false) String queryProCode,
			@RequestParam(value = "queryProName", required = false) String queryProName,
			@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo, Model model) {
		int totalCount = providerService.queryCount(queryProCode, queryProName);
		Page page = new Page();
		page.setPageSize(9); // 每页显示的行数
		page.setCurrPageNo(pageNo); // 页码
		page.setTotalCount(totalCount); // 总记录数
		Integer from = (page.getCurrPageNo() - 1) * page.getPageSize();
		List<Provider> providerList = providerService.getProviderList(queryProCode, queryProName, from,
				page.getPageSize());
		model.addAttribute("page", page);
		model.addAttribute("providerList", providerList);// 供应商列表
		model.addAttribute("queryProCode", queryProCode);// 供应商编码
		model.addAttribute("queryProName", queryProName);// 供应商名称
		return "providerlist";
	}

	/*
	 * @ModelAttribute注解,将方法如参对象(Provider), 添加到模型中,
	 * 然后根据HTTP请求信息进一步填充覆盖provider对象
	 */
	@RequestMapping(value = "/addProvider", method = RequestMethod.GET)
	public String addProvider(Model model, Provider provider) {
		model.addAttribute("provider", provider);
		return "provideradd";
	}

	// 使用Spring表单标签是,表单提交处理方法
	@RequestMapping(value = "/addProvider", method = RequestMethod.POST)
	public String addProvider(@Valid Provider provider, BindingResult bindingResult, HttpSession session, Model model) {
		if (bindingResult.hasErrors()) {
			// 判断bindingResult对象中是否保存到JSR 303数据校验后的错误结果信息，true表示存在
			model.addAttribute("provider", provider); // 将roleList保存到Model中
			return "provideradd"; // 如果存储JSR 303验证的错误信息，那么跳回用户新增页面，显示错误信息。
		}
		// 为provider对象中的createdBy(创建者的ID)、creationDate(创建时间)两个属性赋值
		User user = (User) session.getAttribute("userSession"); // 获取当前登录用户的对象信息
		if (user == null) {
			System.out.println("空");
		}
		provider.setCreatedBy(user.getId()); // 为创建者ID赋值
		provider.setCreationDate(new Date()); // 为创建时间赋值
		int result = providerService.addProvider(provider);
		System.out.println(result);
		if (result > 0) {
			return "redirect:/provider/providerlist"; // 新增用户成功，跳转到用户列表。
		} else {
			return "redirect:/provider/provideradd"; // 新增失败，跳回用户添加页面
		}
	}

	@RequestMapping(value = "/addProviderSave", method = RequestMethod.POST)
	public String addProviderSave(Provider provider, HttpSession session, HttpServletRequest request,
			@RequestParam(value = "attachs", required = false) MultipartFile[] attachs) {
		String companyLicPicPath = null;
		String orgCodePicPath = null;
		String errorInfo = null;
		boolean flag = true;

		// 判断文件是否为空

		String path = request.getSession().
				getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
		logger.info("uploadFile path =========>" + path);
		for (int i = 0; i < attachs.length; i++) {
			MultipartFile attach = attachs[i];
			if (!attach.isEmpty()) {
				if (i == 0) {
					errorInfo = "uploadFileError";
				} else if (i == 1) {
					errorInfo = "uploadWpError";
				}
				String oldFileName = attach.getOriginalFilename(); // 原文件名
				logger.info("uploadFile oldFileName========>" + oldFileName);
				String prefix = FilenameUtils.getExtension(oldFileName); // 原文件后缀
				logger.debug("uploadFile prefix==========>" + prefix);
				int filesize = 500000;
				logger.debug("uploadFile size=========>" + attach.getSize());
				if (attach.getSize() > filesize) { // 上传大小不得超过500KB
					request.setAttribute(errorInfo, "*上传大小不得超过500KB");
					flag = false;
					// return "provideradd";
				} else if (prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png")
						|| prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("pneg")) { // 上传图片格式不正确
					String fileName = System.currentTimeMillis() + RandomUtils.nextInt(1000000) + "_Personal.jpg";
					logger.debug("new fileName========" + attach.getName());
					File targetFile = new File(path, fileName);
					if (!targetFile.exists()) {
						targetFile.mkdirs();
					}
					// 保存
					try {
						attach.transferTo(targetFile);
						/*companyLicPicPath = path+ File.separator + fileName;

						orgCodePicPath = path+ File.separator + fileName;*/
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						request.setAttribute(errorInfo, "*上传失败!");
						flag = false;
					}
					if (i == 0) {
						companyLicPicPath = fileName;
					} else if (i == 1) {
						orgCodePicPath =  fileName;
					}
					logger.debug("companyLicPicPath:" + companyLicPicPath);
					logger.debug("orgCodePicPath:" + orgCodePicPath);
					// companyLicPicPath=path+File.separator+fileName;
				} else {
					request.setAttribute(errorInfo, "*上传图片格式不正确");
					// return "provideradd";
					flag = false;
				}
			}
		}

		if (flag) {
			// 为provider对象中的createdBy(创建者的ID)、creationDate(创建时间)两个属性赋值
			User create = (User) session.getAttribute("userSession"); // 获取当前登录用户的对象信息
			provider.setCreatedBy(create.getId()); // 为创建者ID赋值
			provider.setCreationDate(new Date()); // 为创建时间赋值
			provider.setCompanyLicPicPath(companyLicPicPath);
			provider.setOrgCodePicPath(orgCodePicPath);
			int result = providerService.addProvider(provider);
			// System.out.println(result);
			if (result > 0) {
				return "redirect:/provider/providerlist"; // 新增用户成功，跳转到用户列表。
			} else {
				return "provideradd"; // 新增失败，跳回用户添加页面
			}
		}
		return "provideradd";
	}

	// 通过id查询供应商的详细对象信息，并跳转到修改页面，在修改页面显示供应商信息。
	@RequestMapping(value = "/updateProvider/{id}", method = RequestMethod.GET)
	public String providermodify(@PathVariable Integer id, Model model) {
		logger.debug("providermodify" + id);
		Provider provider = providerService.getProviderById(id); // 通过id查询供应商详细对象信息
		model.addAttribute("provider", provider);
		return "providermodify"; // 跳转到供应商修改页面
	}

	// 在修改页面修改完相应的信息后，点击提交按扭，在方法完成保存供应商的修改操作。
	@RequestMapping(value = "/saveUpdateProvider", method = RequestMethod.POST)
	public String saveUpdateProvider(Provider provider, HttpSession session) {
		User create = (User) session.getAttribute("userSession"); // 获取当前登录供应商的对象信息
		provider.setModifyBy(create.getId()); // 为修改者的ID赋值
		provider.setModifyDate(new Date());
		int result = providerService.modifyProvider(provider);
		if (result > 0) {
			return "redirect:/provider/providerlist"; // 修改成功跳转到供应商列表信息
		} else {
			return "providermodify"; // 修改失败再回到修改页面。
		}
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable Integer id, Model model) {
		logger.debug("view============" + id);
		Provider provider = providerService.getProviderById(id); // 通过id查询供应商详细对象信息
		model.addAttribute(provider);
		return "providerview";// 跳转到供应商查看页面
	}
}
