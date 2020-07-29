package io.nuite.modules.order_platform_app.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import io.nuite.common.utils.Constant;
import io.nuite.modules.order_platform_app.dao.PublicityPicDao;
import io.nuite.modules.order_platform_app.service.BaseService;
import io.nuite.modules.sr_base.dao.BaseUserDao;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.system.service.ShiroService;

@Service
public class BaseServiceImpl implements BaseService {

    @Autowired
    private BaseUserDao baseUserDao;
    
    @Autowired
    private PublicityPicDao publicityPicDao;
    
    @Autowired
    private ShiroService shiroService;
    

	
	/**
	 * 根据 品牌编号 查询波次编号列表
	 */
	@Override
	public List<Integer> getPeriodListByBrandSeq(Integer brandSeq) {
		return publicityPicDao.getPeriodListByBrandSeq(brandSeq);
	}
	
	
    /**
     * 查询登录用户所属的工厂管理员
     */
	@Override
	public BaseUserEntity getAdminUserByLoginUser(BaseUserEntity loginUser) {
		BaseUserEntity baseUserEntity = new BaseUserEntity();
		baseUserEntity.setCompanySeq(loginUser.getCompanySeq());
		baseUserEntity.setBrandSeq(loginUser.getBrandSeq());
		baseUserEntity.setAttachType(Constant.UserAttachType.FACTORY.getValue());
		baseUserEntity.setSaleType(Constant.UserSaleType.FACTORY.getValue());
		return baseUserDao.selectOne(baseUserEntity);
	}
	
	
	
    /**
     * 查询登录用户所属工厂拥有特定权限的工厂用户列表
     */
	@Override
	public List<BaseUserEntity> getUserByPermission(BaseUserEntity loginUser, String[] perms) {
		Wrapper<BaseUserEntity> wrapper = new EntityWrapper<BaseUserEntity>();
		wrapper.where("Company_Seq = {0} AND Brand_Seq = {1} AND SaleType = {2} ", loginUser.getCompanySeq(), loginUser.getBrandSeq(), Constant.UserSaleType.FACTORY.getValue());
		
		List<BaseUserEntity> userList = baseUserDao.selectList(wrapper);
		if(perms != null && perms.length > 0) {
			for(int i = 0; i < userList.size(); i++) {
				BaseUserEntity user = userList.get(i);
				Set<String> permissions = shiroService.getUserPermissions(user.getSeq());
				for(String s : perms) {
					if(!permissions.contains(s)) {
						userList.remove(user);
						i--;
						break;
					}
				}
			}
			return userList;
		} else {
			return userList;
		}
	}

	
	
	/**
	 * 查询工厂所有账号（管理员+所有子账号）
	 */
	@Override
	public List<BaseUserEntity> getAllCompanyUser(Integer companySeq) {
		Wrapper<BaseUserEntity> wrapper = new EntityWrapper<BaseUserEntity>();
		wrapper.where("Company_Seq = {0} AND SaleType = {1} ", companySeq, Constant.UserSaleType.FACTORY.getValue());
		List<BaseUserEntity> userList = baseUserDao.selectList(wrapper);
		return userList;
	}
	

	
}
