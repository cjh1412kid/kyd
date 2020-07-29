package io.nuite.modules.order_platform_app.service;

import java.util.List;
import java.util.Map;

import io.nuite.modules.order_platform_app.entity.ShoesValuateEntity;

public interface ShoesValuateService {
	
	Integer getBrowseNum(Integer userSeq);
	
	Integer getCollectedNum(Integer userSeq);
	
	List<ShoesValuateEntity> getBrowseShoesValuateList(Integer userSeq, Integer start, Integer num);

	List<ShoesValuateEntity> getCollectedShoesValuateList(Integer userSeq, Integer start, Integer num);

	List<Map<String, Object>> getShoesBySeqList(List<Integer> shoesSeqList);

	void deleteBrowseShoesValuate(List<Integer> seqList);

	void deleteAllBrowseShoesValuate(Integer userSeq);

	void deleteCollectedShoesValuate(List<Integer> seqList);

	void deleteAllCollectedShoesValuate(Integer userSeq);

	void deleteShoesValuateByShoesSeq(Integer shoesSeq);

}
