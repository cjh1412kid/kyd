package com.nuite.modules.platform.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.nuite.modules.platform.dao.PlatformDao;
import com.nuite.modules.platform.entity.Platform;
import com.nuite.modules.platform.service.PlatformService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PlatformServiceImpl extends ServiceImpl<PlatformDao, Platform> implements PlatformService {

    @Override
    public Platform queryPlatformByKey(@NotBlank String key) {
        EntityWrapper<Platform> wrapper = new EntityWrapper<>();
        wrapper.eq("PlatKey", key);
        List<Platform> platformList = selectList(wrapper);
        if (platformList == null || platformList.isEmpty()) {
            return null;
        } else {
            if (platformList.size() > 1) {
                log.warn("平台账号 key:" + key + " 有多个平台账号使用");
            }
            return platformList.get(0);
        }
    }
}
