package io.nuite.modules.sr_base.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.nuite.modules.sr_base.entity.GoodsShoesEntity;
import io.nuite.modules.system.erp.entity.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 0
 *
 * @author admin
 * @email
 * @date 2018-04-11 11:38:09
 */
@Mapper
public interface GoodsShoesDao extends BaseMapper<GoodsShoesEntity> {

    List<String> selectGoodsIDsByCompanySeq(@Param("companySeq") Integer companySeq);

    Integer selectGoodsSeqByGoodsID(@Param("companySeq") Integer companySeq, @Param("goodsId") String goodsID);

    /**
     * 查找属性选项是否与商品有关联
     * @param companySeq
     * @param sxid
     * @param optCode
     * @return
     */
    int getCountBySXAndOption(@Param("companySeq") Integer companySeq, @Param("sxid") String sxid, @Param("optCode") String optCode);

    /**
     * 根据工厂号查询所有关联商品
     * @param companySeq
     * @return
     */
    List<GoodsShoesEntity> selectGoodsByCompanySeq(@Param("companySeq") Integer companySeq);

    List<GoodsVo> getShoesDataToERP(@Param("list") List<Integer> shoesSeqs);
    
    List<Map<String, Object>> getRankList(@Param("companySeq") Integer companySeq,@Param("periodSeq") Integer periodSeq,@Param("categorySeq") Integer categorySeq,@Param("type") Integer type);

    List<Map<String, Object>> getCategory(@Param("companySeq")Integer companySeq,@Param("periodSeq")Integer periodSeq,@Param("categorySeq") Integer categorySeq,@Param("type") Integer type);

    List<Map<String, Object>> getSumRankList(@Param("companySeq") Integer companySeq,@Param("periodSeq") Integer periodSeq);

    /**
     * 订单下货品
     * @param map
     * @return
     * @throws Exception
     */
    List<Map<String, Object>>getOrderGoods(Map<String, Object> map) throws Exception;
}
