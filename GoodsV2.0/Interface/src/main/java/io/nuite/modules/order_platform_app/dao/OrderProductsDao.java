package io.nuite.modules.order_platform_app.dao;

import io.nuite.modules.order_platform_app.entity.OrderProductsEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 0
 * 
 * @author admin
 * @email 
 * @date 2018-04-11 11:29:57
 */
@Mapper
public interface OrderProductsDao extends BaseMapper<OrderProductsEntity> {

    List<Integer> getShoeDataSeqsByOrderseqs(@Param("orderSeqs") List<Integer> orderSeqs);

    /**
     * 订单总价
     * @param map
     * @return
     * @throws Exception
     */
    BigDecimal getTotalMoney(Map<String,Object> map) throws Exception;
}
