package io.nuite.modules.order_platform_app.service;

import java.util.List;
import java.util.Map;

import io.nuite.modules.order_platform_app.entity.UserJurisdictionEntity;
import io.nuite.modules.sr_base.entity.BaseAgentEntity;
import io.nuite.modules.sr_base.entity.BaseAreaEntity;
import io.nuite.modules.sr_base.entity.BaseUserEntity;

public interface UserJurisdictionService {

	UserJurisdictionEntity getUserJurisdictionByUserSeq(Integer userSeq);
	
	List<Map<String, Object>> getCreatedUserList(Integer userSeq);
	
	BaseAreaEntity getBaseAreaBySeq(Integer areaSeq);

	BaseAgentEntity getBaseAgentBySeq(Integer agentSeq);
	
	List<Map<String, Object>> getAreaListByBrandSeq(Integer brandSeq);

	List<Map<String, Object>> getOfficeListByAreaSeq(Integer areaSeq);

	List<Map<String, Object>> getShopListByOfficeSeq(Integer officeSeq);
	
	List<Map<String, Object>> getAgentListByBrandSeq(Integer brandSeq);

	boolean accountNameIsExisted(String accountName, Integer userSeq);
	
	void createUser(BaseUserEntity loginUser, Integer attachType, Integer attachSeq, Integer saleType, Integer shopSeq, String accountName, 
			String password, String userName, Integer effectiveYears, UserJurisdictionEntity creatorUserJurisdiction);
	
	@Deprecated
	void updateUser(BaseUserEntity loginUser, Integer userSeq, Integer companyType, String companyName, Integer shopSeq, String accountName,
			String password);

	void disableUser(Integer userSeq, Integer isDisable);


}
