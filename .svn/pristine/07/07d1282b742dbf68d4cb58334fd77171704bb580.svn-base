package io.nuite.modules.order_platform_app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import io.nuite.modules.order_platform_app.dao.MeetingOrderCartDao;
import io.nuite.modules.order_platform_app.dao.MeetingOrderCartDetailDao;
import io.nuite.modules.order_platform_app.dao.MeetingOrderCartDistributeBoxDao;
import io.nuite.modules.order_platform_app.dao.MeetingShoppingCartDao;
import io.nuite.modules.order_platform_app.dao.MeetingShoppingCartDetailDao;
import io.nuite.modules.order_platform_app.dao.MeetingShoppingCartDistributeBoxDao;
import io.nuite.modules.order_platform_app.entity.MeetingOrderCartDetailEntity;
import io.nuite.modules.order_platform_app.entity.MeetingOrderCartDistributeBoxEntity;
import io.nuite.modules.order_platform_app.entity.MeetingOrderCartEntity;
import io.nuite.modules.order_platform_app.entity.MeetingShoppingCartDetailEntity;
import io.nuite.modules.order_platform_app.entity.MeetingShoppingCartDistributeBoxEntity;
import io.nuite.modules.order_platform_app.entity.MeetingShoppingCartEntity;
import io.nuite.modules.order_platform_app.service.MeetingOrderExcelService;
import io.nuite.modules.sr_base.dao.BaseUserDao;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.system.dao.MeetingOrderDao;
import io.nuite.modules.system.dao.MeetingOrderProductDao;
import io.nuite.modules.system.entity.MeetingOrderEntity;
import io.nuite.modules.system.entity.MeetingOrderProductEntity;

@Service
public class MeetingOrderExcelServiceImpl implements MeetingOrderExcelService {

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
    
    @Autowired
    private MeetingOrderCartDao meetingOrderCartDao;
    
    @Autowired
    private MeetingOrderCartDetailDao meetingOrderCartDetailDao;
    
    @Autowired
    private MeetingOrderCartDistributeBoxDao meetingOrderCartDistributeBoxDao;
    
    
    
    
    /**
     * 根据购物车序号获取购物车详情列表
     */
	@Override
	public List<MeetingShoppingCartDetailEntity> getMeetingShoppingCartDetailListByShoppingCartSeq(Integer meetingShoppingCartSeq) {
		return meetingShoppingCartDetailDao.selectGroupedMeetingShoppingCartDetail(meetingShoppingCartSeq);
	}
	
	
	
    /**
     * 提交订单
     */
	@Override
    @Transactional(propagation = Propagation.REQUIRED)
	public Integer submitMeetingOrder(List<Integer> meetingShoppingCartSeqs, MeetingOrderEntity meetingOrderEntity,
			List<MeetingOrderProductEntity> orderProductList, Date nowDate) {
		
        //1.新增订单
        meetingOrderDao.insert(meetingOrderEntity);
        
        //2.新增订单关联产品
        for (MeetingOrderProductEntity orderProductEntity : orderProductList) {
        	orderProductEntity.setMeetingOrderSeq(meetingOrderEntity.getSeq());
            meetingOrderProductDao.insert(orderProductEntity);
        }
        
		//3.复制购物车到订单购物车
		copyMeetingShoppingCartToMeetingOrderCart(meetingOrderEntity.getSeq(), meetingShoppingCartSeqs, nowDate);
		
        //4.删除购物车
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
        
        return meetingOrderEntity.getSeq();
	}



	//复制购物车3张表的数据到订单购物车3张表
	private void copyMeetingShoppingCartToMeetingOrderCart(Integer meetingOrderSeq, List<Integer> meetingShoppingCartSeqs, Date nowDate) {
		for(Integer meetingShoppingCartSeq : meetingShoppingCartSeqs) {
			
			//1.复制购物车表
			MeetingShoppingCartEntity meetingShoppingCartEntity = meetingShoppingCartDao.selectById(meetingShoppingCartSeq);
			MeetingOrderCartEntity meetingOrderCartEntity = new MeetingOrderCartEntity();
//			Seq					int	0	0	0	-1	0	0		0	0	0	0	序号(主键)		-1			
//			Meeting_Seq			int	0	0	0	0	0	0		0	0	0	0	订货会序号		0			
//			User_Seq			int	0	0	0	0	0	0		0	0	0	0	用户Seq(外键:YHSR_Base_User表)		0			
//			MeetingGoods_Seq	int	0	0	0	0	0	0		0	0	0	0	订货会鞋子序号		0			
//			TotalSelectNum		int	0	0	-1	0	0	0		0	0	0	0	总选款数量		0			
//			PerBoxNum			int	0	0	-1	0	0	0		0	0	0	0	每箱数量（配件）		0			
//			IsAllocated			int	0	0	0	0	0	0	((0))	0	0	0	0	是否已配码0：否 1：是		0			
//			IsChecked			int	0	0	0	0	0	0	((1))	0	0	0	0	是否勾选，0不勾选 1勾选		0			
//			InputTime			datetime	0	0	-1	0	0	0		0	0	0	0	插入时间		0			
//			Del					int	0	0	0	0	0	0	((0))	0	0	0	0	删除标识(0:未删除,1:已删除)		0			
//			MeetingGoodsID		varchar	50	0	0	0	0	0		0	0	0	0	货号	Chinese_PRC_CI_AS	0			
//			UserGoodID			varchar	50	0	-1	0	0	0		0	0	0	0	用户贴牌货号	Chinese_PRC_CI_AS	0			
//			MeetingOrderSeq		int	0	0	-1	0	0	0		0	0	0	0	订单Seq		0			
			meetingOrderCartEntity.setMeetingSeq(meetingShoppingCartEntity.getMeetingSeq());
			meetingOrderCartEntity.setUserSeq(meetingShoppingCartEntity.getUserSeq());
			meetingOrderCartEntity.setMeetingGoodsSeq(meetingShoppingCartEntity.getMeetingGoodsSeq());
			meetingOrderCartEntity.setTotalSelectNum(meetingShoppingCartEntity.getTotalSelectNum());
			meetingOrderCartEntity.setPerBoxNum(meetingShoppingCartEntity.getPerBoxNum());
			meetingOrderCartEntity.setIsAllocated(meetingShoppingCartEntity.getIsAllocated());
			meetingOrderCartEntity.setIsChecked(meetingShoppingCartEntity.getIsChecked());
			meetingOrderCartEntity.setInputTime(nowDate);
			meetingOrderCartEntity.setDel(0);
			meetingOrderCartEntity.setMeetingGoodsID(meetingShoppingCartEntity.getMeetingGoodsID());
			meetingOrderCartEntity.setUserGoodID(meetingShoppingCartEntity.getUserGoodID());
			meetingOrderCartEntity.setMeetingOrderSeq(meetingOrderSeq);
			meetingOrderCartDao.insert(meetingOrderCartEntity);
			
			
			//2.复制购物车配箱表
			Wrapper<MeetingShoppingCartDistributeBoxEntity> wrapper = new EntityWrapper<MeetingShoppingCartDistributeBoxEntity>();
			wrapper.where("MeetingShoppingCart_Seq = {0}", meetingShoppingCartSeq);
			List<MeetingShoppingCartDistributeBoxEntity> meetingShoppingCartDistributeBoxList = meetingShoppingCartDistributeBoxDao.selectList(wrapper);
			for(MeetingShoppingCartDistributeBoxEntity meetingShoppingCartDistributeBoxEntity : meetingShoppingCartDistributeBoxList) {
				MeetingOrderCartDistributeBoxEntity meetingOrderCartDistributeBoxEntity = new MeetingOrderCartDistributeBoxEntity();
//				Seq						int	0	0	0	-1	0	0		0	0	0	0	序号(主键)		-1			
//				MeetingOrderCart_Seq	int	0	0	0	0	0	0		0	0	0	0	购物车序号		0			
//				MeetingGoods_Seq		int	0	0	0	0	0	0		0	0	0	0	订货会鞋子序号(冗余字段)		0			
//				Color_Seq				int	0	0	0	0	0	0		0	0	0	0	颜色seq		0			
//				PerBoxNum				int	0	0	0	0	0	0		0	0	0	0	每箱数量（配件） （冗余字段）		0			
//				BoxCount				int	0	0	-1	0	0	0		0	0	0	0	箱数（件数）		0			
//				ColorTotalNum			int	0	0	0	0	0	0		0	0	0	0	本颜色总数量		0			
//				InputTime				datetime	0	0	-1	0	0	0		0	0	0	0	插入时间		0			
//				AllocatedType			int	0	0	0	0	0	0		0	0	0	0	配码类型 1：代码 2：具体数值		0			
				meetingOrderCartDistributeBoxEntity.setMeetingOrderCartSeq(meetingOrderCartEntity.getSeq());
				meetingOrderCartDistributeBoxEntity.setMeetingGoodsSeq(meetingShoppingCartDistributeBoxEntity.getMeetingGoodsSeq());
				meetingOrderCartDistributeBoxEntity.setColorSeq(meetingShoppingCartDistributeBoxEntity.getColorSeq());
				meetingOrderCartDistributeBoxEntity.setPerBoxNum(meetingShoppingCartDistributeBoxEntity.getPerBoxNum());
				meetingOrderCartDistributeBoxEntity.setBoxCount(meetingShoppingCartDistributeBoxEntity.getBoxCount());
				meetingOrderCartDistributeBoxEntity.setColorTotalNum(meetingShoppingCartDistributeBoxEntity.getColorTotalNum());
				meetingOrderCartDistributeBoxEntity.setInputTime(nowDate);
				meetingOrderCartDistributeBoxEntity.setAllocatedType(meetingShoppingCartDistributeBoxEntity.getAllocatedType());
				meetingOrderCartDistributeBoxDao.insert(meetingOrderCartDistributeBoxEntity);
				
				
				//3.复制购物车配码详情表
				Wrapper<MeetingShoppingCartDetailEntity> wrapper2 = new EntityWrapper<MeetingShoppingCartDetailEntity>();
				wrapper2.where("MeetingShoppingCartDistributeBox_Seq = {0}", meetingShoppingCartDistributeBoxEntity.getSeq());
				List<MeetingShoppingCartDetailEntity> meetingShoppingCartDetailList = meetingShoppingCartDetailDao.selectList(wrapper2);
				for(MeetingShoppingCartDetailEntity meetingShoppingCartDetailEntity : meetingShoppingCartDetailList) {
					MeetingOrderCartDetailEntity meetingOrderCartDetailEntity = new MeetingOrderCartDetailEntity();
//					Seq						int	0	0	0	-1	0	0		0	0	0	0	序号(主键)		-1			
//					MeetingOrderCart_Seq	int	0	0	0	0	0	0		0	0	0	0	购物车序号		0			
//					MeetingGoods_Seq		int	0	0	0	0	0	0		0	0	0	0	订货会鞋子序号(冗余字段)		0			
//					Color_Seq				int	0	0	0	0	0	0		0	0	0	0	颜色seq		0			
//					Size					int	0	0	0	0	0	0		0	0	0	0	尺码		0			
//					SelectNum				int	0	0	-1	0	0	0		0	0	0	0	选款数量		0			
//					InputTime				datetime	0	0	-1	0	0	0		0	0	0	0	插入时间		0			
//					MeetingOrderCartDistributeBox_Seq	int	0	0	0	0	0	0		0	0	0	0	购物车配箱序号		0			
					meetingOrderCartDetailEntity.setMeetingOrderCartSeq(meetingOrderCartEntity.getSeq());
					meetingOrderCartDetailEntity.setMeetingGoodsSeq(meetingShoppingCartDetailEntity.getMeetingGoodsSeq());
					meetingOrderCartDetailEntity.setColorSeq(meetingShoppingCartDetailEntity.getColorSeq());
					meetingOrderCartDetailEntity.setSize(meetingShoppingCartDetailEntity.getSize());
					meetingOrderCartDetailEntity.setSelectNum(meetingShoppingCartDetailEntity.getSelectNum());
					meetingOrderCartDetailEntity.setInputTime(nowDate);
					meetingOrderCartDetailEntity.setMeetingOrderCartDistributeBoxSeq(meetingOrderCartDistributeBoxEntity.getSeq());
					meetingOrderCartDetailDao.insert(meetingOrderCartDetailEntity);
				}
			}
		}
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
	
	

}
