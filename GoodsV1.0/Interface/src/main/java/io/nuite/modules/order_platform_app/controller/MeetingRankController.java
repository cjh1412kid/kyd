package io.nuite.modules.order_platform_app.controller;

import java.text.DecimalFormat;
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
import io.nuite.modules.order_platform_app.annotation.Login;
import io.nuite.modules.order_platform_app.annotation.LoginUser;
import io.nuite.modules.order_platform_app.entity.MeetingGoodsEntity;
import io.nuite.modules.order_platform_app.service.MeetingGoodsService;
import io.nuite.modules.order_platform_app.service.MeetingRankService;
import io.nuite.modules.order_platform_app.service.MeetingShoppingCartService;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.sr_base.entity.GoodsCategoryEntity;
import io.nuite.modules.sr_base.service.GoodsCategoryService;
import io.nuite.modules.system.entity.MeetingAreaEntity;
import io.nuite.modules.system.entity.MeetingEntity;
import io.nuite.modules.system.service.MeetingAreaService;
import io.nuite.modules.system.service.MeetingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 订货平台2期 - 订货排行相关
 * @author yy
 * @date 2018-04-16 13:47
 */
@RestController
@RequestMapping("/order/app/meetingRank")
@Api(tags = "订货平台2期 - 订货排行相关")
public class MeetingRankController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private MeetingRankService meetingRankService;
    
    @Autowired
    private MeetingGoodsService meetingGoodsService;
    
    @Autowired
    private MeetingShoppingCartService meetingShoppingCartService;
    
    @Autowired
    private MeetingAreaService meetingAreaService;
    
    @Autowired
    private MeetingService meetingService;

    @Autowired
    private GoodsCategoryService goodsCategoryService;

	/**
     * 地区排行接口  （柱状图与商品详情页 两处使用）
     * [{rank: 1, areaSeq:1, areaName: "东北区域", num: 9999},{rank: 2, areaSeq:2, areaName: "西北区域", num: 8888},{...}]
     */
    @Login
    @GetMapping("areaRank")
    @ApiOperation("地区排行接口")
    public R areaRank(@ApiIgnore @LoginUser BaseUserEntity loginUser,
    		@ApiParam("订货会Seq") @RequestParam("meetingSeq") Integer meetingSeq,
    		@ApiParam(value="订货会鞋子Seqs（逗号分隔，不传则为订货会全部货品）", required=false) @RequestParam(value = "meetingGoodsSeqs", required = false) List<Object> meetingGoodsSeqs) {
    	
    	Date startTaskTime = new Date();
		logger.info("###MeetingRankController-地区排行接口areaRank，被调用: " + startTaskTime);
    	
    	try {
    		
    		//如果没有传具体鞋子seq，则查询该次订货会所有的鞋子序号
    		if(meetingGoodsSeqs == null || meetingGoodsSeqs.size() == 0) {
    			meetingGoodsSeqs = meetingGoodsService.getAllMeetingGoodsSeqsByMeetingSeq(meetingSeq);
    		}
    		if(meetingGoodsSeqs.size() == 0) {
    			return R.ok("暂无数据");
    		}

    		//查询鞋子总订货量的区域排行
    		List<Map<String, Object>> areaRankList = meetingRankService.getMeetingGoodsAreaRank(meetingGoodsSeqs);
    		
    		Integer totalNum = 0;
    		for(int i = 0; i < areaRankList.size(); i++) {
    			Map<String, Object> map = areaRankList.get(i);
    			map.put("rank", i + 1);
    			
    			totalNum += (Integer)map.get("num");
    		}
    		
    		//2019-05-13新需求：个人账号需要展示各个区域的百分比，隐藏具体数值
    		if(totalNum > 0) {
    			DecimalFormat df  = new DecimalFormat("0"); //这样为保持0位
	    		for(Map<String, Object> map : areaRankList) {
	    			int num = (Integer)map.get("num");
	    			map.put("percent", df.format((float)num/totalNum*100) + "%");
	    		}
    		}
    		
    		
    		
    		//查询订货会所有区域，将排行里没有的添加进去，数量填0
			List<Integer> areaSeqList = new ArrayList<Integer>();
    		for(Map<String, Object> map : areaRankList) {
    			areaSeqList.add((Integer)map.get("areaSeq"));
			}
    		List<MeetingAreaEntity> allMeetingAreaList = meetingAreaService.getCompanyAllMeetingArea(loginUser.getCompanySeq());
    		Integer rankNow = areaRankList.size() + 1;
    		for(MeetingAreaEntity meetingAreaEntity : allMeetingAreaList) {
    			if(!areaSeqList.contains(meetingAreaEntity.getSeq())) {
    				Map<String, Object> num0AreaMap = new HashMap<String, Object>();
    				num0AreaMap.put("rank", rankNow++);
    				num0AreaMap.put("areaSeq", meetingAreaEntity.getSeq());
    				num0AreaMap.put("areaName", meetingAreaEntity.getAreaName());
    				num0AreaMap.put("num", 0);
    				num0AreaMap.put("percent", "0%");
    				areaRankList.add(num0AreaMap);
    			}
    		}
    		
    		Map<String, Object> resultMap = new HashMap<String, Object>();
    		resultMap.put("totalNum", totalNum);
    		resultMap.put("rankList", areaRankList);
    		
    		Date endTaskTime = new Date();
    		logger.info("###MeetingRankController-地区排行接口areaRank，执行完毕返回: " + endTaskTime + "本次总计耗时:" + (endTaskTime.getTime() - startTaskTime.getTime()));
    		return R.ok(resultMap);
    		
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

    }
    
    
    
    
	/**
     * 货号订货量排行接口
     * [{rank: 1, "meetingGoodsSeq": 5, goodId: "9502", num: 9999, myNum:100,},{rank: 2,"meetingGoodsSeq": 3, goodId: "9503", num: 8888, myNum:100},{...}]
     */
    @Login
    @GetMapping("goodsIdRank")
    @ApiOperation("货号订货量排行接口")
    public R goodsIdRank(@ApiIgnore @LoginUser BaseUserEntity loginUser,
    		@ApiParam("订货会Seq") @RequestParam("meetingSeq") Integer meetingSeq,
    		@ApiParam(value="区域Seq", required = false) @RequestParam(value = "areaSeq", required = false) Integer areaSeq) {
    	
    	
    	Date startTaskTime = new Date();
		logger.info("###MeetingRankController-货号订货量排行接口goodsIdRank，被调用: " + startTaskTime);
    	
    	try {
    		
    		boolean userFlag = false;
    		if(!isFactoryUser(loginUser)) {
    			userFlag = true;  //订货方用户要在数据结构中添加我的订货量 myNum
    		}
    		
    		//查询鞋子总订货量排行
    		List<Map<String, Object>> goodIdRankList = meetingRankService.getMeetingGoodsIdRank(meetingSeq, areaSeq);
    		
    		Map<Integer, Integer> mySelectNumMap = null;
    		Map<Integer, Integer> myBuyNumMap = null;
    		if(userFlag) {
    			//我的购物车数量所有 商品-数量的Map
    			mySelectNumMap = meetingShoppingCartService.getMySelectNumMap(loginUser.getSeq(), meetingSeq);
    			//我的订单数量所有 商品-数量的Map
    			myBuyNumMap = meetingShoppingCartService.getMyBuyNumMap(loginUser.getSeq(), meetingSeq);
    		}
    		
    		Integer totalNum = 0;
    		for(int i = 0; i < goodIdRankList.size(); i++) {
    			Map<String, Object> map = goodIdRankList.get(i);
    			map.put("rank", i + 1);
    			
    			Integer meetingGoodsSeq = (Integer)map.get("meetingGoodsSeq");
    			MeetingGoodsEntity meetingGoodsEntity = meetingGoodsService.selectById(meetingGoodsSeq);
    			map.put("goodId", meetingGoodsEntity.getGoodID());
    			
    			totalNum += (Integer)map.get("num");
    			
    			if(userFlag) {
    				//添加我的订货量（我的购物车数量 + 我的订单数量）
    	    		Integer mySelectNum = mySelectNumMap.get(meetingGoodsSeq) == null ? 0 : mySelectNumMap.get(meetingGoodsSeq);
    	    		Integer myBuyNum = myBuyNumMap.get(meetingGoodsSeq) == null ? 0 : myBuyNumMap.get(meetingGoodsSeq);
    	    		map.put("myNum", mySelectNum + myBuyNum);
    			}
    		}
    		
    		
    		//查询订货会所有商品，将排行里没有的添加进去，数量填0
			List<Integer> meetingGoodsSeqList = new ArrayList<Integer>();
    		for(Map<String, Object> map : goodIdRankList) {
    			meetingGoodsSeqList.add((Integer)map.get("meetingGoodsSeq"));
			}
    		List<MeetingGoodsEntity> allMeetingGoodsList = meetingGoodsService.getAllMeetingGoodsByMeetingSeq(meetingSeq);
    		Integer rankNow = goodIdRankList.size() + 1;
    		for(MeetingGoodsEntity meetingGoodsEntity : allMeetingGoodsList) {
    			if(!meetingGoodsSeqList.contains(meetingGoodsEntity.getSeq())) {
    				Map<String, Object> num0GoodIdMap = new HashMap<String, Object>();
    				num0GoodIdMap.put("rank", rankNow++);
    				num0GoodIdMap.put("meetingGoodsSeq", meetingGoodsEntity.getSeq());
    				num0GoodIdMap.put("goodId", meetingGoodsEntity.getGoodID());
    				num0GoodIdMap.put("num", 0);
    				num0GoodIdMap.put("myNum", 0);
    				goodIdRankList.add(num0GoodIdMap);
    			}
    		}
    		
    		
    		Map<String, Object> resultMap = new HashMap<String, Object>();
    		resultMap.put("totalNum", totalNum);
    		resultMap.put("rankList", goodIdRankList);
    		
    		Date endTaskTime = new Date();
    		logger.info("###MeetingRankController-货号订货量排行接口goodsIdRank，执行完毕返回: " + endTaskTime + "本次总计耗时:" + (endTaskTime.getTime() - startTaskTime.getTime()));

    		
    		return R.ok(resultMap);
    		
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

    }
    
  
    @Login
    @GetMapping("numRank")
    @ApiOperation("选款排行接口")
    public R numRank( @RequestParam("meetingSeq") Integer meetingSeq,@ApiIgnore @LoginUser BaseUserEntity loginUser) {
    	
    	Date startTaskTime = new Date();
		logger.info("###MeetingRankController-选款排行接口numRank，被调用: " + startTaskTime);
		
    	try {
    		boolean userFlag = false;
    		if(!isFactoryUser(loginUser)) {
    			userFlag = true;  //订货方用户要在数据结构中添加我的订货量 myNum
    		}
    		
    		List<Map<String, Object>> numRankList=new ArrayList<Map<String,Object>>();
    		
    		Map<Integer, Integer> mySelectCountMap = null;
    		Map<Integer, Integer> myBuyCountMap = null;
    		if(userFlag) {
    			//我的购物车数量所有 商品-选款次数的Map
    			mySelectCountMap = meetingShoppingCartService.getMySelectCountMap(loginUser.getSeq(), meetingSeq);
    			//我的订单数量所有 商品-购买次数的Map
    			myBuyCountMap = meetingShoppingCartService.getMyBuyCountMap(loginUser.getSeq(), meetingSeq);
    		}
    		
    		
    		Integer totalNum = 0;
    		//查询鞋子订货次数排行
    			numRankList = meetingRankService.getMeetingGoodsNumRank(meetingSeq);
    			
    			Date startTaskTime1 = new Date();
    			logger.info("###MeetingRankController-选款排行接口numRank，循环获取我的订货量: " + startTaskTime1);
    			
    			
    			for(int i = 0; i < numRankList.size(); i++) {
        			Map<String, Object> map = numRankList.get(i);
        			map.put("rank", i + 1);
        			Integer meetingGoodsSeq = (Integer)map.get("meetingGoodsSeq");
        			MeetingGoodsEntity meetingGoodsEntity = meetingGoodsService.selectById(meetingGoodsSeq);
        			map.put("goodId", meetingGoodsEntity.getGoodID());
        			totalNum += (Integer)map.get("goodsCount");
        			
        			if(userFlag) {
        				//添加我的选款次数（购物车次数（最多只有一次）+ 下订单次数）
        	    		Integer mySelectCount = mySelectCountMap.get(meetingGoodsSeq) == null ? 0 : mySelectCountMap.get(meetingGoodsSeq);
        	    		Integer myBuyCount = myBuyCountMap.get(meetingGoodsSeq) == null ? 0 : myBuyCountMap.get(meetingGoodsSeq);
        	    		map.put("myNum", mySelectCount + myBuyCount);
        			}
        		}
        		Date endTaskTime1 = new Date();
        		logger.info("###MeetingRankController-选款排行接口numRank，循环获取我的订货量完毕: " + endTaskTime1 + "本次总计耗时:" + (endTaskTime1.getTime() - startTaskTime1.getTime()));

    			
    			//查询订货会所有商品，将排行里没有的添加进去，数量填0
    			List<Integer> meetingGoodsSeqList = new ArrayList<Integer>();
        		for(Map<String, Object> map : numRankList) {
        			meetingGoodsSeqList.add((Integer)map.get("meetingGoodsSeq"));
    			}
        		List<MeetingGoodsEntity> allMeetingGoodsList = meetingGoodsService.getAllMeetingGoodsByMeetingSeq(meetingSeq);
        		Integer rankNow = numRankList.size() + 1;
        		for(MeetingGoodsEntity meetingGoodsEntity : allMeetingGoodsList) {
        			if(!meetingGoodsSeqList.contains(meetingGoodsEntity.getSeq())) {
        				Map<String, Object> num0GoodIdMap = new HashMap<String, Object>();
        				num0GoodIdMap.put("rank", rankNow++);
        				num0GoodIdMap.put("meetingGoodsSeq", meetingGoodsEntity.getSeq());
        				num0GoodIdMap.put("goodId", meetingGoodsEntity.getGoodID());
        				num0GoodIdMap.put("goodsCount", 0);
        				num0GoodIdMap.put("myNum", 0);
        				numRankList.add(num0GoodIdMap);
        			}
        		}
        		
    		Map<String, Object> resultMap = new HashMap<String, Object>();
    		resultMap.put("rankList", numRankList);
    		resultMap.put("totalNum", totalNum);
    		
    		Date endTaskTime = new Date();
    		logger.info("###MeetingRankController-选款排行接口numRank，执行完毕返回: " + endTaskTime + "本次总计耗时:" + (endTaskTime.getTime() - startTaskTime.getTime()));

    		return R.ok(resultMap);
    		
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

    }
    
    @Login
    @GetMapping("kindRank")
    @ApiOperation("品类排行接口")
    public R kindRank(@RequestParam("meetingSeq") Integer meetingSeq) {
    	
    	Date startTaskTime = new Date();
		logger.info("###MeetingRankController-品类排行接口kindRank，被调用: " + startTaskTime);
		
		
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
				//获取三级分类
				manCategoryList=meetingGoodsService.getParentCategory(meetingEntity.getCompanySeq(), meetingSeq, manCategorySeq,1);
				for (Map<String, Object> map : manCategoryList) {
					Integer cateSeq=(Integer) map.get("categorySeq");
					GoodsCategoryEntity categoryEntity=goodsCategoryService.selectById(cateSeq);
					GoodsCategoryEntity parentCategory=goodsCategoryService.selectById(categoryEntity.getParentSeq());
					map.put("categoryName",parentCategory.getName()+"/"+categoryEntity.getName());
				}
				

				
			}
			//女鞋分类占比
			List<Map<String, Object>> womanCategoryList=new ArrayList<Map<String,Object>>();
			if(womanCategorySeq!=null) {
				List<GoodsCategoryEntity> goodsCategoryEntities2=goodsCategoryService.getGoodsCategoryByParent(womanCategorySeq,meetingEntity.getCompanySeq());
				//获取三级分类
			
				womanCategoryList=meetingGoodsService.getParentCategory(meetingEntity.getCompanySeq(), meetingSeq,womanCategorySeq,1);
				for (Map<String, Object> map : womanCategoryList) {
					Integer cateSeq=(Integer) map.get("categorySeq");
					GoodsCategoryEntity categoryEntity=goodsCategoryService.selectById(cateSeq);
					GoodsCategoryEntity parentCategory=goodsCategoryService.selectById(categoryEntity.getParentSeq());
					map.put("categoryName", parentCategory.getName()+"/"+categoryEntity.getName());
				}
			}
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("goodsCategoryList",goodsCategoryList );
			map.put("manCategoryList",manCategoryList );
			map.put("womanCategoryList",womanCategoryList );
			
    		Date endTaskTime = new Date();
    		logger.info("######MeetingRankController-品类排行接口kindRank，执行完毕返回: " + endTaskTime + "本次总计耗时:" + (endTaskTime.getTime() - startTaskTime.getTime()));
    		
    		
			return R.ok(map);
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}
    }
    
    
  	/**
       * 货号订货量排行接口（分类）
       * [{rank: 1, "meetingGoodsSeq": 5, goodId: "9502", num: 9999, myNum:100,},{rank: 2,"meetingGoodsSeq": 3, goodId: "9503", num: 8888, myNum:100},{...}]
       */
      @Login
      @GetMapping("goodsIdRankByCategory")
      @ApiOperation("货号订货量排行接口（分类）")
      public R goodsIdRankByCategory(@ApiIgnore @LoginUser BaseUserEntity loginUser,
      		@ApiParam("订货会Seq") @RequestParam("meetingSeq") Integer meetingSeq,
      		@ApiParam(value="分类Seq", required = false) @RequestParam(value = "categorySeq", required = false) Integer categorySeq) {
      	
    	  
      	Date startTaskTime = new Date();
  		logger.info("###MeetingRankController-货号订货量排行接口（分类）goodsIdRankByCategory，被调用: " + startTaskTime);

  		
  		
    	  
    	try {
      		
      		boolean userFlag = false;
      		if(!isFactoryUser(loginUser)) {
      			userFlag = true;  //订货方用户要在数据结构中添加我的订货量 myNum
      		}
      		
      		//查询鞋子总订货量排行
      		List<Map<String, Object>> goodIdRankList = meetingRankService.getRankByCategorySeq(meetingSeq, categorySeq);
      		
    		Map<Integer, Integer> mySelectNumMap = null;
    		Map<Integer, Integer> myBuyNumMap = null;
    		if(userFlag) {
    			//我的购物车数量所有 商品-数量的Map
    			mySelectNumMap = meetingShoppingCartService.getMySelectNumMap(loginUser.getSeq(), meetingSeq);
    			//我的订单数量所有 商品-数量的Map
    			myBuyNumMap = meetingShoppingCartService.getMyBuyNumMap(loginUser.getSeq(), meetingSeq);
    		}
    		
      		Integer totalNum = 0;
      		for(int i = 0; i < goodIdRankList.size(); i++) {
      			Map<String, Object> map = goodIdRankList.get(i);
      			map.put("rank", i + 1);
      			
      			Integer meetingGoodsSeq = (Integer)map.get("meetingGoodsSeq");
      			MeetingGoodsEntity meetingGoodsEntity = meetingGoodsService.selectById(meetingGoodsSeq);
      			map.put("goodId", meetingGoodsEntity.getGoodID());
      			
      			totalNum += (Integer)map.get("num");
      			
      			if(userFlag) {
      				//添加我的订货量（我的购物车数量 + 我的订单数量）
    	    		Integer mySelectNum = mySelectNumMap.get(meetingGoodsSeq) == null ? 0 : mySelectNumMap.get(meetingGoodsSeq);
    	    		Integer myBuyNum = myBuyNumMap.get(meetingGoodsSeq) == null ? 0 : myBuyNumMap.get(meetingGoodsSeq);
      	    		map.put("myNum", mySelectNum + myBuyNum);
      			}
      		}
      		
      		
      		//查询订货会所有商品，将排行里没有的添加进去，数量填0
  			List<Integer> meetingGoodsSeqList = new ArrayList<Integer>();
      		for(Map<String, Object> map : goodIdRankList) {
      			meetingGoodsSeqList.add((Integer)map.get("meetingGoodsSeq"));
  			}
      		List<MeetingGoodsEntity> allMeetingGoodsList = meetingGoodsService.getAllMeetingGoodsByMeetingSeq(meetingSeq);
      		Integer rankNow = goodIdRankList.size() + 1;
      		for(MeetingGoodsEntity meetingGoodsEntity : allMeetingGoodsList) {
      			if(!meetingGoodsSeqList.contains(meetingGoodsEntity.getSeq())) {
      				Map<String, Object> num0GoodIdMap = new HashMap<String, Object>();
      				num0GoodIdMap.put("rank", rankNow++);
      				num0GoodIdMap.put("meetingGoodsSeq", meetingGoodsEntity.getSeq());
      				num0GoodIdMap.put("goodId", meetingGoodsEntity.getGoodID());
      				num0GoodIdMap.put("num", 0);
      				num0GoodIdMap.put("myNum", 0);
      				goodIdRankList.add(num0GoodIdMap);
      			}
      		}
      		
      		
      		Map<String, Object> resultMap = new HashMap<String, Object>();
      		resultMap.put("totalNum", totalNum);
      		resultMap.put("rankList", goodIdRankList);
      		
    		Date endTaskTime = new Date();
    		logger.info("#########MeetingRankController-货号订货量排行接口（分类）goodsIdRankByCategory，执行完毕返回: " + endTaskTime + "本次总计耗时:" + (endTaskTime.getTime() - startTaskTime.getTime()));
 
    		
      		return R.ok(resultMap);
      		
      	} catch (Exception e) {
  			e.printStackTrace();
  			logger.error(e.getMessage(), e);
  			return R.error("服务器异常");
  		}

      }
      
      
      
      @Login
      @GetMapping("numRankByCategory")
      @ApiOperation("选款排行接口(分类)")
      public R numRankByCategory( @RequestParam("meetingSeq") Integer meetingSeq,@ApiIgnore @LoginUser BaseUserEntity loginUser,@ApiParam(value="分类Seq", required = false) @RequestParam(value = "categorySeq", required = false) Integer categorySeq) {
      	
    	  
        	Date startTaskTime = new Date();
      		logger.info("###MeetingRankController-选款排行接口(分类)numRankByCategory，被调用: " + startTaskTime);

      		
    	  try {
      		boolean userFlag = false;
      		if(!isFactoryUser(loginUser)) {
      			userFlag = true;  //订货方用户要在数据结构中添加我的订货量 myNum
      		}
      		
      		List<Map<String, Object>> numRankList=new ArrayList<Map<String,Object>>();
      		
    		Map<Integer, Integer> mySelectCountMap = null;
    		Map<Integer, Integer> myBuyCountMap = null;
    		if(userFlag) {
    			//我的购物车数量所有 商品-选款次数的Map
    			mySelectCountMap = meetingShoppingCartService.getMySelectCountMap(loginUser.getSeq(), meetingSeq);
    			//我的订单数量所有 商品-购买次数的Map
    			myBuyCountMap = meetingShoppingCartService.getMyBuyCountMap(loginUser.getSeq(), meetingSeq);
    		}
    		
      		Integer totalNum = 0;
      		//查询鞋子订货次数排行
      			numRankList = meetingRankService.getNumRankByCategorySeq(meetingSeq, categorySeq);
      			for(int i = 0; i < numRankList.size(); i++) {
          			Map<String, Object> map = numRankList.get(i);
          			map.put("rank", i + 1);
          			Integer meetingGoodsSeq = (Integer)map.get("meetingGoodsSeq");
          			MeetingGoodsEntity meetingGoodsEntity = meetingGoodsService.selectById(meetingGoodsSeq);
          			map.put("goodId", meetingGoodsEntity.getGoodID());
          			totalNum += (Integer)map.get("goodsCount");
          			
          			if(userFlag) {
        				//添加我的选款次数（购物车次数（最多只有一次）+ 下订单次数）
        	    		Integer mySelectCount = mySelectCountMap.get(meetingGoodsSeq) == null ? 0 : mySelectCountMap.get(meetingGoodsSeq);
        	    		Integer myBuyCount = myBuyCountMap.get(meetingGoodsSeq) == null ? 0 : myBuyCountMap.get(meetingGoodsSeq);
          	    		map.put("myNum", mySelectCount + myBuyCount);
          			}
          		}
      			
      			//查询订货会所有商品，将排行里没有的添加进去，数量填0
      			List<Integer> meetingGoodsSeqList = new ArrayList<Integer>();
          		for(Map<String, Object> map : numRankList) {
          			meetingGoodsSeqList.add((Integer)map.get("meetingGoodsSeq"));
      			}
          		List<MeetingGoodsEntity> allMeetingGoodsList = meetingGoodsService.getAllMeetingGoodsByMeetingSeq(meetingSeq);
          		Integer rankNow = numRankList.size() + 1;
          		for(MeetingGoodsEntity meetingGoodsEntity : allMeetingGoodsList) {
          			if(!meetingGoodsSeqList.contains(meetingGoodsEntity.getSeq())) {
          				Map<String, Object> num0GoodIdMap = new HashMap<String, Object>();
          				num0GoodIdMap.put("rank", rankNow++);
          				num0GoodIdMap.put("meetingGoodsSeq", meetingGoodsEntity.getSeq());
          				num0GoodIdMap.put("goodId", meetingGoodsEntity.getGoodID());
          				num0GoodIdMap.put("goodsCount", 0);
          				num0GoodIdMap.put("myNum", 0);
          				numRankList.add(num0GoodIdMap);
          			}
          		}
          		
      		Map<String, Object> resultMap = new HashMap<String, Object>();
      		resultMap.put("rankList", numRankList);
      		resultMap.put("totalNum", totalNum);
      		
    		Date endTaskTime = new Date();
    		logger.info("###MeetingRankController-选款排行接口(分类)numRankByCategory，执行完毕返回: " + endTaskTime + "本次总计耗时:" + (endTaskTime.getTime() - startTaskTime.getTime()));
 
    		
      		return R.ok(resultMap);
      		
      	} catch (Exception e) {
  			e.printStackTrace();
  			logger.error(e.getMessage(), e);
  			return R.error("服务器异常");
  		}

      }
    
}
