package io.nuite.modules.online_sales_app.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import io.nuite.modules.online_sales_app.dao.OnlineSalesOrderDao;
import io.nuite.modules.online_sales_app.dao.OnlineSalesOrderProductsDao;
import io.nuite.modules.online_sales_app.dao.OnlineSalesShoesDataDao;
import io.nuite.modules.online_sales_app.entity.OrderEntity;
import io.nuite.modules.online_sales_app.entity.OrderProductsEntity;
import io.nuite.modules.online_sales_app.entity.ShoesDataEntity;
import io.nuite.modules.online_sales_app.service.OnlineOrderService;
import io.nuite.modules.online_sales_app.service.VerificationService;
import io.nuite.modules.sr_base.dao.BaseBrandDao;
import io.nuite.modules.sr_base.dao.GoodsPeriodDao;
import io.nuite.modules.sr_base.dao.GoodsShoesDao;
import io.nuite.modules.sr_base.dao.GoodsSizeDao;
import io.nuite.modules.sr_base.entity.BaseBrandEntity;
import io.nuite.modules.sr_base.entity.GoodsPeriodEntity;
import io.nuite.modules.sr_base.entity.GoodsShoesEntity;
import io.nuite.modules.sr_base.entity.GoodsSizeEntity;

@Service
public class VerificationServiceImpl implements VerificationService {

    @Autowired
    private OnlineSalesOrderDao onlineSalesOrderDao;

    @Autowired
    private OnlineSalesOrderProductsDao onlineSalesOrderProductsDao;
    
    @Autowired
    private OnlineSalesShoesDataDao onlineSalesShoesDataDao;
    
    @Autowired
    private GoodsShoesDao goodsShoesDao;
    
    @Autowired
    private GoodsSizeDao goodsSizeDao;
    
    @Autowired
    private BaseBrandDao baseBrandDao;
    
    @Autowired
    private GoodsPeriodDao goodsPeriodDao;
    
    @Autowired
    private OnlineOrderService onlineOrderService;
    
    
	/**
	 * 获取工厂今日内全部已支付（待发货1，已发货2，已到货3）的订单
	 */
	@Override
	public List<Map<String, Object>> getTodayPaidOrderByCompanySeq(Integer companySeq) {
		Wrapper<OrderEntity> wrapper = new EntityWrapper<OrderEntity>();
		wrapper.setSqlSelect("Seq AS seq, User_Seq AS userSeq, OrderNum AS orderNum, OrderPrice AS orderPrice, InputTime AS inputTime")
		.where("Company_Seq = {0} AND (OrderStatus = 1 OR OrderStatus = 2 OR OrderStatus = 3) AND "
				+ " DateDiff(dd, InputTime, GETDATE()) = 0", companySeq);
		return onlineSalesOrderDao.selectMaps(wrapper);
	}


	/**
	 * 根据订单号，获取订单购买所有商品的货号+尺码，多个数量显示多次
	 */
	@Override
	public String getGoodIdSizeByOrderSeq(Integer orderSeq) {
		Wrapper<OrderProductsEntity> wrapper = new EntityWrapper<OrderProductsEntity>();
		wrapper.where("Order_Seq = {0}", orderSeq);
		List<OrderProductsEntity> orderProductsList = onlineSalesOrderProductsDao.selectList(wrapper);
		
		StringBuilder goodIdSizeBuilder = new StringBuilder();
		for(OrderProductsEntity orderProductsEntity : orderProductsList) {
			ShoesDataEntity shoesDataEntity = onlineSalesShoesDataDao.selectById(orderProductsEntity.getShoesDataSeq());
			GoodsShoesEntity goodsShoesEntity = goodsShoesDao.selectById(shoesDataEntity.getShoesSeq());
			GoodsSizeEntity goodsSizeEntity = goodsSizeDao.selectById(shoesDataEntity.getSizeSeq());
			
			for(int i = 0; i < orderProductsEntity.getBuyCount(); i++) {
				goodIdSizeBuilder.append(goodsShoesEntity.getGoodID()).append(goodsSizeEntity.getSizeName()).append(",");
			}
		}
		if(goodIdSizeBuilder.length() > 0) {
			goodIdSizeBuilder.deleteCharAt(goodIdSizeBuilder.length() - 1);
		}
		
		return goodIdSizeBuilder.toString();
	}


	/**
	 * 根据品牌序号、货号、尺码修改库存
	 */
	@Override
    @Transactional(propagation = Propagation.REQUIRED)
	public void changeStockByGoodIdSize(Integer companySeq, String[] goodIdSizeArr) {
		
    	for(int i = 0; i < goodIdSizeArr.length; i++) {
    		//获取货号、尺码
    		String goodIdSize= goodIdSizeArr[i].trim();
    		String goodId = goodIdSize.substring(0, goodIdSize.length() - 2);
    		String sizeName = goodIdSize.substring(goodIdSize.length() - 2);
    		
    		//0.根据公司序号查询品牌序号
    		BaseBrandEntity baseBrandEntity = new BaseBrandEntity();
    		baseBrandEntity.setCompanySeq(companySeq);
    		baseBrandEntity = baseBrandDao.selectOne(baseBrandEntity);
    		Integer brandSeq = baseBrandEntity.getSeq();
    		
    		//1.根据 品牌编号 查询 波次编号列表
    		Wrapper<GoodsPeriodEntity> periodWrapper = new EntityWrapper<GoodsPeriodEntity>();
    		periodWrapper.setSqlSelect("Seq").where("Brand_Seq = {0}", brandSeq);
    		List<Object> periodSeqList = goodsPeriodDao.selectObjs(periodWrapper);
    		
    		//2.根据 波次编号列表、货号 获取鞋子seq
    		Wrapper<GoodsShoesEntity> shoesWrapper = new EntityWrapper<GoodsShoesEntity>();
    		shoesWrapper.setSqlSelect("Seq").in("Period_Seq", periodSeqList).where("GoodID = {0}", goodId);
    		Integer shoesSeq = (Integer) goodsShoesDao.selectObjs(shoesWrapper).get(0);
    		
    		//3.根据companySeq、sizeName获取size Seq
    		GoodsSizeEntity goodsSizeEntity = new GoodsSizeEntity();
    		goodsSizeEntity.setCompanySeq(companySeq);
    		goodsSizeEntity.setSizeName(sizeName);
    		goodsSizeEntity = goodsSizeDao.selectOne(goodsSizeEntity);
    		Integer sizeSeq = goodsSizeEntity.getSeq();
    		
    		//4.根据鞋子seq、尺码seq获取shoesDataSeq列表
    		Wrapper<ShoesDataEntity> shoesDataWrapper = new EntityWrapper<ShoesDataEntity>();
    		shoesDataWrapper.setSqlSelect("Seq").where("Shoes_Seq = {0} AND Size_Seq = {1}", shoesSeq, sizeSeq);
    		List<Object> shoesDataSeqList = onlineSalesShoesDataDao.selectObjs(shoesDataWrapper);
    		for(Object shoesDataSeq : shoesDataSeqList) {
    			//修改库存
    			onlineOrderService.changeShoesDataStock((Integer)shoesDataSeq, -1);
    		}
    		
    	}
    	
	}
	
}
