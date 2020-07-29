package io.nuite.modules.sr_base.service;

import com.baomidou.mybatisplus.service.IService;
import io.nuite.modules.sr_base.entity.BaseAreaEntity;

import java.util.List;

public interface BaseAreaService extends IService<BaseAreaEntity> {
    List<Integer> nOcategoryList(Integer brand_Seq);
}
