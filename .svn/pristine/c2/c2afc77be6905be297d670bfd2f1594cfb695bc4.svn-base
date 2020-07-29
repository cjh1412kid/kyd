package io.nuite.modules.system.service.online_sale;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import io.nuite.modules.online_sales_app.entity.CustomerUserInfo;

public interface CustomUserService  extends IService<CustomerUserInfo> {
	Page<Map<String, Object>> getCustomUserList(Integer companySeq, Integer nickNameState, Integer telephoneState, String startTime,
			String endTime, Integer pageNum, Integer pageSize);
	
	
	Map<String, Object> getCustomUserOrderSumMap(Integer customUserSeq);

	void enableUser(Integer seq);

	void disableUser(Integer seq);

}
