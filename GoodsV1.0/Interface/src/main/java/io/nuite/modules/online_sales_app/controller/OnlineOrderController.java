package io.nuite.modules.online_sales_app.controller;


import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;

import io.nuite.common.exception.RRException;
import io.nuite.common.utils.FileUtils;
import io.nuite.common.utils.IPUtils;
import io.nuite.common.utils.R;
import io.nuite.modules.online_sales_app.annotation.XcxCustom;
import io.nuite.modules.online_sales_app.annotation.XcxLogin;
import io.nuite.modules.online_sales_app.entity.CustomerUserInfo;
import io.nuite.modules.online_sales_app.entity.OrderCartEntity;
import io.nuite.modules.online_sales_app.entity.OrderEntity;
import io.nuite.modules.online_sales_app.entity.OrderProductsEntity;
import io.nuite.modules.online_sales_app.entity.UserDeliveryInfo;
import io.nuite.modules.online_sales_app.service.CustomerUserInfoService;
import io.nuite.modules.online_sales_app.service.OnlineOrderService;
import io.nuite.modules.online_sales_app.service.OrderCartService;
import io.nuite.modules.online_sales_app.utils.OrderStatusEnum;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.sr_base.entity.GoodsShoesEntity;
import io.nuite.modules.sr_base.service.BaseUserService;
import io.nuite.modules.sr_base.service.GoodsShoesService;
import io.nuite.modules.sr_base.utils.ConfigUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@RequestMapping("/online/miniapp/order")
@Api(tags = "线上销售APP订单", description = "订单相关接口")
public class OnlineOrderController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OnlineOrderService onlineOrderService;

    @Autowired
    private ConfigUtils configUtils;
    
    @Autowired
    private GoodsShoesService goodsShoesService;
    
    @Autowired
    private BaseUserService baseUserService;
    
    @Autowired
    private CustomerUserInfoService customerUserInfoService;
    
    @Autowired
    private OrderCartService orderCartService;
  
    
    



    @XcxLogin
    @PostMapping("submitOrder")
    @ApiOperation(value = "提交订单", notes = "包括从购物车提交和直接购买，从购物车提交时，需要在shoesDataBuyCountList参数中加入购物车序号shoppingCartSeq")
    public R submitOrder(@ApiIgnore @XcxCustom CustomerUserInfo customerUserInfo,
                         @ApiParam("购买商品列表(包含shoesDataSeq,buyCount,openIDLinks的json数组)") @RequestParam(value = "shoesDataBuyCountList") String shoesDataBuyCountList,
                         @ApiParam("提交类型(1:购物车结算 2：直接购买)") @RequestParam("submitType") Integer submitType,
                         @ApiParam("订单价格") @RequestParam("orderPrice") BigDecimal orderPrice,
                         @ApiParam("收货地址序号") @RequestParam("userDeliverySeq") Integer userDeliverySeq,
                         @ApiParam("是否自取") @RequestParam("selfPick") Boolean selfPick,
                         @ApiParam("留言") @RequestParam(value = "suggestion", required = false) String suggestion,
                         HttpServletRequest request) {
        try {

            //订单号
            int random = (int) (Math.random() * 900) + 100; //三位随机数
            Date nowDate = new Date();
            String orderNum = nowDate.getTime() + "" + random;

			/* 涉及到需要修改的表：
			 YHSR_OP_ShoppingCart（购物车表） 		删除被提交订单的购物车
			 YHSR_OP_Order (订单信息表)				新增一条订单信息
			 YHSR_OP_OrderProducts（订单关联产品表） 将购买的商品列表分多条存储到这里
			 */
            //1.库存变动List
            List<Map<String, Integer>> stockChangeList = new ArrayList<Map<String, Integer>>();
            Map<String, Integer> stockChangeMap;

            //2.购物车序号List
            List<Integer> shoppingCartSeqList = new ArrayList<Integer>();

            //4.订单关联产品表对象List
            List<OrderProductsEntity> orderProductsList = new ArrayList<OrderProductsEntity>();
            OrderProductsEntity orderProductsEntity;

            JSONArray jsonArray = JSONArray.fromObject(shoesDataBuyCountList);
            JSONObject jsonObject;
            for (int i = 0; i < jsonArray.size(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                //1.库存变动
                stockChangeMap = new HashMap<>();
                stockChangeMap.put("shoesDataSeq", jsonObject.getInt("shoesDataSeq"));
                stockChangeMap.put("changNum", -jsonObject.getInt("buyCount"));
                stockChangeList.add(stockChangeMap);

                //2.购物车序号
                if (submitType == 1) {
                    shoppingCartSeqList.add(jsonObject.getInt("shoppingCartSeq"));
                }

                //4.订单关联产品表对象
                orderProductsEntity = new OrderProductsEntity();
                orderProductsEntity.setShoesDataSeq(jsonObject.getInt("shoesDataSeq"));
                orderProductsEntity.setBuyCount(jsonObject.getInt("buyCount"));
                if (jsonObject.containsKey("openIDLinks") && !jsonObject.getString("openIDLinks").equals("")) {
                    orderProductsEntity.setOpenIDLinks(jsonObject.getString("openIDLinks"));
                }
                orderProductsEntity.setInputTime(nowDate);
                orderProductsEntity.setDel(0);
                orderProductsList.add(orderProductsEntity);
            }

            //3.订单对象
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setUserSeq(customerUserInfo.getSeq().intValue());
            orderEntity.setOrderNum(orderNum);
            orderEntity.setOrderPrice(orderPrice);
            orderEntity.setPaid(BigDecimal.valueOf(0));
            orderEntity.setOrderStatus(OrderStatusEnum.ORDSTATUS_ZERO.getValue());
            orderEntity.setCompanySeq(customerUserInfo.getCompanySeq().intValue());
            orderEntity.setShopSeq(customerUserInfo.getShopSeq() == null ? null : customerUserInfo.getShopSeq().intValue());

            //查询、拼接收货人信息
            UserDeliveryInfo userDeliveryInfo = onlineOrderService.getUserDeliveryInfoBySeq(userDeliverySeq);
            orderEntity.setReceiverName(userDeliveryInfo.getRecipientsName());
            orderEntity.setTelephone(userDeliveryInfo.getTelephone());
            orderEntity.setFullAddress(userDeliveryInfo.getAddress());

            orderEntity.setInputTime(nowDate);
            orderEntity.setSuggestion(suggestion);
            orderEntity.setSelfPick((selfPick != null && selfPick) ? 1 : 0);
            orderEntity.setDel(0);

            Integer orderSeq;
            try {
                orderSeq = onlineOrderService.submitOrder(stockChangeList, shoppingCartSeqList, orderEntity, orderProductsList);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                if (e.getMessage().equals("库存不足")) {
                    return R.error(HttpStatus.SC_FORBIDDEN, "提交订单失败，库存不足");
                } else {
                    return R.error(HttpStatus.SC_FORBIDDEN, "提交订单失败");
                }
            }
            List<Integer> list = new ArrayList<Integer>();
            list.add(orderSeq);
            return R.ok(list).put("msg", "提交订单成功，订单号：" + orderNum);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }


    @XcxLogin
    @PostMapping("cancelOrder")
    @ApiOperation("取消订单")
    public R cancelOrder(@ApiIgnore @RequestAttribute("userSeq") Integer userSeq,
                         @ApiParam("订单编号") @RequestParam("orderSeq") Integer orderSeq,
                         HttpServletRequest request) {


            /*涉及到需要修改的表：
             YHSR_OP_Order (订单信息表)				修改订单状态
             **/

        try {
            onlineOrderService.cancelOrder(orderSeq);
            return R.ok("取消订单成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error(HttpStatus.SC_FORBIDDEN, "取消订单失败");
        }
    }

    @XcxLogin
    @PostMapping("gotShipOrder")
    @ApiOperation("确认收货")
    public R gotShipOrder(@ApiIgnore @RequestAttribute("userSeq") Integer userSeq,
                          @ApiParam("订单编号") @RequestParam("orderSeq") Integer orderSeq,
                          HttpServletRequest request) {


            /*涉及到需要修改的表：
             YHSR_OP_Order (订单信息表)				修改订单状态
             **/

        try {
            onlineOrderService.shipGotOrder(orderSeq);
            return R.ok("收货成功");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error(HttpStatus.SC_FORBIDDEN, "收货失败");
        }
    }


    @XcxLogin
    @PostMapping("deleteOrder")
    @ApiOperation("删除订单")
    public R deleteOrder(@ApiIgnore @RequestAttribute("userSeq") Integer userSeq,
                         @ApiParam("订单编号") @RequestParam("orderSeq") Integer orderSeq,
                         HttpServletRequest request) {
        try {

            /**涉及到需要修改的表：
             YHSR_OP_Order (订单信息表)				删除订单
             YHSR_OP_OrderProducts（订单关联产品表）  删除购买的商品列表
             **/
            onlineOrderService.deleteOrder(orderSeq);
            return R.ok("删除订单成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error(HttpStatus.SC_FORBIDDEN, "删除订单失败");
        }
    }


    /**
     * 订单列表
     */
    @XcxLogin
    @GetMapping("orderList")
    @ApiOperation("订单列表")
    public R orderList(@ApiIgnore @XcxCustom CustomerUserInfo customerUserInfo,
                       @ApiParam("订单状态") @RequestParam(value = "orderStatus", required = false, defaultValue = "-1") Integer orderStatus,
                       @ApiParam("起始条数") @RequestParam("start") Integer start,
                       @ApiParam("总条数") @RequestParam("num") Integer num) {
        try {
            //分页查询订单列表
            List<Map<String, Object>> orderList = onlineOrderService.getOrderListByUserSeq(customerUserInfo.getSeq().intValue(), orderStatus, start, num);

            List<OrderProductsEntity> orderProductsList;
            int shoesTotalNum;
            List<String> shoesImgList;
            GoodsShoesEntity goodsShoesEntity;
            for (Map<String, Object> orderMap : orderList) {

                //订单关联商品列表
                orderProductsList = onlineOrderService.getOrderProductsListByOrderSeq((Integer) orderMap.get("seq"));
                shoesTotalNum = 0;
                shoesImgList = new ArrayList<String>();
                for (OrderProductsEntity orderProductsEntity : orderProductsList) {
                    shoesTotalNum += orderProductsEntity.getBuyCount();
                    //根据shoesDateSeq获取鞋子的图片(可能重复)
                    goodsShoesEntity = onlineOrderService.getGoodsShoesByShoesDateSeq(orderProductsEntity.getShoesDataSeq());
                    shoesImgList.add(getGoodsShoesPictureUrl(goodsShoesEntity.getGoodID()) + goodsShoesEntity.getImg1());
                }

                // 订单状态名称
                orderMap.put("statusName", OrderStatusEnum.get((Integer) orderMap.get("orderStatus")).getCustomerName());
                // 购买商品总数
                orderMap.put("shoesTotalNum", shoesTotalNum);
                // 订单不同尺码鞋子图片
                orderMap.put("shoesImgList", shoesImgList);
            }
            return R.ok(orderList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }

    }


    /**
     * 订单详情
     */
    @XcxLogin
    @GetMapping("orderDetail")
    @ApiOperation("订单详情")
    public R orderDetail(@ApiIgnore @XcxCustom CustomerUserInfo customerUserInfo,
                         @ApiParam("订单序号") @RequestParam("seq") Integer seq) {
        try {
            //查询订单详情
            Map<String, Object> orderMap = onlineOrderService.getOrderBySeq(seq, customerUserInfo.getSeq());

            //订单关联商品列表
            List<Map<String, Object>> orderProductsList = onlineOrderService.getOrderProductsDetail((Integer) orderMap.get("seq"));
            for (Map<String, Object> orderProductsMap : orderProductsList) {
                orderProductsMap.put("img", getGoodsShoesPictureUrl(orderProductsMap.get("goodID").toString()) + orderProductsMap.get("img"));
            }
            orderMap.put("orderProductsList", orderProductsList);
       
            
            
            orderMap.put("orderShoppingCartMapList", orderCartService.getCartDetail(seq));
            
            
            // 订单状态名称
            orderMap.put("statusName", OrderStatusEnum.get((Integer) orderMap.get("orderStatus")).getCustomerName());

            return R.ok(orderMap);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    @XcxLogin
    @PostMapping("payOrder")
    @ApiOperation(value = "订单支付", notes = "支付接口主要作用为下单未支付的订单提供支付功能")
    public R submitOrder(@ApiIgnore @XcxCustom CustomerUserInfo customerUserInfo,
                         @ApiParam("订单序号") @RequestParam("seq") Integer seq,
                         HttpServletRequest request) {
        if (seq == null) {
            return R.error("参数错误");
        }
        String customerIp = IPUtils.getIpAddr(request);
        try {
            WxPayMpOrderResult payOrderResult = onlineOrderService.createWxpayOrder(seq, customerUserInfo, customerIp);
            return R.ok().put("payOrderResult", payOrderResult);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error(e.getMessage());
        }

    }

    /**
     * 订单支付状态获取
     */
    @XcxLogin
    @GetMapping("payStatus")
    @ApiOperation("获取订单是否已经支付完成")
    public R orderPayStatus(@ApiIgnore @XcxCustom CustomerUserInfo customerUserInfo,
                            @ApiParam("订单序号") @RequestParam("seq") Integer seq) {
        try {
            //查询订单详情
            Map<String, Object> orderMap = onlineOrderService.getOrderBySeq(seq, customerUserInfo.getSeq());
            int orderStatus = Integer.valueOf(orderMap.get("orderStatus").toString());
            if (orderStatus != OrderStatusEnum.ORDSTATUS_ZERO.getValue() && orderStatus != OrderStatusEnum.ORDSTATUS_FOUR.getValue()) {
                return R.ok().put("status", 1);
            } else {
                return R.ok().put("status", 0);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    /**
     * 获取订单消磁二维码
     * 门店取货出门使用
     */
    @XcxLogin
    @GetMapping("cancelQRCode")
    @ApiOperation("获取订单消磁二维码")
    public R cancelQRCode(@ApiIgnore @XcxCustom CustomerUserInfo customerUserInfo,
                          @ApiParam("订单序号") @RequestParam("seq") Integer seq) {
        try {
            String orderNumber = onlineOrderService.getCancelQRCode(seq, customerUserInfo.getSeq(), customerUserInfo.getOpenId());
            String codeUrl = baseDir() + "ordertemp/" + orderNumber + ".jpg";
            //查询订单详情
            return R.ok().put("url", codeUrl);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            if (e instanceof RRException) {
                return R.error(-1, ((RRException) e).getMsg());
            }
            return R.error("服务器异常");
        }
    }

    @RequestMapping("payNotify")
    public String payNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
            onlineOrderService.wxPayNotify(xmlResult);
            logger.error("支付通知成功");
            return WxPayNotifyResponse.success("处理成功!");
        } catch (Exception e) {
            logger.error("微信回调结果异常,异常原因{}", e.getMessage());
            return WxPayNotifyResponse.fail(e.getMessage());
        }
    }


    //基础库访问目录
    private String baseDir() {
        return configUtils.getPictureBaseUrl() + "/" + configUtils.getBaseDir() + "/";
    }

    //鞋子基本信息图片路径
    private String getGoodsShoesPictureUrl(String folder) {
        return baseDir() + configUtils.getGoodsShoes() + "/" + folder + "/";
    }
    @XcxLogin
    @PostMapping("orderSubmit")
    @ApiOperation(value = "提交订单2.0")
    public R orderSubmit(@ApiIgnore @XcxCustom CustomerUserInfo customerUserInfo,
            @ApiParam("购买商品列表(包含shoesDataSeq,buyCount,openIDLinks的json数组)") @RequestParam(value = "shoesDataBuyCountList") String shoesDataBuyCountList,
            @ApiParam("提交类型(1:购物车结算 2：直接购买)") @RequestParam("submitType") Integer submitType,
            @ApiParam("订单价格") @RequestParam("orderPrice") BigDecimal orderPrice,
            @ApiParam("收货地址序号") @RequestParam("userDeliverySeq") Integer userDeliverySeq,
            @ApiParam("是否自取") @RequestParam("selfPick") Boolean selfPick,
            @ApiParam("留言") @RequestParam(value = "suggestion", required = false) String suggestion,
            @ApiParam("希望到货时间") @RequestParam(value = "expectedTime", required = false) String expectedTime,
            @ApiParam("是否贴牌") @RequestParam(value = "isOem", required = false) Integer isOem,
            @ApiParam("贴配地址") @RequestParam(value = "oemUrl", required = false) String oemUrl,
            @ApiParam("备注") @RequestParam(value = "remark", required = false) String remark,
            HttpServletRequest request) {
    	 try {
    		  //订单号
             int random = (int) (Math.random() * 900) + 100; //三位随机数
             Date nowDate = new Date();
             String orderNum = nowDate.getTime() + "" + random;
             //2.购物车序号List
             List<Integer> shoppingCartSeqList = new ArrayList<Integer>();
    		 
             JSONArray jsonArray = JSONArray.fromObject(shoesDataBuyCountList);
             JSONObject jsonObject;
             for (int i = 0; i < jsonArray.size(); i++) {
                 jsonObject = jsonArray.getJSONObject(i);
                 //2.购物车序号
                 shoppingCartSeqList.add(jsonObject.getInt("shoppingCartSeq"));
             }
             //3.订单对象
             OrderEntity orderEntity = new OrderEntity();
             orderEntity.setUserSeq(customerUserInfo.getSeq().intValue());
             orderEntity.setRemark(remark);
             orderEntity.setIsOem(isOem);
             orderEntity.setOemUrl(oemUrl);
             SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
             orderEntity.setExpectedTime(sdf.parse(expectedTime));
             orderEntity.setOrderNum(orderNum);
             orderEntity.setOrderPrice(orderPrice);
             orderEntity.setPaid(BigDecimal.valueOf(0));
             orderEntity.setOrderStatus(OrderStatusEnum.ORDSTATUS_ZERO.getValue());
             orderEntity.setCompanySeq(customerUserInfo.getCompanySeq().intValue());
             orderEntity.setShopSeq(customerUserInfo.getShopSeq() == null ? null : customerUserInfo.getShopSeq().intValue());
             //查询、拼接收货人信息
             UserDeliveryInfo userDeliveryInfo = onlineOrderService.getUserDeliveryInfoBySeq(userDeliverySeq);
             orderEntity.setReceiverName(userDeliveryInfo.getRecipientsName());
             orderEntity.setTelephone(userDeliveryInfo.getTelephone());
             orderEntity.setFullAddress(userDeliveryInfo.getAddress());

             orderEntity.setInputTime(nowDate);
             orderEntity.setSuggestion(suggestion);
             orderEntity.setSelfPick((selfPick != null && selfPick) ? 1 : 0);
             orderEntity.setDel(0);
    		
             Integer orderSeq=onlineOrderService.orderSubmit(shoppingCartSeqList, orderEntity,nowDate);
            		 
             List<Integer> list = new ArrayList<Integer>();
             list.add(orderSeq);
             return R.ok(list).put("msg", "提交订单成功，订单号：" + orderNum);
         } catch (Exception e) {
             logger.error(e.getMessage(), e);
             return R.error("服务器异常");
         }
    
    	 
    }
    
    
    @XcxLogin
    @PostMapping("upload")
    @ApiOperation(value = "小程序上传图片")
    public R upload(HttpServletRequest request, HttpServletResponse response) {
    	 try {
    		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
    		 MultipartFile uf = multipartRequest.getFile("file");

    		 	String uploadUrl =getMeetingGoodsUploadUrl(request);
    		 	
    			// 存放目录
    			File fileDir = new File(uploadUrl);
    			// 如果目录不存在就创建
    			if (!fileDir.exists()) {
    				fileDir.mkdirs();
    			}
    			// 重新定义文件名
    			String fileName = System.currentTimeMillis() +".jpg";
    			// 上传文件
    			File file = new File(fileDir, fileName);
    			file.createNewFile();
    			uf.transferTo(file);
    			
    			String fileName1 = System.currentTimeMillis() + ".jpg";
    			fileName1=fileName1.replace("png", "jpg");
    			File file1 = new File(fileDir, fileName1);
    			file1.createNewFile();
    			Thumbnails.of(file).scale(1f).outputQuality(0.75f).outputFormat("jpg").toFile(file1);
    			Map<String, Object> map=new HashMap<String, Object>();
    			
    			String fileUrl=configUtils.getPictureBaseUrl() +"/" + configUtils.getOnlineSalesApp().getOnlineDir() +"/"+fileName1;
    			map.put("file", fileUrl);
    			map.put("fileName", fileName1);
    		 return R.ok().put("map", map);
         } catch (Exception e) {
             logger.error(e.getMessage(), e);
             return R.error("服务器异常");
         }
    	 
    }
    
    
    /**
     * 订单列表
     */
    @XcxLogin
    @GetMapping("onlineOrderList")
    @ApiOperation("订单列表")
    public R onlineOrderList(@ApiIgnore @XcxCustom CustomerUserInfo customerUserInfo,
                       @ApiParam("订单状态") @RequestParam(value = "orderStatus", required = false, defaultValue = "-1") Integer orderStatus,
                       @ApiParam("起始条数") @RequestParam("start") Integer start,
                       @ApiParam("总条数") @RequestParam("num") Integer num) {
        try {
        	String telephone=customerUserInfo.getTelephone();
        	Integer companySeq=customerUserInfo.getCompanySeq();
        	Integer  brandSeq=customerUserInfo.getBrandSeq();
        	//根据telephone查询用户是否为管理员
        	BaseUserEntity baseUserEntity=baseUserService.getUserByPhoneAndCompany(telephone, companySeq,brandSeq);
            //分页查询订单列表
            List<Map<String, Object>> orderList = onlineOrderService.getOrderListByUserSeq(customerUserInfo.getSeq().intValue(), orderStatus, start, num);
            	if(baseUserEntity!=null) {
            		//查询所有用户
            		List<Object> userSeqs=customerUserInfoService.getAllCustomerByCompanySeq(companySeq, brandSeq);
            		
            		orderList = onlineOrderService.getOrderListByComapnySeq(userSeqs, orderStatus, start, num);
            	}
            List<OrderCartEntity> OrderCartList;
            int shoesTotalNum;
            List<String> shoesImgList;
            GoodsShoesEntity goodsShoesEntity;
            for (Map<String, Object> orderMap : orderList) {

                //订单关联商品列表
            	OrderCartList = onlineOrderService.getOrderCartListByOrderSeq((Integer) orderMap.get("seq"));
                shoesTotalNum = 0;
                shoesImgList = new ArrayList<String>();
                for (OrderCartEntity orderCartEntity : OrderCartList) {
                    shoesTotalNum += orderCartEntity.getTotalSelectNum();
                    //根据shoesDateSeq获取鞋子的图片(可能重复)
                    goodsShoesEntity = goodsShoesService.selectById(orderCartEntity.getShoesSeq());
                    if(goodsShoesEntity.getImg1()!=null) {
                    	 shoesImgList.add(getGoodsShoesPictureUrl(goodsShoesEntity.getGoodID()) + goodsShoesEntity.getImg1());
                    }
                }

                // 订单状态名称
                orderMap.put("statusName", OrderStatusEnum.get((Integer) orderMap.get("orderStatus")).getCustomerName());
                // 购买商品总数
                orderMap.put("shoesTotalNum", shoesTotalNum);
                // 订单不同尺码鞋子图片
                orderMap.put("shoesImgList", shoesImgList);
            }
            return R.ok(orderList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }

    }
    
    /**
     * 订单列表
     */
    @XcxLogin
    @PostMapping("paySuccess")
    @ApiOperation("订单列表")
    public R paySuccess( @ApiParam("订单序号") @RequestParam("seq") Integer seq,@ApiParam("支付金额") @RequestParam("paidMoney") Double paidMoney) {
    	 try {	
    		 OrderEntity orderEntity=onlineOrderService.getOrderEntityBySeq(seq);
    		 orderEntity.setPaid(new BigDecimal(paidMoney));
    		 orderEntity.setOrderStatus(1);
    		 onlineOrderService.update(orderEntity);
    		 return R.ok();
    	  } catch (Exception e) {
              e.printStackTrace();
              logger.error(e.getMessage(), e);
              return R.error("服务器异常");
          }
    }
    
    
    protected String getMeetingGoodsUploadUrl(HttpServletRequest request) {
		return orderOnlineUploadDir(request)  +"/";
	}
    
	//订货平台上传目录
	private String orderOnlineUploadDir(HttpServletRequest request) {
//		//tomcat实际路径
//		String path = request.getSession().getServletContext().getRealPath("/");
//		//上传项目路径
//		String uploadProject1 = path.substring(0, path.length() - 11) + configUtils.getPictureBaseUploadProject() +"/";
		String uploadProject = FileUtils.getWebAppsPath() + configUtils.getPictureBaseUploadProject() +"/";
		return uploadProject + configUtils.getOnlineSalesApp().getOnlineDir() + "/";
	}
}
