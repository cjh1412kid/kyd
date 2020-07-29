package io.nuite.modules.sr_base.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.nuite.modules.sr_base.dao.GoodsShoesDao;
import io.nuite.modules.sr_base.entity.GoodsShoesEntity;
import io.nuite.modules.sr_base.service.GoodsShoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsShoesServiceImpl extends ServiceImpl<GoodsShoesDao, GoodsShoesEntity> implements GoodsShoesService {

    @Autowired
    private GoodsShoesDao goodsShoesDao;

    /**
     * 根据公司seq、货号获取商品
     */
    @Override
    public GoodsShoesEntity getGoodsByGoodId(Integer companySeq, String goodId) {
        GoodsShoesEntity goodsEntity = new GoodsShoesEntity();
        goodsEntity.setCompanySeq(companySeq);
        goodsEntity.setGoodID(goodId);
        return goodsShoesDao.selectOne(goodsEntity);
    }

    @Override
    public int getCountBySXAndOption(Integer companySeq, String sxid, String optCode) {
        return goodsShoesDao.getCountBySXAndOption(companySeq, sxid, optCode);
    }
}
