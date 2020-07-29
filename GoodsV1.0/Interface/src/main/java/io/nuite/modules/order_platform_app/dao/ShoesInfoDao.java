package io.nuite.modules.order_platform_app.dao;

import io.nuite.modules.order_platform_app.entity.ShoesInfoEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 0
 * 
 * @author admin
 * @email 
 * @date 2018-04-11 11:29:58
 */
@Mapper
public interface ShoesInfoDao extends BaseMapper<ShoesInfoEntity> {

	List<Map<String, Object>> getShoesList(@Param("periodSeq")String periodSeq, 
											@Param("companyTypeSeq")Integer companyTypeSeq,
											@Param("categorySeqs")String categorySeqs, 
											@Param("shoesSeqs")String shoesSeqs, 
											@Param("goodNameId")String goodNameId,
											@Param("orderBy")Integer orderBy, 
											@Param("orderDir")Integer orderDir, 
											@Param("start")Integer start,
											@Param("num")Integer num);

	List<Map<String, Object>> getAllGoodIds(@Param("periodSeq")String periodSeq, @Param("companyTypeSeq")Integer companyTypeSeq);

	List<Map<String, Object>> getHotSearchGoodIds(@Param("periodSeq")String periodSeq, @Param("companyTypeSeq")Integer companyTypeSeq);

    void deleteByShoesSeq(@Param("seq")Integer seq);

    void updateOnSale(@Param("goodsSeq")Integer goodsSeq,@Param("onSale")int onSale);
	
   
}
