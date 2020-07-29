package io.nuite.modules.sr_base.dao;

import io.nuite.modules.sr_base.entity.BaseShopEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 0
 * 
 * @author admin
 * @email
 * @date 2018-04-11 11:38:09
 */
@Mapper
public interface BaseShopDao extends BaseMapper<BaseShopEntity> {

    List<Map<String, Object>> getAreaListByBrandSeq(Integer brandSeq);

    List<Map<String, Object>> getOfficeListByAreaSeq(Integer areaSeq);

    List<Map<String, Object>> getShopListByOfficeSeq(Integer officeSeq);

    List<BaseShopEntity> getShopListByAreaSeq(@Param("areaSeqList") List<Integer> areaSeqList,
            @Param("start") Integer start, @Param("num") Integer num);

    /**
     * 根据该门店的Seq返回该门店的信息
     */
    BaseShopEntity getShopBySeq(@Param("seq") Integer seq);

    /**
     * 同等条件下没有分页的数据条数
     */
    int getTotalCount(@Param("areaSeqList") List<Integer> areaSeqList);

}
