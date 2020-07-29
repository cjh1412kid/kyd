package io.nuite.modules.order_platform_app.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import io.nuite.modules.system.service.SysMeetingOrderProductService;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import io.nuite.common.utils.R;
import io.nuite.modules.order_platform_app.annotation.Login;
import io.nuite.modules.order_platform_app.annotation.LoginUser;
import io.nuite.modules.order_platform_app.entity.ExpressCompanyEntity;
import io.nuite.modules.order_platform_app.entity.MeetingGoodsEntity;
import io.nuite.modules.order_platform_app.entity.MeetingOrderCartDetailEntity;
import io.nuite.modules.order_platform_app.entity.MeetingOrderCartDistributeBoxEntity;
import io.nuite.modules.order_platform_app.entity.MeetingOrderCartEntity;
import io.nuite.modules.order_platform_app.entity.MeetingShoppingCartDetailEntity;
import io.nuite.modules.order_platform_app.entity.MeetingShoppingCartEntity;
import io.nuite.modules.order_platform_app.entity.ReceiverInfoEntity;
import io.nuite.modules.order_platform_app.service.MeetingGoodsService;
import io.nuite.modules.order_platform_app.service.MeetingOrderCartDetailService;
import io.nuite.modules.order_platform_app.service.MeetingOrderCartDistributeBoxService;
import io.nuite.modules.order_platform_app.service.MeetingOrderCartService;
import io.nuite.modules.order_platform_app.service.MeetingOrderExcelService;
import io.nuite.modules.order_platform_app.service.MeetingOrderService;
import io.nuite.modules.order_platform_app.service.MeetingShoppingCartService;
import io.nuite.modules.order_platform_app.service.ReceiverInfoService;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.sr_base.entity.GoodsColorEntity;
import io.nuite.modules.sr_base.service.GoodsColorService;
import io.nuite.modules.system.entity.MeetingEntity;
import io.nuite.modules.system.entity.MeetingOrderEntity;
import io.nuite.modules.system.entity.MeetingOrderProductEntity;
import io.nuite.modules.system.service.ExpressCompanyService;
import io.nuite.modules.system.service.MeetingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 订货平台4期 - 订货会订单    再次配码需求改版后接口 （修改为类似Excel填写方式）
 * @author yy
 * @date 2018-04-19 13:47
 */
@RestController
@RequestMapping("/order/app/meetingOrderExcel")
@Api(tags = "订货平台4期 - 订货会订单    再次配码需求改版后接口 （修改为类似Excel填写方式）")
public class MeetingOrderExcelController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MeetingOrderExcelService meetingOrderExcelService;
    
    @Autowired
    private ReceiverInfoService receiverInfoService;

    @Autowired
    private MeetingService meetingService;
    
    @Autowired
    private MeetingGoodsService meetingGoodsService;
    
    @Autowired
    private GoodsColorService goodsColorService;
    
    @Autowired
    private MeetingShoppingCartService meetingShoppingCartService;

    @Autowired
    private MeetingOrderService meetingOrderService;

    @Autowired
    private MeetingOrderCartService meetingOrderCartService;
    
    @Autowired
    private ExpressCompanyService expressCompanyService;
    
    @Autowired
    private MeetingOrderCartDistributeBoxService meetingOrderCartDistributeBoxService;
    
    @Autowired
    private MeetingOrderCartDetailService meetingOrderCartDetailService;
    
    @Autowired
    private GoodsColorService GoodsColorService;

    @Autowired
    private SysMeetingOrderProductService sysMeetingOrderProductService;
    
    /**
     * 提交订货会订单
     */
    @Login
    @PostMapping("submitMeetingOrder")
    @ApiOperation(value = "提交订货会订单")
    public R submitMeetingOrder(@ApiIgnore @LoginUser BaseUserEntity loginUser,
                         @ApiParam("收货人seq") @RequestParam("receiverInfoSeq") Integer receiverInfoSeq,
                         @ApiParam("购物车列表（逗号分隔）") @RequestParam(value = "meetingShoppingCartSeqs") List<Integer> meetingShoppingCartSeqs,
                         @ApiParam("物流seq")@RequestParam("expressSeq")Integer expressSeq,
                         HttpServletRequest request) {
        try {
    		if(isFactoryUser(loginUser)) {
    			return R.error("管理员无法订货");
    		}
    		
    		// 当前订货会
    		MeetingEntity meetingEntity = meetingService.getNowMeetingEntity(loginUser.getCompanySeq());
    		if(meetingEntity == null) {
    			return R.error("没有正在进行的订货会");
    		}
    		
    		// 判断有无本次订货会权限
    		if(!hasNowMeetingPermission(loginUser, meetingEntity)) {
    			return R.error("没有本次订货会权限");
    		}
    		
    		
            //订单号
            int random = (int) (Math.random() * 900) + 100; //三位随机数
            Date nowDate = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            String orderNum = df.format(nowDate) + meetingEntity.getSeq() + loginUser.getSeq() + random;

            
            /**涉及到需要修改的表：
             YHSR_OP_ShoppingCart（购物车表） 		删除被提交订单的购物车
             YHSR_OP_Order (订单信息表)				新增一条订单信息
             YHSR_OP_OrderProducts（订单关联产品表） 将购买的商品列表分多条存储到这里
             **/

            //3.订单关联产品表对象List
            List<MeetingOrderProductEntity> orderProductsList = new ArrayList<MeetingOrderProductEntity>();
            for (Integer meetingShoppingCartSeq : meetingShoppingCartSeqs) {
            	// 判断是否已配码，没配码的购物车无法生成订单
            	MeetingShoppingCartEntity meetingShoppingCartEntity = meetingShoppingCartService.selectById(meetingShoppingCartSeq);
            	if(meetingShoppingCartEntity.getIsAllocated() != 1) {
                    return R.error(HttpStatus.SC_FORBIDDEN, "失败，存在没有配码的货品");
            	}
            	
            	//根据购物车序号，查询购物车详细商品
            	List<MeetingShoppingCartDetailEntity> meetingShoppingCartDetailEntityList = meetingOrderExcelService.getMeetingShoppingCartDetailListByShoppingCartSeq(meetingShoppingCartSeq);
            	for(MeetingShoppingCartDetailEntity meetingShoppingCartDetailEntity : meetingShoppingCartDetailEntityList) {
            		
//	            	Seq					int	0	0	0	-1	0	0		0	0	0	0	订货会订单商品序号		-1			
//	            	MeetingOrder_Seq	int	0	0	0	0	0	0		0	0	0	0	订货会订单序号		0			
//	            	MeetingGoods_Seq	int	0	0	0	0	0	0		0	0	0	0	订货会商品序号		0			
//	            	Color_Seq			int	0	0	-1	0	0	0		0	0	0	0	颜色序号		0			
//	            	Size_Seq			int	0	0	-1	0	0	0		0	0	0	0	尺码序号		0			
//	            	BuyCount			int	0	0	-1	0	0	0	((0))	0	0	0	0	购买数量		0			
//	            	InputTime			datetime	0	0	-1	0	0	0	(getdate())	0	0	0	0	创建时间		0			
//	            	Del					int	0	0	-1	0	0	0	((0))	0	0	0	0	是否删除（1删除 0未删除）		0			
//	            	Cancel				int	0	0	0	0	0	0	((0))	0	0	0	0	是否取消（1取消 0未取消）		0	
            		
	            	MeetingOrderProductEntity orderProductsEntity = new MeetingOrderProductEntity();
//	            	orderProductsEntity.setMeetingOrderSeq(meetingOrderSeq);
	            	orderProductsEntity.setMeetingGoodsSeq(meetingShoppingCartDetailEntity.getMeetingGoodsSeq());
	            	orderProductsEntity.setColorSeq(meetingShoppingCartDetailEntity.getColorSeq());
	            	orderProductsEntity.setSize(meetingShoppingCartDetailEntity.getSize());
	            	orderProductsEntity.setBuyCount(meetingShoppingCartDetailEntity.getSelectNum());
	            	orderProductsEntity.setCancel(0);
	                orderProductsEntity.setInputTime(nowDate);
	                orderProductsEntity.setDel(0);
	                orderProductsList.add(orderProductsEntity);
            	}
            }
            
            
//          Seq				int	0	0	0	-1	0	0		0	0	0	0	订单序号		-1			
//          OrderNum		varchar	255	0	-1	0	0	0		0	0	0	0	订单号	Chinese_PRC_CI_AS	0			
//          Meeting_Seq		int	0	0	-1	0	0	0		0	0	0	0	订货会序号		0			
//          User_Seq		int	0	0	-1	0	0	0		0	0	0	0	定货方序号		0			
//          Company_Seq		int	0	0	0	0	0	0		0	0	0	0	公司序号		0			
//          ReceiverName	varchar	50	0	-1	0	0	0		0	0	0	0	收件人	Chinese_PRC_CI_AS	0			
//          Telephone		varchar	50	0	-1	0	0	0		0	0	0	0	联系电话	Chinese_PRC_CI_AS	0			
//          Address			varchar	50	0	-1	0	0	0		0	0	0	0	收件地址	Chinese_PRC_CI_AS	0			
//          InputTime		datetime	0	0	0	0	0	0	(getdate())	0	0	0	0	创建时间		0			
//          Del				int	0	0	0	0	0	0	((0))	0	0	0	0	删除状态（1,已删 0，未删）		0	
            //2.订单对象
            MeetingOrderEntity orderEntity = new MeetingOrderEntity();
            orderEntity.setOrderNum(orderNum);
            orderEntity.setMeetingSeq(meetingEntity.getSeq());
            orderEntity.setUserSeq(loginUser.getSeq());
            orderEntity.setCompanySeq(loginUser.getCompanySeq());
            
            //查询、拼接收货人信息
            ReceiverInfoEntity receiverInfoEntity = receiverInfoService.getReceiverInfoBySeq(receiverInfoSeq);
            orderEntity.setReceiverName(receiverInfoEntity.getReceiverName());
            orderEntity.setTelephone(receiverInfoEntity.getTelephone());
            orderEntity.setAddress(receiverInfoEntity.getProvince() + receiverInfoEntity.getCity() + receiverInfoEntity.getDistrict() + receiverInfoEntity.getDetailAddress());

            orderEntity.setInputTime(nowDate);
            orderEntity.setDel(0);

            //物流信息
            ExpressCompanyEntity expressCompanyEntity=expressCompanyService.selectById(expressSeq);
            orderEntity.setExpressName(expressCompanyEntity.getName());
            orderEntity.setExpressPhone(expressCompanyEntity.getPhone());
            
            //提交订单
            Integer orderSeq;
            try {
                orderSeq = meetingOrderExcelService.submitMeetingOrder(meetingShoppingCartSeqs, orderEntity, orderProductsList, nowDate);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage(), e);
                return R.error(HttpStatus.SC_FORBIDDEN, "提交订单失败");
            }

//            //发送手机推送消息
//            List<BaseUserEntity> userList = null;     //工厂所有接单员
//            try {
//                //给接单员推送
//                userList = baseService.getUserByPermission(loginUser, new String[]{PermissionKeys.ORDER_RECEIVE});
//                List<String> aliasList = new ArrayList<String>();
//                for (BaseUserEntity user : userList) {
//                    aliasList.add(user.getAccountName());
//                }
//                jPushUtils.sendPush(aliasList, "您有新订单，请及时接单处理！订单号：" + orderNum, "0");
//            } catch (Exception e) {
//                e.printStackTrace();
//                logger.error("提交订单后，发送手机推送消息失败" + e.getMessage(), e);
//            }
//
//            //发送网页推送消息
//            try {
//                //给接单员推送
//                messageEventHandler.sendHandleOrderEvent(loginUser.getCompanySeq ().toString(), PermissionKeys.ORDER_RECEIVE, "您有新订单，请及时接单处理！订单号：" + orderNum, orderSeq, 0);
//            } catch (Exception e) {
//                e.printStackTrace();
//                logger.error("提交订单后，发送网页推送消息失败" + e.getMessage(), e);
//            }
//
//            //保存推送消息
//            try {
//                for (BaseUserEntity user : userList) {
//                    pushRecordService.addPushRecord(loginUser.getSeq(), user.getSeq(), user.getAccountName(), 1, orderSeq, "您有新订单，请及时接单处理！订单号：" + orderNum);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                logger.error("提交订单后，保存推送消息失败" + e.getMessage(), e);
//            }
            
            List<Integer> list = new ArrayList<Integer>();
            list.add(orderSeq);
            return R.ok(list).put("msg", "提交订单成功");

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }
    
    
    
    
    

    /**
     * 订货会订单列表
     */
    @Login
    @GetMapping("meetingOrderList")
    @ApiOperation("订货会订单列表")
    public R meetingOrderList(@ApiIgnore @LoginUser BaseUserEntity loginUser,
                       @ApiParam("订货会序号") @RequestParam("meetingSeq") Integer meetingSeq,
                       @ApiParam("起始条数") @RequestParam("start") Integer start,
                       @ApiParam("总条数") @RequestParam("num") Integer num) {
    	
    	Date startTaskTime = new Date();
		logger.info("MeetingOrderExcelController-订货会订单列表meetingOrderList，被调用: " + startTaskTime);

    	try {
        	
            List<Map<String, Object>> orderList = new ArrayList<Map<String, Object>>();
            
            List<MeetingOrderEntity> orderEntityList;
            //如果是工厂用户，按照公司编号companySeq查询订单列表
            if (isFactoryUser(loginUser)) {
                orderEntityList = meetingOrderExcelService.getCompanyMeetingOrderList(meetingSeq, start, num);
            } else { //如果是订货方，按照用户编号userSeq查询订单列表
                orderEntityList = meetingOrderExcelService.getUserMeetingOrderList(loginUser.getSeq(), meetingSeq, start, num);
            }
            
            for (MeetingOrderEntity orderEntity : orderEntityList) {
            	Map<String, Object> orderMap = new HashMap<String, Object>();
            	
                orderMap.put("orderSeq", orderEntity.getSeq());
                orderMap.put("orderNum", orderEntity.getOrderNum());
                orderMap.put("inputTime", orderEntity.getInputTime());
                BigDecimal totalMoney = meetingOrderCartService.selectOrderMoney(orderEntity.getSeq());
                BigDecimal cancelMoney = sysMeetingOrderProductService.selectCancelMoneyByOrderSeq(orderEntity.getSeq());
                if(totalMoney != null) {
                    if(cancelMoney != null) {
                        orderMap.put("totalMoney", totalMoney.subtract(cancelMoney));
                    }else {
                        orderMap.put("totalMoney", totalMoney);
                    }

                }else {
                    orderMap.put("totalMoney", "");
                }


                //订单用户信息
                Map<String, Object> userInfo = meetingOrderExcelService.getUserMapByUserSeq(orderEntity.getUserSeq());
                orderMap.put("userInfo", userInfo);
                
                
                //根据订单序号Seq，查询订单关联的商品，获取商品图片
                List<MeetingOrderProductEntity> orderProductList = meetingOrderExcelService.getMeetingOrderProductListByMeetingOrderSeq(orderEntity.getSeq());

                int totalNum = 0;
                Set<String> shoesImgSet = new LinkedHashSet<String>();
                int calcelColorNums = 0;
                Set<Integer> cancelColorSeqSet = new TreeSet<Integer>();
                int cancelBuyCount = 0;
                //获取orderProducts中shoesDateSeq获取每个鞋子的图片，可能重复
                for (MeetingOrderProductEntity orderProductEntity : orderProductList) {
                	totalNum += orderProductEntity.getBuyCount();
                    MeetingGoodsEntity meetingGoodsEntity = meetingGoodsService.selectById(orderProductEntity.getMeetingGoodsSeq());
                    if (meetingGoodsEntity != null) {
                    	shoesImgSet.add(getMeetingGoodsPictureUrl(meetingGoodsEntity.getGoodID()) + meetingGoodsEntity.getImg());
                    }
                    if(orderProductEntity.getCancel() == 1) {
                    	if(!cancelColorSeqSet.contains(orderProductEntity.getColorSeq())) {
                        	calcelColorNums ++;
                        	cancelColorSeqSet.add(orderProductEntity.getColorSeq());
                    	}
                    	cancelBuyCount += orderProductEntity.getBuyCount();
                    }
                }
                orderMap.put("totalNum", totalNum-cancelBuyCount);  //订单中鞋子总数
                orderMap.put("shoesImgSet", shoesImgSet);
                orderMap.put("calcelColorNums", calcelColorNums);  // 被取消颜色数
                orderMap.put("cancelBuyCount", cancelBuyCount);  // 被取消购买数量
                
                orderList.add(orderMap);
            }
            
    		Date endTaskTime = new Date();
    		logger.info("###MeetingOrderExcelController-订货会订单列表meetingOrderList，执行完毕返回: " + endTaskTime + "本次总计耗时:" + (endTaskTime.getTime() - startTaskTime.getTime()));
 
            return R.ok(orderList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }

    }


    
    
    /**
     * 订货会订单详情
     */
    @Login
    @GetMapping("meetingOrderDetail")
    @ApiOperation("订货会订单详情")
    public R meetingOrderDetail(@ApiIgnore @LoginUser BaseUserEntity loginUser,
                         @ApiParam("订货会订单序号") @RequestParam("meetingOrderSeq") Integer meetingOrderSeq) {
        try {
            //查询订单详情
            Map<String, Object> orderMap = meetingOrderExcelService.getMeetingOrderMapBySeq(meetingOrderSeq);
            
            //订单用户信息
            Map<String, Object> userInfo = meetingOrderExcelService.getUserMapByUserSeq((Integer) orderMap.get("userSeq"));
            orderMap.put("userInfo", userInfo);


            
            /*组装商品详情列表*/
            List<Map<String, Object>> orderProductsList = new ArrayList<Map<String, Object>>();
//            [
//            {goodId:9502, totalNum: 1000, img:".../dhge.img", cancelColorNum: 3,  
//              colorDetailList:[ {colorName:"红色", isCancelled:1 ,sizeAndNumList:[{size:35, num: 100},{size:36, num:200},...]}, 
//            					  {colorName:"绿色", isCancelled:1 ,sizeAndNumList:[{size:35, num: 200},{size:36, num:300},...]}, ...]     
//        	  },
//            {goodId:9502, totalNum: 1000, img:".../dhge.img", cancelColorNum: 3,  
//          	colorDetailList:[ {colorName:"红色", isCancelled:1 ,sizeAndNumList:[{size:35, num: 100},{size:36, num:200},...]}, 
//			  						{colorName:"绿色", isCancelled:1 ,sizeAndNumList:[{size:35, num: 200},{size:36, num:300},...]}, ...]     
//			   },
//            {...}
//            ]
            //1.获取 订单 所有鞋子seq
            List<Object> meetingGoodsSeqList = meetingOrderExcelService.getMeetingOrderGoodsSeqList(meetingOrderSeq);
            for(Object meetingGoodsSeq : meetingGoodsSeqList) {
            	Map<String, Object> goodIdDetailMap = new HashMap<String, Object>();
            	MeetingGoodsEntity meetingGoodsEntity = meetingGoodsService.selectById((Integer)meetingGoodsSeq);
            	
            	goodIdDetailMap.put("meetingGoodsSeq", meetingGoodsEntity.getSeq());
            	goodIdDetailMap.put("goodId", meetingGoodsEntity.getGoodID());
            	goodIdDetailMap.put("img", getMeetingGoodsPictureUrl(meetingGoodsEntity.getGoodID()) +  meetingGoodsEntity.getImg());



            	//2.查询 订单 此商品 所有购买的颜色seq、取消状态
            	List<Map<String, Object>> colorDetailList = meetingOrderExcelService.getMeetingOrderGoodsColorDetailList(meetingOrderSeq, (Integer)meetingGoodsSeq);

            	Integer totalNum = 0;
            	Integer cancelColorNum = 0;   //被取消颜色数
            	Integer cancelBuyCount = 0;	  //被取消购买数量
            	for(Map<String, Object> colorDetailMap : colorDetailList) {
            		Integer colorSeq = (Integer)colorDetailMap.get("colorSeq");
            		GoodsColorEntity goodsColorEntity = goodsColorService.selectById(colorSeq);
            		colorDetailMap.put("colorName", goodsColorEntity.getName());
            		
            		Integer isCancelled = (Integer)colorDetailMap.get("isCancelled");
            		if(isCancelled == 1) {
            			cancelColorNum ++;
            		}
            		
            		//3. 查询 订单 此商品 此颜色所有购买的尺码、数量
            		List<Map<String, Object>> sizeAndNumList = meetingOrderExcelService.getMeetingOrderGoodsColorSizeNumDetailList(meetingOrderSeq, (Integer)meetingGoodsSeq, colorSeq);
            		for(Map<String, Object> sizeAndNumMap : sizeAndNumList) {
            			Integer buyCount = (Integer)sizeAndNumMap.get("buyCount");
            			totalNum += buyCount;
                		if(isCancelled == 1) {
                			cancelBuyCount += buyCount;
                		}
            		}
            		colorDetailMap.put("sizeAndNumList", sizeAndNumList);
            	}
            	
            	goodIdDetailMap.put("totalNum", totalNum);
            	goodIdDetailMap.put("cancelColorNum", cancelColorNum);
            	goodIdDetailMap.put("cancelBuyCount", cancelBuyCount);
            	goodIdDetailMap.put("colorDetailList", colorDetailList);
            	
            	orderProductsList.add(goodIdDetailMap);
            }
            List<MeetingOrderCartEntity> meetingCartEntities =  meetingOrderCartService.getListByOrderSeq((Integer)orderMap.get("orderSeq"));
            List<Map<String, Object>> distributeBoxs=new ArrayList<Map<String,Object>>();
            for (MeetingOrderCartEntity shoppingCartEntity:meetingCartEntities) {
            	Integer meetingShoppingCartSeq=shoppingCartEntity.getSeq();
            	List<MeetingOrderCartDistributeBoxEntity> meetingShoppingCartDistributeBoxEntities=meetingOrderCartDistributeBoxService.getMeetingShoppingCartDistributeBoxListByShoppingCartSeq(meetingShoppingCartSeq);
            	 List<Map<String, Object>> distributeBoxGoods=new ArrayList<Map<String,Object>>();
            	 Map<String, Object> distributeBoxGoodsMap=new HashMap<String, Object>();
            	for (MeetingOrderCartDistributeBoxEntity meetingShoppingCartDistributeBoxEntity : meetingShoppingCartDistributeBoxEntities) {
					Integer meetingShoppingCartDistributeBoxSeq=meetingShoppingCartDistributeBoxEntity.getSeq();
					Integer colorSeq =meetingShoppingCartDistributeBoxEntity.getColorSeq();//颜色seq
					GoodsColorEntity goodsColorEntity=GoodsColorService.selectById(colorSeq);
					List<MeetingOrderCartDetailEntity> meetingShoppingCartDetailEntities=meetingOrderCartDetailService.getListByMeetingShoppingCartDistributeBoxSeq(meetingShoppingCartDistributeBoxSeq);
					Map<String, Object> distributeBoxMap=new HashMap<String, Object>();
	            	distributeBoxMap.put("meetingShoppingCartDetailEntities", meetingShoppingCartDetailEntities);
	            	distributeBoxMap.put("color", goodsColorEntity.getName());
	            	distributeBoxMap.put("colorTotalNum", meetingShoppingCartDistributeBoxEntity.getColorTotalNum());
	            	distributeBoxGoods.add(distributeBoxMap);
	            	
            	}
            	Integer meetingGoodsSeq=shoppingCartEntity.getMeetingGoodsSeq();
            	MeetingGoodsEntity meetingGoodsEntity=meetingGoodsService.selectById(meetingGoodsSeq);
            	meetingGoodsEntity.setImg(getMeetingGoodsPictureUrl(meetingGoodsEntity.getGoodID()) +  meetingGoodsEntity.getImg());
            	meetingGoodsEntity.setTotalMoney(meetingOrderCartService.selectOrderDetailMoney(meetingGoodsEntity.getGoodID(),meetingOrderSeq));
            	distributeBoxGoodsMap.put("meetingGoodsEntity", meetingGoodsEntity);
            	distributeBoxGoodsMap.put("distributeBoxGoods", distributeBoxGoods);
            	distributeBoxGoodsMap.put("shoppingCartEntity", shoppingCartEntity);
            	distributeBoxs.add(distributeBoxGoodsMap);
            }
            orderMap.put("distributeBoxs", distributeBoxs);
            orderMap.put("orderProductsList", orderProductsList);
            
            return R.ok(orderMap);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }

    }

    @Login
    @GetMapping("/selectTotal")
    @ApiOperation("获取订单总数")
    public R selectTotal(@ApiIgnore @LoginUser BaseUserEntity userEntity,
                         @ApiParam("订货会序号") @RequestParam("meetingSeq") Integer meetingSeq) {
        try {
            Wrapper<MeetingOrderEntity> meetingOrderWrapper = new EntityWrapper<>();
            meetingOrderWrapper.eq("Meeting_Seq",meetingSeq);
            Integer totalOrderNum = meetingOrderService.selectCount(meetingOrderWrapper);
            List<MeetingOrderEntity> meetingOrderEntities = meetingOrderService.selectList(meetingOrderWrapper);
            List<Integer> meetingOrderSeqList = new ArrayList<>(10);
            for(int i = 0;i < meetingOrderEntities.size();i++) {
                meetingOrderSeqList.add(meetingOrderEntities.get(i).getSeq());
            }
            meetingOrderWrapper.groupBy("User_Seq").setSqlSelect("User_Seq");
            Wrapper<MeetingOrderCartEntity> meetingOrderCartWrapper = new EntityWrapper<>();
            meetingOrderCartWrapper.setSqlSelect("SUM(TotalSelectNum) AS totalGoodsNum").in("MeetingOrderSeq",meetingOrderSeqList).eq("Meeting_Seq",meetingSeq);
            Integer totalGoodsNum = (Integer) meetingOrderCartService.selectObj(meetingOrderCartWrapper);
            Map<String,Object> resultMap = new HashMap<>(10);
            //订货数量
            if(totalGoodsNum == null) {
                resultMap.put("totalGoodsNum",0);
            }else {
                resultMap.put("totalGoodsNum",totalGoodsNum);
            }
            //订单数量
            resultMap.put("totalOrderNum",totalOrderNum);
            //订货人数
            resultMap.put("totalPeopleNum",meetingOrderService.selectList(meetingOrderWrapper).size());
            return R.ok(resultMap);
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
            e.printStackTrace();
        }
        return R.error();
    }
}
