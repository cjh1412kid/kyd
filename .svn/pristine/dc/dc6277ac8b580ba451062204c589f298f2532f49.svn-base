package io.nuite.modules.sr_base.service;

import com.baomidou.mybatisplus.service.IService;
import io.nuite.modules.sr_base.entity.BaseUserEntity;

import java.util.List;

public interface BaseUserService extends IService<BaseUserEntity> {

    void updateBaseUser(BaseUserEntity baseUserEntity);

    BaseUserEntity getBaseUserBySeq(Integer userSeq);

    /**
     * 根据用户名，查询系统用户
     */
    BaseUserEntity queryByUserName(String username);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);

    /*
	 PageUtils queryPage(Map<String, Object> params);

	*//**
     * 查询用户的所有权限
     * @param userId  用户ID
     *//*
	List<String> queryAllPerms(Long userId);

	*//**
     * 保存用户
     *//*
	void save(SysUserEntity user);

	*//**
     * 修改用户
     *//*
	void update(SysUserEntity user);

	*//**
     * 删除用户
     *//*
	void deleteBatch(Long[] userIds);

	*/

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param password    原密码
     * @param newPassword 新密码
     */
    boolean updatePassword(Long userId, String password, String newPassword);

    boolean checkUserPlatform(BaseUserEntity userEntity);

    void updateRongCloud(Integer userSeq, String token);

    /**
     * 根据companySeq和accountName，忽略Del
     *
     * @param accountName
     * @param companySeq
     * @return
     */
    BaseUserEntity queryUserByCompanyAndAccountName(String accountName, Integer companySeq);

    /**
     * 根据seq更新，忽略Del
     *
     * @param baseUserEntity
     * @return
     */
    boolean updateUserBySeq(BaseUserEntity baseUserEntity);

    List<Integer> getSeqsByAttachTypeAndSaleType(int attachType , int saleType);

    /**
     * 客服列表:获取工厂账号和工厂子账号列表
     * @param companySeq
     * @return
     */
	List<BaseUserEntity> getCompanyUsers(Integer companySeq);

	BaseUserEntity getUserByPhone(String phone);
	
	BaseUserEntity getUserByPhoneAndCompany(String phone,Integer companySeq,Integer brandSeq);
}
