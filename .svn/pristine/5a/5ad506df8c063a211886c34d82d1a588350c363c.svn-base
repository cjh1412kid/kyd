package io.nuite.modules.sr_base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.nuite.modules.sr_base.dao.GoodsColorDao;
import io.nuite.modules.sr_base.entity.GoodsColorEntity;
import io.nuite.modules.sr_base.service.GoodsColorService;

@Service
public class GoodsColorServiceImpl extends ServiceImpl<GoodsColorDao, GoodsColorEntity> implements GoodsColorService {

    @Autowired
    private GoodsColorDao goodsColorDao;
    
    
    
	/**
	 * 根据seq获取颜色
	 */
	@Override
	public GoodsColorEntity getGoodsColorBySeq(Integer colorSeq) {
		return goodsColorDao.selectById(colorSeq);
	}
	
	
}
