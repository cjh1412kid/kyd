package io.nuite.modules.system.service.impl;

import io.nuite.common.utils.Constant;
import io.nuite.common.utils.PageUtils;
import io.nuite.modules.order_platform_app.utils.GotyeUtils;
import io.nuite.modules.order_platform_app.utils.RongCloudUtils;
import io.nuite.modules.sr_base.entity.BaseBrandEntity;
import io.nuite.modules.sr_base.entity.BaseCompanyEntity;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.sr_base.service.BaseBrandService;
import io.nuite.modules.sr_base.service.BaseCompanyService;
import io.nuite.modules.sr_base.service.BaseUserService;
import io.nuite.modules.sr_base.service.SysUserMenuService;
import io.nuite.modules.system.dao.SystemFactoryDao;
import io.nuite.modules.system.entity.SysFactoryEntity;
import io.nuite.modules.system.service.SystemFactoryService;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class SystemFactoryServiceImpl implements SystemFactoryService {

    @Autowired
    private SystemFactoryDao systemFactoryDao;

    @Autowired
    private SysUserMenuService sysUserMenuService;

    @Autowired
    private BaseUserService baseUserService;

    @Autowired
    private BaseCompanyService baseCompanyService;

    @Autowired
    private BaseBrandService baseBrandService;

    @Autowired
    private RongCloudUtils rongCloudUtils;

    @Autowired
    private GotyeUtils gotyeUtils;

    @Override
    public PageUtils queryFactoryPage(Map<String, Object> params) {
        int limit = Integer.valueOf(params.get("limit").toString());
        int page = Integer.valueOf(params.get("page").toString());
        Object keywords = params.get("keywords");
        List<SysFactoryEntity> list = systemFactoryDao.queryFactoryPage(limit, page, keywords);
        int totalCount = systemFactoryDao.queryFactoryTotal(keywords);
        return new PageUtils(list, totalCount, limit, page);
    }


    @Override
    public SysFactoryEntity queryFactory(Long seq) {
        SysFactoryEntity sysFactoryEntity = systemFactoryDao.queryFactoryOne(seq);
        List<Long> allMenuId = baseUserService.queryAllMenuId(sysFactoryEntity.getSeq());
        sysFactoryEntity.setMenuIdList(allMenuId);
        return sysFactoryEntity;
    }

    @Override
    @Transactional
    public void saveFactory(SysFactoryEntity factoryEntity) {
        // 保存公司信息
        BaseCompanyEntity baseCompanyEntity = new BaseCompanyEntity();
        baseCompanyEntity.setName(factoryEntity.getCompanyName());
        baseCompanyEntity.setCompanyTypeSeq(1);
        baseCompanyEntity.setParentSeq(0);
        Long companySeq = factoryEntity.getCompanySeq();
        if (companySeq != null) {
            baseCompanyEntity.setSeq(companySeq.intValue());
        }
        baseCompanyService.insertOrUpdate(baseCompanyEntity);

        Integer initedCompanySeq = baseCompanyEntity.getSeq();

        // 保存品牌信息
        BaseBrandEntity baseBrandEntity = new BaseBrandEntity();
        baseBrandEntity.setName(factoryEntity.getBrandName());
        baseBrandEntity.setCompanySeq(initedCompanySeq);
        Long brandSeq = factoryEntity.getBrandSeq();
        if (brandSeq != null) {
            baseBrandEntity.setSeq(brandSeq.intValue());
        }

        baseBrandService.insertOrUpdate(baseBrandEntity);

        Integer initedBrandSeq = baseBrandEntity.getSeq();

        // 保存用户信息
        BaseUserEntity baseUserEntity = new BaseUserEntity();
        baseUserEntity.setAccountName(factoryEntity.getAccountName());
        baseUserEntity.setUserName(factoryEntity.getUserName());
        baseUserEntity.setCompanySeq(initedCompanySeq);
        baseUserEntity.setBrandSeq(initedBrandSeq);
        baseUserEntity.setShopSeq("");
        baseUserEntity.setSaleType(Constant.UserSaleType.FACTORY.getValue());
        baseUserEntity.setAttachType(Constant.UserAttachType.FACTORY.getValue());
        if (factoryEntity.getSeq() != null) {
            baseUserEntity.setSeq(factoryEntity.getSeq().intValue());
        } else {
            String newPassword = DigestUtils.sha256Hex("123");
            baseUserEntity.setPassword(newPassword);
            baseBrandEntity.setName("default");
        }

        baseUserService.insertOrUpdate(baseUserEntity);

        Integer initedUserSeq = baseUserEntity.getSeq();

        // 获取融云token
        baseUserEntity = baseUserService.selectById(initedUserSeq);
        String userName = baseUserEntity.getUserName();
        String rongCloudToken = baseUserEntity.getRongCloudToken();
        if (userName == null) userName = "default";
        String newRongCloudToken;
        if (StringUtils.isBlank(rongCloudToken)) {
            newRongCloudToken = rongCloudUtils.registerUser(initedUserSeq, userName, "123");
            BaseUserEntity rongCloudUpdate = new BaseUserEntity();
            rongCloudUpdate.setSeq(initedUserSeq);
            rongCloudUpdate.setRongCloudToken(newRongCloudToken);
            baseUserService.updateById(rongCloudUpdate);
        } else {
            rongCloudUtils.refreshUserInfo(initedUserSeq, userName, "123");
        }

        /*try {
            //创建直播间
            JSONObject roomsObject = gotyeUtils.getRooms(initedUserSeq.toString());
            if (roomsObject == null) {
                gotyeUtils.createRoom(factoryEntity.getCompanyName(), initedUserSeq.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        // 更新订货平台信息
        if (factoryEntity.getHasOrderPlatform()) {
            if (factoryEntity.getOpSeq() != null) {
                systemFactoryDao.updateOpUserJurisdiction(factoryEntity.getOpSeq(), factoryEntity.getOpDate(), factoryEntity.getUserNumbers());
            } else {
                systemFactoryDao.insertOpUserJurisdiction(initedUserSeq, factoryEntity.getOpDate(), factoryEntity.getUserNumbers());
            }
        } else {
            //删除user对应的权限
            systemFactoryDao.delOpUserJurisdiction(initedUserSeq);
        }

        // 更新分销平台信息
        if (factoryEntity.getHasOnlineSale()) {
            if (factoryEntity.getOlsSeq() != null && factoryEntity.getOlsDate() != null) {
                systemFactoryDao.updateOlsUserJurisdiction(factoryEntity.getOlsSeq(), factoryEntity.getOlsDate());
            } else {
                systemFactoryDao.insertOlsUserJurisdiction(initedUserSeq, factoryEntity.getOlsDate());
            }
        } else {
            //删除user对应的权限
            systemFactoryDao.delOlsUserJurisdiction(initedUserSeq);
        }

        // 更新菜单权限
        List<Long> menuIdList = factoryEntity.getMenuIdList();
        sysUserMenuService.updateUserMenu(initedUserSeq.longValue(), menuIdList);
    }

    @Override
    public void deleteFactory(Long seq) {
        //todo 删除工厂的所有子账号和对应的菜单
        //todo 删除用户对应的菜单
    }
}
