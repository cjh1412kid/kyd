package io.nuite.modules.order_platform_app.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.nuite.modules.order_platform_app.entity.MeetingGoodsEntity;
import io.nuite.modules.order_platform_app.entity.ShareRecordEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @description: 分享记录Dao接口
 * @author: jxj
 * @create: 2019-09-23 17:13
 */
@Mapper
public interface ShareRecordDao extends BaseMapper<ShareRecordEntity> {
    /**
     * 批量插入分享记录
     * @param shareRecordEntities
     * @return
     * @throws Exception
     */
    Integer insertShareRecord(List<ShareRecordEntity> shareRecordEntities) throws Exception;

    /**
     * 分享记录列表
     * @param map
     * @param page
     * @return
     * @throws Exception
     */
    List<MeetingGoodsEntity> selectShareRecord(Map<String,Object> map,Page<MeetingGoodsEntity> page) throws Exception;
}
