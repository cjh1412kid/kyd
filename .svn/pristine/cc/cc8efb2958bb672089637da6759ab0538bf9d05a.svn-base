package io.nuite.modules.online_sales_app.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import io.nuite.modules.online_sales_app.dao.OnlineSalesShoppingCartDao;
import io.nuite.modules.online_sales_app.entity.ShoppingCartEntity;
import io.nuite.modules.online_sales_app.service.OnlineCartService;

@Service
public class OnlineCartServiceImpl implements OnlineCartService {
    
    @Autowired
    private OnlineSalesShoppingCartDao onlineSalesShoppingCartDao;
	
	
	/**
	 * 根据用户编号，鞋子数据编号，查询购物车实体
	 */
	@Override
	public ShoppingCartEntity getShoppingCartByUserSeqShoesDataSeq(Integer userSeq, Integer shoesDataSeq, String openIDLinks) {
		ShoppingCartEntity shoppingCartEntity = new ShoppingCartEntity();
		shoppingCartEntity.setUserSeq(userSeq);
		shoppingCartEntity.setShoesDataSeq(shoesDataSeq);
		shoppingCartEntity.setOpenIDLinks(openIDLinks);
		shoppingCartEntity.setDel(0);
		return onlineSalesShoppingCartDao.selectOne(shoppingCartEntity);
	}
	
	
	/**
	 * 加入购物车
	 */
	@Override
	public void addToShoppingCart(ShoppingCartEntity shoppingCartEntity) {
		onlineSalesShoppingCartDao.insert(shoppingCartEntity);
	}
	
	
	/**
	 * 购物车列表
	 */
	@Override
	public List<Map<String, Object>> getShoppingCartListByUserSeq(Integer userSeq) {
		return onlineSalesShoppingCartDao.getShoppingCartListByUserSeq(userSeq);
	}
	
	
	/**
	 * 修改购物车
	 */
	@Override
	public void updateShoppingCart(ShoppingCartEntity shoppingCartEntity) {
		onlineSalesShoppingCartDao.updateById(shoppingCartEntity);
	}
	
	

	/**
	 * 购物车全选/全不选
	 */
	@Override
	public void checkAllShoppingCart(ShoppingCartEntity shoppingCartEntity, Integer userSeq) {
		Wrapper<ShoppingCartEntity> wrapper = new EntityWrapper<ShoppingCartEntity>();
		wrapper.where("User_Seq = {0} ", userSeq);
		onlineSalesShoppingCartDao.update(shoppingCartEntity, wrapper);
	}
	
	
	/**
	 * 删除购物车
	 */
	@Override
	public void deleteShoppingCart(Integer shoppingCartSeq) {
		onlineSalesShoppingCartDao.deleteById(shoppingCartSeq);
	}

	
    
}
