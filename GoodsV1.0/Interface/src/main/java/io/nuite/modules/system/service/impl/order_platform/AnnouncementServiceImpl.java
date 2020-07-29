package io.nuite.modules.system.service.impl.order_platform;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.Query;
import io.nuite.modules.order_platform_app.dao.AnnouncementDao;
import io.nuite.modules.order_platform_app.entity.AnnouncementEntity;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.system.service.order_platform.AnnouncementService;

@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementDao, AnnouncementEntity>
        implements AnnouncementService {

    @Override
    public PageUtils list(BaseUserEntity baseUserEntity, Map<String, Object> params) {
        Wrapper<AnnouncementEntity> wrapper = new EntityWrapper<AnnouncementEntity>();
        wrapper.where("Company_Seq = {0} AND Del = 0", baseUserEntity.getCompanySeq()).orderBy("InputTime DESC");
        Page<AnnouncementEntity> shoesPage = this.selectPage(new Query<AnnouncementEntity>(params).getPage(), wrapper);
        return new PageUtils(shoesPage);
    }
}
