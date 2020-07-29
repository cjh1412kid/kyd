package io.nuite.modules.online_sales_app.service;

import java.util.List;
import java.util.Map;

import io.nuite.modules.online_sales_app.entity.ShoppingCartEntity;


public interface OnlineCartService {
	
	ShoppingCartEntity getShoppingCartByUserSeqShoesDataSeq(Integer userSeq, Integer shoesDataSeq, String openIDLinks);
	
	void addToShoppingCart(ShoppingCartEntity shoppingCartEntity);
	
	List<Map<String, Object>> getShoppingCartListByUserSeq(Integer userSeq);
	
	void updateShoppingCart(ShoppingCartEntity shoppingCartEntity);
	
	void checkAllShoppingCart(ShoppingCartEntity shoppingCartEntity, Integer userSeq);
	
	void deleteShoppingCart(Integer shoppingCartSeq);
	
}
