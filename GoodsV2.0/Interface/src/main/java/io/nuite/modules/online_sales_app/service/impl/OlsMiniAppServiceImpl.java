package io.nuite.modules.online_sales_app.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.nuite.modules.online_sales_app.dao.OlsMiniAppDao;
import io.nuite.modules.online_sales_app.entity.MiniAppEntity;
import io.nuite.modules.online_sales_app.service.OlsMiniAppService;
import org.springframework.stereotype.Service;

@Service
public class OlsMiniAppServiceImpl extends ServiceImpl<OlsMiniAppDao, MiniAppEntity> implements OlsMiniAppService {
    public MiniAppEntity queryOneByCompanySeq(Integer companySeq) {
        EntityWrapper<MiniAppEntity> ew = new EntityWrapper<>();
        ew.eq("Company_Seq", companySeq);
        return selectOne(ew);
    }
}
