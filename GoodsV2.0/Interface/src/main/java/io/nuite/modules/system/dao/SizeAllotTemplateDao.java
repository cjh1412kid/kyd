package io.nuite.modules.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.nuite.modules.system.entity.SizeAllotTemplateEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 配码模版
 * 
 * @author yangchuang
 * @email ${email}
 * @date 2019-04-26 10:18:29
 */
@Mapper
public interface SizeAllotTemplateDao extends BaseMapper<SizeAllotTemplateEntity> {
    
    /**
     * 查询当前公司的所有配码模版
     *
     * @param companySeq
     * @return
     */
    List<SizeAllotTemplateEntity> selectAllByCompanySeq(@Param("companySeq") Integer companySeq);
}
