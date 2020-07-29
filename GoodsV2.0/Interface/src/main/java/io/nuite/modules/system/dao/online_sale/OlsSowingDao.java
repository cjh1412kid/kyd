package io.nuite.modules.system.dao.online_sale;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.nuite.modules.system.entity.online_sale.OlsSowingEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OlsSowingDao extends BaseMapper<OlsSowingEntity> {
    List<Map<String, Object>> selectOlsGoodsList(Page<Map<String, Object>> page, Map<String, Object> conditions);
}
