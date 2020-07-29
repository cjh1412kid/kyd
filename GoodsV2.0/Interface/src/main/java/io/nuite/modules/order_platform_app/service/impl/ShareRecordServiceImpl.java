package io.nuite.modules.order_platform_app.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.base.Joiner;
import io.nuite.modules.order_platform_app.dao.PublicityPicDao;
import io.nuite.modules.order_platform_app.dao.ShareRecordDao;
import io.nuite.modules.order_platform_app.entity.MeetingGoodsEntity;
import io.nuite.modules.order_platform_app.entity.ShareRecordEntity;
import io.nuite.modules.order_platform_app.service.ShareRecordService;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @description: 分享记录Service实现类
 * @author: jxj
 * @create: 2019-09-23 17:23
 */
@Service
public class ShareRecordServiceImpl extends ServiceImpl<ShareRecordDao,ShareRecordEntity> implements ShareRecordService {
    @Autowired
    private PublicityPicDao publicityPicDao;

    @Override
    public boolean insertShareRecord(BaseUserEntity baseUserEntity, List<Integer> meetingGoodsSeqs) throws Exception {
        List<ShareRecordEntity> shareRecordEntities = new ArrayList<>(10);
        for(Integer meetingGoodsSeq : meetingGoodsSeqs) {
            ShareRecordEntity shareRecordEntity = new ShareRecordEntity();
            shareRecordEntity.setMeetingGoodsSeq(meetingGoodsSeq);
            shareRecordEntity.setUserSeq(baseUserEntity.getSeq());
            shareRecordEntity.setInputTime(new Date());
            shareRecordEntities.add(shareRecordEntity);
        }
        boolean isSuccess = false;
        if(shareRecordEntities.size() > 0) {
            isSuccess = retBool(baseMapper.insertShareRecord(shareRecordEntities));
        }
        return isSuccess;
    }

    @Override
    public List<MeetingGoodsEntity> getShareRecordList(BaseUserEntity baseUserEntity,Page<MeetingGoodsEntity> page,Integer meetingSeq,String year,Integer type,Integer status) throws Exception {
        Map<String,Object> map = new HashMap<>(10);
        List<Integer> periodSeqList = publicityPicDao.getPeriodListByBrandSeq(baseUserEntity.getBrandSeq());
        map.put("periodSeq", Joiner.on(",").join(periodSeqList));
        map.put("companyTypeSeq",baseUserEntity.getSaleType());
        map.put("userSeq",baseUserEntity.getSeq());
        map.put("meetingSeq",meetingSeq);
        map.put("year",year);
        map.put("type",type);
        map.put("status",status);
        return baseMapper.selectShareRecord(map,page);
    }
}
