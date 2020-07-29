package io.nuite.modules.sr_base.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.nuite.common.exception.RRException;
import io.nuite.modules.sr_base.dao.BaseUserDao;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.sr_base.service.BaseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BaseUserServiceImpl extends ServiceImpl<BaseUserDao, BaseUserEntity> implements BaseUserService {

    @Autowired
    private BaseUserDao baseUserDao;

    /**
     * 修改用户信息
     */
    @Override
    public void updateBaseUser(BaseUserEntity baseUserEntity) {
        baseUserDao.updateById(baseUserEntity);
    }

    /**
     * 根据seq获取用户信息
     */
    @Override
    public BaseUserEntity getBaseUserBySeq(Integer userSeq) {
        return baseUserDao.selectById(userSeq);
    }

    /*	@Autowired
        private SysUserRoleService sysUserRoleService;
        @Autowired
        private SysRoleService sysRoleService;
    */
    @Override
    public BaseUserEntity queryByUserName(String username) {
        return baseMapper.queryByUserName(username);
    }

    @Override
    public List<Long> queryAllMenuId(Long userSeq) {
        return baseMapper.queryAllMenuId(userSeq);
    }

	/*
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String username = (String)params.get("username");
		Long createUserId = (Long)params.get("createUserId");

		Page<BaseUserEntity> page = this.selectPage(
			new Query<SysUserEntity>(params).getPage(),
			new EntityWrapper<SysUserEntity>()
				.like(StringUtils.isNotBlank(username),"username", username)
				.eq(createUserId != null,"create_user_id", createUserId)
		);

		return new PageUtils(page);
	}

	@Override
	public List<String> queryAllPerms(Long userId) {
		return baseMapper.queryAllPerms(userId);
	}

	@Override
	@Transactional
	public void save(SysUserEntity user) {
		user.setCreateTime(new Date());
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
		user.setSalt(salt);
		this.insert(user);

		//检查角色是否越权
		checkRole(user);

		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserSeq(), user.getRoleIdList());
	}

	@Override
	@Transactional
	public void update(SysUserEntity user) {
		if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
		}
		this.updateById(user);

		//检查角色是否越权
		checkRole(user);

		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserSeq(), user.getRoleIdList());
	}

	@Override
	public void deleteBatch(Long[] userId) {
		this.deleteBatchIds(Arrays.asList(userId));
	}*/

    @Override
    public boolean updatePassword(Long userId, String password, String newPassword) {
        BaseUserEntity userEntity = new BaseUserEntity();
        userEntity.setPassword(newPassword);
        return this.update(userEntity,
            new EntityWrapper<BaseUserEntity>().eq("Seq", userId).eq("Password", password));
    }

    /**
     * 检查角色是否越权
     *//*
	private void checkRole(SysUserEntity user){
		if(user.getRoleIdList() == null || user.getRoleIdList().size() == 0){
			return;
		}
		//如果不是超级管理员，则需要判断用户的角色是否自己创建
		if(user.getCreateUserId() == Constant.SUPER_ADMIN){
			return ;
		}

		//查询用户创建的角色列表
		List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());

		//判断是否越权
		if(!roleIdList.containsAll(user.getRoleIdList())){
			throw new RRException("新增用户所选角色，不是本人创建");
		}
	}*/
    @Override
    public boolean checkUserPlatform(BaseUserEntity userEntity) {
        Map<String, Object> onlineSaleUser = baseUserDao.getUserInOnlineSales(userEntity.getSeq().longValue());
        Map<String, Object> orderPlatformUser = baseUserDao.getUserInOrderPlatform(userEntity.getSeq().longValue());
        if (onlineSaleUser != null) {
            checkUserMap(onlineSaleUser);
        }

        if (orderPlatformUser != null) {
            checkUserMap(orderPlatformUser);
        }

        return onlineSaleUser != null || orderPlatformUser != null;
    }

    private void checkUserMap(Map<String, Object> userMap) {
        Date effectiveDate = (Date) userMap.get("EffectiveDate");
        if (effectiveDate.before(new Date())) {
            throw new RRException("分销平台账号已过期");
        }
        Integer isDisable = (Integer) userMap.getOrDefault("IsDisable", 1);
        if (isDisable != 0) {
            throw new RRException("分销平台账号被锁定");
        }
    }

    @Override
    public void updateRongCloud(Integer userSeq, String token) {
        BaseUserEntity rongCloudUpdate = new BaseUserEntity();
        rongCloudUpdate.setSeq(userSeq);
        rongCloudUpdate.setRongCloudToken(token);
        this.updateById(rongCloudUpdate);
    }

    @Override
    public BaseUserEntity queryUserByCompanyAndAccountName(String accountName, Integer companySeq) {
		BaseUserEntity baseUserEntity = new BaseUserEntity();
		baseUserEntity.setCompanySeq(companySeq);
		baseUserEntity.setAccountName(accountName);
		return baseUserDao.selectOne(baseUserEntity);
    }

    /**
     * 根据seq更新，忽略Del
     *
     * @param baseUserEntity
     * @return
     */
    @Override
    public boolean updateUserBySeq(BaseUserEntity baseUserEntity) {
        try {
            baseUserDao.updateUserBySeq(baseUserEntity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Integer> getSeqsByAttachTypeAndSaleType(int attachType, int saleType) {
        return baseUserDao.selectSeqByAttachTypeAndSaleType(attachType,saleType);
    }

    /**
     * 客服列表:获取工厂账号和工厂子账号列表
     */
	@Override
	public List<BaseUserEntity> getCompanyUsers(Integer companySeq) {
		Wrapper<BaseUserEntity> wrapper = new EntityWrapper<BaseUserEntity>();
		wrapper.where("Company_Seq = {0} AND (AttachType = 1 OR AttachType = 4) AND SaleType = 1", companySeq);
		return baseUserDao.selectList(wrapper);
	}

	@Override
	public BaseUserEntity getUserByPhone(String phone) {
		Wrapper<BaseUserEntity> wrapper = new EntityWrapper<BaseUserEntity>();
		wrapper.where("Telephone = {0} AND SaleType = 1", phone);
		List<BaseUserEntity> baseUserEntities=baseUserDao.selectList(wrapper);
		if(baseUserEntities != null && baseUserEntities.size() > 0 && baseUserEntities.get(0) != null) {
			return baseUserEntities.get(0);
		}else {
			return null;
		}
	}

	@Override
	public BaseUserEntity getUserByPhoneAndCompany(String phone, Integer companySeq,Integer brandSeq) {
		Wrapper<BaseUserEntity> wrapper = new EntityWrapper<BaseUserEntity>();
		wrapper.where("Telephone = {0} AND SaleType = 1 AND Company_Seq ={1} AND Brand_Seq ={2}", phone,companySeq,brandSeq);
		List<BaseUserEntity> baseUserEntities=baseUserDao.selectList(wrapper);
		if(baseUserEntities != null && baseUserEntities.size() > 0 && baseUserEntities.get(0) != null) {
			return baseUserEntities.get(0);
		}else {
			return null;
		}
	}
}
