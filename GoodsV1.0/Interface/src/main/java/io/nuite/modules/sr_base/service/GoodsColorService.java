package io.nuite.modules.sr_base.service;

import com.baomidou.mybatisplus.service.IService;

import io.nuite.modules.sr_base.entity.GoodsColorEntity;

public interface GoodsColorService extends IService<GoodsColorEntity> {

	GoodsColorEntity getGoodsColorBySeq(Integer colorSeq);
	
}
