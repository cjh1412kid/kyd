package io.nuite.modules.online_sales_app.dao;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import io.nuite.modules.online_sales_app.entity.CustomerUserInfo;
import io.nuite.modules.sr_base.entity.GoodsPeriodEntity;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.nuite.modules.online_sales_app.entity.OrderEntity;

/**
 * 0
 * 
 * @author admin
 * @email 
 * @date 2018-04-11 11:29:57
 */
@Mapper
public interface OnlineSalesOrderDao extends BaseMapper<OrderEntity> {

	List<Map<String, Object>> getOrderProductsDetail(Integer orderSeq);

	/**
	 * 获取定货方订单列表
	 * @param page
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<OrderEntity> selectOrder(Page<OrderEntity> page,Map<String, Object> map) throws Exception;

	/**
	 * 获取波次年份
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<Integer> selectYear(Map<String,Object> map) throws Exception;

	/**
	 * 获取波次
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<GoodsPeriodEntity> selectPeriod(Map<String,Object> map) throws Exception;

	/**
	 * 获取定货方
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<CustomerUserInfo> selectCustom(Map<String,Object> map) throws Exception;

	/**
	 * 获取定货方详情
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<OrderEntity> selectOrderDetail(Map<String,Object> map) throws Exception;

	/**
	 * 获取总订货量
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Integer selectTotalOrderNum(Map<String,Object> map) throws Exception;

	/**
	 * 根据波次获取订单
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<OrderEntity> selectOrderAllColumn(Map<String,Object> map) throws Exception;
}
