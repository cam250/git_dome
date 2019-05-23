package com.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.Provider;

public interface ProviderService {
	/**
	 * 供应商编码(模糊匹配)
	 * 供应商名称(模糊匹配)
	 * @param proCode
	 * @param proName
	 * @return
	 */
	int queryCount(String proCode,String proName);
			
	/**
	 * 分页并按条件查询供应商表
	 * @param proCode
	 * @param proName
	 * @param from
	 * @param pageSize
	 * @return
	 */
	public List<Provider> getProviderList(String proCode,String proName,
				Integer from,Integer pageSize);
	/**
	 * 供应商新增方法
	 * @param provider
	 * @return
	 */
	int addProvider(Provider provider);
	/**
	 * 通过ID查询供应商详情对象信息
	 * @param id 供应商ID
	 * @return 供应商详细对象信息
	 */
	Provider getProviderById(Integer id);
	
	/**
	 * 供应商修改方法
	 * @param provider
	 * @return
	 */
	int modifyProvider(Provider provider);
}
