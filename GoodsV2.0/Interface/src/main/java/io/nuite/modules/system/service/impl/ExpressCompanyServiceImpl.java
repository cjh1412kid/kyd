package io.nuite.modules.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.Query;
import io.nuite.modules.order_platform_app.dao.ExpressCompanyDao;
import io.nuite.modules.order_platform_app.entity.ExpressCompanyEntity;
import io.nuite.modules.order_platform_app.entity.ReceiverInfoEntity;
import io.nuite.modules.system.service.ExpressCompanyService;

@Service
public class ExpressCompanyServiceImpl  extends ServiceImpl<ExpressCompanyDao, ExpressCompanyEntity> implements ExpressCompanyService {

	@Autowired
	private ExpressCompanyDao expressCompanyDao;
	
	@Override
	public PageUtils getExpressList(Map<String, Object> params) {
		 Page<ExpressCompanyEntity> page = super.selectPage(
		            new Query<ExpressCompanyEntity>(params).getPage(),
		            new EntityWrapper<ExpressCompanyEntity>().orderBy("InputTime",false)
		   );
		return  new PageUtils(page);
	}

	@Override
	public List<ExpressCompanyEntity> getAllExpressList() {
		Wrapper<ExpressCompanyEntity> wrapper = new EntityWrapper<ExpressCompanyEntity>();
		return expressCompanyDao.selectList(wrapper);
	}

}
