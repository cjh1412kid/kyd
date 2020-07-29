package io.nuite.modules.order_platform_app.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.nuite.modules.order_platform_app.entity.ShoesDataEntity;
import io.nuite.modules.system.erp.entity.GoodsStock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 0
 *
 * @author admin
 * @email
 * @date 2018-04-11 11:29:58
 */
@Mapper
public interface ShoesDataDao extends BaseMapper<ShoesDataEntity> {

    void changeShoesDataStock(@Param("shoesDataSeq") Integer shoesDataSeq, @Param("changNum") Integer changNum);

    List<ShoesDataEntity> queryDataAndColorName(@Param("goodsSeq") Integer goodsSeq);

    ShoesDataEntity selectByGoodsseqAndColorseqAndSizeseq(@Param("goodsSeq") Integer goodsSeq, @Param("colorSeq") Integer colorSeq, @Param("sizeSeq") Integer sizeSeq);

    List<Integer> selectByShoeSeq(@Param("shoeSeq") Integer shoeSeq);

    List<Integer> getShoesSeqsByShoesDataSeqs(@Param("list") List<Integer> shoesDataSeqs);

    List<GoodsStock> getShoeDataByShoeSeq(@Param("shoeSeq")Integer shoeSeq);

    void deleteByShoesSeq(@Param("seq")Integer seq);
}
