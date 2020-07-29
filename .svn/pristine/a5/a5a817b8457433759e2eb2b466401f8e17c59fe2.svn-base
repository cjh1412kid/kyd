package io.nuite.modules.system.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.nuite.modules.system.entity.SizeAllotTemplateEntity;


public interface SizeAllotTemplateService extends IService<SizeAllotTemplateEntity> {


	List<Map<String, Object>> getSizeAllotTemplateList(Integer companySeq, Integer minSize, Integer maxSize);

	List<Map<String, Object>> getSizeAllotTemplateDetail(Integer sizeAllotTemplateSeq);

    
    /**
     * 查询当前公司的所有配码模版
     *
     * @param companySeq
     * @return
     */
    Map<String,List<SizeAllotTemplateEntity>> listAllByCompanySeq(Integer companySeq);
    
    /**
     * 删除指定配码模版
     *
     * @param templateSeq
     */
    void delTemplateBySeq(Integer templateSeq);
    
    /**
     * 保存配码模版
     *
     * @param sizeAllotTemplateEntity
     */
    void saveTemplate(SizeAllotTemplateEntity sizeAllotTemplateEntity);
}

