package io.nuite.modules.sr_base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.nuite.modules.online_sales_app.entity.MiniAppEntity;
import io.nuite.modules.sr_base.dao.GoodsCategoryDao;
import io.nuite.modules.sr_base.entity.GoodsCategoryEntity;
import io.nuite.modules.sr_base.service.GoodsCategoryService;

@Service
public class GoodsCategoryServiceImpl extends ServiceImpl<GoodsCategoryDao, GoodsCategoryEntity> implements GoodsCategoryService {

    @Autowired
    private GoodsCategoryDao goodsCategoryDao;
    
	/**
	 * 根据seq获取GoodsView对象
	 */
	@Override
	public GoodsCategoryEntity getGoodsCategoryBySeq(Integer categorySeq) {
		return goodsCategoryDao.selectById(categorySeq);
	}

	@Override
	public GoodsCategoryEntity getGoodsCateGoryByCompanySeq(Integer companySeq) {
		   EntityWrapper<GoodsCategoryEntity> ew = new EntityWrapper<GoodsCategoryEntity>();
		   ew.where("ParentSeq =0 AND Company_Seq={0}", companySeq);
		 List<GoodsCategoryEntity> goodsCategoryEntities=  goodsCategoryDao.selectList(ew);
		return goodsCategoryEntities.get(0);
	}

	@Override
	public List<GoodsCategoryEntity> getGoodsCategoryByParent(Integer parentSeq,Integer companySeq) {
		 EntityWrapper<GoodsCategoryEntity> ew = new EntityWrapper<GoodsCategoryEntity>();
		 ew.where("ParentSeq ={0} AND Company_Seq ={1}", parentSeq,companySeq);
		 List<GoodsCategoryEntity> goodsCategoryEntities=  goodsCategoryDao.selectList(ew);
		return goodsCategoryEntities;
	}
}
