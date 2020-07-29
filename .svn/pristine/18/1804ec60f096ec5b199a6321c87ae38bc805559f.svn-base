package io.nuite.modules.order_platform_app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import io.nuite.modules.order_platform_app.dao.MeetingShoppingCartDao;
import io.nuite.modules.order_platform_app.dao.MeetingShoppingCartDetailDao;
import io.nuite.modules.order_platform_app.dao.MeetingShoppingCartDistributeBoxDao;
import io.nuite.modules.order_platform_app.entity.MeetingShoppingCartDetailEntity;
import io.nuite.modules.order_platform_app.entity.MeetingShoppingCartDistributeBoxEntity;
import io.nuite.modules.order_platform_app.service.MeetingOrderService;
import io.nuite.modules.sr_base.dao.BaseUserDao;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.system.dao.MeetingOrderDao;
import io.nuite.modules.system.dao.MeetingOrderProductDao;
import io.nuite.modules.system.entity.MeetingOrderEntity;
import io.nuite.modules.system.entity.MeetingOrderProductEntity;

@Service
public class MeetingOrderServiceImpl extends ServiceImpl<MeetingOrderDao,MeetingOrderEntity> implements MeetingOrderService {

    @Autowired
    private MeetingOrderDao meetingOrderDao;

    @Autowired
    private MeetingOrderProductDao meetingOrderProductDao;

    @Autowired
    private MeetingShoppingCartDao meetingShoppingCartDao;
    
    @Autowired
    private MeetingShoppingCartDetailDao meetingShoppingCartDetailDao;
    
    @Autowired
    private MeetingShoppingCartDistributeBoxDao meetingShoppingCartDistributeBoxDao;
    
    @Autowired
    private BaseUserDao baseUserDao;
    
    
    
    /**
     * 根据购物车序号获取购物车详情列表
     */
	@Override
	public List<MeetingShoppingCartDetailEntity> getMeetingShoppingCartDetailListByShoppingCartSeq(Integer meetingShoppingCartSeq) {
		Wrapper<MeetingShoppingCartDetailEntity> wrapper = new EntityWrapper<MeetingShoppingCartDetailEntity>();
		wrapper.where("MeetingShoppingCart_Seq = {0}", meetingShoppingCartSeq);
		return meetingShoppingCartDetailDao.selectList(wrapper);
	}
	
	
	
    /**
     * 提交订单
     */
	@Override
    @Transactional(propagation = Propagation.REQUIRED)
	public Integer submitMeetingOrder(List<Integer> meetingShoppingCartSeqs, MeetingOrderEntity meetingOrderEntity,
			List<MeetingOrderProductEntity> orderProductList) {
        //1.删除购物车
        if (meetingShoppingCartSeqs.size() > 0) {
    		//1).删除购物车
    		meetingShoppingCartDao.deleteBatchIds(meetingShoppingCartSeqs);
    		//2).删除对应购物车详情
    		Wrapper<MeetingShoppingCartDetailEntity> wrapper1 = new EntityWrapper<MeetingShoppingCartDetailEntity>();
    		wrapper1.in("MeetingShoppingCart_Seq", meetingShoppingCartSeqs);
    		meetingShoppingCartDetailDao.delete(wrapper1);
    		//3).删除购物车配箱详情
    		Wrapper<MeetingShoppingCartDistributeBoxEntity> wrapper2 = new EntityWrapper<MeetingShoppingCartDistributeBoxEntity>();
    		wrapper2.in("MeetingShoppingCart_Seq", meetingShoppingCartSeqs);
    		meetingShoppingCartDistributeBoxDao.delete(wrapper2);
        }
        
        //2.新增订单
        meetingOrderDao.insert(meetingOrderEntity);
        
        //3.新增订单关联产品
        for (MeetingOrderProductEntity orderProductEntity : orderProductList) {
        	orderProductEntity.setMeetingOrderSeq(meetingOrderEntity.getSeq());
            meetingOrderProductDao.insert(orderProductEntity);
        }
        
        return meetingOrderEntity.getSeq();
	}



	
	/**
	 * 公司订货会订单列表
	 */
	@Override
	public List<MeetingOrderEntity> getCompanyMeetingOrderList(Integer meetingSeq, Integer start, Integer num) {
		Wrapper<MeetingOrderEntity> wrapper = new EntityWrapper<MeetingOrderEntity>();
		wrapper.where("Meeting_Seq = {0}", meetingSeq).orderBy("Seq DESC");
		return meetingOrderDao.selectPage(new RowBounds(start - 1, num), wrapper);
	}



	/**
	 * 用户订货会订单列表
	 */
	@Override
	public List<MeetingOrderEntity> getUserMeetingOrderList(Integer userSeq, Integer meetingSeq, Integer start, Integer num) {
		Wrapper<MeetingOrderEntity> wrapper = new EntityWrapper<MeetingOrderEntity>();
		wrapper.where("Meeting_Seq = {0} AND User_Seq = {1}", meetingSeq, userSeq).orderBy("Seq DESC");
		return meetingOrderDao.selectPage(new RowBounds(start - 1, num), wrapper);
	}



	/**
	 * 根据订单序号获取订单详细货品
	 */
	@Override
	public List<MeetingOrderProductEntity> getMeetingOrderProductListByMeetingOrderSeq(Integer meetingOrderSeq) {
		Wrapper<MeetingOrderProductEntity> wrapper = new EntityWrapper<MeetingOrderProductEntity>();
		wrapper.where("MeetingOrder_Seq = {0}", meetingOrderSeq);
		return meetingOrderProductDao.selectList(wrapper);
	}



	/**
	 * 根据seq获取订单Map
	 */
	@Override
	public Map<String, Object> getMeetingOrderMapBySeq(Integer meetingOrderSeq) {
        Wrapper<MeetingOrderEntity> wrapper = new EntityWrapper<MeetingOrderEntity>();
        wrapper.setSqlSelect("Seq AS orderSeq, Company_Seq AS companySeq, Meeting_Seq AS meetingSeq, User_Seq AS userSeq, OrderNum AS orderNum,"
                + "ReceiverName AS receiverName, Telephone AS telephone, Address AS address, InputTime AS inputTime,ExpressName AS expressName,ExpressPhone AS expressPhone");
        wrapper.where("Seq = {0}", meetingOrderSeq);
        return meetingOrderDao.selectMaps(wrapper).get(0);
    
	}



	/**
	 * 查询订单中所有商品的seq
	 */
	@Override
	public List<Object> getMeetingOrderGoodsSeqList(Integer meetingOrderSeq) {
		Wrapper<MeetingOrderProductEntity> wrapper = new EntityWrapper<MeetingOrderProductEntity>();
		wrapper.setSqlSelect("DISTINCT MeetingGoods_Seq").where("MeetingOrder_Seq = {0}", meetingOrderSeq);
		return meetingOrderProductDao.selectObjs(wrapper);
	}



	/**
	 * 查询订单中某一商品的所有颜色、取消状态
	 */
	@Override
	public List<Map<String, Object>> getMeetingOrderGoodsColorDetailList(Integer meetingOrderSeq, Integer meetingGoodsSeq) {
		Wrapper<MeetingOrderProductEntity> wrapper = new EntityWrapper<MeetingOrderProductEntity>();
		wrapper.setSqlSelect("DISTINCT Color_Seq AS colorSeq, Cancel AS isCancelled").where("MeetingOrder_Seq = {0} AND MeetingGoods_Seq = {1}", meetingOrderSeq, meetingGoodsSeq);
		return meetingOrderProductDao.selectMaps(wrapper);
	}



	/**
	 * 查询订单中某一商品的某一颜色的尺码、购买量
	 */
	@Override
	public List<Map<String, Object>> getMeetingOrderGoodsColorSizeNumDetailList(Integer meetingOrderSeq, Integer meetingGoodsSeq,
			Integer colorSeq) {
		Wrapper<MeetingOrderProductEntity> wrapper = new EntityWrapper<MeetingOrderProductEntity>();
		wrapper.setSqlSelect("Size AS size, BuyCount AS buyCount")
		.where("MeetingOrder_Seq = {0} AND MeetingGoods_Seq = {1} AND Color_Seq = {2}", meetingOrderSeq, meetingGoodsSeq, colorSeq);
		return meetingOrderProductDao.selectMaps(wrapper);
	}



	/**
	 * 查询用户信息Map
	 */
	@Override
	public Map<String, Object> getUserMapByUserSeq(Integer userSeq) {
        Wrapper<BaseUserEntity> wrapper = new EntityWrapper<BaseUserEntity>();
        wrapper.setSqlSelect("Seq AS userSeq, UserName AS userName, Telephone AS telephone");
        wrapper.where("Seq = {0}", userSeq);
        return baseUserDao.selectMaps(wrapper).get(0);
	}



	@Override
	public List<MeetingOrderProductEntity> getListByMeetingGoodsSeq(Integer meetingGoodsSeq) {
		Wrapper<MeetingOrderProductEntity> wrapper = new EntityWrapper<MeetingOrderProductEntity>();
		wrapper.where("MeetingGoods_Seq={0}", meetingGoodsSeq);
		return meetingOrderProductDao.selectList(wrapper);
	}



	@Override
	public List<BaseUserEntity> getAllUserInMeeting(Integer meetingSeq) {
		Wrapper<MeetingOrderEntity> wrapper=new EntityWrapper<MeetingOrderEntity>();
		wrapper.setSqlSelect("distinct User_Seq as userSeq");
		wrapper.where("Meeting_Seq ={0}", meetingSeq);
		List<Map<String, Object>> results=meetingOrderDao.selectMaps(wrapper);
		List<BaseUserEntity> users=new ArrayList<BaseUserEntity>();
		for (Map<String, Object> map : results) {
			Integer userSeq=(Integer) map.get("userSeq");
			BaseUserEntity baseUserEntity=baseUserDao.selectById(userSeq);
			users.add(baseUserEntity);
		}
		return users;
	}
	
	

}
