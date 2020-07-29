package io.nuite.modules.sr_base.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.nuite.modules.sr_base.dao.GoodsPeriodDao;
import io.nuite.modules.sr_base.entity.GoodsPeriodEntity;
import io.nuite.modules.sr_base.service.GoodsPeriodService;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsPeriodServiceImpl extends ServiceImpl<GoodsPeriodDao, GoodsPeriodEntity> implements GoodsPeriodService {

	@Autowired
	private GoodsPeriodDao goodsPeriodDao;
	
	@Override
	public Integer getPeriodByTime(Integer brandSeq, Date date) {
		Integer seq=goodsPeriodDao.getPeriodByTime(brandSeq, date);
		
		return seq;
	}
}
