package io.nuite.modules.order_platform_app.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import io.nuite.modules.order_platform_app.entity.MeetingGoodsEntity;
import io.nuite.modules.order_platform_app.entity.ShareRecordEntity;
import io.nuite.modules.sr_base.entity.BaseUserEntity;

import java.util.List;

/**
 * @description: 分享记录Service接口
 * @author: jxj
 * @create: 2019-09-23 17:22
 */

public interface ShareRecordService extends IService<ShareRecordEntity> {
    /**
     * 批量新增分享记录
     * @param baseUserEntity
     * @param meetingGoodsSeqs
     * @return
     * @throws Exception
     */
    boolean insertShareRecord(BaseUserEntity baseUserEntity,List<Integer> meetingGoodsSeqs) throws Exception;

    /**
     * 分享记录列表
     * @param baseUserEntity
     * @param page
     * @return
     * @throws Exception
     */
    List<MeetingGoodsEntity> getShareRecordList(BaseUserEntity baseUserEntity,Page<MeetingGoodsEntity> page,Integer meetingSeq,String year,Integer type,Integer status) throws Exception;
}
