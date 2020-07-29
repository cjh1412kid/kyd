package io.nuite.modules.online_sales_app.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.nuite.modules.online_sales_app.dao.CustomerUserInfoDao;
import io.nuite.modules.online_sales_app.entity.CustomerUserInfo;
import io.nuite.modules.online_sales_app.entity.MiniAppEntity;
import io.nuite.modules.online_sales_app.service.CustomerUserInfoService;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerUserInfoServiceImpl extends ServiceImpl<CustomerUserInfoDao, CustomerUserInfo> implements CustomerUserInfoService {

    @Transactional
    public CustomerUserInfo updateOrInsertUser(String openId, String sessionKey, String unionId, MiniAppEntity miniAppEntity) {
        EntityWrapper<CustomerUserInfo> wrapper = new EntityWrapper<CustomerUserInfo>();
        wrapper.eq("Openid", openId);
        CustomerUserInfo customerUserInfo = this.selectOne(wrapper);
        if (customerUserInfo == null) {
            customerUserInfo = new CustomerUserInfo();
        }
        customerUserInfo.setOpenId(openId);
        customerUserInfo.setSessionKey(sessionKey);
        customerUserInfo.setUnionId(unionId);

        // 暂时先为用户默认设置公司和门店
        customerUserInfo.setCompanySeq(miniAppEntity.getCompanySeq());
        customerUserInfo.setBrandSeq(miniAppEntity.getBrandSeq());

        this.insertOrUpdate(customerUserInfo);

        return customerUserInfo;
    }

	@Override
	public CustomerUserInfo updateOrInsertCustomer(String openId, String sessionKey, String unionId,
			MiniAppEntity miniAppEntity, String nickName, String sex, String province, String country, String city) {
		 EntityWrapper<CustomerUserInfo> wrapper = new EntityWrapper<CustomerUserInfo>();
	        wrapper.eq("Openid", openId);
	        CustomerUserInfo customerUserInfo = this.selectOne(wrapper);
	        if (customerUserInfo == null) {
	            customerUserInfo = new CustomerUserInfo();
	        }
	        customerUserInfo.setOpenId(openId);
	        customerUserInfo.setSessionKey(sessionKey);
	        customerUserInfo.setUnionId(unionId);

	        // 暂时先为用户默认设置公司和门店
	        customerUserInfo.setCompanySeq(miniAppEntity.getCompanySeq());
	        customerUserInfo.setBrandSeq(miniAppEntity.getBrandSeq());

	        customerUserInfo.setCity(city);
	        customerUserInfo.setCountry(country);
	        customerUserInfo.setProvince(province);
	        customerUserInfo.setNickName(nickName);
	        customerUserInfo.setSex(Integer.parseInt(sex));
	        
	        this.insertOrUpdate(customerUserInfo);

	        return customerUserInfo;
	}

	@Override
	public List<Object> getAllCustomerByCompanySeq(Integer companySeq, Integer brandSeq) {
		 EntityWrapper<CustomerUserInfo> wrapper = new EntityWrapper<CustomerUserInfo>();
		 wrapper.setSqlSelect("Seq AS userSeq");
		 wrapper.where("Company_Seq ={0} AND Brand_Seq ={1}", companySeq,brandSeq);
		return this.selectObjs(wrapper);
	}
}
