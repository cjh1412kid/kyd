package io.nuite.modules.online_sales_app.service;

import com.baomidou.mybatisplus.service.IService;
import io.nuite.common.utils.R;
import io.nuite.modules.online_sales_app.entity.UserDeliveryInfo;

public interface OlsAddressService extends IService<UserDeliveryInfo> {
    void editAddress(UserDeliveryInfo userDeliveryInfo);

    R deleteAddress(Long addressSeq, Integer userSeq);

    R updateDefault(Long addressSeq, Integer userSeq,Boolean isdefault);
}
