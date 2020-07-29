package com.nuite.modules.platform.service.impl;

import com.nuite.modules.platform.entity.Platform;
import com.nuite.modules.platform.service.PlatformService;
import com.nuite.modules.platform.service.ShiroService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShiroServiceImpl implements ShiroService {
    @Autowired
    private PlatformService platformService;


    @Override
    public Platform queryPlatformByKey(@NotBlank String key) {
        return platformService.queryPlatformByKey(key);
    }
}
