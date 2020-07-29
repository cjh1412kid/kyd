package io.nuite.modules.sr_base.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.nuite.modules.sr_base.dao.BaseShopDao;
import io.nuite.modules.sr_base.entity.BaseShopEntity;
import io.nuite.modules.sr_base.service.BaseShopService;
import org.springframework.stereotype.Service;

@Service
public class BaseShopServiceImpl extends ServiceImpl<BaseShopDao, BaseShopEntity> implements BaseShopService {
}
