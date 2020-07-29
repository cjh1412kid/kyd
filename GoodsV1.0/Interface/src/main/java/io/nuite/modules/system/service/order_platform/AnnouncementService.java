package io.nuite.modules.system.service.order_platform;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.nuite.common.utils.PageUtils;
import io.nuite.modules.order_platform_app.entity.AnnouncementEntity;
import io.nuite.modules.sr_base.entity.BaseUserEntity;

public interface AnnouncementService extends IService<AnnouncementEntity> {

    PageUtils list(BaseUserEntity baseUserEntity, Map<String, Object> params);
}
