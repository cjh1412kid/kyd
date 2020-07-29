package io.nuite.modules.system.controller;

import java.util.ArrayList;
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

import io.nuite.common.utils.FileUtils;
import io.nuite.common.utils.R;
import io.nuite.modules.order_platform_app.annotation.Login;
import io.nuite.modules.order_platform_app.annotation.LoginUser;
import io.nuite.modules.order_platform_app.entity.MeetingGoodsEntity;
import io.nuite.modules.order_platform_app.service.MeetingGoodsService;
import io.nuite.modules.order_platform_app.service.MeetingRankService;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.sr_base.entity.GoodsCategoryEntity;
import io.nuite.modules.sr_base.service.GoodsCategoryService;
import io.nuite.modules.sr_base.utils.ConfigUtils;
import io.nuite.modules.system.entity.MeetingAreaEntity;
import io.nuite.modules.system.entity.MeetingEntity;
import io.nuite.modules.system.service.MeetingAreaService;
import io.nuite.modules.system.service.MeetingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/system/meetingRank" )

public class SysMeetingRankController extends AbstractController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    private MeetingRankService meetingRankService;
    
    @Autowired
    private MeetingGoodsService meetingGoodsService;
    
 
    @Autowired
    private MeetingAreaService meetingAreaService;
    
    @Autowired
    protected ConfigUtils configUtils;
    
    @Autowired
    private GoodsCategoryService goodsCategoryService;
    
    @Autowired
    private MeetingService meetingService;
	
	/**
     * 货号排行接口
     */
    @RequestMapping("goodsIdRank")
    public R goodsIdRank(
    		 @RequestParam("meetingSeq") Integer meetingSeq,
    		 @RequestParam(value = "areaSeq", required = false) Integer areaSeq) {
    	try {
    		//查询鞋子总订货量排行
    		List<Map<String, Object>> goodIdRankList = meetingRankService.getMeetingGoodsIdRank(meetingSeq, areaSeq);
    		Integer totalNum = 0;
    		Integer top10MyNum = 0;
    		Integer after10MyNum = 0;
    		for(int i = 0; i < goodIdRankList.size(); i++) {
    			Map<String, Object> map = goodIdRankList.get(i);
    			map.put("rank", i + 1);
    		
    			Integer meetingGoodsSeq = (Integer)map.get("meetingGoodsSeq");
    			MeetingGoodsEntity meetingGoodsEntity = meetingGoodsService.selectById(meetingGoodsSeq);
    			map.put("goodId", meetingGoodsEntity.getGoodID());
				   String imageUrl =configUtils.getPictureBaseUrl() + "/" + configUtils.getOrderPlatformApp().getOrderPlatformDir() + "/"+ configUtils.getOrderPlatformApp().getMeetingGoods() +"/" +meetingGoodsEntity.getGoodID()+"/"+ meetingGoodsEntity.getImg();
    			map.put("img", imageUrl);
    			totalNum += (Integer)map.get("num");
    		}
    		
    		//查询订货会所有商品，将排行里没有的添加进去，数量填0
			/*
			 * List<Integer> meetingGoodsSeqList = new ArrayList<Integer>(); for(Map<String,
			 * Object> map : goodIdRankList) {
			 * meetingGoodsSeqList.add((Integer)map.get("meetingGoodsSeq")); }
			 * List<MeetingGoodsEntity> allMeetingGoodsList =
			 * meetingGoodsService.getAllMeetingGoodsByMeetingSeq(meetingSeq); Integer
			 * rankNow = goodIdRankList.size() + 1; for(MeetingGoodsEntity
			 * meetingGoodsEntity : allMeetingGoodsList) {
			 * if(!meetingGoodsSeqList.contains(meetingGoodsEntity.getSeq())) { Map<String,
			 * Object> num0GoodIdMap = new HashMap<String, Object>();
			 * num0GoodIdMap.put("rank", rankNow++); num0GoodIdMap.put("meetingGoodsSeq",
			 * meetingGoodsEntity.getSeq()); num0GoodIdMap.put("goodId",
			 * meetingGoodsEntity.getGoodID()); String imageUrl
			 * =configUtils.getPictureBaseUrl() + "/" +
			 * configUtils.getOrderPlatformApp().getOrderPlatformDir() + "/"+
			 * configUtils.getOrderPlatformApp().getMeetingGoods() +"/"
			 * +meetingGoodsEntity.getGoodID()+"/"+ meetingGoodsEntity.getImg();
			 * num0GoodIdMap.put("img", imageUrl); num0GoodIdMap.put("num", 0);
			 * num0GoodIdMap.put("myNum", 0); goodIdRankList.add(num0GoodIdMap); } }
			 */
    		
    		
    		Map<String, Object> resultMap = new HashMap<String, Object>();
    		resultMap.put("totalNum", totalNum);
    		resultMap.put("top10MyNum", top10MyNum);
    		resultMap.put("after10MyNum", after10MyNum);
    		resultMap.put("rankList", goodIdRankList);
    		return R.ok(resultMap);
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

    }
    

  
    @GetMapping("areaRank")
    public R areaRank( @RequestParam("meetingSeq") Integer meetingSeq,@RequestParam(value = "meetingGoodsSeqs", required = false) List<Object> meetingGoodsSeqs) {
    	try {
    		if(meetingGoodsSeqs == null || meetingGoodsSeqs.size() == 0) {
    			meetingGoodsSeqs = meetingGoodsService.getAllMeetingGoodsSeqsByMeetingSeq(meetingSeq);
    		}
    		List<Map<String, Object>> areaRankList=new ArrayList<Map<String,Object>>();
    		Integer totalNum = 0;
    		if(meetingGoodsSeqs.size()>0) {
    		//查询鞋子总订货量的区域排行
    		areaRankList = meetingRankService.getMeetingGoodsAreaRank(meetingGoodsSeqs);
    		for(int i = 0; i < areaRankList.size(); i++) {
    			Map<String, Object> map = areaRankList.get(i);
    			map.put("rank", i + 1);
    			
    			totalNum += (Integer)map.get("num");
    		}
    		}
    		
    		//查询订货会所有区域，将排行里没有的添加进去，数量填0
			/*
			 * List<Integer> areaSeqList = new ArrayList<Integer>(); for(Map<String, Object>
			 * map : areaRankList) { areaSeqList.add((Integer)map.get("areaSeq")); }
			 * List<MeetingAreaEntity> allMeetingAreaList =
			 * meetingAreaService.getCompanyAllMeetingArea(getUser().getCompanySeq());
			 * Integer rankNow = areaRankList.size() + 1; for(MeetingAreaEntity
			 * meetingAreaEntity : allMeetingAreaList) {
			 * if(!areaSeqList.contains(meetingAreaEntity.getSeq())) { Map<String, Object>
			 * num0AreaMap = new HashMap<String, Object>(); num0AreaMap.put("rank",
			 * rankNow++); num0AreaMap.put("areaSeq", meetingAreaEntity.getSeq());
			 * num0AreaMap.put("areaName", meetingAreaEntity.getAreaName());
			 * num0AreaMap.put("num", 0);
			 * 
			 * areaRankList.add(num0AreaMap); } }
			 */
    		
    		Map<String, Object> resultMap = new HashMap<String, Object>();
    		resultMap.put("totalNum", totalNum);
    		resultMap.put("rankList", areaRankList);
    		return R.ok(resultMap);
    		
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

    }
    
    @GetMapping("numRank")
    public R numRank( @RequestParam("meetingSeq") Integer meetingSeq) {
    	try {
    	
    		List<Map<String, Object>> numRankList=new ArrayList<Map<String,Object>>();
    		Integer totalNum = 0;
    		//查询鞋子订货次数排行
    			numRankList = meetingRankService.getMeetingGoodsNumRank(meetingSeq);
    			for(int i = 0; i < numRankList.size(); i++) {
        			Map<String, Object> map = numRankList.get(i);
        			map.put("rank", i + 1);
        		
        			Integer meetingGoodsSeq = (Integer)map.get("meetingGoodsSeq");
        			MeetingGoodsEntity meetingGoodsEntity = meetingGoodsService.selectById(meetingGoodsSeq);
        			map.put("goodId", meetingGoodsEntity.getGoodID());
        			totalNum += (Integer)map.get("goodsCount");
        		}
    		//查询订货会所有区域，将排行里没有的添加进去，数量填0
			/*
			 * List<Integer> areaSeqList = new ArrayList<Integer>(); for(Map<String, Object>
			 * map : areaRankList) { areaSeqList.add((Integer)map.get("areaSeq")); }
			 * List<MeetingAreaEntity> allMeetingAreaList =
			 * meetingAreaService.getCompanyAllMeetingArea(getUser().getCompanySeq());
			 * Integer rankNow = areaRankList.size() + 1; for(MeetingAreaEntity
			 * meetingAreaEntity : allMeetingAreaList) {
			 * if(!areaSeqList.contains(meetingAreaEntity.getSeq())) { Map<String, Object>
			 * num0AreaMap = new HashMap<String, Object>(); num0AreaMap.put("rank",
			 * rankNow++); num0AreaMap.put("areaSeq", meetingAreaEntity.getSeq());
			 * num0AreaMap.put("areaName", meetingAreaEntity.getAreaName());
			 * num0AreaMap.put("num", 0);
			 * 
			 * areaRankList.add(num0AreaMap); } }
			 */
    		
    		Map<String, Object> resultMap = new HashMap<String, Object>();
    		resultMap.put("rankList", numRankList);
    		resultMap.put("totalNum", totalNum);
    		return R.ok(resultMap);
    		
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

    }
    
    
    @GetMapping("kindRank")
    public R kindRank(@RequestParam("meetingSeq") Integer meetingSeq) {
    	try {
    		//获取该公司的一级分类
			MeetingEntity meetingEntity=meetingService.selectById(meetingSeq);
			List<Map<String, Object>> goodsCategoryList=meetingGoodsService.getParentCategory(meetingEntity.getCompanySeq(), meetingSeq, 0,1);
			
			List<GoodsCategoryEntity> goodsCategoryEntities=goodsCategoryService.getGoodsCategoryByParent(0,meetingEntity.getCompanySeq());
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
			List<Map<String, Object>> manCategoryList=new ArrayList<Map<String,Object>>();
			if(manCategorySeq!=null) {
				List<GoodsCategoryEntity> goodsCategoryEntities2=goodsCategoryService.getGoodsCategoryByParent(manCategorySeq,meetingEntity.getCompanySeq());
				//获取三级分类
				for (GoodsCategoryEntity goodsCategoryEntity3 : goodsCategoryEntities2) {
				List<Map<String, Object>> goodsCategoryList2=meetingGoodsService.getParentCategory(meetingEntity.getCompanySeq(), meetingSeq, goodsCategoryEntity3.getSeq(),1);
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
				List<GoodsCategoryEntity> goodsCategoryEntities2=goodsCategoryService.getGoodsCategoryByParent(womanCategorySeq,meetingEntity.getCompanySeq());
				//获取三级分类
				for (GoodsCategoryEntity goodsCategoryEntity3 : goodsCategoryEntities2) {
				List<Map<String, Object>> goodsCategoryList2=meetingGoodsService.getParentCategory(meetingEntity.getCompanySeq(), meetingSeq, goodsCategoryEntity3.getSeq(),1);
				for (Map<String, Object> map : goodsCategoryList2) {
					Integer cateSeq=(Integer) map.get("categorySeq");
					GoodsCategoryEntity categoryEntity=goodsCategoryService.selectById(cateSeq);
					GoodsCategoryEntity parentCategory=goodsCategoryService.selectById(categoryEntity.getParentSeq());
					map.put("categoryName", parentCategory.getName()+"/"+categoryEntity.getName());
				}
				
				womanCategoryList.addAll(goodsCategoryList2);
				}
			}
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("goodsCategoryList",goodsCategoryList );
			map.put("manCategoryList",manCategoryList );
			map.put("womanCategoryList",womanCategoryList );
			return R.ok(map);
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}
    }
}
