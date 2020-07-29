package io.nuite.modules.online_sales_app.service;

import io.nuite.common.utils.PageUtils;
import io.nuite.modules.online_sales_app.entity.ShoesDataEntity;
import io.nuite.modules.online_sales_app.utils.TopicType;

import java.util.List;
import java.util.Map;

public interface ShoesService {
    PageUtils getShoesInfoPage(int page, int limit, Integer orderBy, Integer orderDir, TopicType type, Integer brandSeq, Integer categorySeq);

    Map<String, Object> getShoesDetail(Integer brandSeq, Integer seq);

    List<ShoesDataEntity> getShoesCartDetail(Integer seq);

    List<Map<String, Object>> getShoesCategory(Integer companySeq, Integer parentSeq);
}
