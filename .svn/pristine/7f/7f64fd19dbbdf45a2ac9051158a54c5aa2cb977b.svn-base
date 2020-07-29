package io.nuite.modules.online_sales_app.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.nuite.modules.online_sales_app.entity.TopicEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TopicDao extends BaseMapper<TopicEntity> {
    // 根据品牌的Seq获取专题列表
    List<TopicEntity> getTopicTypeList(@Param("brandSeq") Integer brandSeq);

    void updateGoodsTopic(@Param("topicType") int topicType, @Param("list") List<String> goodsSeqList);
}
