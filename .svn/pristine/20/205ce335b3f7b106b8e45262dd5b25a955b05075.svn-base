package io.nuite.modules.system.service.impl.online_sale;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.R;
import io.nuite.modules.online_sales_app.dao.OnlineSalesOrderProductsDao;
import io.nuite.modules.online_sales_app.dao.OnlineSalesShoesDataDao;
import io.nuite.modules.online_sales_app.entity.OrderProductsEntity;
import io.nuite.modules.online_sales_app.entity.ShoesDataEntity;
import io.nuite.modules.online_sales_app.utils.OrderStatusEnum;
import io.nuite.modules.sr_base.utils.ConfigUtils;
import io.nuite.modules.system.dao.online_sale.OnlineOrderDao;
import io.nuite.modules.system.dao.online_sale.OnlineOrderManageDao;
import io.nuite.modules.system.entity.online_sale.OnlineOrderEntity;
import io.nuite.modules.system.service.online_sale.OnlineManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OnlineManageServiceImpl implements OnlineManageService {
    @Autowired
    private OnlineOrderManageDao onlineOrderManageDao;

    @Autowired
    private OnlineOrderDao onlineOrderDao;

    @Autowired
    private OnlineSalesOrderProductsDao onlineSalesOrderProductsDao;

    @Autowired
    private OnlineSalesShoesDataDao onlineSalesShoesDataDao;

    @Autowired
    private ConfigUtils configUtils;

    // 订单列表
    public PageUtils orderlist(Long userSeq, Integer orderStatus, String keywords, Integer page, Integer limit) {
        List<OnlineOrderEntity> orderList = new ArrayList<OnlineOrderEntity>();
        int totalCount = 0;
        Integer start = new Integer(0);
        Integer num = new Integer(0);
        if (page >= 1 && limit > 0) {
            start = (page - 1) * limit;
            num = page * limit;
        }
        // 根据用户查询公司Seq
        Integer companySeq = onlineOrderManageDao.getCompanyByUserSeq(userSeq);
        if (companySeq != null) {
            // 在列表页中有列表显示的时候查询同等条件下没有分页的情况下数据量
            totalCount = onlineOrderManageDao.getTotalCount(orderStatus, keywords, companySeq);
            // 根据公司的Seq查询所有的订单
            orderList = onlineOrderManageDao.orderList(orderStatus, keywords, companySeq, start, num);
            if (orderList != null) {
                // 再循环订单列表拼接快递单图片和获取状态名
                for (OnlineOrderEntity onlineOrderEntity : orderList) {
                    if (onlineOrderEntity != null) {
                        onlineOrderEntity.setStatusName(OrderStatusEnum.get(onlineOrderEntity.getOrderStatus()).getFactoryName());
                    }
                }
            }
        }

        return new PageUtils(orderList, totalCount, limit, page);
    }

    @Override
    public OnlineOrderEntity getOrderBySeq(Integer seq) {
        return onlineOrderDao.selectById(seq);
    }

    // 根据订单序号删除订单
    public Integer deleteOeder(Integer seq) {
        return onlineOrderManageDao.deleteOeder(seq);
    }

    // 根据状态序号和订单序号对该订单状态进行跟进
    @Override
    @Transactional
    public R updateOederStatus(Integer orderStatus, Integer seq) {
        OnlineOrderEntity onlineOrderEntity = getOrderBySeq(seq);
        if (onlineOrderEntity == null) {
            return R.error("订单不存在！");
        }
        Integer currentStatus = onlineOrderEntity.getOrderStatus();

        OnlineOrderEntity newOnlineOrderEntity = new OnlineOrderEntity();
        newOnlineOrderEntity.setSeq(onlineOrderEntity.getSeq());
        // 待接单->已接单(未付款)、已取消
        if (currentStatus == 0 && (orderStatus == 4 || orderStatus == 1)) {
            //后台处理已付款
            if (orderStatus == 1) {
                // 修改库存
                changeOrderProductStock(seq);
            }
            newOnlineOrderEntity.setOrderStatus(orderStatus);
        }
        // 已付款->已发货
        if (currentStatus == 1 && orderStatus == 2) {
            newOnlineOrderEntity.setOrderStatus(orderStatus);
        }
        if (newOnlineOrderEntity.getOrderStatus() == null) {
            return R.error("当前订单状态已变更");
        }
        // 根据传入的订单序号
        onlineOrderDao.updateById(newOnlineOrderEntity);
        return R.ok();
    }


    @Override
    public PageUtils goodsList(int userSeq) {
        List<Map<String, Object>> list = onlineOrderManageDao.getGoodsListByOrderSeq(userSeq);
        for (Map<String, Object> map : list) {
            map.put("img1", configUtils.getPictureBaseUrl() + "/" + configUtils.getBaseDir() + "/"
                    + configUtils.getGoodsShoes() + "/" + map.get("goodId") + "/" + map.get("img1"));
        }
        return new PageUtils(list, list.size(), list.size(), 1);
    }

    //修改订单库存
    private void changeOrderProductStock(Integer orderSeq) {
        EntityWrapper<OrderProductsEntity> ew = new EntityWrapper<>();
        ew.eq("Order_Seq", orderSeq);
        List<OrderProductsEntity> orderProducts = onlineSalesOrderProductsDao.selectList(ew);
        for (OrderProductsEntity orderProduct : orderProducts) {
            changeShoesDataStock(orderProduct.getShoesDataSeq(), orderProduct.getBuyCount() * (-1));
        }
    }

    //修改订单中单个商品库存
    public void changeShoesDataStock(Integer shoesDataSeq, Integer changNum) {
        ShoesDataEntity shoesDataEntity = onlineSalesShoesDataDao.selectById(shoesDataSeq);
        //判断库存是否足够
        if (shoesDataEntity.getStock() + changNum >= 0) {
            onlineSalesShoesDataDao.changeShoesDataStock(shoesDataSeq, changNum);
            shoesDataEntity = onlineSalesShoesDataDao.selectById(shoesDataSeq);
            if (shoesDataEntity.getStock() < 0) {
                throw new RuntimeException("库存不足");
            }
        } else {
            throw new RuntimeException("库存不足");
        }
    }
}
