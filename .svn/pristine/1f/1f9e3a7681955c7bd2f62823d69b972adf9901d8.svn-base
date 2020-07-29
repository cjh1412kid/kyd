package io.nuite.modules.online_sales_app.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.nuite.common.utils.R;
import io.nuite.modules.online_sales_app.dao.OlsAddressDao;
import io.nuite.modules.online_sales_app.entity.UserDeliveryInfo;
import io.nuite.modules.online_sales_app.service.OlsAddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OlsAddressServiceImpl extends ServiceImpl<OlsAddressDao, UserDeliveryInfo> implements OlsAddressService {

    @Override
    @Transactional
    public void editAddress(UserDeliveryInfo userDeliveryInfo) {
        insertOrUpdate(userDeliveryInfo);
    }

    @Override
    @Transactional
    public R deleteAddress(Long addressSeq, Integer userSeq) {
        UserDeliveryInfo userDeliveryInfo = selectById(addressSeq);
        if (userDeliveryInfo == null) {
            return R.error("删除的地址不存在！");
        }
        if (!userDeliveryInfo.getCustomSeq().equals(userSeq)) {
            return R.error("删除错误！");
        }

        deleteById(addressSeq);
        return R.ok();
    }

    @Override
    @Transactional
    public R updateDefault(Long addressSeq, Integer userSeq, Boolean isdefault) {
        UserDeliveryInfo userDeliveryInfo = selectById(addressSeq);
        if (userDeliveryInfo == null) {
            return R.error("删除的地址不存在！");
        }
        if (!userDeliveryInfo.getCustomSeq().equals(userSeq)) {
            return R.error("删除错误！");
        }
        UserDeliveryInfo newDe = new UserDeliveryInfo();
        newDe.setSeq(addressSeq);
        newDe.setDefault(isdefault == null ? false : isdefault);

        updateById(newDe);
        return R.ok();
    }
}
