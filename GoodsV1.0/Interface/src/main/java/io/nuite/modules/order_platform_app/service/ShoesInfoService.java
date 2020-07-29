package io.nuite.modules.order_platform_app.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import io.nuite.modules.order_platform_app.entity.ShoesInfoEntity;


public interface ShoesInfoService extends IService<ShoesInfoEntity> {

	List<Map<String, Object>> getShoesCategory(Integer companySeq, Integer parentSeq);

	List<Map<String, Object>> getShoesList(String periodSeq, Integer companyTypeSeq, List<Integer> categorySeqList, List<Integer> shoesSeqList, String goodNameId,
			Integer orderBy, Integer orderDir, Integer start, Integer num);

	List<Map<String, Object>> getAllGoodIds(String periodSeq, Integer companyTypeSeq);

	List<Map<String, Object>> getHotSearchGoodIds(String periodSeq, Integer companyTypeSeq);

	ShoesInfoEntity getShoesInfoByShoesSeq(Integer shoesSeq);

}
