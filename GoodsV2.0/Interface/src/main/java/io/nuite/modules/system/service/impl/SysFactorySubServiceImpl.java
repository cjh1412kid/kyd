package io.nuite.modules.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.nuite.common.exception.RRException;
import io.nuite.common.utils.*;
import io.nuite.modules.order_platform_app.utils.RongCloudUtils;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.sr_base.service.BaseUserService;
import io.nuite.modules.sr_base.service.SysUserMenuService;
import io.nuite.modules.system.dao.online_sale.OnlineUserJurisdictionDao;
import io.nuite.modules.system.dao.order_platform.OrderUserJurisdictionDao;
import io.nuite.modules.system.entity.online_sale.OnlineUserJurisdictionEntity;
import io.nuite.modules.system.entity.order_platform.OrderUserJurisdictionEntity;
import io.nuite.modules.system.service.SysFactorySubService;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SysFactorySubServiceImpl implements SysFactorySubService {
    @Autowired
    private BaseUserService baseUserService;

    @Autowired
    private SysUserMenuService sysUserMenuService;

    @Autowired
    private OrderUserJurisdictionDao orderUserJurisdictionDao;

    @Autowired
    private OnlineUserJurisdictionDao onlineUserJurisdictionDao;

    @Autowired
    private RongCloudUtils rongCloudUtils;

    @Override
    public PageUtils subAccountList(BaseUserEntity baseUserEntity, Map<String, Object> params) {
        // 查询工厂子账号
        String key = (String) params.get("key");

        Page<BaseUserEntity> page = baseUserService.selectPage(
                new Query<BaseUserEntity>(params).getPage(),
                new EntityWrapper<BaseUserEntity>()
                        .like(StringUtils.isNotBlank(key), "UserName", key)
                        .eq("Company_Seq", baseUserEntity.getCompanySeq())
                        .eq("AttachType", Constant.UserAttachType.SUBACCOUNT.getValue())
                        .eq("Del", 0)
        );
        // 去除账号中的密码
        for (BaseUserEntity user : page.getRecords()) {
            user.setPassword("");
        }

        return new PageUtils(page);
    }

    @Override
    public R querySubFactory(Long seq) {
        R result = R.ok();
        BaseUserEntity baseUserEntity = baseUserService.selectById(seq.intValue());
        baseUserEntity.setPassword("");
        result.put("account", baseUserEntity);
        List<Long> allMenuId = baseUserService.queryAllMenuId(seq);
        result.put("menu", allMenuId);
        return result;
    }

    @Override
    @Transactional
    public void newSubAccount(BaseUserEntity baseUserEntity, List<Long> menus, BaseUserEntity createUser) {
        // 判断账号是否存在，并保存当前用户信息
        BaseUserEntity oldUserEntity = baseUserService.selectOne(new EntityWrapper<BaseUserEntity>()
                .eq("AccountName", baseUserEntity.getAccountName().trim())
                .eq("Del", 0));
        if (oldUserEntity != null) {
            throw new RRException("账号名已存在，请输入其他账号名称！");
        }
        BaseUserEntity newUserEntity = new BaseUserEntity();
        newUserEntity.setAccountName(baseUserEntity.getAccountName().trim());
        String newPassword = DigestUtils.sha256Hex(baseUserEntity.getPassword());
        newUserEntity.setPassword(newPassword);
        newUserEntity.setUserName(baseUserEntity.getUserName().trim());
        newUserEntity.setTelephone(baseUserEntity.getTelephone().trim());
        newUserEntity.setCompanySeq(createUser.getCompanySeq());
        newUserEntity.setBrandSeq(createUser.getBrandSeq());
        newUserEntity.setAttachType(Constant.UserAttachType.SUBACCOUNT.getValue());
        newUserEntity.setSaleType(Constant.UserSaleType.FACTORY.getValue());
        newUserEntity.setShopSeq("");
        newUserEntity.setInputTime(new Date());
        newUserEntity.setDel(0);
        baseUserService.insert(newUserEntity);

        Integer initedUserSeq = newUserEntity.getSeq();
        // 获取融云token
        baseUserEntity = baseUserService.selectById(initedUserSeq);
        String userName = baseUserEntity.getUserName();
        String rongCloudToken = baseUserEntity.getRongCloudToken();
        if (userName == null) userName = "default";
        String newRongCloudToken;
        if (org.apache.commons.lang.StringUtils.isBlank(rongCloudToken)) {
            newRongCloudToken = rongCloudUtils.registerUser(initedUserSeq, userName, "123");
            baseUserService.updateRongCloud(initedUserSeq, newRongCloudToken);
        } else {
            rongCloudUtils.refreshUserInfo(initedUserSeq, userName, "123");
        }

        // 查询工厂账号订货平台权限
        OrderUserJurisdictionEntity orderJurisdiction = new OrderUserJurisdictionEntity();
        orderJurisdiction.setUserSeq(createUser.getSeq());
        orderJurisdiction.setCreateUserSeq(0);
        orderJurisdiction.setIsAdministrator(1);
        orderJurisdiction = orderUserJurisdictionDao.selectOne(orderJurisdiction);

        // 有订货平台权限为子账号添加权限
        if (orderJurisdiction != null) {
            OrderUserJurisdictionEntity curOrderJurisdiction = new OrderUserJurisdictionEntity();
            curOrderJurisdiction.setIsAdministrator(0);
            curOrderJurisdiction.setIsDisable(0);
            curOrderJurisdiction.setUserSeq(initedUserSeq);
            curOrderJurisdiction.setCreateUserSeq(createUser.getSeq());
            curOrderJurisdiction.setEffectiveDate(orderJurisdiction.getEffectiveDate());
            curOrderJurisdiction.setDel(0);
            curOrderJurisdiction.setIntOfCreateUsers(0);
            curOrderJurisdiction.setAlreadyCreateUsers(0);
            orderUserJurisdictionDao.insert(curOrderJurisdiction);
        }

        // 查询工厂账号分销平台权限
        OnlineUserJurisdictionEntity onlineJurisdiction = new OnlineUserJurisdictionEntity();
        onlineJurisdiction.setUserSeq(createUser.getSeq());
        onlineJurisdiction.setCreateUserSeq(0);
        onlineJurisdiction.setIsAdministrator(1);
        onlineJurisdiction = onlineUserJurisdictionDao.selectOne(onlineJurisdiction);

        // 有分销平台权限为子账号添加权限
        if (onlineJurisdiction != null) {
            OnlineUserJurisdictionEntity curOnlineJurisdiction = new OnlineUserJurisdictionEntity();
            curOnlineJurisdiction.setIsAdministrator(0);
            curOnlineJurisdiction.setIsDisable(0);
            curOnlineJurisdiction.setUserSeq(initedUserSeq);
            curOnlineJurisdiction.setCreateUserSeq(createUser.getSeq());
            curOnlineJurisdiction.setEffectiveDate(onlineJurisdiction.getEffectiveDate());
            curOnlineJurisdiction.setDel(0);
            onlineUserJurisdictionDao.insert(curOnlineJurisdiction);
        }

        // 插入用户的菜单
        sysUserMenuService.updateUserMenu(initedUserSeq.longValue(), menus);
    }

    @Override
    @Transactional
    public void updateSubAccount(BaseUserEntity baseUserEntity, List<Long> menus, BaseUserEntity createUser) {
        // 判断账号是否存在，并保存当前用户信息
        BaseUserEntity oldUserEntity = baseUserService.selectOne(new EntityWrapper<BaseUserEntity>()
                .eq("AccountName", baseUserEntity.getAccountName().trim())
                .eq("Seq", baseUserEntity.getSeq())
                .eq("Del", 0));
        if (oldUserEntity == null) {
            throw new RRException("修改的账号不存在！");
        }

        if(StringUtils.isNotBlank(baseUserEntity.getPassword())) {
	        String newPassword = DigestUtils.sha256Hex(baseUserEntity.getPassword());
	        oldUserEntity.setPassword(newPassword);
        }
        
        oldUserEntity.setTelephone(baseUserEntity.getTelephone().trim());
        oldUserEntity.setUserName(baseUserEntity.getUserName().trim());
        baseUserService.updateById(oldUserEntity);

        Integer initedUserSeq = oldUserEntity.getSeq();
        rongCloudUtils.refreshUserInfo(initedUserSeq, baseUserEntity.getUserName().trim(), "123");

        // 插入用户的菜单
        sysUserMenuService.updateUserMenu(initedUserSeq.longValue(), menus);
    }

    @Override
    @Transactional
    public void delSubAccount(Long seq, BaseUserEntity createUser) {
        BaseUserEntity oldUserEntity = baseUserService.selectOne(new EntityWrapper<BaseUserEntity>()
                .eq("Seq", seq)
                .eq("Del", 0));
        if (oldUserEntity == null) {
            throw new RRException("账号不存在！");
        }

        orderUserJurisdictionDao.deleteByMap(new MapUtils().put("User_Seq", seq).put("CreateUser_Seq", createUser.getSeq()));
        onlineUserJurisdictionDao.deleteByMap(new MapUtils().put("User_Seq", seq).put("CreateUser_Seq", createUser.getSeq()));

        sysUserMenuService.updateUserMenu(seq, null);

        baseUserService.deleteById(seq.intValue());
    }
}
