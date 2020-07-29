package io.nuite.modules.system.service;

import com.baomidou.mybatisplus.service.IService;

import io.nuite.modules.system.entity.OrderAgreementEntity;


public interface OrderAgreementService extends IService<OrderAgreementEntity> {

	OrderAgreementEntity getCompanyOrderAgreement(Integer companySeq);

	  /**
     * 保存用户编辑的内容
     *
     * @param ur
     */
    Boolean saveOrUpdate(OrderAgreementEntity oa);
    
    /**
     * 根据id更新用户信息
     *
     * @param ur
     * @return
     */
    Boolean update(OrderAgreementEntity oa);
}

