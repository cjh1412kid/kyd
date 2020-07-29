package io.nuite.modules.order_platform_app.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.nuite.modules.order_platform_app.dao.ShoesDataDao;
import io.nuite.modules.order_platform_app.entity.ShoesDataEntity;
import io.nuite.modules.order_platform_app.service.ShoesDataService;
import org.springframework.stereotype.Service;

@Service
public class ShoesDataServiceImpl extends ServiceImpl<ShoesDataDao, ShoesDataEntity> implements ShoesDataService {
    @Override
    public ShoesDataEntity selectShoesDataByColorAndSize(Integer colorSeq, Integer sizeSeq, Integer shoesSeq) {
        return this.selectOne(new EntityWrapper<ShoesDataEntity>().eq("Shoes_Seq", shoesSeq).eq("Color_Seq", colorSeq).eq("Size_Seq", sizeSeq));
    }
}
