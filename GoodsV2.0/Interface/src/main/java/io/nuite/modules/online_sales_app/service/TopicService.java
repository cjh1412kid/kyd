package io.nuite.modules.online_sales_app.service;

import com.baomidou.mybatisplus.service.IService;
import io.nuite.common.utils.PageUtils;
import io.nuite.modules.online_sales_app.entity.TopicEntity;

import java.util.Map;

public interface TopicService extends IService<TopicEntity> {
    PageUtils queryPage(Map<String, Object> params, Integer brandSeq);

    TopicEntity selectOneBySeq(Long seq);

    String saveTopic(TopicEntity topicEntity) throws Exception;
}
