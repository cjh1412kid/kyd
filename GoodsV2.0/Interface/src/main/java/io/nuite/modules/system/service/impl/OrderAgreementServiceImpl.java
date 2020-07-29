package io.nuite.modules.system.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.nuite.modules.system.dao.OrderAgreementDao;
import io.nuite.modules.system.entity.OrderAgreementEntity;
import io.nuite.modules.system.service.OrderAgreementService;


@Service
public class OrderAgreementServiceImpl extends ServiceImpl<OrderAgreementDao, OrderAgreementEntity> implements OrderAgreementService {

    @Autowired
    private OrderAgreementDao orderAgreementDao;
	
	
	@Override
	public OrderAgreementEntity getCompanyOrderAgreement(Integer companySeq) {
		Wrapper<OrderAgreementEntity> wrapper = new EntityWrapper<OrderAgreementEntity>();
		wrapper.where("Company_Seq = {0}", companySeq);
		List<OrderAgreementEntity> list = orderAgreementDao.selectList(wrapper);
		if(list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}


	@Override
	public Boolean saveOrUpdate(OrderAgreementEntity oa) {
	        oa.setDel(0);
	        oa.setInputTime(new Date());
	        return super.insertOrUpdate(oa);
	}


	@Override
	public Boolean update(OrderAgreementEntity oa) {
	        oa.setInputTime(new Date());
		return super.insertOrUpdate(oa);
	}

}
