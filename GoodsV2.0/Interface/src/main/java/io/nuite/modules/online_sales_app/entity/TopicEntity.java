package io.nuite.modules.online_sales_app.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.nuite.datasources.DatabaseNames;
import lombok.Data;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(DatabaseNames.SR_ONLINE_SALES + "YHSR_OLS_Topic")
public class TopicEntity implements Serializable {
    @TableId(value = "Seq")
    private Integer seq;

    //专题名称
    @TableField(value = "TopicName")
    private String topicName;

    @TableField(exist = false)
    private MultipartFile topicImageFile;
    //专题图片
    @TableField(value = "TopicImage")
    private String topicImage;

    @TableField(value = "InputTime")
    private Date inputTime;

    @TableField(value = "Del")
    private int del;

    @TableField(value = "Brand_Seq")
    private Long brandSeq;

    @TableField(value = "TopicType")
    private int topicType;

    @TableField(exist = false)
    private String topicLink;

  
}
