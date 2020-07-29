package io.nuite.modules.system.dao.order_platform;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.nuite.modules.system.entity.order_platform.OrderUserJurisdictionEntity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 0
 * 
 * @author admin
 * @email
 * @date 2018-04-11 11:29:58
 */
@Mapper
public interface OrderUserJurisdictionDao extends BaseMapper<OrderUserJurisdictionEntity> {

    /**
     * 修改禁用状态
     */
    void updateDisable(@Param("seq")Integer seq, @Param("disable")int disable);

    /**
     * 修改删除状态
     * @param userSeq
     * @param del
     */
    void updateDel(@Param("userSeq") Integer userSeq,@Param("del") int del);

    OrderUserJurisdictionEntity selectByUserSeq(@Param("userSeq") Integer userSeq);

}
