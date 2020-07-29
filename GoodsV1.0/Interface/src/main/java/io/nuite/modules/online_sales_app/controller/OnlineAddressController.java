package io.nuite.modules.online_sales_app.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.nuite.common.utils.R;
import io.nuite.modules.online_sales_app.annotation.XcxCustom;
import io.nuite.modules.online_sales_app.annotation.XcxLogin;
import io.nuite.modules.online_sales_app.entity.CustomerUserInfo;
import io.nuite.modules.online_sales_app.entity.UserDeliveryInfo;
import io.nuite.modules.online_sales_app.service.OlsAddressService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/online/miniapp/address")
@Api(tags = "线上销售APP", description = "地址管理")
public class OnlineAddressController {

    @Autowired
    private OlsAddressService olsAddressService;

    @XcxLogin
    @GetMapping("list")
    @ResponseBody
    public R list(@ApiIgnore @XcxCustom CustomerUserInfo customerUserInfo) {
        EntityWrapper<UserDeliveryInfo> ew = new EntityWrapper<>();
        ew.eq("User_Seq", customerUserInfo.getSeq()).eq("Del", 0);
        try {
            List<UserDeliveryInfo> deliveryInfos = olsAddressService.selectList(ew);
            return R.ok().put("addresses", deliveryInfos);
        } catch (Exception e) {
            return R.error("服务器开小差啦！");
        }
    }

    @XcxLogin
    @GetMapping("getdefault")
    @ResponseBody
    public R getDefault(@ApiIgnore @XcxCustom CustomerUserInfo customerUserInfo) {
        EntityWrapper<UserDeliveryInfo> ew = new EntityWrapper<>();
        ew.eq("User_Seq", customerUserInfo.getSeq()).eq("Isdefault", 1).eq("Del", 0);
        try {
            UserDeliveryInfo deliveryInfo = olsAddressService.selectOne(ew);
            return R.ok().put("address", deliveryInfo);
        } catch (Exception e) {
            return R.error("服务器开小差啦！");
        }
    }

    /**
     * 编辑 包括新增和修改，通过seq判断
     *
     * @param customerUserInfo
     * @param userDeliveryInfo
     * @return
     */
    @XcxLogin
    @PostMapping("edit")
    @ResponseBody
    public R edit(@ApiIgnore @XcxCustom CustomerUserInfo customerUserInfo,
                  UserDeliveryInfo userDeliveryInfo) {
        if (StringUtils.isBlank(userDeliveryInfo.getRecipientsName())
                || StringUtils.isBlank(userDeliveryInfo.getTelephone())
                || StringUtils.isBlank(userDeliveryInfo.getAddress())) {
            return R.error("地址信息不完整");
        }
        Integer currentUserSeq = customerUserInfo.getSeq();
        if (userDeliveryInfo.getCustomSeq() != null && !userDeliveryInfo.getCustomSeq().equals(currentUserSeq)) {
            return R.error("修改地址信息错误");
        }
        userDeliveryInfo.setCustomSeq(currentUserSeq);
        try {
            olsAddressService.editAddress(userDeliveryInfo);
            return R.ok();
        } catch (Exception e) {
            return R.error("服务器开小差啦！");
        }
    }

    @XcxLogin
    @PostMapping("del")
    @ResponseBody
    public R del(@ApiIgnore @XcxCustom CustomerUserInfo customerUserInfo,
                 Long seq) {
        try {
            return olsAddressService.deleteAddress(seq, customerUserInfo.getSeq());
        } catch (Exception e) {
            return R.error("服务器开小差啦！");
        }
    }

    @XcxLogin
    @GetMapping("select")
    @ResponseBody
    public R select(@ApiIgnore @XcxCustom CustomerUserInfo customerUserInfo,
                    Long seq) {
        EntityWrapper<UserDeliveryInfo> ew = new EntityWrapper<>();
        ew.eq("Seq", seq).eq("User_Seq", customerUserInfo.getSeq()).eq("Del", 0);
        try {
            UserDeliveryInfo userDeliveryInfo = olsAddressService.selectOne(ew);
            return R.ok().put("address", userDeliveryInfo);
        } catch (Exception e) {
            return R.error("服务器开小差啦！");
        }
    }

    @XcxLogin
    @PostMapping("setdefault")
    @ResponseBody
    public R setdefault(@ApiIgnore @XcxCustom CustomerUserInfo customerUserInfo,
                        Long seq, Boolean isdefault) {
        if (seq == null) {
            return R.error("参数错误");
        }

        try {
            olsAddressService.updateDefault(seq, customerUserInfo.getSeq(), isdefault);
            return R.ok();
        } catch (Exception e) {
            return R.error("服务器开小差啦！");
        }
    }
}
