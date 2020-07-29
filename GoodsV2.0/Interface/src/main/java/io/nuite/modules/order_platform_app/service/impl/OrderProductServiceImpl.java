package io.nuite.modules.order_platform_app.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.nuite.modules.order_platform_app.dao.OrderProductsDao;
import io.nuite.modules.order_platform_app.entity.OrderProductsEntity;
import io.nuite.modules.order_platform_app.service.OrderProductService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 订单详情service实现类
 * @author: jxj
 * @create: 2019-09-29 17:01
 */
@Service
public class OrderProductServiceImpl extends ServiceImpl<OrderProductsDao,OrderProductsEntity> implements OrderProductService {
    @Override
    public List<OrderProductsEntity> getOrderProduct(Integer orderSeq) throws Exception {
        Wrapper<OrderProductsEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("Order_Seq",orderSeq);
        return baseMapper.selectList(wrapper);
    }
}
