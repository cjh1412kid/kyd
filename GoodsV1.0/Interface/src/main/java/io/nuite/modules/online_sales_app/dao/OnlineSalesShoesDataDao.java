package io.nuite.modules.online_sales_app.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.nuite.modules.online_sales_app.entity.ShoesDataEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 0
 *
 * @author admin
 * @email
 * @date 2018-04-16 15:41:03
 */
@Mapper
public interface OnlineSalesShoesDataDao extends BaseMapper<ShoesDataEntity> {
    Map<String, Object> getStockByShoesSeq(@Param("shoesSeq") Integer seq);

    void changeShoesDataStock(@Param("shoesDataSeq") Integer shoesDataSeq, @Param("changNum") Integer changNum);

    List<ShoesDataEntity> queryDataAndColorName(@Param("goodsSeq") Integer goodsSeq);

    Map<String, Object> queryGoodsDetail(@Param("shoesDataSeq") Integer seq);

    void deleteByShoesSeq(@Param("seq")Integer seq);
}
