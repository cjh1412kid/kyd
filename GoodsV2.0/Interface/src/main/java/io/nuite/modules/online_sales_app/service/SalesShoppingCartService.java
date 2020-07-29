package io.nuite.modules.online_sales_app.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.nuite.common.utils.PageUtils;
import io.nuite.modules.online_sales_app.entity.CustomerUserInfo;
import io.nuite.modules.online_sales_app.entity.SalesShoppingCartEntity;


public interface SalesShoppingCartService extends IService<SalesShoppingCartEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    void sizeAllot(CustomerUserInfo customerUserInfo,Integer goodSeq, Integer isChart,String userGoodId,Integer perBoxNum,String colorArray,Integer seq);

	List<Map<String, Object>> getShoppingCartListByUserSeq(Integer userSeq);
	
	void deleteShoppingCart(Integer shoppingCartSeq);
	
	 Map<String, Object> getCartDetail(Integer shoppingCartSeq);
	 
	Map<String, Object> getCartList(Integer shoesSeq,Integer userSeq);
}

