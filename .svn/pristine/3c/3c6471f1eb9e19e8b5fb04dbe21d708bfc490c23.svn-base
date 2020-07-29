package io.nuite.modules.online_sales_app.controller;

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
import io.nuite.modules.online_sales_app.annotation.XcxCustom;
import io.nuite.modules.online_sales_app.annotation.XcxLogin;
import io.nuite.modules.online_sales_app.entity.CustomerUserInfo;
import io.nuite.modules.online_sales_app.service.RankService;
import io.nuite.modules.sr_base.entity.GoodsCategoryEntity;
import io.nuite.modules.sr_base.service.GoodsCategoryService;
import io.nuite.modules.sr_base.service.GoodsPeriodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/online/miniapp/rank")
@Api(tags = "线上销售排行榜", description = "排行榜相关接口")
public class OnlineRankController {

	 private Logger logger = LoggerFactory.getLogger(getClass());
	 
	 @Autowired
	 private RankService rankService;
	 
	 @Autowired
	 private GoodsPeriodService goodsPeriodService;
	 
	 @Autowired
	 private GoodsCategoryService goodsCategoryService;
	 
	
	 
	 @XcxLogin
	 @GetMapping("rankList")
	 @ApiOperation("排行榜")
	 public R getRankList(@ApiIgnore @XcxCustom CustomerUserInfo customerUserInfo,@ApiParam("鞋子品类seq)") @RequestParam(value = "categorySeq", required = false) Integer categorySeq,@ApiParam("查询类型（1.次数，2.订货量）") @RequestParam(value = "type") Integer type) {
		 try {
			 //获取当前用户的companySeq
			 Integer companySeq=customerUserInfo.getCompanySeq();
			 Integer brandSeq=customerUserInfo.getBrandSeq();
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
			
			
			
			
			
			List<GoodsCategoryEntity> goodsCategoryEntities=goodsCategoryService.getGoodsCategoryByParent(0,companySeq);
			Integer manCategorySeq=null;
			Integer womanCategorySeq=null;
			for (GoodsCategoryEntity goodsCategoryEntity : goodsCategoryEntities) {
				String name=goodsCategoryEntity.getName();
				Integer seq=goodsCategoryEntity.getSeq();
				if("男鞋".equals(name)) {
					manCategorySeq=seq;
				}else if("女鞋".equals(name)) {
					womanCategorySeq=seq;
				}
			}
			//男鞋分类占比
			List<Map<String, Object>> manCategoryList=new ArrayList<Map<String,Object>>();
			if(manCategorySeq!=null) {
				List<GoodsCategoryEntity> goodsCategoryEntities2=goodsCategoryService.getGoodsCategoryByParent(manCategorySeq,companySeq);
				//获取三级分类
				for (GoodsCategoryEntity goodsCategoryEntity3 : goodsCategoryEntities2) {
				List<Map<String, Object>> goodsCategoryList2=rankService.getParentCategory(companySeq, periodSeq, goodsCategoryEntity3.getSeq(),type);
				for (Map<String, Object> map : goodsCategoryList2) {
					Integer cateSeq=(Integer) map.get("categorySeq");
					GoodsCategoryEntity categoryEntity=goodsCategoryService.selectById(cateSeq);
					GoodsCategoryEntity parentCategory=goodsCategoryService.selectById(categoryEntity.getParentSeq());
					map.put("categoryName",parentCategory.getName()+"/"+categoryEntity.getName());
				}
				
				manCategoryList.addAll(goodsCategoryList2);
				}
			}
			//女鞋分类占比
			List<Map<String, Object>> womanCategoryList=new ArrayList<Map<String,Object>>();
			if(womanCategorySeq!=null) {
				List<GoodsCategoryEntity> goodsCategoryEntities2=goodsCategoryService.getGoodsCategoryByParent(womanCategorySeq,companySeq);
				//获取三级分类
				for (GoodsCategoryEntity goodsCategoryEntity3 : goodsCategoryEntities2) {
				List<Map<String, Object>> goodsCategoryList2=rankService.getParentCategory(companySeq, periodSeq, goodsCategoryEntity3.getSeq(),type);
				for (Map<String, Object> map : goodsCategoryList2) {
					Integer cateSeq=(Integer) map.get("categorySeq");
					GoodsCategoryEntity categoryEntity=goodsCategoryService.selectById(cateSeq);
					GoodsCategoryEntity parentCategory=goodsCategoryService.selectById(categoryEntity.getParentSeq());
					map.put("categoryName", parentCategory.getName()+"/"+categoryEntity.getName());
				}
				
				womanCategoryList.addAll(goodsCategoryList2);
				}
			}
			//对三个数据进行重新计算
			List<Map<String, Object>> categoryList2=new ArrayList<Map<String,Object>>();
			
			List<Map<String, Object>> manCategoryList2=new ArrayList<Map<String,Object>>();
			
			List<Map<String, Object>> womanCategoryList2=new ArrayList<Map<String,Object>>();
			
			
			List<Map<String, Object>> categoryListOther=new ArrayList<Map<String,Object>>();
			
			List<Map<String, Object>> manCategoryOther=new ArrayList<Map<String,Object>>();
			
			List<Map<String, Object>> womanCategoryOther=new ArrayList<Map<String,Object>>();
			
			
			
			Integer categoryList2OtherCount=0;
			Integer categoryList2OtherSum=0;
			for (int i = 0; i < categoryList.size(); i++) {
				if(i<5) {
					categoryList2.add(categoryList.get(i));
				}else {
					categoryList2OtherCount+=(Integer)(categoryList.get(i).get("count"));
					categoryList2OtherSum+=(Integer)(categoryList.get(i).get("num"));	
					categoryListOther.add(categoryList.get(i));
				}
			}
			
			if((type==1&&categoryList2OtherCount>0)||(type==2&&categoryList2OtherSum>0)) {
				Map<String, Object> categoryList2Other=new HashMap<String, Object>();
				categoryList2Other.put("count", categoryList2OtherCount);
				categoryList2Other.put("num", categoryList2OtherSum);
				categoryList2Other.put("categoryName", "其他");
				categoryList2.add(categoryList2Other);
			}
			
			

			
			
			Integer manCategoryList2OtherCount=0;
			Integer manCategoryList2OtherSum=0;
			for (int i = 0; i < manCategoryList.size(); i++) {
				if(i<3) {
					manCategoryList2.add(manCategoryList.get(i));
				}else {
					manCategoryList2OtherCount+=(Integer)(manCategoryList.get(i).get("count"));
					manCategoryList2OtherSum+=(Integer)(manCategoryList.get(i).get("num"));	
					manCategoryOther.add(manCategoryList.get(i));
					
				}
			}
			
			if((type==1&&manCategoryList2OtherCount>0)||(type==2&&manCategoryList2OtherSum>0)) {
			Map<String, Object> manCategoryList2Other=new HashMap<String, Object>();
			manCategoryList2Other.put("count", manCategoryList2OtherCount);
			manCategoryList2Other.put("num", manCategoryList2OtherSum);
			manCategoryList2Other.put("categoryName", "其他");
			manCategoryList2.add(manCategoryList2Other);
			}
			
			
			
			Integer womanCategoryList2OtherCount=0;
			Integer womanCategoryList2OtherSum=0;
			for (int i = 0; i < womanCategoryList.size(); i++) {
				if(i<3) {
					womanCategoryList2.add(womanCategoryList.get(i));
				}else {
					womanCategoryList2OtherCount+=(Integer)(womanCategoryList.get(i).get("count"));
					womanCategoryList2OtherSum+=(Integer)(womanCategoryList.get(i).get("num"));	
					womanCategoryOther.add(womanCategoryList.get(i));
				}
			}
			if((type==1&&womanCategoryList2OtherCount>0)||(type==2&&womanCategoryList2OtherSum>0)) {
			Map<String, Object> womanCategoryList2Other=new HashMap<String, Object>();
			womanCategoryList2Other.put("count", womanCategoryList2OtherCount);
			womanCategoryList2Other.put("num", womanCategoryList2OtherSum);
			womanCategoryList2Other.put("categoryName", "其他");
			womanCategoryList2.add(womanCategoryList2Other);
			}
			
			
			
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("rankList", rankList);
			map.put("goodsCategoryList", goodsCategoryList);
			map.put("categoryList", categoryList2);
			map.put("manCategoryList", manCategoryList2);
			map.put("womanCategoryList", womanCategoryList2);
			
			map.put("categoryListOther", categoryListOther);
			map.put("manCategoryOther", manCategoryOther);
			map.put("womanCategoryOther", womanCategoryOther);
			
			 return R.ok().put("rank", map);
		 	} catch (Exception e) {
	            logger.error(e.getMessage(), e);
	            return R.error("服务器异常");
	        }
		 
	 }
	
}
