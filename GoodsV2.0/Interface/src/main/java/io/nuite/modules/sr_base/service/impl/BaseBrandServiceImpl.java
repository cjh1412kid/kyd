package io.nuite.modules.sr_base.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.nuite.modules.sr_base.dao.BaseBrandDao;
import io.nuite.modules.sr_base.entity.BaseBrandEntity;
import io.nuite.modules.sr_base.service.BaseBrandService;

@Service
public class BaseBrandServiceImpl extends ServiceImpl<BaseBrandDao, BaseBrandEntity> implements BaseBrandService {

    @Autowired
    private BaseBrandDao baseBrandDao;
    
	
	/**
	 * 根据seq获取品牌信息指定字段
	 */
	@Override
	public Map<String, Object> getBrandMapBySeq(Integer brandSeq) {
		Wrapper<BaseBrandEntity> wrapper = new EntityWrapper<BaseBrandEntity>();
		wrapper.setSqlSelect("Name AS brandName, Image AS brandImage, Remark AS brandRemark").where("Seq = {0} ", brandSeq);
		return baseBrandDao.selectMaps(wrapper).get(0);
	}
}
