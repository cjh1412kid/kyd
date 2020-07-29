package com.nuite.mobile.appversion.mapper;


import com.nuite.mobile.appversion.entity.Version;
import com.nuite.mobile.dao.BaseDaoService;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 连接数据库 实例类
 *
 * @author fengjunming_t
 */
@Mapper
public interface VersionMapper extends BaseDaoService<Version> {
    /**
     * 查询 版本 下载地址
     *
     * @param map
     * @return
     */
    List<Version> getVersionInfo(Map<String, Object> map);

}
