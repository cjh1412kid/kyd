package io.nuite.modules.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.Constant.TypeOfStore;
import io.nuite.modules.sr_base.dao.BaseAreaDao;
import io.nuite.modules.sr_base.dao.BaseShopDao;
import io.nuite.modules.sr_base.dao.BaseUserDao;
import io.nuite.modules.sr_base.entity.BaseAreaEntity;
import io.nuite.modules.sr_base.entity.BaseShopEntity;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.system.service.SystemShopMmanageService;

@Service
public class SystemShopMmanageServiceImpl implements SystemShopMmanageService {

    @Autowired
    private BaseShopDao baseShopDao;

    @Autowired
    private BaseUserDao baseUserDao;

    @Autowired
    private BaseAreaDao baseAreaDao;

    @Override
    public PageUtils queryShopByUser(Long userSeq, Integer page, Integer limit) {
        Integer start = new Integer(0);
        Integer num = new Integer(0);
        if (page >= 1 && limit > 0) {
            start = (page - 1) * limit;
            num = page * limit;
        }
        BaseUserEntity baseUserEntity = new BaseUserEntity();
        baseUserEntity.setSeq(userSeq.intValue());
        baseUserEntity = baseUserDao.selectById(baseUserEntity);
        // 根据品牌查所有的地区——
        EntityWrapper<BaseAreaEntity> entityWrapper = new EntityWrapper<BaseAreaEntity>();
        entityWrapper.eq("brand_Seq", baseUserEntity.getBrandSeq()).eq("Del", 0);
        List<BaseAreaEntity> areaList = baseAreaDao.selectList(entityWrapper);
        List<Integer> areaSeqList = new ArrayList<Integer>();
        for (BaseAreaEntity baseAreaEntity : areaList) {
            areaSeqList.add(baseAreaEntity.getSeq());
        }
        // 根据区域seq集合获取门店信息
        List<BaseShopEntity> shopList = baseShopDao.getShopListByAreaSeq(areaSeqList, start, num);
        int totalCount = baseShopDao.getTotalCount(areaSeqList);
        return new PageUtils(shopList, totalCount, limit, page);
    }

    @Override
    public void delete(Integer seq) {
        BaseShopEntity baseShopEntity = new BaseShopEntity();
        baseShopEntity.setDel(1);
        baseShopEntity.setSeq(seq);
        baseShopDao.updateById(baseShopEntity);
    }

    // 根据该门店的Seq返回该门店的信息
    public BaseShopEntity getShopBySeq(Integer seq) {
        return baseShopDao.getShopBySeq(seq);
    }

    @Override
    public List<BaseAreaEntity> areaList(Long userSeq) {
        BaseUserEntity baseUser = new BaseUserEntity();
        baseUser.setSeq(userSeq.intValue());
        baseUser = baseUserDao.selectById(baseUser);
        EntityWrapper<BaseAreaEntity> entityWrapper = new EntityWrapper<BaseAreaEntity>();
        entityWrapper.eq("brand_Seq", baseUser.getBrandSeq()).eq("Del", 0);
        List<BaseAreaEntity> baseAreaEntitys = baseAreaDao.selectList(entityWrapper);
        return baseAreaEntitys;
    }

    @Override
    public void updateShop(BaseShopEntity baseShopEntity) {
        if (baseShopEntity.getInstallDate() == null) {
            return;
        }
        baseShopDao.updateById(baseShopEntity);
    }

    @Override
    public void saveShop(BaseShopEntity baseShopEntity) {
        if (baseShopEntity.getInstallDate() == null) {
            Date date = new Date();
            baseShopEntity.setInstallDate(date);
        }
        baseShopDao.insert(baseShopEntity);
    }

}