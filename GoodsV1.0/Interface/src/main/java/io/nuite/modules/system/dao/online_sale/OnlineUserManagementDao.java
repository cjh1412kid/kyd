package io.nuite.modules.system.dao.online_sale;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OnlineUserManagementDao {

    /**
     * 客户列表
     * */
    List<Map<String,Object>> getUsersList(Long userSeq, String keywords, Integer page, Integer limit);

}
