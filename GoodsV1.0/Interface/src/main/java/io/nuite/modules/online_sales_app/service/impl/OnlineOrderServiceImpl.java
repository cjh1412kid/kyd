package io.nuite.modules.online_sales_app.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.github.binarywang.utils.qrcode.QrcodeUtils;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.BaseWxPayResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.util.SignUtils;
import io.nuite.common.exception.RRException;
import io.nuite.common.utils.DateUtils;
import io.nuite.modules.online_sales_app.dao.*;
import io.nuite.modules.online_sales_app.entity.*;
import io.nuite.modules.online_sales_app.service.OlsMiniAppService;
import io.nuite.modules.online_sales_app.service.OnlineOrderService;
import io.nuite.modules.online_sales_app.utils.OrderStatusEnum;
import io.nuite.modules.online_sales_app.wx.WxMyService;
import io.nuite.modules.online_sales_app.wx.WxServiceUtils;
import io.nuite.modules.order_platform_app.entity.MeetingShoppingCartDetailEntity;
import io.nuite.modules.order_platform_app.entity.MeetingShoppingCartDistributeBoxEntity;
import io.nuite.modules.sr_base.dao.GoodsShoesDao;
import io.nuite.modules.sr_base.entity.GoodsPeriodEntity;
import io.nuite.modules.sr_base.entity.GoodsShoesEntity;
import io.nuite.modules.sr_base.utils.ConfigUtils;
import io.nuite.modules.system.entity.online_sale.OnlineOrderEntity;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OnlineOrderServiceImpl implements OnlineOrderService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OnlineSalesOrderDao onlineSalesOrderDao;

    @Autowired
    private OnlineSalesOrderProductsDao onlineSalesOrderProductsDao;

    @Autowired
    private OnlineSalesShoesDataDao onlineSalesShoesDataDao;

    @Autowired
    private OnlineSalesShoppingCartDao onlineSalesShoppingCartDao;

    @Autowired
    private GoodsShoesDao goodsShoesDao;

    @Autowired
    private OlsAddressDao olsAddressDao;

    @Autowired
    private OlsMiniAppService olsMiniAppService;

    @Autowired
    private ConfigUtils configUtils;
    
	@Autowired
	private SalesShoppingCartDao salesShoppingCartDao;
	
	@Autowired
	private SalesShoppingCartDetailDao salesShoppingCartDetailDao;
	
	@Autowired
	private SalesShoppingCartDistributeBoxDao salesShoppingCartDistributeBoxDao;
	
	@Autowired
	private OrderCartDao orderCartDao;
	
	@Autowired
	private OrderCartDetailDao orderCartDetailDao;
	
	@Autowired
	private OrderCartDistributeBoxDao orderCartDistributeBoxDao;

    /**
     * 提交订单
     *
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer submitOrder(List<Map<String, Integer>> stockChangeList, List<Integer> shoppingCartSeqList, OrderEntity orderEntity,
                               List<OrderProductsEntity> orderProductsList) {

        //1.修改库存
        /*for (Map<String, Integer> map : stockChangeList) {
            changeShoesDataStock(map.get("shoesDataSeq"), map.get("changNum"));
        }*/

        //2.删除购物车
        if (shoppingCartSeqList.size() > 0) {
            onlineSalesShoppingCartDao.deleteBatchIds(shoppingCartSeqList);
        }

        //3.新增订单
        onlineSalesOrderDao.insert(orderEntity);

        //4.新增订单关联产品
        for (OrderProductsEntity orderProductsEntity : orderProductsList) {
            orderProductsEntity.setOrderSeq(orderEntity.getSeq());
            onlineSalesOrderProductsDao.insert(orderProductsEntity);
        }

        return orderEntity.getSeq();
    }


    /**
     * 修改库存（注意：本类内部其他方法调用此方法时，必须添加事物，否则导致本方法的事物失效）
     */
    //TODO 后台管理端修改库存时，没有同步，可能会出问题
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void changeShoesDataStock(Integer shoesDataSeq, Integer changNum) {
        synchronized (this) {
            ShoesDataEntity shoesDataEntity = onlineSalesShoesDataDao.selectById(shoesDataSeq);
            //判断库存是否足够
            if (shoesDataEntity.getStock() + changNum >= 0) {
                onlineSalesShoesDataDao.changeShoesDataStock(shoesDataSeq, changNum);
                shoesDataEntity = onlineSalesShoesDataDao.selectById(shoesDataSeq);
                if (shoesDataEntity.getStock() < 0) {
                    throw new RuntimeException("库存不足");
                }
            } else {
                throw new RuntimeException("库存不足");
            }
        }
    }

    private void changeOrderStock(Integer orderSeq) {
        EntityWrapper<OrderProductsEntity> ew = new EntityWrapper<>();
        ew.eq("Order_Seq", orderSeq);
        List<OrderProductsEntity> orderProducts = onlineSalesOrderProductsDao.selectList(ew);
        for (OrderProductsEntity orderProduct : orderProducts) {
            changeShoesDataStock(orderProduct.getShoesDataSeq(), orderProduct.getBuyCount() * (-1));
        }
    }

    private void checkOrderStock(Integer orderSeq) {
        EntityWrapper<OrderProductsEntity> ew = new EntityWrapper<>();
        ew.eq("Order_Seq", orderSeq);
        List<OrderProductsEntity> orderProducts = onlineSalesOrderProductsDao.selectList(ew);
        for (OrderProductsEntity orderProduct : orderProducts) {
            checkShoesDataStock(orderProduct.getShoesDataSeq(), orderProduct.getBuyCount() * (-1));
        }
    }

    private void checkShoesDataStock(Integer shoesDataSeq, Integer changNum) {
        ShoesDataEntity shoesDataEntity = onlineSalesShoesDataDao.selectById(shoesDataSeq);
        //判断库存是否足够
        if (shoesDataEntity.getStock() + changNum < 0) {
            throw new RuntimeException("库存不足");
        }
    }


    /**
     * 根据订单编号，查询订单的购买的商品列表
     */
    @Override
    public List<OrderProductsEntity> getOrderProductsListByOrderSeq(Integer orderSeq) {
        Map<String, Object> columnMap = new HashMap<String, Object>();
        columnMap.put("Order_Seq", orderSeq);
        return onlineSalesOrderProductsDao.selectByMap(columnMap);

    }


    /**
     * 取消订单
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void cancelOrder(Integer orderSeq) {

        //修改订单状态
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setSeq(orderSeq);
        orderEntity.setOrderStatus(OrderStatusEnum.ORDSTATUS_FOUR.getValue());
        if (onlineSalesOrderDao.selectOne(orderEntity) == null) {
            onlineSalesOrderDao.updateById(orderEntity);
        } else {
            throw new RuntimeException();
        }
        //修改库存
        /*for (Map<String, Integer> map : stockChangeList) {
            changeShoesDataStock(map.get("shoesDataSeq"), map.get("changNum"));
        }*/

    }

    /**
     * 确认收货
     *
     * @param orderSeq
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void shipGotOrder(Integer orderSeq) {
        //修改订单状态
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setSeq(orderSeq);
        orderEntity.setOrderStatus(OrderStatusEnum.ORDSTATUS_THREE.getValue());
        if (onlineSalesOrderDao.selectOne(orderEntity) == null) {
            onlineSalesOrderDao.updateById(orderEntity);
        } else {
            throw new RuntimeException();
        }
    }


    /**
     * 删除订单
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteOrder(Integer orderSeq) {
        //删除订单
        onlineSalesOrderDao.deleteById(orderSeq);

        //删除订单关联产品
        Map<String, Object> columnMap = new HashMap<String, Object>();
        columnMap.put("Order_Seq", orderSeq);
        onlineSalesOrderProductsDao.deleteByMap(columnMap);

    }


    /**
     * 根据用户编号获取订单列表
     */
    @Override
    public List<Map<String, Object>> getOrderListByUserSeq(Integer userSeq, Integer orderStatus, Integer start, Integer num) {
        Wrapper<OrderEntity> wrapper = new EntityWrapper<OrderEntity>();
        wrapper.setSqlSelect("Seq AS seq, OrderNum AS orderNum, OrderPrice AS orderPrice, Paid AS paid, OrderStatus AS orderStatus, ExpressCompany_Seq AS expressCompanySeq, ExpressNo AS expressNo, InputTime AS inputTime");
        if (orderStatus != null && orderStatus >= 0) {
            wrapper.where("User_Seq = {0} AND OrderStatus = {1}", userSeq, orderStatus);
        } else {
            wrapper.where("User_Seq = {0}", userSeq);
        }
        wrapper.orderBy("InputTime DESC");
        return onlineSalesOrderDao.selectMapsPage(new RowBounds(start - 1, num), wrapper);
    }


    /**
     * 根据shoesDateSeq获取鞋子基本信息实体
     */
    @Override
    public GoodsShoesEntity getGoodsShoesByShoesDateSeq(Integer shoesDataSeq) {
        ShoesDataEntity shoesDataEntity = onlineSalesShoesDataDao.selectById(shoesDataSeq);
        return goodsShoesDao.selectById(shoesDataEntity.getShoesSeq());
    }


    /**
     * 根据seq获取订单
     */
    @Override
    public Map<String, Object> getOrderBySeq(Integer orderSeq, Integer userSeq) {
        Wrapper<OrderEntity> wrapper = new EntityWrapper<OrderEntity>();
        wrapper.setSqlSelect("Seq AS seq, OrderNum AS orderNum, OrderPrice AS orderPrice, Paid AS paid," +
                " OrderStatus AS orderStatus, ExpressCompany_Seq AS expressCompanySeq, ExpressNo AS expressNo," +
                " ReceiverName AS receiverName, Telephone AS telephone, FullAddress AS fullAddress, SelfPick as selfPick");
        wrapper.where("Seq = {0}", orderSeq );
        List<Map<String, Object>> list = onlineSalesOrderDao.selectMaps(wrapper);
        if (list.size() <= 0) {
            throw new RRException("订单不存在");
        }
        return list.get(0);
    }

    /**
     * 根据seq获取订单对象
     */
    @Override
    public WxPayMpOrderResult createWxpayOrder(Integer orderSeq, CustomerUserInfo customerUserInfo, String customerIp) {
        OrderEntity orderEntity = onlineSalesOrderDao.selectById(orderSeq);
        if (orderEntity.getUserSeq().longValue() != customerUserInfo.getSeq()) {
            throw new RRException("非法操作");
        }
        if (orderEntity.getOrderStatus() != OrderStatusEnum.ORDSTATUS_ZERO.getValue()) {
            throw new RRException("订单状态已变更");
        }
        Integer companySeq = customerUserInfo.getCompanySeq();
        MiniAppEntity miniAppEntity = olsMiniAppService.queryOneByCompanySeq(companySeq.intValue());
        WxMyService wxMyService = WxServiceUtils.getWxMyService(miniAppEntity);
        if (wxMyService == null) {
            throw new RRException("当前未绑定支付信息");
        }
        // 校验库存
        checkOrderStock(orderSeq);

        WxPayService wxPayService = wxMyService.getWxPayService();
        if (orderEntity.getWxpayPrepayId() != null && !orderEntity.getWxpayPrepayId().trim().isEmpty()) {
            long timeDiff = new Date().getTime() - orderEntity.getWxpayPrepayTime().getTime();
            if (timeDiff < 2 * 3600 * 1000) {
                String timestamp = String.valueOf(System.currentTimeMillis() / 1000L);
                String nonceStr = String.valueOf(System.currentTimeMillis());
                String prepayId = orderEntity.getWxpayPrepayId();
                String signType = "MD5";
                WxPayMpOrderResult payResult = WxPayMpOrderResult.builder().appId(wxPayService.getConfig().getAppId()).timeStamp(timestamp).nonceStr(nonceStr).packageValue("prepay_id=" + prepayId).signType(signType).build();
                payResult.setPaySign(SignUtils.createSign(payResult, signType, wxPayService.getConfig().getMchKey(), false));
                return payResult;
            }
        }
        Date prepayTime = new Date();
        try {
            WxPayMpOrderResult wxPayMpOrderResult = wxUnifiedOrder(orderEntity, customerUserInfo.getOpenId(), customerIp, wxPayService);
            String prepayId = wxPayMpOrderResult.getPackageValue().replace("prepay_id=", "");
            OrderEntity updateEntity = new OrderEntity();
            updateEntity.setSeq(orderSeq);
            updateEntity.setWxpayPrepayId(prepayId);
            updateEntity.setWxpayPrepayTime(prepayTime);
            onlineSalesOrderDao.updateById(updateEntity);
            return wxPayMpOrderResult;
        } catch (Exception e) {
            logger.error("调取微信支付失败", e);
            throw new RRException("调取微信支付失败");
        }
    }

    @Override
    public void wxPayNotify(String xmlResult) throws Exception {
        WxPayOrderNotifyResult result = WxPayOrderNotifyResult.fromXML(xmlResult);
        String attach = result.getAttach();
        if (attach == null) {
            throw new RRException("支付通知attach信息错误");
        }
        String[] attachArray = attach.split("&");
        if (attachArray.length < 2) {
            throw new RRException("支付通知attach信息错误");
        }
        Integer companySeq = Integer.valueOf(attachArray[0]);
        MiniAppEntity miniAppEntity = olsMiniAppService.queryOneByCompanySeq(companySeq);
        WxMyService wxMyService = WxServiceUtils.getWxMyService(miniAppEntity);
        if (wxMyService == null) {
            throw new RRException("支付通知attach工厂信息错误");
        }

        result.checkResult(wxMyService.getWxPayService(), wxMyService.getWxPayService().getConfig().getSignType(), false);
        // 结果正确
        Integer orderSeq = Integer.valueOf(attachArray[1]);
        String orderNum = result.getOutTradeNo();
        String tradeNo = result.getTransactionId();
        String totalFee = BaseWxPayResult.feeToYuan(result.getTotalFee());
        Date timeEnd = DateUtils.stringToDate(result.getTimeEnd(), DateUtils.WX_DATE_PATTERN);
        OrderEntity orderEntity = onlineSalesOrderDao.selectById(orderSeq);
        if (!orderEntity.getOrderNum().equals(orderNum)) {
            logger.error("订单内容和数据不匹配:seq=" + orderSeq + ",orderNum:" + orderNum + ",localOrderNum:" + orderEntity.getOrderNum());
            throw new RRException("订单内容和数据不匹配");
        }
        if (orderEntity.getOrderStatus() == OrderStatusEnum.ORDSTATUS_ZERO.getValue()) {
            if (orderEntity.getSelfPick() != null && orderEntity.getSelfPick() == 1) {
                orderEntity.setOrderStatus(OrderStatusEnum.ORDSTATUS_THREE.getValue());
            } else {
                orderEntity.setOrderStatus(OrderStatusEnum.ORDSTATUS_ONE.getValue());
            }

            //付款成功扣除库存
            changeOrderStock(orderEntity.getSeq());
        } else {
            logger.error("支付状态已经处理:seq=" + orderSeq + ",orderNum:" + orderNum);
        }
        orderEntity.setPaymentTime(timeEnd);
        orderEntity.setPaid(new BigDecimal(totalFee));
        orderEntity.setWxTransactionId(tradeNo);
        onlineSalesOrderDao.updateById(orderEntity);

    }


    /**
     * 获取订单购买商品详细信息
     */
    @Override
    public List<Map<String, Object>> getOrderProductsDetail(Integer orderSeq) {
        return onlineSalesOrderDao.getOrderProductsDetail(orderSeq);
    }


    /**
     * 获取收货地址信息
     */
    @Override
    public UserDeliveryInfo getUserDeliveryInfoBySeq(Integer userDeliverySeq) {
        return olsAddressDao.selectById(userDeliverySeq);
    }

    private WxPayMpOrderResult wxUnifiedOrder(OrderEntity orderEntity, String openId, String customerIp, WxPayService wxPayService) throws Exception {
        WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
        orderRequest.setDeviceInfo("MINIAPP");
        orderRequest.setBody("有好鞋-商品购买");
        orderRequest.setAttach(orderEntity.getCompanySeq().toString() + "&" + orderEntity.getSeq().toString());
        orderRequest.setOutTradeNo(orderEntity.getOrderNum());
        orderRequest.setTotalFee(BaseWxPayRequest.yuanToFen(orderEntity.getOrderPrice().toString()));
        orderRequest.setOpenid(openId);
        orderRequest.setSpbillCreateIp(customerIp);
        Date date = orderEntity.getInputTime();
        orderRequest.setTimeStart(DateUtils.format(date, DateUtils.WX_DATE_PATTERN));

        Date expireDate = DateUtils.addDateDays(date, 1);
        orderRequest.setTimeExpire(DateUtils.format(expireDate, DateUtils.WX_DATE_PATTERN));

        return wxPayService.createOrder(orderRequest);
    }

    /**
     * 获取消磁二维码
     *
     * @param orderSeq
     * @param userSeq
     * @return "RemoveRFID,13896620180122173026131,133****2929,699,2018-01-24 10:38:13.310,B795654B0133,B795654B0134,B795654B0135"
     */
    @Override
    public String getCancelQRCode(Integer orderSeq, Integer userSeq, String openId) {
        String imageOrderDir = io.nuite.common.utils.FileUtils.getWebAppsPath() + configUtils.getPictureBaseUploadProject() + File.separator
                + configUtils.getBaseDir() + File.separator + "ordertemp" + File.separator;

        Map<String, Object> orderData = getOrderBySeq(orderSeq, userSeq);
        int orderStatus = Integer.valueOf(orderData.get("orderStatus").toString());
        if (orderStatus == 0) {
            throw new RRException("订单未支付");
        }
        if (orderStatus == 4) {
            throw new RRException("订单已取消");
        }
        String orderNum = orderData.get("orderNum").toString();

        File file = new File(imageOrderDir + orderNum + ".jpg");
        if (file.exists()) {
            return orderNum;
        }
        String orderPrice = orderData.get("orderPrice").toString();
        Date inputTime = (Date) orderData.get("inputTime");
        StringBuffer stringBuffer = new StringBuffer("RemoveRFID,");
        stringBuffer.append(orderNum).append(",");
        stringBuffer.append(openId).append(",");
        stringBuffer.append(orderPrice).append(",");
        stringBuffer.append(DateUtils.format(inputTime, DateUtils.DATE_TIME_SECOND_PATTERN)).append(",");

        EntityWrapper<OrderProductsEntity> ew = new EntityWrapper<>();
        ew.eq("Order_Seq", orderSeq);
        List<OrderProductsEntity> orderProducts = onlineSalesOrderProductsDao.selectList(ew);
        for (OrderProductsEntity orderProduct : orderProducts) {
            int buyCount = orderProduct.getBuyCount();
            Integer shoesDataSeq = orderProduct.getShoesDataSeq();
            Map<String, Object> detail = onlineSalesShoesDataDao.queryGoodsDetail(shoesDataSeq);
            String goodDetail = detail.get("goodId").toString() + detail.get("sizeName").toString();
            for (int i = 0; i < buyCount; i++) {
                stringBuffer.append(goodDetail).append(",");
            }
        }

        String removeString = stringBuffer.toString();
        if (removeString.endsWith(",")) {
            removeString = removeString.substring(0, removeString.length() - 1);
        }
        File fileDir = new File(imageOrderDir);
        if (!fileDir.exists()) fileDir.mkdirs();
        byte[] qrcodeString = QrcodeUtils.createQrcode(removeString, 280, null);
        try {
            FileUtils.writeByteArrayToFile(file, qrcodeString);
        } catch (IOException e) {
            throw new RRException("二维码保存失败");
        }
        return orderNum;
    }


	@Override
	public Integer orderSubmit(List<Integer> shoppingCartSeqList, OrderEntity OrderEntity,Date nowDate) {
		//1.新增订单
		onlineSalesOrderDao.insert(OrderEntity);
		
		//3.复制购物车到订单购物车
		copyMeetingShoppingCartToMeetingOrderCart(OrderEntity.getSeq(), shoppingCartSeqList, nowDate);
		
		 //4.删除购物车
		  if (shoppingCartSeqList.size() > 0) {
			//1).删除购物车
			  	salesShoppingCartDao.deleteBatchIds(shoppingCartSeqList);
	    		//2).删除对应购物车详情
	    		Wrapper<SalesShoppingCartDistributeBoxEntity> wrapper1 = new EntityWrapper<SalesShoppingCartDistributeBoxEntity>();
	    		wrapper1.in("SalesShoppingCart_Seq", shoppingCartSeqList);
	    		salesShoppingCartDistributeBoxDao.delete(wrapper1);
	    		//3).删除购物车配箱详情
	    		Wrapper<SalesShoppingCartDetailEntity> wrapper2 = new EntityWrapper<SalesShoppingCartDetailEntity>();
	    		wrapper2.in("SalesShoppingCart_Seq", shoppingCartSeqList);
	    		salesShoppingCartDetailDao.delete(wrapper2);
		  }
		return OrderEntity.getSeq();
	}
	
	private void copyMeetingShoppingCartToMeetingOrderCart(Integer meetingOrderSeq, List<Integer> saleShoppingCartSeqs, Date nowDate) {
		for(Integer saleShoppingCartSeq : saleShoppingCartSeqs) {
			//1.复制购物车表
			SalesShoppingCartEntity salesShoppingCartEntity=salesShoppingCartDao.selectById(saleShoppingCartSeq);
			OrderCartEntity orderCartEntity=new OrderCartEntity();
			orderCartEntity.setIsAllocated(salesShoppingCartEntity.getIsAllocated());
			orderCartEntity.setIsChecked(1);
			orderCartEntity.setGoodsID(salesShoppingCartEntity.getGoodsID());
			orderCartEntity.setGoodsPeriodSeq(salesShoppingCartEntity.getGoodsPeriodSeq());
			orderCartEntity.setInputTime(nowDate);
			orderCartEntity.setOrderSeq(meetingOrderSeq);
			orderCartEntity.setPerBoxNum(salesShoppingCartEntity.getPerBoxNum());
			orderCartEntity.setShoesSeq(salesShoppingCartEntity.getShoesSeq());
			orderCartEntity.setTotalSelectNum(salesShoppingCartEntity.getTotalSelectNum());
			orderCartEntity.setUserGoodID(salesShoppingCartEntity.getUserGoodID());
			orderCartEntity.setUserSeq(salesShoppingCartEntity.getUserSeq());
			
			orderCartDao.insert(orderCartEntity);
			//2.复制购物车配箱表
			Wrapper<SalesShoppingCartDistributeBoxEntity> wrapper = new EntityWrapper<SalesShoppingCartDistributeBoxEntity>();
			wrapper.where("SalesShoppingCart_Seq = {0}", saleShoppingCartSeq);
			List<SalesShoppingCartDistributeBoxEntity> salesShoppingCartDistributeBoxEntities=salesShoppingCartDistributeBoxDao.selectList(wrapper);
			for(SalesShoppingCartDistributeBoxEntity salesShoppingCartDistributeBoxEntity : salesShoppingCartDistributeBoxEntities) {
				OrderCartDistributeBoxEntity orderCartDistributeBoxEntity=new OrderCartDistributeBoxEntity();
				orderCartDistributeBoxEntity.setAllocatedType(salesShoppingCartDistributeBoxEntity.getAllocatedType());
				orderCartDistributeBoxEntity.setBoxCount(salesShoppingCartDistributeBoxEntity.getBoxCount());
				orderCartDistributeBoxEntity.setColorSeq(salesShoppingCartDistributeBoxEntity.getColorSeq());
				orderCartDistributeBoxEntity.setColorTotalNum(salesShoppingCartDistributeBoxEntity.getColorTotalNum());
				orderCartDistributeBoxEntity.setInputTime(nowDate);
				orderCartDistributeBoxEntity.setOrderShoppingCartSeq(orderCartEntity.getSeq());
				orderCartDistributeBoxDao.insert(orderCartDistributeBoxEntity);
				//3.复制购物车配码详情表
				Wrapper<SalesShoppingCartDetailEntity> wrapper2 = new EntityWrapper<SalesShoppingCartDetailEntity>();
				wrapper2.where("SalesShoppingCartDistributeBox_Seq = {0}", salesShoppingCartDistributeBoxEntity.getSeq());
				List<SalesShoppingCartDetailEntity> salesShoppingCartDetailEntities=salesShoppingCartDetailDao.selectList(wrapper2);
				for (SalesShoppingCartDetailEntity salesShoppingCartDetailEntity : salesShoppingCartDetailEntities) {
					OrderCartDetailEntity orderCartDetailEntity=new OrderCartDetailEntity();
					orderCartDetailEntity.setInputTime(nowDate);
					orderCartDetailEntity.setOrderShoppingCartDistributeBoxSeq(orderCartDistributeBoxEntity.getSeq());
					orderCartDetailEntity.setOrderShoppingCartSeq(orderCartEntity.getSeq());
					orderCartDetailEntity.setSelectNum(salesShoppingCartDetailEntity.getSelectNum());
					orderCartDetailEntity.setShoeDataSeq(salesShoppingCartDetailEntity.getShoeDataSeq());
					orderCartDetailDao.insert(orderCartDetailEntity);
				}
			}
		}
	}


	@Override
	public List<OrderCartEntity> getOrderCartListByOrderSeq(Integer orderSeq) {
		  Map<String, Object> columnMap = new HashMap<String, Object>();
	        columnMap.put("OrderSeq", orderSeq);
	        return orderCartDao.selectByMap(columnMap);
	}

    @Override
    public Page selectOrder(Page<OrderEntity> page,Integer companySeq, Integer year,Integer periodSeq,Integer customSeq) throws Exception {
        Map<String,Object> map = new HashMap<>(10);
        map.put("companySeq",companySeq);
        map.put("year",year);
        map.put("periodSeq",periodSeq);
        map.put("customSeq",customSeq);
        List<OrderEntity> orderEntities = onlineSalesOrderDao.selectOrder(page, map);
        for(OrderEntity orderEntity :orderEntities) {
            orderEntity.setOemUrl(configUtils.getPictureBaseUrl() +"/" + configUtils.getOnlineSalesApp().getOnlineDir() +"/"+orderEntity.getOemUrl());
            if(orderEntity.getPaid() != null && orderEntity.getPaid().compareTo(BigDecimal.ZERO) > 0) {
                orderEntity.setIsPaid(true);
            }else {
                orderEntity.setIsPaid(false);
            }
        }
        page.setRecords(orderEntities);
        return page;
    }

    @Override
    public List<Integer> selectYear(Integer brandSeq) throws Exception {
        Map<String,Object> map = new HashMap<>(10);
        map.put("brandSeq",brandSeq);
        return onlineSalesOrderDao.selectYear(map);
    }

    @Override
    public List<GoodsPeriodEntity> selectPeriod(Integer brandSeq, Integer year) throws Exception {
        Map<String,Object> map = new HashMap<>(10);
        map.put("brandSeq",brandSeq);
        map.put("year",year);
        return onlineSalesOrderDao.selectPeriod(map);
    }

    @Override
    public List<CustomerUserInfo> selectCustom(Integer companySeq, Integer periodSeq) throws Exception {
        Map<String,Object> map = new HashMap<>(10);
        map.put("companySeq",companySeq);
        map.put("periodSeq",periodSeq);
        return onlineSalesOrderDao.selectCustom(map);
    }
    @Override
	public List<Map<String, Object>> getOrderListByComapnySeq(List<Object> userSeqList, Integer orderStatus,
			Integer start, Integer num) {
		Wrapper<OrderEntity> wrapper = new EntityWrapper<OrderEntity>();
        wrapper.setSqlSelect("Seq AS seq, OrderNum AS orderNum, OrderPrice AS orderPrice, Paid AS paid, OrderStatus AS orderStatus, ExpressCompany_Seq AS expressCompanySeq, ExpressNo AS expressNo, InputTime AS inputTime");
        if (orderStatus != null && orderStatus >= 0) {
            wrapper.where("OrderStatus = {0}", orderStatus).in("User_Seq", userSeqList);
        } else {
            wrapper.in("User_Seq", userSeqList);
        }
        wrapper.orderBy("InputTime DESC");
        return onlineSalesOrderDao.selectMapsPage(new RowBounds(start - 1, num), wrapper);
	}


	@Override
	public OrderEntity getOrderEntityBySeq(Integer orderSeq) {
		return onlineSalesOrderDao.selectById(orderSeq);
	}


	@Override
	public void update(OrderEntity orderEntity) {
		onlineSalesOrderDao.updateById(orderEntity);
	}

    @Override
    public List<OrderEntity> selectOrderDetail(Integer companySeq,Integer customSeq, Integer periodSeq) throws Exception {
        Map<String,Object> map = new HashMap<>(10);
        map.put("companySeq",companySeq);
        map.put("periodSeq",periodSeq);
        map.put("customSeq",customSeq);
        return onlineSalesOrderDao.selectOrderDetail(map);
    }

    @Override
    public Integer selectTotalOrderNum(Integer companySeq,Integer customSeq, Integer periodSeq) throws Exception {
        Map<String,Object> map = new HashMap<>(10);
        map.put("companySeq",companySeq);
        map.put("periodSeq",periodSeq);
        map.put("customSeq",customSeq);
        return onlineSalesOrderDao.selectTotalOrderNum(map);
    }

    @Override
    public List<OrderEntity> selectOrderAllColumn(Integer companySeq, Integer periodSeq) throws Exception {
        Map<String,Object> map = new HashMap<>(10);
        map.put("companySeq",companySeq);
        map.put("periodSeq",periodSeq);
        return onlineSalesOrderDao.selectOrderAllColumn(map);
    }


	@Override
	public List<OrderEntity> getOrderByUserSeq(Integer userSeq) {
		Wrapper<OrderEntity> wrapper = new EntityWrapper<OrderEntity>();
		wrapper.where("User_Seq ={0}", userSeq);
		return onlineSalesOrderDao.selectList(wrapper);
	}
}
