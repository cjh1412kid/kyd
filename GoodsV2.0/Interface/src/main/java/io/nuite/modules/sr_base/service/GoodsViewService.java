package io.nuite.modules.sr_base.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.nuite.modules.sr_base.entity.GoodsViewEntity;

public interface GoodsViewService extends IService<GoodsViewEntity> {
	
	GoodsViewEntity getGoodsViewBySeq(Integer shoesSeq);

	Map<String, Object> getGoodsViewMapBySeq(Integer shoesSeq);

}
