package com.entity;

import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 供应商实体类
 * 
 * @author Administrator
 *
 */
public class Provider {
	private Integer id; // 主键id
	@NotEmpty(message = "供应商编码不能为空")
	private String proCode; // 供应商编码
	@NotEmpty(message = "供应商名称不能为空")
	private String proName; // 供应商名称
	private String proDesc; // 供应商详细描述
	@NotEmpty(message = "联系人不能为空")
	private String proContact; // 供应商联系人
	@Pattern(regexp = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$", message = "请输入正确格式的手机号")
	private String proPhone; // 联系电话
	private String proAddress; // 地址
	private String proFax; // 传真
	private Integer createdBy; // 创建者

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date creationDate; // 创建时间

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date modifyDate; // 更新时间

	private Integer modifyBy; // 更新者

	private String companyLicPicPath;

	public String getCompanyLicPicPath() {
		return companyLicPicPath;
	}
	
	public String orgCodePicPath;   //工作证照片路径
	

	public String getOrgCodePicPath() {
		return orgCodePicPath;
	}

	public void setOrgCodePicPath(String orgCodePicPath) {
		this.orgCodePicPath = orgCodePicPath;
	}

	public void setCompanyLicPicPath(String companyLicPicPath) {
		this.companyLicPicPath = companyLicPicPath;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProCode() {
		return proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProDesc() {
		return proDesc;
	}

	public void setProDesc(String proDesc) {
		this.proDesc = proDesc;
	}

	public String getProContact() {
		return proContact;
	}

	public void setProContact(String proContact) {
		this.proContact = proContact;
	}

	public String getProPhone() {
		return proPhone;
	}

	public void setProPhone(String proPhone) {
		this.proPhone = proPhone;
	}

	public String getProAddress() {
		return proAddress;
	}

	public void setProAddress(String proAddress) {
		this.proAddress = proAddress;
	}

	public String getProFax() {
		return proFax;
	}

	public void setProFax(String proFax) {
		this.proFax = proFax;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Integer getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(Integer modifyBy) {
		this.modifyBy = modifyBy;
	}
}
