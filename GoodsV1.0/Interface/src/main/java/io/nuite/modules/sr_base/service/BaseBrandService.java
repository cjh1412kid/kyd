package io.nuite.modules.sr_base.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import io.nuite.modules.sr_base.entity.BaseBrandEntity;

public interface BaseBrandService extends IService<BaseBrandEntity> {

	Map<String, Object> getBrandMapBySeq(Integer brandSeq);
}
