package io.nuite.modules.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.nuite.modules.order_platform_app.dao.MeetingGoodsDao;
import io.nuite.modules.order_platform_app.entity.MeetingGoodsEntity;
import io.nuite.modules.order_platform_app.utils.JPushUtils;
import io.nuite.modules.sr_base.dao.GoodsColorDao;
import io.nuite.modules.sr_base.entity.GoodsColorEntity;
import io.nuite.modules.system.dao.MeetingOrderDao;
import io.nuite.modules.system.dao.MeetingOrderProductDao;
import io.nuite.modules.system.entity.MeetingOrderEntity;
import io.nuite.modules.system.entity.MeetingOrderProductEntity;
import io.nuite.modules.system.service.SysMeetingOrderProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SysMeetingOrderProductServiceImpl extends ServiceImpl<MeetingOrderProductDao, MeetingOrderProductEntity> implements SysMeetingOrderProductService {
    
    @Autowired
    JPushUtils jPushUtils;
    
    @Autowired
    MeetingOrderProductDao meetingOrderProductDao;
    
    @Autowired
    MeetingOrderDao meetingOrderDao;
    
    @Autowired
    MeetingGoodsDao meetingGoodsDao;
    
    @Autowired
    GoodsColorDao goodsColorDao;
    
    @Override
    public void modifyCancel(Integer meetingOrderSeq, Integer goodSeq, Integer colorSeq, Integer isCancel) {
        MeetingOrderProductEntity meetingOrderProductEntity = new MeetingOrderProductEntity();
        meetingOrderProductEntity.setCancel(isCancel);
        super.update(meetingOrderProductEntity, new EntityWrapper<MeetingOrderProductEntity>()
            .eq("MeetingOrder_Seq", meetingOrderSeq)
            .eq("MeetingGoods_Seq", goodSeq)
            .eq("Color_Seq", colorSeq)
        );
        
        try {
            List<String> accountNameList = meetingOrderProductDao.getAccountNameByMeetingOrderSeq(meetingOrderSeq);
            
            //订单
            MeetingOrderEntity meetingOrderEntity = meetingOrderDao.selectById(meetingOrderSeq);
            //货品
            MeetingGoodsEntity meetingGoodsEntity = meetingGoodsDao.selectById(goodSeq);
            //颜色
            GoodsColorEntity goodsColorEntity = goodsColorDao.selectById(colorSeq);
            
            //发送手机消息
            StringBuilder sb = new StringBuilder();
            sb.append("订货会 订单号：")
                .append(meetingOrderEntity.getOrderNum())
                .append(" 货号：")
                .append(meetingGoodsEntity.getGoodID())
                .append(" 颜色：")
                .append(goodsColorEntity.getName());
            
            if (isCancel == 0) {
                sb.append(" 全部恢复");
            } else {
                sb.append(" 全部取消");
            }
            
            jPushUtils.sendPush(accountNameList, sb.toString(), sb.toString(), "");
        } catch (Exception e) {
           // e.printStackTrace();
            log.error("修改订货会订单状态，发送手机推送消息失败" + e.getMessage(), e);
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyGoodColorCancel(Integer companySeq, Integer goodSeq, Integer colorSeq, Integer isCancel) {
        
        meetingOrderProductDao.updateGoodColorCancel(companySeq, goodSeq, colorSeq, isCancel);
        
        try {
            List<String> accountNameList = meetingOrderProductDao.getAccountNameByGoodSeqAndColorSeq(companySeq, goodSeq, colorSeq);
            //货品
            MeetingGoodsEntity meetingGoodsEntity = meetingGoodsDao.selectById(goodSeq);
            //颜色
            GoodsColorEntity goodsColorEntity = goodsColorDao.selectById(colorSeq);
            
            //发送手机消息
            StringBuilder sb = new StringBuilder()
                .append("订货会")
                .append(" 货号：")
                .append(meetingGoodsEntity.getGoodID())
                .append(" 颜色：")
                .append(goodsColorEntity.getName());
            
            if (isCancel == 0) {
                sb.append(" 全部恢复");
            } else {
                sb.append(" 全部取消");
            }
    
            jPushUtils.sendPush(accountNameList, sb.toString(), sb.toString(), "");
        } catch (Exception e) {
            //e.printStackTrace();
            log.error("修改订货会订单状态，发送手机推送消息失败" + e.getMessage(), e);
        }
    }
    
    @Override
    public void modifyGoodCancel(Integer companySeq, Integer goodSeq, Integer isCancel) {
        meetingOrderProductDao.updateGoodCancel(companySeq, goodSeq, isCancel);
    
        try {
            List<String> accountNameList = meetingOrderProductDao.getAccountNameByGoodSeq(companySeq, goodSeq);
            //货品
            MeetingGoodsEntity meetingGoodsEntity = meetingGoodsDao.selectById(goodSeq);
        
            //发送手机消息
            StringBuilder sb = new StringBuilder()
                .append("订货会")
                .append(" 货号：")
                .append(meetingGoodsEntity.getGoodID());
        
            if (isCancel == 0) {
                sb.append(" 全部恢复");
            } else {
                sb.append(" 全部取消");
            }
        
            jPushUtils.sendPush(accountNameList, sb.toString(), sb.toString(), "");
        } catch (Exception e) {
            //e.printStackTrace();
            log.error("修改订货会订单状态，发送手机推送消息失败" + e.getMessage(), e);
        }
    }

	@Override
	public void updateBySize(MeetingOrderProductEntity meetingOrderProductEntity) {
		MeetingOrderProductEntity oldMeetingOrderProductEntity=super.selectOne(new EntityWrapper<MeetingOrderProductEntity>()
				.eq("MeetingOrder_Seq", meetingOrderProductEntity.getMeetingOrderSeq())
	            .eq("MeetingGoods_Seq", meetingOrderProductEntity.getMeetingGoodsSeq())
	            .eq("Color_Seq", meetingOrderProductEntity.getColorSeq())
	            .eq("Size", meetingOrderProductEntity.getSize()));
		if(oldMeetingOrderProductEntity!=null) {
			if(oldMeetingOrderProductEntity.getBuyCount().equals(meetingOrderProductEntity.getBuyCount())) {
			return;
			}else {
				meetingOrderProductEntity.setSeq(oldMeetingOrderProductEntity.getSeq());
			}
		}
		super.insertOrUpdate(meetingOrderProductEntity);
	    try {
            List<String> accountNameList = meetingOrderProductDao.getAccountNameByMeetingOrderSeq( meetingOrderProductEntity.getMeetingOrderSeq());
            
            //订单
            MeetingOrderEntity meetingOrderEntity = meetingOrderDao.selectById( meetingOrderProductEntity.getMeetingOrderSeq());
            //货品
            MeetingGoodsEntity meetingGoodsEntity = meetingGoodsDao.selectById( meetingOrderProductEntity.getMeetingGoodsSeq());
            //颜色
            GoodsColorEntity goodsColorEntity = goodsColorDao.selectById( meetingOrderProductEntity.getColorSeq());
            
            //发送手机消息
            StringBuilder sb = new StringBuilder();
            sb.append("订货会 订单号：")
                .append(meetingOrderEntity.getOrderNum())
                .append(" 货号：")
                .append(meetingGoodsEntity.getGoodID())
                .append(" 颜色：")
                .append(goodsColorEntity.getName())
                .append("尺码：")
                .append(meetingOrderProductEntity.getSize())
                .append("数量更改为：")
                .append(meetingOrderProductEntity.getBuyCount());
            log.info(sb.toString());
            jPushUtils.sendPush(accountNameList, sb.toString(), sb.toString(), "");
        } catch (Exception e) {
            //e.printStackTrace();
            log.error("修改订货会订单状态，发送手机推送消息失败" + e.getMessage(), e);
        }
	}

    @Override
    public BigDecimal selectCancelMoneyByOrderSeq(Integer meetingOrderSeq) throws Exception {
        Map<String,Object> map = new HashMap<>(10);
        map.put("meetingOrderSeq",meetingOrderSeq);
        return baseMapper.selectCancelMoneyByOrderSeq(map);
    }

    @Override
    public Integer selectCancelTotal(Integer companySeq,Integer meetingSeq, Integer userSeq,String keywords,Integer areaSeq) throws Exception {
        Map<String,Object> map = new HashMap<>(10);
        map.put("companySeq",companySeq);
        map.put("meetingSeq",meetingSeq);
        map.put("userSeq",userSeq);
        map.put("keywords",keywords);
        map.put("areaSeq",areaSeq);
        return baseMapper.selectCancelTotal(map);
    }

    @Override
    public BigDecimal selectCancelMoney(Integer companySeq,Integer meetingSeq, Integer userSeq,String keywords,Integer areaSeq) throws Exception {
        Map<String,Object> map = new HashMap<>(10);
        map.put("companySeq",companySeq);
        map.put("meetingSeq",meetingSeq);
        map.put("userSeq",userSeq);
        map.put("keywords",keywords);
        map.put("areaSeq",areaSeq);
        return baseMapper.selectCancelMoney(map);
    }

    @Override
    public List<MeetingOrderProductEntity> selectCancelOrder(Integer meetingOrderSeq) throws Exception {
        Map<String,Object> map = new HashMap<>(10);
        map.put("meetingOrderSeq",meetingOrderSeq);
        return baseMapper.selectCancelOrder(map);
    }

    @Override
    public Integer selectCancelGoodNum(Integer companySeq,Integer meetingSeq, Integer userSeq,String keywords,Integer areaSeq) throws Exception {
        Map<String,Object> map = new HashMap<>(10);
        map.put("companySeq",companySeq);
        map.put("meetingSeq",meetingSeq);
        map.put("userSeq",userSeq);
        map.put("keywords",keywords);
        map.put("areaSeq",areaSeq);
        return baseMapper.selectCancelGoodNum(map);
    }

    @Override
    public Integer selectCancelSku(Integer companySeq, Integer meetingSeq, Integer userSeq, String keywords) throws Exception {
        Map<String,Object> map = new HashMap<>(10);
        map.put("companySeq",companySeq);
        map.put("meetingSeq",meetingSeq);
        map.put("userSeq",userSeq);
        map.put("keywords",keywords);
        return baseMapper.selectCancelSku(map);
    }


	@Override
	public Integer selectCancelTotalByOrderSeq(Integer orderSeq) throws Exception {
		   Map<String,Object> map = new HashMap<>(10);
	        map.put("orderSeq",orderSeq);
		return baseMapper.selectCancelTotalByOrderSeq(map);
	}

    
    
    
	@Override
	public boolean isOrderGoodsColorCancel(Integer c_orderSeq, Integer c_meetingGoodsSeq, Integer c_colorSeq) {
		Wrapper<MeetingOrderProductEntity> wrapper = new EntityWrapper<MeetingOrderProductEntity>();
		wrapper.where("MeetingOrder_Seq = {0} AND MeetingGoods_Seq = {1} AND Color_Seq = {2}", c_orderSeq, c_meetingGoodsSeq, c_colorSeq);
		List<MeetingOrderProductEntity> meetingOrderProductList = meetingOrderProductDao.selectList(wrapper);
		if(meetingOrderProductList != null && meetingOrderProductList.size() > 0) {
			MeetingOrderProductEntity meetingOrderProductEntity = meetingOrderProductList.get(0);
			if(meetingOrderProductEntity.getCancel() == 1) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	
}
