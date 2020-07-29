package io.nuite.modules.system.service.online_sale;

import io.nuite.modules.system.entity.online_sale.OnlineOrderEntity;
import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.R;

public interface OnlineManageService {

    /**
     * 订单列表
     *
     * @return
     */
    PageUtils orderlist(Long userSeq, Integer orderStatus, String keywords,Integer page,Integer limit);

    /**
     * 订单详情
     *
     * @return
     */
    OnlineOrderEntity getOrderBySeq(Integer seq);

    /**
     * 根据订单序号删除订单
     */
    Integer deleteOeder(Integer seq);

    /**
     * 根据状态序号和订单序号对该订单状态进行跟进
     */
    R updateOederStatus(Integer orderStatus, Integer seq);

    /**
     * 根据订单的seq返回该订单中所有的鞋子信息
     *
     * @param seq   订单序号
     * @param price 修改金额
     */
    PageUtils goodsList(int seq);
}
