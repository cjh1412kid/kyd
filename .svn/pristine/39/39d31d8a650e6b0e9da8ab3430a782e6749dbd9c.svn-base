package io.nuite.modules.system.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.nuite.common.utils.R;
import io.nuite.modules.online_sales_app.service.RankService;
import io.nuite.modules.sr_base.entity.GoodsCategoryEntity;
import io.nuite.modules.sr_base.entity.GoodsPeriodEntity;
import io.nuite.modules.sr_base.service.GoodsCategoryService;
import io.nuite.modules.sr_base.service.GoodsPeriodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/system/onlineRank")
@Api(tags = "后台-online排行榜")
public class OnlineRankListController  extends AbstractController  {
	 private Logger logger = LoggerFactory.getLogger(getClass());
	 
	 @Autowired
	 private RankService rankService;
	 
	 @Autowired
	 private GoodsPeriodService goodsPeriodService;
	 
	 @Autowired
	 private GoodsCategoryService goodsCategoryService;

	 @GetMapping("/rankList" )
	 @ApiOperation(value = "排行榜")
	 public R getRankList(@ApiParam("鞋子品类seq)") @RequestParam(value = "categorySeq", required = false) Integer categorySeq,@ApiParam("查询类型（1.次数，2.订货量）") @RequestParam(value = "type") Integer type) {
		 try {
			 //获取当前用户的companySeq
			  Integer companySeq = getUser().getCompanySeq();
			 Integer brandSeq= getUser().getBrandSeq();
			 Date date=new Date();
			 Integer periodSeq=goodsPeriodService.getPeriodByTime(brandSeq, date);
			 //排行榜
			List<Map<String, Object>> rankList=rankService.getRank(companySeq, periodSeq, null,type);
			//查询当前公司的一级分类
		
			//男女鞋占比
			List<Map<String, Object>> goodsCategoryList=rankService.getParentCategory(companySeq, periodSeq, 0,type);
			//具体鞋子占比
			List<Map<String, Object>> categoryList=new ArrayList<Map<String,Object>>();
			if(categorySeq==null) {
				//获取二级分类
				List<GoodsCategoryEntity> goodsCategoryEntities=goodsCategoryService.getGoodsCategoryByParent(0,companySeq);
				for (GoodsCategoryEntity goodsCategoryEntity2 : goodsCategoryEntities) {
					List<GoodsCategoryEntity> goodsCategoryEntities2=goodsCategoryService.getGoodsCategoryByParent(goodsCategoryEntity2.getSeq(),companySeq);
					//获取三级分类
					for (GoodsCategoryEntity goodsCategoryEntity3 : goodsCategoryEntities2) {
					List<Map<String, Object>> goodsCategoryList2=rankService.getParentCategory(companySeq, periodSeq, goodsCategoryEntity3.getSeq(),type);
					for (Map<String, Object> map : goodsCategoryList2) {
						Integer cateSeq=(Integer) map.get("categorySeq");
						GoodsCategoryEntity categoryEntity=goodsCategoryService.selectById(cateSeq);
						GoodsCategoryEntity parentCategory=goodsCategoryService.selectById(categoryEntity.getParentSeq());
						GoodsCategoryEntity parentCategory2=goodsCategoryService.selectById(parentCategory.getParentSeq());
						map.put("categoryName",parentCategory2.getName()+"/"+ parentCategory.getName()+"/"+categoryEntity.getName());
					}
					
					categoryList.addAll(goodsCategoryList2);
					}
				}
			}else {
				
				
				List<GoodsCategoryEntity> goodsCategoryEntities2=goodsCategoryService.getGoodsCategoryByParent(categorySeq,companySeq);
				//获取三级分类
				for (GoodsCategoryEntity goodsCategoryEntity3 : goodsCategoryEntities2) {
				List<Map<String, Object>> goodsCategoryList2=rankService.getParentCategory(companySeq, periodSeq, goodsCategoryEntity3.getSeq(),type);
				for (Map<String, Object> map : goodsCategoryList2) {
					Integer cateSeq=(Integer) map.get("categorySeq");
					GoodsCategoryEntity categoryEntity=goodsCategoryService.selectById(cateSeq);
					GoodsCategoryEntity parentCategory=goodsCategoryService.selectById(categoryEntity.getParentSeq());
					GoodsCategoryEntity parentCategory2=goodsCategoryService.selectById(parentCategory.getParentSeq());
					map.put("categoryName",parentCategory2.getName()+"/"+ parentCategory.getName()+"/"+categoryEntity.getName());
				}
				
				categoryList.addAll(goodsCategoryList2);
				}
				
			}
			Map<String, Object> map=new HashMap<String, Object>();
			
			GoodsPeriodEntity goodsPeriodEntity=goodsPeriodService.selectById(periodSeq);
			
			
			map.put("rankList", rankList);
			map.put("goodsCategoryList", goodsCategoryList);
			map.put("categoryList", categoryList);
			map.put("goodsPeriodEntity", goodsPeriodEntity);
			 return R.ok().put("rank", map);
		 	} catch (Exception e) {
	            logger.error(e.getMessage(), e);
	            return R.error("服务器异常");
	        }
		 
	 }
}
