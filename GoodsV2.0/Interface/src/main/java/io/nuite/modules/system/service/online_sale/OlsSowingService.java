package io.nuite.modules.system.service.online_sale;

import com.baomidou.mybatisplus.service.IService;
import io.nuite.common.utils.PageInfo;
import io.nuite.common.utils.PageUtils;
import io.nuite.modules.system.entity.online_sale.OlsSowingEntity;

public interface OlsSowingService extends IService<OlsSowingEntity> {
    PageUtils sowingPageList(Integer brandSeq);

    PageUtils olsGoodsList(Integer brandSeq, PageInfo pageInfo);

    OlsSowingEntity selectSowing(Integer seq);

    void saveSowing(OlsSowingEntity olsSowingEntity);

    void updateSowing(Integer brandSeq, OlsSowingEntity olsSowingEntity);
}
