package io.nuite.common.utils;

/**
 * 用户权限字符串
 * @author Administrator
 *
 */
public final class PermissionKeys {
	
	
	//    ---  订单权限  ---
	/**
	 * 接单
	 */
	public static final String ORDER_RECEIVE = "order:receiveOrder";
	/**
	 * 审核
	 */
	public static final String ORDER_CHECK = "order:checkOrder";
	/**
	 * 入库
	 */
	public static final String ORDER_STORE = "order:storeOrder";
	/**
	 * 发货（包括部分发货）
	 */
	public static final String ORDER_DELIVER = "order:deliverOrder";
	/**
	 * 取消订单
	 */
	public static final String ORDER_CANCEL = "order:cancelOrder";





	//    ---  订货会订单权限  ---
	/**
	 * 接单
	 */
	public static final String MEETING_ORDER_RECEIVE = "meetingOrder:receiveOrder";
	/**
	 * 审核
	 */
	public static final String MEETING_ORDER_CHECK = "meetingOrder:checkOrder";
	/**
	 * 入库
	 */
	public static final String MEETING_ORDER_STORE = "meetingOrder:storeOrder";
	/**
	 * 发货（包括部分发货）
	 */
	public static final String MEETING_ORDER_DELIVER = "meetingOrder:deliverOrder";
	/**
	 * 取消订单
	 */
	public static final String MEETING_ORDER_CANCEL = "meetingOrder:cancelOrder";

}
