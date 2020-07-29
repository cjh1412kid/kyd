package io.nuite.modules.sr_base.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.nuite.modules.sr_base.entity.GoodsColorEntity;
import org.apache.ibatis.annotations.Param;

/**
 * 0
 * 
 * @author admin
 * @email 
 * @date 2018-04-11 11:38:09
 */
@Mapper
public interface GoodsColorDao extends BaseMapper<GoodsColorEntity> {

    /**
     * 根据工厂id和颜色代码查询seq
     * @param companySeq
     * @param code
     * @return
     */
    Integer selectColorSeqByCode(@Param("companySeq") Integer companySeq, @Param("code") String code);

    Integer selectColorByColorName(@Param("companySeq") Integer companySeq, @Param("name") String name);
}
