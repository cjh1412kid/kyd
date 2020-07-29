package io.nuite.modules.online_sales_app.utils;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 订单状态
 */
public enum OrderStatusEnum {

	// 工厂方：0待支付、1未发货、2已发货、3已到货、4已取消 
	//消费者方：0待支付、1待发货、2已发货、3已收货、4已取消
	ORDSTATUS_ZERO(0, "待支付", "待支付"),
	ORDSTATUS_ONE(1, "未发货", "待发货"),
	ORDSTATUS_TWO(2, "已发货", "已发货"),
	ORDSTATUS_THREE(3, "已到货", "已收货"),
	ORDSTATUS_FOUR(4, "已取消", "已取消");

	private int value;
	private String factoryName;
	private String customerName;

	private OrderStatusEnum(int value, String factoryName, String customerName) {
		this.value = value;
		this.factoryName = factoryName;
		this.customerName = customerName;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}



	private static final Map<Integer, OrderStatusEnum> lookup = new HashMap<Integer, OrderStatusEnum>();

	static {
		for (OrderStatusEnum s : EnumSet.allOf(OrderStatusEnum.class)) {
			lookup.put(s.getValue(), s);
		}
	}
	
	//根据数字获取枚举对象
	public static OrderStatusEnum get(Integer code) {
		return lookup.get(code);
	}
	
}
