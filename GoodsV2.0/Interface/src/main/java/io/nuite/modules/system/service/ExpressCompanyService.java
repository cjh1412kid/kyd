package io.nuite.modules.system.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.nuite.common.utils.PageUtils;
import io.nuite.modules.order_platform_app.entity.ExpressCompanyEntity;

public interface ExpressCompanyService extends IService<ExpressCompanyEntity> {

	 PageUtils getExpressList(Map<String, Object> params);
	 
	 List<ExpressCompanyEntity> getAllExpressList();
}
