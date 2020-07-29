package com.nuite.modules.platform.service;

import com.baomidou.mybatisplus.service.IService;
import com.nuite.modules.platform.entity.Platform;
import org.hibernate.validator.constraints.NotBlank;

public interface PlatformService extends IService<Platform> {
    Platform queryPlatformByKey(@NotBlank String key);
}
