package io.nuite.modules.online_sales_app.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.nuite.modules.online_sales_app.dao.OnlineSalesShoesInfoDao;
import io.nuite.modules.online_sales_app.entity.ShoesInfoEntity;
import io.nuite.modules.online_sales_app.service.RankService;
import io.nuite.modules.sr_base.dao.GoodsCategoryDao;
import io.nuite.modules.sr_base.dao.GoodsShoesDao;
import io.nuite.modules.sr_base.entity.GoodsCategoryEntity;
import io.nuite.modules.sr_base.entity.GoodsShoesEntity;
import io.nuite.modules.sr_base.utils.ConfigUtils;

@Service
public class RankServiceImpl implements RankService {
	
	@Autowired
	private GoodsShoesDao goodsShoesDao;
	
	@Autowired
	private GoodsCategoryDao goodsCategoryDao;

	@Autowired
	private ConfigUtils configUtils;
	
	@Autowired
	private OnlineSalesShoesInfoDao shoesInfoDao;

	@Override
	public List<Map<String, Object>> getRank(Integer companySeq,Integer periodSeq,Integer categorySeq,Integer type) {
		List<Map<String, Object>> rankList= goodsShoesDao.getRankList(companySeq, periodSeq, categorySeq,type);
		for (Map<String, Object> map : rankList) {
			Integer shoesSeq=(Integer) map.get("shoesSeq");
			GoodsShoesEntity goodsShoesEntity=goodsShoesDao.selectById(shoesSeq);
			map.put("goodId", goodsShoesEntity.getGoodID());
			String img=goodsShoesEntity.getImg1();
		       String imgSrc = configUtils.getPictureBaseUrl() + "/" + configUtils.getBaseDir() + "/" + configUtils.getGoodsShoes() + "/" +  goodsShoesEntity.getGoodID() + "/" + img;
		       map.put("imgSrc", imgSrc);
		ShoesInfoEntity shoesInfoEntity=shoesInfoDao.getShoeInfoByShoesSeq(shoesSeq);
		
		  map.put("wxORCode",configUtils.getPictureBaseUrl() + "/" + configUtils.getBaseDir() + "/" + configUtils.getGoodsShoes() + "/" +  goodsShoesEntity.getGoodID() + "/" +  shoesInfoEntity.getWxQRCode());
		}
		return rankList;
	}

	@Override
	public List<Map<String, Object>> getParentCategory(Integer companySeq, Integer periodSeq,Integer categorySeq,Integer type) {
		List<Map<String, Object>> categoryRankList=goodsShoesDao.getCategory(companySeq, periodSeq, categorySeq,type);
		for (Map<String, Object> map : categoryRankList) {
			Integer cateSeq=(Integer) map.get("categorySeq");
			GoodsCategoryEntity goodsCategoryEntity=goodsCategoryDao.selectById(cateSeq);
			map.put("categoryName", goodsCategoryEntity.getName());
		}
		
		return categoryRankList;
	}

	@Override
	public List<Map<String, Object>> getRankList(Integer companySeq, Integer periodSeq) {
		List<Map<String, Object>> rankList= goodsShoesDao.getSumRankList(companySeq, periodSeq);
		for (Map<String, Object> map : rankList) {
			Integer shoesSeq=(Integer) map.get("shoesSeq");
			GoodsShoesEntity goodsShoesEntity=goodsShoesDao.selectById(shoesSeq);
			
			ShoesInfoEntity shoesInfoEntity=shoesInfoDao.getShoeInfoByShoesSeq(shoesSeq);
			map.put("goodId",goodsShoesEntity.getGoodID());
			map.put("salePrice", shoesInfoEntity.getSalePrice());
			map.put("seq", shoesSeq);
			map.put("imgSrc", configUtils.getPictureBaseUrl() + "/" + configUtils.getBaseDir() + "/" + configUtils.getGoodsShoes() + "/" +  goodsShoesEntity.getGoodID() + "/" + goodsShoesEntity.getImg1());
		}
		
		return rankList;
	}

}
