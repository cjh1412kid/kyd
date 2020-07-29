package io.nuite.modules.online_sales_app.service;

import java.util.List;
import java.util.Map;

public interface VerificationService {

	List<Map<String, Object>> getTodayPaidOrderByCompanySeq(Integer companySeq);

	String getGoodIdSizeByOrderSeq(Integer orderSeq);

	void changeStockByGoodIdSize(Integer companySeq, String[] goodIdSizeArr);

}
