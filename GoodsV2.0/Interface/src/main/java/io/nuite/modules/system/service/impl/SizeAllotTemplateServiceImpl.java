package io.nuite.modules.system.service.impl;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.nuite.modules.system.dao.SizeAllotTemplateDao;
import io.nuite.modules.system.dao.SizeAllotTemplateDetailDao;
import io.nuite.modules.system.entity.SizeAllotTemplateDetailEntity;
import io.nuite.modules.system.entity.SizeAllotTemplateEntity;
import io.nuite.modules.system.service.SizeAllotTemplateService;

@Service
public class SizeAllotTemplateServiceImpl extends ServiceImpl<SizeAllotTemplateDao, SizeAllotTemplateEntity> implements SizeAllotTemplateService {


	@Autowired
	private SizeAllotTemplateDao sizeAllotTemplateDao;
	
	@Autowired
	private SizeAllotTemplateDetailDao sizeAllotTemplateDetailDao;
	
	
	
	/**
	 * 查询尺码范围适合的配码模板
	 */
	@Override
	public List<Map<String, Object>> getSizeAllotTemplateList(Integer companySeq, Integer minSize, Integer maxSize) {
		Wrapper<SizeAllotTemplateEntity> wrapper = new EntityWrapper<SizeAllotTemplateEntity>();
		wrapper.setSqlSelect("Seq AS seq, Name AS name")
		.where("Company_Seq = {0} AND MinSize = {1} AND MaxSize = {2}", companySeq, minSize, maxSize).orderBy("InputTime DESC");
		return sizeAllotTemplateDao.selectMaps(wrapper);
	}

	

	/**
	 * 查询模板详情
	 */
	@Override
	public List<Map<String, Object>> getSizeAllotTemplateDetail(Integer sizeAllotTemplateSeq) {
		Wrapper<SizeAllotTemplateDetailEntity> wrapper = new EntityWrapper<SizeAllotTemplateDetailEntity>();
		wrapper.setSqlSelect("Size AS size, Per AS per")
		.where("TemplateSeq = {0}", sizeAllotTemplateSeq).orderBy("Size ASC");
		return sizeAllotTemplateDetailDao.selectMaps(wrapper);
	}


	

    
    @Override
    public  Map<String,List<SizeAllotTemplateEntity>> listAllByCompanySeq(Integer companySeq) {
        List<SizeAllotTemplateEntity> sizeAllotTemplateEntities = sizeAllotTemplateDao.selectAllByCompanySeq(companySeq);
    
        Map<String,List<SizeAllotTemplateEntity>> result=new LinkedHashMap<>();
        for (SizeAllotTemplateEntity template : sizeAllotTemplateEntities) {
            String sizeKey = template.getMinSize() + " - " + template.getMaxSize();
            if (result.containsKey(sizeKey)) {
                List<SizeAllotTemplateEntity> sizeAllotTemplates= result.get(sizeKey);
                sizeAllotTemplates.add(template);
            }else{
                List<SizeAllotTemplateEntity> list=new ArrayList<>();
                list.add(template);
                result.put(sizeKey,list);
            }
        }
    
        return result;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delTemplateBySeq(Integer templateSeq) {
        
        sizeAllotTemplateDetailDao.delete(new EntityWrapper<SizeAllotTemplateDetailEntity>()
            .eq("TemplateSeq", templateSeq));
        
        sizeAllotTemplateDao.deleteById(templateSeq);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTemplate(SizeAllotTemplateEntity sizeAllotTemplateEntity) {
        super.insert(sizeAllotTemplateEntity);
        
        for (SizeAllotTemplateDetailEntity detail : sizeAllotTemplateEntity.getDetails()) {
            detail.setTemplateSeq(sizeAllotTemplateEntity.getSeq());
            sizeAllotTemplateDetailDao.insert(detail);
        }
        
    }
}
