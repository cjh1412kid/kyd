package io.nuite.modules.online_sales_app.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import io.nuite.modules.online_sales_app.entity.CustomerUserInfo;
import io.nuite.modules.online_sales_app.entity.MiniAppEntity;

public interface CustomerUserInfoService extends IService<CustomerUserInfo> {
    CustomerUserInfo updateOrInsertUser(String openId, String sessionKey, String unionId, MiniAppEntity miniAppEntity);

    CustomerUserInfo updateOrInsertCustomer(String openId, String sessionKey, String unionId, MiniAppEntity miniAppEntity,String nickName,String sex,String province,String country,String city);

    List<Object> getAllCustomerByCompanySeq(Integer companySeq,Integer brandSeq);
}
