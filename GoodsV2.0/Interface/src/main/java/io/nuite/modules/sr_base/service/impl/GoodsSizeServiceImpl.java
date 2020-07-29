package io.nuite.modules.sr_base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.nuite.modules.sr_base.dao.GoodsSizeDao;
import io.nuite.modules.sr_base.entity.GoodsSizeEntity;
import io.nuite.modules.sr_base.service.GoodsSizeService;

@Service
public class GoodsSizeServiceImpl extends ServiceImpl<GoodsSizeDao, GoodsSizeEntity> implements GoodsSizeService {

    @Autowired
    private GoodsSizeDao goodsSizeDao;
    
    
    
	/**
	 * 根据seq获取尺码
	 */
	@Override
	public GoodsSizeEntity getGoodsSizeBySeq(Integer sizeSeq) {
		return goodsSizeDao.selectById(sizeSeq);
	}


	
}
