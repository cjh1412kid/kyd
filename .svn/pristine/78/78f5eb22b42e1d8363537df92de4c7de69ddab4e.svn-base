package io.nuite.modules.sr_base.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

import io.nuite.modules.sr_base.entity.GoodsCategoryEntity;

public interface GoodsCategoryService extends IService<GoodsCategoryEntity> {

	GoodsCategoryEntity getGoodsCategoryBySeq(Integer categorySeq);
	
	GoodsCategoryEntity getGoodsCateGoryByCompanySeq(Integer companySeq);
	
	List<GoodsCategoryEntity> getGoodsCategoryByParent(Integer parentSeq,Integer companySeq);
}
