package com.nuite.manager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuite.manager.entity.Info;
import com.nuite.manager.entity.Page;
import com.nuite.manager.mapper.InfoMapper;
import com.nuite.manager.service.InfoService;

@Service
public class InfoServiceImpl implements InfoService{
	 @Resource
	 InfoMapper infoMapper;
	
	public List<Info> listPageInfo(Page page) {
		// TODO Auto-generated method stub
		return infoMapper.listPageInfo(page);
	}

//	public InfoMapper getInfoMapper() {
//		return infoMapper;
//	}
//
//	public void setInfoMapper(InfoMapper infoMapper) {
//		this.infoMapper = infoMapper;
//	}
}
