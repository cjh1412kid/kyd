package io.nuite.modules.system.dao.order_platform;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderPartyManagementDao {

    /**
     * 根据用户seq删除订货方
     */
    Integer deleteOrderParty(@Param("seq") Integer seq);

    /**
     * 查询该用户当前剩余的创建用户的名额
     */
    int restOfQuota(@Param("userSeq") Long userSeq);

}
