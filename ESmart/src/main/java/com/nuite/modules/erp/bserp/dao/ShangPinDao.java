package com.nuite.modules.erp.bserp.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.nuite.modules.erp.bserp.entity.ShangPin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: yangchuang
 * @Date: 2018/8/8 17:10
 * @Version: 1.0
 * @Description:
 */

@Mapper
public interface ShangPinDao extends BaseMapper<ShangPin> {

    /**
     * 根据年份获取当年及以后的商品
     * @return
     */
    List<ShangPin> selectOnCurrYear();

    /**
     * 根据年份获取当年之前的商品代码
     * @return
     */
    List<String> selectGoodsCodeBeforeCurrYear();


    List<ShangPin> selectByGoodsCodes(@Param("codes") List<String> codes);
}
