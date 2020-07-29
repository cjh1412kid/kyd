package io.nuite.modules.online_sales_app.controller;

import io.nuite.modules.online_sales_app.dao.CustomerUserInfoDao;
import io.nuite.modules.online_sales_app.entity.CustomerUserInfo;
import io.nuite.modules.online_sales_app.service.VerificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/online/miniapp/verification")
@Api(tags = "自助收银台核销接口（对外接口，无需登录）", description = "小程序及自助收银台核销同步 + 自助收银台推送订单货品记录至小程序")
public class VerificationController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static Integer companySeq = 6;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private CustomerUserInfoDao customerUserInfoDao;


    /**
     * 1.2 小程序及自助收银台核销同步
     */
    @GetMapping("syncOrder")
    @ApiOperation("小程序及自助收银台核销同步")
    public String syncOrder(@ApiParam("核销订单单号（当日）(逗号分隔)") @RequestParam("orderNums") String orderNums) {
        try {
            //1.处理订单号参数
            if (orderNums == null || StringUtils.isBlank(orderNums)) {
                return "";
            }
            String regex = "[,，]";
            String[] orderNumArr = orderNums.split(regex);
            List<String> orderNumList = new ArrayList<String>();
            for (int i = 0; i < orderNumArr.length; i++) {
                orderNumList.add(orderNumArr[i].trim());
            }

            //2.查询工厂今日所有已支付订单，筛选出没有传过来的订单
            List<Map<String, Object>> orderMapList = verificationService.getTodayPaidOrderByCompanySeq(companySeq);

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < orderMapList.size(); i++) {
                Map<String, Object> orderMap = orderMapList.get(i);

                //已传过来的订单直接移除
                if (orderNumList.contains(orderMap.get("orderNum").toString())) {
                    orderMapList.remove(i);
                    i--;
                    continue;
                }

                //查询订单信息，组装返回值
                //订单号
                result.append(orderMap.get("orderNum").toString()).append(",");
                //用户openid
                CustomerUserInfo customerUserInfo = customerUserInfoDao.selectById((Integer) orderMap.get("userSeq"));
                result.append(customerUserInfo.getOpenId()).append(",");
                //订单金额
                result.append(orderMap.get("orderPrice").toString()).append(",");
                //下单时间
                result.append(orderMap.get("inputTime").toString()).append(",");
                //多个货号+尺码（美丽华一个货号一个颜色，不考虑颜色）
                String goodIdSize = verificationService.getGoodIdSizeByOrderSeq((Integer) orderMap.get("seq"));
                result.append(goodIdSize).append(";");
            }
            if (result.length() > 0) {
                result.deleteCharAt(result.length() - 1);
            }
            //返回值
//        	未核销订单单号、			 订单用户（openid）、订单总额、 	下单时间、					多个货号+尺码
//        	13896620180122173026131, 133****2929,		699,		2018-01-24 10:38:13.310,	B795654B0133,B795654B0134,B795654B0135;
//        	13896620180122173026131, 133****2929,		699,		2018-01-24 10:38:13.310,	B795654B0133,B795654B0134,B795654B0135

            return result.toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "";
        }
    }


    /**
     * 1.3	自助收银台推送订单货品记录至小程序
     */
    @GetMapping("pushGoods")
    @ApiOperation("自助收银台推送订单货品记录至小程序")
    public String pushGoods(@ApiParam("核销订单单号（当日）(逗号分隔)") @RequestParam("goods") String goods) {
        try {
            //处理参数	货号+尺码    B795654B0133,B795654B0133
            if (goods == null || StringUtils.isBlank(goods)) {
                return "0";
            }
            String regex = "[,，]";
            String[] goodIdSizeArr = goods.split(regex);
            //根据品牌序号、货号+尺码数组修改库存（美丽华一个货号一个颜色，不考虑颜色）
            verificationService.changeStockByGoodIdSize(companySeq, goodIdSizeArr);

//        	返回值	1成功 0失败
            return "1";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "0";
        }

    }


}
