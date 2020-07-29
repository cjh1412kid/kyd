package io.nuite.modules.order_platform_app.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import io.nuite.common.utils.R;
import io.nuite.modules.order_platform_app.annotation.Login;
import io.nuite.modules.order_platform_app.annotation.LoginUser;
import io.nuite.modules.order_platform_app.dao.MeetingPlanDao;
import io.nuite.modules.order_platform_app.entity.MeetingPlanEntity;
import io.nuite.modules.order_platform_app.entity.OrderEntity;
import io.nuite.modules.order_platform_app.entity.OrderProductsEntity;
import io.nuite.modules.order_platform_app.entity.ShoppingCartEntity;
import io.nuite.modules.order_platform_app.service.MeetingPlanService;
import io.nuite.modules.sr_base.dao.GoodsCategoryDao;
import io.nuite.modules.sr_base.dao.GoodsPeriodDao;
import io.nuite.modules.sr_base.dao.GoodsSXDao;
import io.nuite.modules.sr_base.dao.GoodsSXOptionDao;
import io.nuite.modules.sr_base.dao.GoodsShoesDao;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.sr_base.entity.GoodsCategoryEntity;
import io.nuite.modules.sr_base.entity.GoodsPeriodEntity;
import io.nuite.modules.sr_base.entity.GoodsSXEntity;
import io.nuite.modules.sr_base.entity.GoodsSXOptionEntity;
import io.nuite.modules.sr_base.entity.GoodsShoesEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 订货计划接口
 * @author yy
 * @date 2018-08-16 13:47
 */
@RestController
@RequestMapping("/order/app/meetingPlan")
@Api(tags = "订货平台 - 订货计划接口", description = "波次列表 + 设置提醒 + 订货计划柱状图")
public class MeetingPlanController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private MeetingPlanService meetingPlanService;
    
    @Autowired
    private MeetingPlanDao meetingPlanDao;
    
    @Autowired
    private GoodsCategoryDao goodsCategoryDao;
    
    @Autowired
    private GoodsSXDao goodsSXDao;
    
    @Autowired
    private GoodsSXOptionDao goodsSXOptionDao;
    
    @Autowired
    private GoodsShoesDao goodsShoesDao;
    
    @Autowired
    private GoodsPeriodDao goodsPeriodDao;
    
    
    
	/**
     * 波次列表
     */
    @Login
    @GetMapping("periodList")
    @ApiOperation("波次列表")
    public R periodList(@ApiIgnore @LoginUser BaseUserEntity loginUser) {
    	try {
    		List<GoodsPeriodEntity> goodsPeriodList = meetingPlanService.getAllPeriodList(loginUser.getBrandSeq());
    		return R.ok(goodsPeriodList);
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

    }
    
    
    
	/**
     * 设置提醒
     */
    @Login
    @PostMapping("setRemind")
    @ApiOperation("设置提醒")
    public R setRemind(@ApiIgnore @RequestAttribute("userSeq") Integer userSeq,
    					@ApiParam("扫码提醒（0:不提醒,1:提醒）") @RequestParam("scanRemind") Integer scanRemind,
    					@ApiParam("下单提醒（0:不提醒,1:提醒）") @RequestParam("orderRemind") Integer orderRemind) {
    	try {
    		meetingPlanService.updateMeetingRemind(userSeq, scanRemind, orderRemind);
    		return R.ok();
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

    }
    
    
	/**
     * 订货计划柱状图
     */
    @Login
    @GetMapping("barGraph")
    @ApiOperation("订货计划柱状图")
    public R barGraph(@ApiIgnore @LoginUser BaseUserEntity loginUser,
    					@ApiParam("波次序号") @RequestParam("periodSeq") Integer periodSeq) {
    	try {
    		//获取用户指定波次的订货计划列表
            List<MeetingPlanEntity> meetingPlanList= meetingPlanService.getUserMeetingPlanList(loginUser.getSeq(), periodSeq);
            
    		//前端柱状图要求返回8个数组
            List<Integer> planSeqList = new ArrayList<Integer>();
            List<String> categoryDataList = new ArrayList<String>();
            List<Object> planNumList = new ArrayList<Object>();
            List<Object> planMoneyList = new ArrayList<Object>();
            List<Object> planGoodsIDsList = new ArrayList<Object>();
            List<Object> actualNumList = new ArrayList<Object>();
            List<Object> actualMoneyList = new ArrayList<Object>();
            List<Object> actualGoodsIDsList = new ArrayList<Object>();
            
        	GoodsSXEntity goodsSX2Entity = new GoodsSXEntity();
        	goodsSX2Entity.setCompanySeq(loginUser.getCompanySeq());
        	goodsSX2Entity.setSXId("SX2");
        	goodsSX2Entity = goodsSXDao.selectOne(goodsSX2Entity);
        	
        	GoodsSXEntity goodsSX3Entity = new GoodsSXEntity();
        	goodsSX3Entity.setCompanySeq(loginUser.getCompanySeq());
        	goodsSX3Entity.setSXId("SX3");
        	goodsSX3Entity = goodsSXDao.selectOne(goodsSX3Entity);
        	
        	
        	/** 订货会期间实际购买的订单所有商品 **/
    		//获取该用户 该波次订货会期间的所有订单
       		GoodsPeriodEntity goodsPeriodEntity = goodsPeriodDao.selectById(periodSeq);
    		List<OrderEntity> orderEntityList = meetingPlanService.getAllOrderByUserSeq(loginUser.getSeq(), goodsPeriodEntity);
    		List<Integer> orderSeqList = new ArrayList<Integer>();
    		for(OrderEntity orderEntity : orderEntityList) {
    			orderSeqList.add(orderEntity.getSeq());
    		}

    		//获取订单的所有商品列表
    		List<OrderProductsEntity> orderProductsEntityList = new ArrayList<OrderProductsEntity>();
    		if(orderSeqList.size() > 0) {
    			orderProductsEntityList = meetingPlanService.getAllOrderProductByOrderSeqList(orderSeqList);
    		}
    		/** 订货会期间实际购买的订单所有商品 **/
        	
            for(MeetingPlanEntity meetingPlan : meetingPlanList) {
            	planSeqList.add(meetingPlan.getSeq());
            	
                //分类名称
                GoodsCategoryEntity goodsCategoryEntity = goodsCategoryDao.selectById(meetingPlan.getCategorySeq());
                //SX2Value名称
                GoodsSXOptionEntity goodsSX2OptionEntity = new GoodsSXOptionEntity();
                goodsSX2OptionEntity.setSXSeq(goodsSX2Entity.getSeq());
                goodsSX2OptionEntity.setCode(meetingPlan.getSX2());
                goodsSX2OptionEntity = goodsSXOptionDao.selectOne(goodsSX2OptionEntity);
                //SX3Value名称
                GoodsSXOptionEntity goodsSX3OptionEntity = new GoodsSXOptionEntity();
                goodsSX3OptionEntity.setSXSeq(goodsSX3Entity.getSeq());
                goodsSX3OptionEntity.setCode(meetingPlan.getSX3());
                goodsSX3OptionEntity = goodsSXOptionDao.selectOne(goodsSX3OptionEntity);
            	
                //计划类型:大类+品类+风格
                categoryDataList.add(goodsCategoryEntity.getName() + "-" + goodsSX2OptionEntity.getValue() + "\n" + goodsSX3OptionEntity.getValue());
                
                //计划购买量
                planNumList.add(meetingPlan.getPlanNum());
                planMoneyList.add(meetingPlan.getPlanMoney());
                planGoodsIDsList.add(meetingPlan.getPlanGoodsIDs());
                
                
                /** 计算实际购买量 **/
        		Integer num = 0;
        		BigDecimal money = BigDecimal.valueOf(0);
        		Set<String> goodsIDsSet = new TreeSet<String>();
        		Integer goodsIDs = 0;
        		//查询每个shoesDatesSeq对应的鞋子是否和订货计划中的Category_Seq, SX2, SX3一致
        		for(int i = 0; i < orderProductsEntityList.size(); i++) {
        			OrderProductsEntity orderProductsEntity = orderProductsEntityList.get(i);
        			GoodsShoesEntity goodsShoes = meetingPlanService.getGoodsShoesByShoesDateSeq(orderProductsEntity.getShoesDataSeq());
        			
    				//移除掉波次不一致的订单商品，下次meetingPlan循环不用查询，提高效率
        			if(!goodsShoes.getPeriodSeq().equals(periodSeq)) {
        				orderProductsEntityList.remove(i);
        				i--;
        				continue;
        			}
        			
        			if(goodsShoes.getPeriodSeq().equals(periodSeq) 
        					&& goodsShoes.getCategorySeq().equals(meetingPlan.getCategorySeq())
        					&& goodsShoes.getSX2().equals(meetingPlan.getSX2())
        					&& goodsShoes.getSX3().equals(meetingPlan.getSX3())) {
        				num = num + orderProductsEntity.getBuyCount();
        				money = money.add(orderProductsEntity.getProductPrice().multiply(BigDecimal.valueOf(orderProductsEntity.getBuyCount())));
        				goodsIDsSet.add(goodsShoes.getGoodID());
        				
        				//如果商品3个类型与本条订货任务一致，计算好数量后，直接移除掉，下次meetingPlan循环不用查询（后面的meetingPlan一定不会一致），提高效率
        				orderProductsEntityList.remove(i);
        				i--;
        			}
        		}
        		goodsIDs = goodsIDsSet.size();
                
                actualNumList.add(num);
                actualMoneyList.add(money);
                actualGoodsIDsList.add(goodsIDs);
                /** 计算实际购买量 **/
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("planSeq", planSeqList);
            map.put("categoryData", categoryDataList);
            map.put("planNum", planNumList);
            map.put("planMoney", planMoneyList);
            map.put("planGoodsIDs", planGoodsIDsList);
            map.put("actualNum", actualNumList);
            map.put("actualMoney", actualMoneyList);
            map.put("actualGoodsIDs", actualGoodsIDsList);
            return R.ok(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

    }
    
    
    
    
    
	/**
     * 柱状图某一条的 实际订货（量、金额）详情
     */
    @Login
    @GetMapping("graphDetail")
    @ApiOperation("柱状图实际订货量详情")
    public R graphDetail(@ApiIgnore @LoginUser BaseUserEntity loginUser,
    		@ApiParam("订货计划序号") @RequestParam("planSeq") Integer planSeq) {
    	try {
    		//根据seq获取订货计划
    		MeetingPlanEntity meetingPlanEntity = meetingPlanDao.selectById(planSeq);
    		
    		//获取订货会的波次
    		GoodsPeriodEntity goodsPeriodEntity = goodsPeriodDao.selectById(meetingPlanEntity.getPeriodSeq());

    		//获取该用户该波次订货会期间的所有订单
    		List<OrderEntity> orderEntityList = meetingPlanService.getAllOrderByUserSeq(loginUser.getSeq(), goodsPeriodEntity);
    		List<Integer> orderSeqList = new ArrayList<Integer>();
    		for(OrderEntity orderEntity : orderEntityList) {
    			orderSeqList.add(orderEntity.getSeq());
    		}
    		//获取订单的所有商品列表
    		List<OrderProductsEntity> orderProductsEntityList = new ArrayList<OrderProductsEntity>();
    		if(orderSeqList.size() > 0) {
    			orderProductsEntityList = meetingPlanService.getAllOrderProductByOrderSeqList(orderSeqList);
    		}
    		
    		//查询每个shoesDatesSeq对应的鞋子是否和订货计划中的Category_Seq, SX2, SX3一致
    		Map<String, Integer> numMap = new HashMap<String, Integer>();
    		Map<String, BigDecimal> moneyMap = new HashMap<String, BigDecimal>();
    		for(OrderProductsEntity orderProductsEntity : orderProductsEntityList) {
    			GoodsShoesEntity goodsShoes = meetingPlanService.getGoodsShoesByShoesDateSeq(orderProductsEntity.getShoesDataSeq());
    			if(goodsShoes.getPeriodSeq().equals(goodsPeriodEntity.getSeq()) 
    					&& goodsShoes.getCategorySeq().equals(meetingPlanEntity.getCategorySeq())
    					&& goodsShoes.getSX2().equals(meetingPlanEntity.getSX2())
    					&& goodsShoes.getSX3().equals(meetingPlanEntity.getSX3())) {
    				String goodsId = goodsShoes.getGoodID();
    				Integer num = orderProductsEntity.getBuyCount();
    				if(numMap.containsKey(goodsId)) {
    					numMap.put(goodsId, numMap.get(goodsId) + num);
    				} else {
    					numMap.put(goodsId, num);
    				}
    				BigDecimal money = orderProductsEntity.getProductPrice().multiply(BigDecimal.valueOf(num));
    				if(moneyMap.containsKey(goodsId)) {
    					moneyMap.put(goodsId, money.add(moneyMap.get(goodsId)));
    				} else {
    					moneyMap.put(goodsId, money);
    				}
    			}
    		}
    		
    		Set<String> goodsIdList = numMap.keySet();
    		Collection<Integer> numCollection = numMap.values();
    		Collection<BigDecimal> moneyCollection = moneyMap.values();
    		
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("goodsId", goodsIdList);
            map.put("num", numCollection);
            map.put("money", moneyCollection);
            return R.ok(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

    }
    
    
    
	/**
     * 根据货号获取计划数量和已定数量（用于订货时提醒用户）
     */
    @Login
    @GetMapping("getplanAndActualNum")
    @ApiOperation("根据货号获取计划数量和已定数量（用于订货时提醒用户）")
    public R getplanAndActualNum(@ApiIgnore @LoginUser BaseUserEntity loginUser,
    		@ApiParam("鞋子序号") @RequestParam("shoesSeq") Integer shoesSeq) {
    	try {
    		/**计划数量**/
    		Integer planNum = 0;
    		//当前正在开订货会的波次
    		GoodsPeriodEntity goodsPeriodEntity = meetingPlanService.getMeetingPeriodNow(loginUser.getBrandSeq());
    		if(goodsPeriodEntity == null) {
    			return R.error(HttpStatus.SC_FORBIDDEN, "订货会已结束");
    		}
    		
    		//如果该用户没有上传过任何订货计划，直接不提醒 (不需要提醒"不在计划中")
            List<MeetingPlanEntity> meetingPlanList = meetingPlanService.getUserMeetingPlanList(loginUser.getSeq(), goodsPeriodEntity.getSeq());
    		if(meetingPlanList.size() == 0) {
    			return R.error(HttpStatus.SC_FORBIDDEN, "本次订货会没有上传订货计划");
    		}
            
    		//要购买的鞋子
    		GoodsShoesEntity goodsShoesEntityNow = goodsShoesDao.selectById(shoesSeq);
    		if(!goodsShoesEntityNow.getPeriodSeq().equals(goodsPeriodEntity.getSeq())) {
    			return R.error(HttpStatus.SC_FORBIDDEN, "此款鞋子不属于本次订货会波次");
    		}
    		
    		//要购买的鞋子同款的计划数量
    		MeetingPlanEntity meetingPlanEntity = meetingPlanService.getShoesMeetingPlan(goodsShoesEntityNow, goodsPeriodEntity.getSeq(), loginUser.getSeq());
    		if(meetingPlanEntity == null) {
    			//当前商品不在订货计划中
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("planNum", null);
                map.put("actualNum", null);
                return R.ok(map);
    		}
    		planNum = meetingPlanEntity.getPlanNum();
    		
    		
    		
    		/**订单已订数量**/
    		Integer actualNum = 0;
    		//获取该用户订货会期间的所有订单
    		List<OrderEntity> orderEntityList = meetingPlanService.getAllOrderByUserSeq(loginUser.getSeq(), goodsPeriodEntity);
    		List<Integer> orderSeqList = new ArrayList<Integer>();
    		for(OrderEntity orderEntity : orderEntityList) {
    			orderSeqList.add(orderEntity.getSeq());
    		}
    		//获取订单的所有商品列表
    		List<OrderProductsEntity> orderProductsEntityList = new ArrayList<OrderProductsEntity>();
    		if(orderSeqList.size() > 0) {
    			orderProductsEntityList = meetingPlanService.getAllOrderProductByOrderSeqList(orderSeqList);
    		}
    		
    		//查询订单商品每个shoesDatesSeq对应的鞋子是否和当前购买鞋子所属计划的Category_Seq, SX2, SX3一致
    		for(OrderProductsEntity orderProductsEntity : orderProductsEntityList) {
    			GoodsShoesEntity goodsShoes = meetingPlanService.getGoodsShoesByShoesDateSeq(orderProductsEntity.getShoesDataSeq());
    			if(goodsShoes.getPeriodSeq().equals(meetingPlanEntity.getPeriodSeq()) 
    					&& goodsShoes.getCategorySeq().equals(meetingPlanEntity.getCategorySeq())
    					&& goodsShoes.getSX2().equals(meetingPlanEntity.getSX2())
    					&& goodsShoes.getSX3().equals(meetingPlanEntity.getSX3())) {
    				actualNum = actualNum + orderProductsEntity.getBuyCount();
    			}
    		}
    		
    		
    		/**购物车已加入的数量**/
    		Integer shoppingCartNum = 0;
    		//查询用户的购物车列表
    		List<ShoppingCartEntity> shoppingCartList = meetingPlanService.getShoppingCartListByUserSeq(loginUser.getSeq());
    		//查询购物车商品每个shoesDatesSeq对应的鞋子是否和当前购买鞋子所属计划的Category_Seq, SX2, SX3一致
    		for(ShoppingCartEntity shoppingCartEntity : shoppingCartList) {
    			GoodsShoesEntity goodsShoes = meetingPlanService.getGoodsShoesByShoesDateSeq(shoppingCartEntity.getShoesDataSeq());
    			if(goodsShoes.getPeriodSeq().equals(meetingPlanEntity.getPeriodSeq()) 
    					&& goodsShoes.getCategorySeq().equals(meetingPlanEntity.getCategorySeq())
    					&& goodsShoes.getSX2().equals(meetingPlanEntity.getSX2())
    					&& goodsShoes.getSX3().equals(meetingPlanEntity.getSX3())) {
    				shoppingCartNum = shoppingCartNum + shoppingCartEntity.getBuyCount();
    			}
    		}
    		
    		
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("planNum", planNum);
            map.put("actualNum", actualNum);
            map.put("shoppingCartNum", shoppingCartNum);
            return R.ok(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

    }
    
    
    
}
