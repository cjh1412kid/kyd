package io.nuite.modules.order_platform_app.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import io.nuite.common.utils.PageUtils;
import io.nuite.modules.order_platform_app.entity.MeetingGoodsEntity;
import io.nuite.modules.sr_base.entity.BaseUserEntity;

import javax.servlet.http.HttpServletRequest;


public interface MeetingGoodsService extends IService<MeetingGoodsEntity> {

	Integer addMeetingGoods(MeetingGoodsEntity meetingGoodsEntity);

	List<MeetingGoodsEntity> getMeetingGoodsList(Integer meetingSeq, Integer start, Integer num, String searchParam);

	List<Map<String, Object>> getAllColorList(Integer companySeq);

	List<Map<String, Object>> getOftenColorList(Integer meetingSeq);

	Integer getMeetingGoodsNum(Integer meetingSeq);

	void deleteMeetingGoods(List<Integer> meetingGoodsSeqs);

	List<Object> getAllMeetingGoodsSeqsByMeetingSeq(Integer meetingSeq);

	List<MeetingGoodsEntity> getAllMeetingGoodsByMeetingSeq(Integer meetingSeq);
	
	MeetingGoodsEntity getMeetingGoodsByGoodId(String goodid,Integer meetingSeq);
	
	 /**
     * 货品的列表
     */
    PageUtils getMeetingGoodsList(Long UserSeq, Map<String, Object> params);

	/**
	 * 获取货品列表（不分页）
	 * @param UserSeq
	 * @param params
	 * @return
	 */
	List<MeetingGoodsEntity> selectMeetingGoodsList(Long UserSeq, Map<String, Object> params);

    Map<String, Object> edit(Integer seq);

	boolean isInMeetingShoppingCart(Integer meetingGoodsSeq);

	boolean isInMeetingOrder(Integer meetingGoodsSeq);

	void updateCartMeetingGoodsId(Integer meetingGoodsSeq, String goodId);
	
	Integer getMinSizeByMeetingSeq(Integer meetingSeq);
	
	Integer getMaxSizeByMeetingSeq(Integer meetingSeq);
	
	List<Map<String, Object>> getParentCategory(Integer companySeq,Integer meetingSeq,Integer categorySeq,Integer showType);

	List<Map<String, Object>> getCategoryList(Integer companySeq,Integer meetingSeq,Integer categorySeq);

	Boolean hasGoodsInOrders(Integer meetingGoods);

	/**
	 * 新增订货会货品
	 * @param userSeq
	 * @param meetingGoodsEntity
	 * @param uploadUrl
	 * @return
	 * @throws Exception
	 */
	boolean insertMeetingGoods(Integer userSeq,MeetingGoodsEntity meetingGoodsEntity,String uploadUrl) throws Exception;

	/**
	 * 修改订货会货品
	 * @param userSeq
	 * @param meetingGoodsEntity
	 * @param uploadUrl
	 * @return
	 * @throws Exception
	 */
	boolean updateMeetingGoods(Integer userSeq,MeetingGoodsEntity meetingGoodsEntity,String uploadUrl) throws Exception;

	/**
	 * 已订过的订货会货品（解密用）
	 * @param meetingSeq
	 * @param year
	 * @param type
	 * @param status
	 * @param userEntity
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<MeetingGoodsEntity> orderOfMeetingGoods(Integer meetingSeq,String year,Integer type,Integer status,BaseUserEntity userEntity,Page<MeetingGoodsEntity> page) throws Exception;

	/**
	 * 当前用户已订过的订货会货品（解密用）
	 * @param meetingSeq
	 * @param year
	 * @param type
	 * @param status
	 * @param userEntity
	 * @return
	 * @throws Exception
	 */
	List<MeetingGoodsEntity> orderGoodsNum(Integer meetingSeq,String year,Integer type,Integer status,BaseUserEntity userEntity) throws Exception;

	/**
	 * 解密订货会货品
	 * @param meetingGoodsSeq
	 * @param baseUserEntity
	 * @return
	 * @throws Exception
	 */
	boolean decodeMeetingGoods(Integer meetingGoodsSeq,BaseUserEntity baseUserEntity) throws Exception;

	/**
	 * 订单下货品
	 * @param orderSeq
	 * @return
	 * @throws Exception
	 */
	List<Map<String,Object>> getOrderGoods(Integer orderSeq) throws Exception;
}

