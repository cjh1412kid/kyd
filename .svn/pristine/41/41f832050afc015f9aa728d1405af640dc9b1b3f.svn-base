package io.nuite.modules.online_sales_app.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.nuite.modules.online_sales_app.entity.ShoesInfoEntity;
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
public interface OnlineSalesShoesInfoDao extends BaseMapper<ShoesInfoEntity> {
    Map<String, Object> getPlatformInfoByShoesSeq(@Param("shoesSeq") Integer seq);

    //综合排序、暂时不实现
    List<Map<String, Object>> queryShoesPage(@Param("page") int page,
                                             @Param("limit") int limit,
                                             @Param("orderDir") String orderDir,
                                             @Param("brandSeq") Long brandSeq,
                                             @Param("topicSeq") Long topicSeq);

    //根据销量排序
    List<Map<String, Object>> queryShoesPageOrderBySales(@Param("page") int page,
                                                         @Param("limit") int limit,
                                                         @Param("orderDir") String orderDir,
                                                         @Param("brandSeq") Integer brandSeq,
                                                         @Param("topicSeq") Integer topicSeq,
                                                         @Param("categorySeq") Integer categorySeq);

    //根据价格排序
    List<Map<String, Object>> queryShoesPageOrderByPrice(@Param("page") int page,
                                                         @Param("limit") int limit,
                                                         @Param("orderDir") String orderDir,
                                                         @Param("brandSeq") Integer brandSeq,
                                                         @Param("topicSeq") Integer topicSeq,
                                                         @Param("categorySeq") Integer categorySeq);

    //根据上架时间排序
    List<Map<String, Object>> queryShoesPageOrderByTime(@Param("page") int page,
                                                        @Param("limit") int limit,
                                                        @Param("orderDir") String orderDir,
                                                        @Param("brandSeq") Integer brandSeq,
                                                        @Param("topicSeq") Integer topicSeq,
                                                        @Param("categorySeq") Integer categorySeq);

    int queryShoesPageTotal(@Param("brandSeq") Integer brandSeq, @Param("topicSeq") Integer topicSeq);

    void deleteByShoesSeq(@Param("goodsSeq") Integer goodsSeq);

    void updateOnSale(@Param("goodsSeq") Integer goodsSeq, @Param("onSale") int onSale);

    List<Map<String, Object>> queryCurrentAllShoes(Map<String, Object> map);
    
    ShoesInfoEntity getShoeInfoByShoesSeq(@Param("shoesSeq")Integer shoesSeq);

	List<Map<String, Object>> queryMiniAppCodeExcelShoes(Map<String, Object> map);
}
