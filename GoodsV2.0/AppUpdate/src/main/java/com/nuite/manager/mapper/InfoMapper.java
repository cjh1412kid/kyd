package com.nuite.manager.mapper;

import com.nuite.manager.entity.Info;
import com.nuite.manager.entity.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InfoMapper {
    List<Info> listPageInfo(Page page);
}
