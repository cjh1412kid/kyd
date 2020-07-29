package io.nuite.modules.order_platform_app.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import io.nuite.modules.order_platform_app.dao.UserJurisdictionDao;
import io.nuite.modules.order_platform_app.entity.UserJurisdictionEntity;
import io.nuite.modules.order_platform_app.service.UserJurisdictionService;
import io.nuite.modules.order_platform_app.utils.RongCloudUtils;
import io.nuite.modules.sr_base.dao.BaseAgentDao;
import io.nuite.modules.sr_base.dao.BaseAreaDao;
import io.nuite.modules.sr_base.dao.BaseBrandDao;
import io.nuite.modules.sr_base.dao.BaseCompanyDao;
import io.nuite.modules.sr_base.dao.BaseShopDao;
import io.nuite.modules.sr_base.dao.BaseUserDao;
import io.nuite.modules.sr_base.entity.BaseAgentEntity;
import io.nuite.modules.sr_base.entity.BaseAreaEntity;
import io.nuite.modules.sr_base.entity.BaseBrandEntity;
import io.nuite.modules.sr_base.entity.BaseCompanyEntity;
import io.nuite.modules.sr_base.entity.BaseUserEntity;

@Service
public class UserJurisdictionServiceImpl implements UserJurisdictionService {
    
    @Autowired
    private UserJurisdictionDao userJurisdictionDao;

    @Autowired
    private BaseCompanyDao baseCompanyDao;
    
    @Autowired
    private BaseBrandDao baseBrandDao;
    
    @Autowired
    private BaseShopDao baseShopDao;

    @Autowired
    private BaseUserDao baseUserDao;
    
	@Autowired
	private RongCloudUtils rongCloudUtils;
    
	@Autowired
	private BaseAreaDao baseAreaDao;
	
	@Autowired
	private BaseAgentDao baseAgentDao;
    
	
	
    /**
     * 根据用户编号查询用户权限
     */
	@Override
	public UserJurisdictionEntity getUserJurisdictionByUserSeq(Integer userSeq) {
		UserJurisdictionEntity userJurisdictionEntity =  new UserJurisdictionEntity();
		userJurisdictionEntity.setUserSeq(userSeq);
		return userJurisdictionDao.selectOne(userJurisdictionEntity);
	}
	
	
    /**
     * 已添加的品牌方账号列表
     */
	@Override
	public List<Map<String, Object>> getCreatedUserList(Integer userSeq) {
		return userJurisdictionDao.getCreatedUserList(userSeq);
	}


	/**
	 * 判断账号是否已存在
	 */
	@Override
	public boolean accountNameIsExisted(String accountName, Integer userSeq) {
		BaseUserEntity baseUserEntity = new BaseUserEntity();
		baseUserEntity.setAccountName(accountName);
		baseUserEntity = baseUserDao.selectOne(baseUserEntity);
		if(baseUserEntity == null) {
			return false;
		} else if (baseUserEntity != null && baseUserEntity.getSeq().equals(userSeq)){
			return false;
		} else {
			return true;
		}
	}
	
	
	/**
	 * 创建品牌方账号
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void createUser(BaseUserEntity loginUser, Integer attachType, Integer attachSeq, Integer saleType, Integer shopSeq, String accountName, 
			String password, String userName, Integer effectiveYears, UserJurisdictionEntity creatorUserJurisdiction) {

		// 时间处理
		Calendar calendar = Calendar.getInstance();
		Date nowDate = calendar.getTime();
		calendar.add(Calendar.YEAR, effectiveYears);
		Date effectiveDate = calendar.getTime();

		/**
		 * 涉及修改的表：
		 *  1.新增用户信息 
		 *  2.新增用户权限 
		 *  3.已创建用户数+1
		 */
		// 1.创建用户
		BaseUserEntity baseUserEntity = new BaseUserEntity();
		baseUserEntity.setAccountName(accountName);
		baseUserEntity.setPassword(DigestUtils.sha256Hex(password));
		baseUserEntity.setCompanySeq(loginUser.getCompanySeq());
		baseUserEntity.setBrandSeq(loginUser.getBrandSeq());
		baseUserEntity.setAttachType(attachType);
		baseUserEntity.setAttachSeq(attachSeq);
		baseUserEntity.setSaleType(saleType);
		if(shopSeq != null) {
			baseUserEntity.setShopSeq(shopSeq.toString());
		} else {
			baseUserEntity.setShopSeq("");
		}
		baseUserEntity.setUserName(userName);
		baseUserEntity.setInputTime(nowDate);
		baseUserEntity.setDel(0);
		baseUserDao.insert(baseUserEntity);
		
		//注册融云生成token
		String rongCloudToken = rongCloudUtils.registerUser(baseUserEntity.getSeq(), userName, "1.jpg");
		if(rongCloudToken == null) {
			throw new RuntimeException("获取融云Token失败");
		}
		baseUserEntity.setRongCloudToken(rongCloudToken);
		baseUserDao.updateById(baseUserEntity);
		
		// 2.创建用户权限
		UserJurisdictionEntity userJurisdictionEntity = new UserJurisdictionEntity();
		userJurisdictionEntity.setUserSeq(baseUserEntity.getSeq());
		userJurisdictionEntity.setCreateUserSeq(loginUser.getSeq());
		userJurisdictionEntity.setEffectiveDate(effectiveDate);
		userJurisdictionEntity.setIsDisable(0);
		userJurisdictionEntity.setIntOfCreateUsers(0);
		userJurisdictionEntity.setAlreadyCreateUsers(0);
		userJurisdictionEntity.setIsAdministrator(0);
		userJurisdictionEntity.setDel(0);
		userJurisdictionDao.insert(userJurisdictionEntity);

		//3.已创建用户数+1
		creatorUserJurisdiction.setAlreadyCreateUsers(creatorUserJurisdiction.getAlreadyCreateUsers() + 1);
		userJurisdictionDao.updateById(creatorUserJurisdiction);
		
	}


	/**
	 * 根据品牌编号查询区域列表
	 */
	@Override
	public List<Map<String, Object>> getAreaListByBrandSeq(Integer brandSeq) {
		return baseShopDao.getAreaListByBrandSeq(brandSeq);
	}


	/**
	 * 根据区域编号查询办事处列表
	 */
	@Override
	public List<Map<String, Object>> getOfficeListByAreaSeq(Integer areaSeq) {
		return baseShopDao.getOfficeListByAreaSeq(areaSeq);
	}


	/**
	 * 根据办事处编号查询门店列表
	 */
	@Override
	public List<Map<String, Object>> getShopListByOfficeSeq(Integer officeSeq) {
		return baseShopDao.getShopListByOfficeSeq(officeSeq);
	}


	/**
	 * 修改品牌方账号
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	@Deprecated
	public void updateUser(BaseUserEntity loginUser, Integer userSeq, Integer companyType, String companyName, Integer shopSeq, String accountName,
			String password) {
//
//		//原先用户信息
//		BaseUserEntity oldUser = baseUserDao.selectById(userSeq);
//		
//		//原先是贴牌商或批发商
//		if (oldUser.getShopSeq() == 0) {
//			//修改后仍是贴牌商或批发商
//			if (companyType == 2 || companyType == 3) {
//				//1.修改公司名、类型
//				BaseCompanyEntity baseCompanyEntity = new BaseCompanyEntity();
//				baseCompanyEntity.setSeq(oldUser.getCompanySeq());
//				baseCompanyEntity.setName(companyName);
//				baseCompanyEntity.setCompanyTypeSeq(companyType);
//				baseCompanyDao.updateById(baseCompanyEntity);
//				//2.修改默认品牌名
//				BaseBrandEntity baseBrandEntity = new BaseBrandEntity();
//				baseBrandEntity.setSeq(oldUser.getBrandSeq());
//				baseBrandEntity.setName(companyName + "default品牌");
//				baseBrandDao.updateById(baseBrandEntity);
//				//3.修改用户账户名、密码
//				BaseUserEntity baseUserEntity = new BaseUserEntity();
//				baseUserEntity.setSeq(userSeq);
//				baseUserEntity.setAccountName(accountName);
//				baseUserEntity.setPassword(DigestUtils.sha256Hex(password));
//				baseUserDao.updateById(baseUserEntity);
//			} else if (companyType == 4) { //修改后是门店
//				//1.删除之前的公司
//				baseCompanyDao.deleteById(oldUser.getCompanySeq());
//				//2.删除之前创建的默认品牌
//				baseBrandDao.deleteById(oldUser.getBrandSeq());
//				//3.修改用户账户名、密码，并给用户添加门店序号
//				BaseUserEntity baseUserEntity = new BaseUserEntity();
//				baseUserEntity.setSeq(userSeq);
//				baseUserEntity.setAccountName(accountName);
//				baseUserEntity.setPassword(DigestUtils.sha256Hex(password));
//				baseUserEntity.setCompanySeq(loginUser.getCompanySeq());
//				baseUserEntity.setBrandSeq(loginUser.getBrandSeq());
//				baseUserEntity.setShopSeq(shopSeq);
//				baseUserDao.updateById(baseUserEntity);
//			}
//		}
//		// 原先是门店
//		else  if (oldUser.getShopSeq() != 0){
//			//修改后是贴牌商或批发商
//			if (companyType == 2 || companyType == 3) {
//				// 1.创建公司
//				BaseCompanyEntity baseCompanyEntity = new BaseCompanyEntity();
//				baseCompanyEntity.setName(companyName);
//				baseCompanyEntity.setCompanyTypeSeq(companyType);
//				baseCompanyEntity.setDel(0);
//				baseCompanyDao.insert(baseCompanyEntity);
//				// 2.创建默认品牌
//				BaseBrandEntity baseBrandEntity = new BaseBrandEntity();
//				baseBrandEntity.setName(companyName + "default品牌");
//				baseBrandEntity.setCompanySeq(baseCompanyEntity.getSeq());
//				baseBrandEntity.setDel(0);
//				baseBrandDao.insert(baseBrandEntity);
//				//3.修改用户账户名、密码,并设置公司序号、品牌序号,并将门店序号置为0
//				BaseUserEntity baseUserEntity = new BaseUserEntity();
//				baseUserEntity.setSeq(userSeq);
//				baseUserEntity.setAccountName(accountName);
//				baseUserEntity.setPassword(DigestUtils.sha256Hex(password));
//				baseUserEntity.setCompanySeq(baseCompanyEntity.getSeq());
//				baseUserEntity.setBrandSeq(baseBrandEntity.getSeq());
//				baseUserEntity.setShopSeq(0);
//				baseUserDao.updateById(baseUserEntity);
//			} else if (companyType == 4) { //修改后仍是门店
//				//3.修改用户账户名、密码，修改门店序号
//				BaseUserEntity baseUserEntity = new BaseUserEntity();
//				baseUserEntity.setSeq(userSeq);
//				baseUserEntity.setAccountName(accountName);
//				baseUserEntity.setPassword(DigestUtils.sha256Hex(password));
//				baseUserEntity.setShopSeq(shopSeq);
//				baseUserDao.updateById(baseUserEntity);
//			}
//		}
	}


	/**
	 * 启用、停用品牌方账号
	 */
	@Override
	public void disableUser(Integer userSeq, Integer isDisable) {
		UserJurisdictionEntity userJurisdiction = new UserJurisdictionEntity();
		userJurisdiction.setIsDisable(isDisable);
		Wrapper<UserJurisdictionEntity> wrapper = new EntityWrapper<UserJurisdictionEntity>();
		wrapper.where("User_Seq = {0} ", userSeq);
		userJurisdictionDao.update(userJurisdiction, wrapper);
	}


	/**
	 * 根据seq获取分公司
	 */
	@Override
	public BaseAreaEntity getBaseAreaBySeq(Integer areaSeq) {
		return baseAreaDao.selectById(areaSeq);
	}

	
	/**
	 * 根据seq获取代理商
	 */
	@Override
	public BaseAgentEntity getBaseAgentBySeq(Integer agentSeq) {
		return baseAgentDao.selectById(agentSeq);
	}


	/**
	 * 获取所有代理
	 */
	@Override
	public List<Map<String, Object>> getAgentListByBrandSeq(Integer brandSeq) {
		Wrapper<BaseAgentEntity> wrapper = new EntityWrapper<BaseAgentEntity>();
		wrapper.setSqlSelect("Seq AS seq, AgentName AS agentName").where("Brand_Seq = {0}", brandSeq);
		return baseAgentDao.selectMaps(wrapper);
	}


    
}
