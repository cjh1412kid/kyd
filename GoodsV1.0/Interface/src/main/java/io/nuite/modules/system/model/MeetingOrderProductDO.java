package io.nuite.modules.system.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: yangchuang
 * @Date: 2019/4/18 7:55
 * @Version: 1.0
 */

@Data
public class MeetingOrderProductDO {
    /**
     * 货号
     */
    private String goodID;
    /**
     * 货号图片
     */
    private String picName;
    /**
     * 订货会货品序号
     */
    private Integer meetingGoodSeq;
    /**
     * 颜色
     */
    private String color;
    /**
     * 颜色序号
     */
    private Integer colorSeq;
    
    /**
     * 尺码-数量
     */
    private Map<Integer, Integer> size;
    
    /**
     * 是否取消
     */
    private Integer cancel;
    
    /**
     * 尺码标题
     */
    private List<Integer> titles;
    
    /**
     * 区域
     */
    private String areaName;

    /**
     * 订货方
     */
    private String userName;

    /**
     * 供应价
     */
    private BigDecimal price;
}
