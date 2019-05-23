package com.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dao.ProviderMapper;
import com.entity.Provider;
import com.service.ProviderService;

@Service("providerService")
public class ProviderServiceImpl implements ProviderService {
	@Resource
	private ProviderMapper providerMapper;
	/**
	 * 供应商编码(模糊匹配)
	 * 供应商名称(模糊匹配)
	 * @param proCode
	 * @param proName
	 * @return
	 */
	public int queryCount(String proCode, String proName) {
		System.out.println("4从产能充分56");
		return providerMapper.queryCount(proCode, proName);
	}
	/**
	 * 分页并按条件查询供应商表
	 * @param proCode
	 * @param proName
	 * @param from
	 * @param pageSize
	 * @return
	 */
	public List<Provider> getProviderList(String proCode, String proName, Integer from, Integer pageSize) {
		// TODO Auto-generated method stub
		return providerMapper.getProviderList(proCode, proName, from, pageSize);
	}
	/**
	 * 供应商新增方法
	 * @param provider
	 * @return
	 */
	public int addProvider(Provider provider) {
		// TODO Auto-generated method stub
		return providerMapper.addProvider(provider);
	}
	/**
	 * 通过ID查询供应商详情对象信息
	 * @param id 供应商ID
	 * @return 供应商详细对象信息
	 */
	public Provider getProviderById(Integer id) {
		// TODO Auto-generated method stub
		return providerMapper.getProviderById(id);
	}
	/**
	 * 供应商修改方法
	 * @param provider
	 * @return
	 */
	public int modifyProvider(Provider provider) {
		// TODO Auto-generated method stub
		return providerMapper.modifyProvider(provider);
	}
}
