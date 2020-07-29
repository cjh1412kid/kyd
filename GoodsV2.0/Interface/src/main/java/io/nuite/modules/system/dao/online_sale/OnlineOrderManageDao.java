package io.nuite.modules.system.dao.online_sale;

import io.nuite.modules.system.entity.online_sale.OnlineOrderEntity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OnlineOrderManageDao {
    /**
     * 在同等的查询条件并且没有分页的条件下查询所有的条数
     */
     int getTotalCount(@Param("orderStatus")Integer orderStatus, @Param("keywords")String keywords, @Param("companySeq")Integer companySeq);

    /**
     * 根据用户Seq返回公司的Seq
     */
    Integer getCompanyByUserSeq(@Param("userSeq") Long userSeq);

    /**
     * 根据搜索关键词和状态序号返回订单列表
     */
    List<OnlineOrderEntity> orderList(@Param("orderStatus") Integer orderStatus, @Param("keywords") String keywords,
            @Param("companySeq") Integer companySeq, @Param("start") Integer start, @Param("num") Integer num);

    /**
     * 根据订单序号删除订单
     */
    Integer deleteOeder(@Param("seq") Integer seq);

    /**
     * 获得订单序号查询该订单中的各种鞋子信息列表
     */
    List<Map<String, Object>> getGoodsListByOrderSeq(@Param("orderSeq") Integer orderSeq);

}
