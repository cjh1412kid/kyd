package com.nuite.modules.erp.bserp.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.nuite.modules.erp.bserp.entity.Dingdan;
import com.nuite.modules.erp.bserp.entity.DingdanMX;
import com.nuite.modules.erp.bserp.entity.DingdanPh;
import com.nuite.modules.erp.bserp.entity.DingdanPhMX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface DingdanDao extends BaseMapper<Dingdan> {
    List<Dingdan> getDingdan(@Param("startDate") Date startDate);

    List<DingdanMX> getDingdanMX(@Param("OrderNum") String orderNum);

    List<DingdanPh> getDingdanPh(@Param("OrderNum") String orderNum);

    List<DingdanPhMX> getDingdanPhMx(@Param("ExpressNum") String expressNum);

    String selectDingdanYGDM(@Param("UserCode")String userCode);
}
