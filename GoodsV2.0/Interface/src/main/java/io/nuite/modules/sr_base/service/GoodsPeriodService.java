package io.nuite.modules.sr_base.service;

import java.util.Date;

import com.baomidou.mybatisplus.service.IService;
import io.nuite.modules.sr_base.entity.GoodsPeriodEntity;

public interface GoodsPeriodService extends IService<GoodsPeriodEntity> {
	
	Integer getPeriodByTime(Integer brandSeq,Date date);
}
