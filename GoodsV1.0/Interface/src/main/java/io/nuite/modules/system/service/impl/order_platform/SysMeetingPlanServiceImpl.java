package io.nuite.modules.system.service.impl.order_platform;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import io.nuite.common.utils.Constant.UserAttachType;
import io.nuite.common.utils.PageUtils;
import io.nuite.modules.order_platform_app.dao.MeetingPlanDao;
import io.nuite.modules.order_platform_app.dao.MeetingRemindDao;
import io.nuite.modules.order_platform_app.entity.MeetingPlanEntity;
import io.nuite.modules.order_platform_app.entity.MeetingRemindEntity;
import io.nuite.modules.order_platform_app.entity.OrderEntity;
import io.nuite.modules.order_platform_app.entity.OrderProductsEntity;
import io.nuite.modules.order_platform_app.service.MeetingPlanService;
import io.nuite.modules.sr_base.dao.BaseAgentDao;
import io.nuite.modules.sr_base.dao.BaseAreaDao;
import io.nuite.modules.sr_base.dao.GoodsCategoryDao;
import io.nuite.modules.sr_base.dao.GoodsPeriodDao;
import io.nuite.modules.sr_base.dao.GoodsSXDao;
import io.nuite.modules.sr_base.dao.GoodsSXOptionDao;
import io.nuite.modules.sr_base.entity.BaseAgentEntity;
import io.nuite.modules.sr_base.entity.BaseAreaEntity;
import io.nuite.modules.sr_base.entity.GoodsCategoryEntity;
import io.nuite.modules.sr_base.entity.GoodsPeriodEntity;
import io.nuite.modules.sr_base.entity.GoodsSXEntity;
import io.nuite.modules.sr_base.entity.GoodsSXOptionEntity;
import io.nuite.modules.sr_base.entity.GoodsShoesEntity;
import io.nuite.modules.system.service.order_platform.SysMeetingPlanService;

@Service
public class SysMeetingPlanServiceImpl implements SysMeetingPlanService {
    @Autowired
    private MeetingPlanDao meetingPlanDao;

    @Autowired
    private BaseAreaDao baseAreaDao;

    @Autowired
    private BaseAgentDao baseAgentDao;
    
    @Autowired
    private GoodsPeriodDao goodsPeriodDao;
    
    @Autowired
    private GoodsCategoryDao goodsCategoryDao;
    
    @Autowired
    private GoodsSXDao goodsSXDao;
    
    @Autowired
    private GoodsSXOptionDao goodsSXOptionDao;
    
    @Autowired
    private MeetingRemindDao meetingRemindDao;
    
    @Autowired
    private MeetingPlanService meetingPlanService;

    
    
    /**
     * 订货方订货计划列表
     */
	@Override
	public PageUtils getUserPlanList(Integer companySeq, Integer saleType, Integer periodSeq, Integer uploadState, Integer pageNo, Integer pageSize) {
        Page<Map<String, Object>> page = new Page<>(pageNo, pageSize);
        List<Map<String, Object>> list = meetingPlanDao.getUserMeetingPlanList(page, companySeq, saleType, periodSeq, uploadState);
        for(Map<String, Object> map : list) {
        	//是否已上传订货计划
        	if(map.get("userSeq") == null) {
        		map.put("isUpload", 0);
        	} else {
        		map.put("isUpload", 1);
        	}

            if ((int)map.get("attachType") == 1) {
            	map.put("attachTypeName", UserAttachType.FACTORY.getName());
            } else if ((int)map.get("attachType") == 2) {             
            	map.put("attachTypeName", UserAttachType.OFFICE.getName());
            	//分公司名
                BaseAreaEntity baseAreaEntity = baseAreaDao.selectById((long)map.get("attachSeq"));
                map.put("attachCompanyName", baseAreaEntity.getName());
            } else if ((int)map.get("attachType") == 3) {
            	map.put("attachTypeName", UserAttachType.AGENT.getName());
                //代理商名
                BaseAgentEntity baseAgentEntity = baseAgentDao.selectById((long)map.get("attachSeq"));
                map.put("attachCompanyName", baseAgentEntity.getAgentName());
            }
        }
        return new PageUtils(list, page.getTotal(), pageSize, pageNo);
	}



	/**
	 * 所有波次列表
	 */
	@Override
	public List<GoodsPeriodEntity> getPeriodListByBrandSeq(Integer brandSeq) {
		Wrapper<GoodsPeriodEntity> wrapper = new EntityWrapper<GoodsPeriodEntity>();
		wrapper.where("Brand_Seq = {0}", brandSeq).orderBy("Seq DESC");
		return goodsPeriodDao.selectList(wrapper);
	}

	
    /**
     * 订货计划详细列表
     */
	@Override
	public PageUtils getUserPlanDetailsList(Integer companySeq, Integer userSeq, Integer periodSeq, Integer pageNo, Integer pageSize) {
        
		//查询用户某一波次的全部订货计划列表
		Page<Map<String, Object>> page = new Page<>(pageNo, pageSize);
		Wrapper<MeetingPlanEntity> meetingPlanWrapper = new EntityWrapper<MeetingPlanEntity>();
		meetingPlanWrapper.where("User_Seq = {0} AND Period_Seq = {1}", userSeq, periodSeq);
        List<Map<String, Object>> meetingPlanList = meetingPlanDao.selectMapsPage(page, meetingPlanWrapper);
		
        
    	GoodsSXEntity goodsSX2Entity = new GoodsSXEntity();
    	goodsSX2Entity.setCompanySeq(companySeq);
    	goodsSX2Entity.setSXId("SX2");
    	goodsSX2Entity = goodsSXDao.selectOne(goodsSX2Entity);
    	
    	GoodsSXEntity goodsSX3Entity = new GoodsSXEntity();
    	goodsSX3Entity.setCompanySeq(companySeq);
    	goodsSX3Entity.setSXId("SX3");
    	goodsSX3Entity = goodsSXDao.selectOne(goodsSX3Entity);
    	
    	/** 订货会期间实际购买的订单所有商品 **/
//		//获取该用户 该波次订货会期间的所有订单
//   		GoodsPeriodEntity goodsPeriodEntity = goodsPeriodDao.selectById(periodSeq);
//		List<OrderEntity> orderEntityList = meetingPlanService.getAllOrderByUserSeq(userSeq, goodsPeriodEntity);
//		List<Integer> orderSeqList = new ArrayList<Integer>();
//		for(OrderEntity orderEntity : orderEntityList) {
//			orderSeqList.add(orderEntity.getSeq());
//		}
//
//		//获取订单的所有商品列表
//		List<OrderProductsEntity> orderProductsEntityList = new ArrayList<OrderProductsEntity>();
//		if(orderSeqList.size() > 0) {
//			orderProductsEntityList = meetingPlanService.getAllOrderProductByOrderSeqList(orderSeqList);
//		}
		/** 订货会期间实际购买的订单所有商品 **/
    	
        for(Map<String, Object> map : meetingPlanList) {
        	//分类名称
        	GoodsCategoryEntity goodsCategoryEntity = goodsCategoryDao.selectById((int)map.get("categorySeq"));
        	map.put("categoryName", goodsCategoryEntity.getName());
        	
        	//SX2Value名称
        	GoodsSXOptionEntity goodsSX2OptionEntity = new GoodsSXOptionEntity();
        	goodsSX2OptionEntity.setSXSeq(goodsSX2Entity.getSeq());
        	goodsSX2OptionEntity.setCode((String)map.get("SX2"));
        	goodsSX2OptionEntity = goodsSXOptionDao.selectOne(goodsSX2OptionEntity);
        	map.put("SX2Value", goodsSX2OptionEntity.getValue());
        	
        	//SX3Value名称
        	GoodsSXOptionEntity goodsSX3OptionEntity = new GoodsSXOptionEntity();
        	goodsSX3OptionEntity.setSXSeq(goodsSX3Entity.getSeq());
        	goodsSX3OptionEntity.setCode((String)map.get("SX3"));
        	goodsSX3OptionEntity = goodsSXOptionDao.selectOne(goodsSX3OptionEntity);
        	map.put("SX3Value", goodsSX3OptionEntity.getValue());
        	
        	
            /** 计算实际购买量  **/
//    		Integer num = 0;
//    		BigDecimal money = BigDecimal.valueOf(0);
//    		Set<String> goodsIDsSet = new TreeSet<String>();
//    		Integer goodsIDs = 0;
//    		//查询每个shoesDatesSeq对应的鞋子是否和订货计划中的Category_Seq, SX2, SX3一致
//    		for(int i = 0; i < orderProductsEntityList.size(); i++) {
//    			OrderProductsEntity orderProductsEntity = orderProductsEntityList.get(i);
//    			GoodsShoesEntity goodsShoes = meetingPlanService.getGoodsShoesByShoesDateSeq(orderProductsEntity.getShoesDataSeq());
//    			
//				//移除掉波次不一致的订单商品，下次meetingPlan循环不用查询，提高效率
//    			if(!goodsShoes.getPeriodSeq().equals(periodSeq)) {
//    				orderProductsEntityList.remove(i);
//    				i--;
//    				continue;
//    			}
//    			
//    			if(goodsShoes.getPeriodSeq().equals(periodSeq) 
//    					&& goodsShoes.getCategorySeq().equals((int)map.get("categorySeq"))
//    					&& goodsShoes.getSX2().equals((String)map.get("SX2"))
//    					&& goodsShoes.getSX3().equals((String)map.get("SX3"))) {
//    				num = num + orderProductsEntity.getBuyCount();
//    				money = money.add(orderProductsEntity.getProductPrice().multiply(BigDecimal.valueOf(orderProductsEntity.getBuyCount())));
//    				goodsIDsSet.add(goodsShoes.getGoodID());
//    				
//    				//如果商品3个类型与本条订货任务一致，计算好数量后，直接移除掉，下次meetingPlan循环不用查询（后面的meetingPlan一定不会一致），提高效率
//    				orderProductsEntityList.remove(i);
//    				i--;
//    			}
//    		}
//    		goodsIDs = goodsIDsSet.size();
//            
//    		map.put("actualNum", num);
//    		map.put("actualMoney", money);
//    		map.put("actualGoodsIDs", goodsIDs);
            /** 计算实际购买量  **/
            
        }
        return new PageUtils(meetingPlanList, page.getTotal(), pageSize, pageNo);
	}
	
	

	/**
	 * 删除用户的订货计划
	 */
	@Override
	public void deleteMeetingPlan(Integer periodSeq, Integer userSeq) {
		Wrapper<MeetingPlanEntity> wrapper = new EntityWrapper<MeetingPlanEntity>();
		wrapper.where("Period_Seq = {0} AND User_Seq = {1}", periodSeq, userSeq);
		meetingPlanDao.delete(wrapper);
	}



	/**
	 * 批量插入订货计划
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void addBatchMeetingPlan(Integer[] userSeqArr, Integer periodSeq, List<MeetingPlanEntity> meetingPlanList) {
		for(Integer userSeq : userSeqArr) {
			//删除原来的计划
			Wrapper<MeetingPlanEntity> wrapper = new EntityWrapper<MeetingPlanEntity>();
			wrapper.where("Period_Seq = {0} AND User_Seq = {1}", periodSeq, userSeq);
			meetingPlanDao.delete(wrapper);
		
			//新增新的计划
			for(MeetingPlanEntity meetingPlanEntity : meetingPlanList) {
				meetingPlanEntity.setUserSeq(userSeq);
				meetingPlanDao.insert(meetingPlanEntity);
			}
			
			//注：当用户第一次有订货计划上传时，如果此时用户还没有在手机上设置过扫码、下单的提醒，订货计划计划提醒表因为无数据，返回设置不提醒，这里插入一条数据给他默认设置成提醒
			//订货计划提醒表，只在用户在手机上设置过扫码、下单的提醒后才插入/更新数据
			MeetingRemindEntity meetingRemindEntity = new MeetingRemindEntity();
			meetingRemindEntity.setUserSeq(userSeq);
			if(meetingRemindDao.selectOne(meetingRemindEntity) == null) {
				meetingRemindDao.insert(meetingRemindEntity);
			}
			
		}
	}




}
