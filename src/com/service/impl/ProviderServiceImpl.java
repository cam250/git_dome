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
	 * 渚涘簲鍟嗙紪鐮�(妯＄硦鍖归厤)
	 * 渚涘簲鍟嗗悕绉�(妯＄硦鍖归厤)
	 * @param proCode
	 * @param proName
	 * @return
	 */
	public int queryCount(String proCode, String proName) {
		System.out.println("123");
		return providerMapper.queryCount(proCode, proName);
	}
	/**
	 * 鍒嗛〉骞舵寜鏉′欢鏌ヨ渚涘簲鍟嗚〃
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
	 * 渚涘簲鍟嗘柊澧炴柟娉�
	 * @param provider
	 * @return
	 */
	public int addProvider(Provider provider) {
		// TODO Auto-generated method stub
		return providerMapper.addProvider(provider);
	}
	/**
	 * 閫氳繃ID鏌ヨ渚涘簲鍟嗚鎯呭璞′俊鎭�
	 * @param id 渚涘簲鍟咺D
	 * @return 渚涘簲鍟嗚缁嗗璞′俊鎭�
	 */
	public Provider getProviderById(Integer id) {
		// TODO Auto-generated method stub
		return providerMapper.getProviderById(id);
	}
	/**
	 * 渚涘簲鍟嗕慨鏀规柟娉�
	 * @param provider
	 * @return
	 */
	public int modifyProvider(Provider provider) {
		// TODO Auto-generated method stub
		return providerMapper.modifyProvider(provider);
	}
}
