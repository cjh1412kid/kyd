package io.nuite.modules.online_sales_app.service;

import java.util.List;
import java.util.Map;

public interface RankService {

	List<Map<String, Object>> getRank(Integer companySeq,Integer periodSeq,Integer categorySeq,Integer type);
	
	List<Map<String, Object>> getParentCategory(Integer companySeq,Integer periodSeq,Integer categorySeq,Integer type);

	List<Map<String, Object>> getRankList(Integer companySeq,Integer periodSeq);
}
