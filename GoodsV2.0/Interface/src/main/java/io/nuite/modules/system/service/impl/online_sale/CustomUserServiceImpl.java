package io.nuite.modules.system.service.impl.online_sale;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.nuite.modules.online_sales_app.dao.CustomerUserInfoDao;
import io.nuite.modules.online_sales_app.dao.OnlineSalesOrderDao;
import io.nuite.modules.online_sales_app.entity.CustomerUserInfo;
import io.nuite.modules.online_sales_app.entity.OrderEntity;
import io.nuite.modules.sr_base.entity.GoodsSizeEntity;
import io.nuite.modules.system.service.online_sale.CustomUserService;

@Service
public class CustomUserServiceImpl extends ServiceImpl<CustomerUserInfoDao, CustomerUserInfo> implements CustomUserService {
	@Autowired
	private CustomerUserInfoDao customerUserInfoDao;
	
	@Autowired
	private OnlineSalesOrderDao onlineSalesOrderDao;
    
    
    @Override
    public Page<Map<String, Object>> getCustomUserList(Integer companySeq, Integer nickNameState, Integer telephoneState, String startTime,
    		String endTime, Integer pageNum, Integer pageSize) {
        Wrapper<CustomerUserInfo> wrapper = new EntityWrapper<CustomerUserInfo>();
        wrapper.setSqlSelect("Seq AS seq, Isuse AS isUse, Nickname AS nickName, Telephone AS telephone, InputTime AS inputTime");
        wrapper.where("Company_Seq = {0}", companySeq);
        if(nickNameState != null) {
        	if(nickNameState == 0) {
            	wrapper.where("Nickname IS NOT NULL");
        	} else if(nickNameState == 1) {
            	wrapper.where("Nickname IS NULL");
        	}
        }
        if(telephoneState != null) {
        	if(telephoneState == 0) {
            	wrapper.where("Telephone IS NOT NULL");
        	} else if(telephoneState == 1) {
            	wrapper.where("Telephone IS NULL");
        	}
        }
        if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
            wrapper.where("InputTime >= {0} AND InputTime <= {1}", startTime, endTime);
        }
        Page<GoodsSizeEntity> page = new Page<GoodsSizeEntity>(pageNum, pageSize);
        return this.selectMapsPage(page, wrapper);
    }


	@Override
	public Map<String, Object> getCustomUserOrderSumMap(Integer customUserSeq) {
//		label : '总订单数量',
//		name : 'orderNum',
        
//		label : '总订单金额',
//		name : 'totalOrderPrice',
        
//		label : '总预付款金额',
//		name : 'totalPaidPrice',
		
//		label : '最后下单日期',
//		name : 'lastOrderTime',
        Wrapper<OrderEntity> wrapper = new EntityWrapper<OrderEntity>();
        wrapper.setSqlSelect("COUNT(1) AS orderNum, SUM (OrderPrice) AS totalOrderPrice, SUM (Paid) AS totalPaidPrice, MAX (InputTime) AS lastOrderTime");
        wrapper.where("User_Seq = {0}", customUserSeq);
        List<Map<String, Object>> list = onlineSalesOrderDao.selectMaps(wrapper);
        if(list != null && list.size() > 0 && list.get(0) != null) {
        	return list.get(0);
        } else {
        	return new HashMap<String, Object>();
        }
	}


	@Override
	public void enableUser(Integer seq) {
		CustomerUserInfo customerUserInfo = new CustomerUserInfo();
		customerUserInfo.setSeq(seq);
		customerUserInfo.setIsUse(0);
		customerUserInfoDao.updateById(customerUserInfo);
	}


	@Override
	public void disableUser(Integer seq) {
		CustomerUserInfo customerUserInfo = new CustomerUserInfo();
		customerUserInfo.setSeq(seq);
		customerUserInfo.setIsUse(1);
		customerUserInfoDao.updateById(customerUserInfo);
	}
	
	
	

}
