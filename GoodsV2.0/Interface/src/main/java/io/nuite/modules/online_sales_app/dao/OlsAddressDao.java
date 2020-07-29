package io.nuite.modules.online_sales_app.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.nuite.modules.online_sales_app.entity.UserDeliveryInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OlsAddressDao extends BaseMapper<UserDeliveryInfo> {
}
