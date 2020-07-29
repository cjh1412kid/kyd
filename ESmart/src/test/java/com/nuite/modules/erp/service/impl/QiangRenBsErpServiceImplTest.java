package com.nuite.modules.erp.service.impl;

import com.alibaba.fastjson.JSON;
import com.nuite.modules.erp.bserp.entity.GoodsColorEntity;
import com.nuite.modules.erp.bserp.entity.GoodsSizeEntity;
import com.nuite.modules.erp.bserp.entity.GoodsStock;
import com.nuite.modules.erp.bserp.entity.ShangPin;
import com.nuite.modules.erp.config.ErpConfig;
import com.nuite.modules.erp.entity.vo.OrderGoodsVo;
import com.nuite.modules.erp.entity.vo.OrderVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: yangchuang
 * @Date: 2018/8/2 17:28
 * @Version: 1.0
 * @Description:
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class QiangRenBsErpServiceImplTest {

    @Autowired
    QiangRenBsErpServiceImpl qiangRenBsErpService;

    @Autowired
    ErpConfig erpConfig;

    @Test
    public void getAllGoodsSizes() {
        List<GoodsSizeEntity> allGoodsSizes = qiangRenBsErpService.getAllGoodsSizes();

        for (GoodsSizeEntity goodsSize : allGoodsSizes) {
            System.out.println("goodsSize = " + goodsSize);
        }

    }

    @Test
    public void getAllGoodsColor() {
        List<GoodsColorEntity> allGoodsColors = qiangRenBsErpService.getAllGoodsColor();

        for (GoodsColorEntity goodsColor : allGoodsColors) {
            System.out.println("goodsColor = " + goodsColor);
        }
    }

    @Test
    public void getAllGoodsSizes1() {
    }

    @Test
    public void getAllGoodsColor1() {
    }

    @Test
    public void getAllGoodsCategory() {
    }

    @Test
    public void getAllSXandOption() {
    }

    @Test
    public void getAllUsersFromCangKu() {
    }

    @Test
    public void getStockInfo() {
    }

    @Test
    public void getAllDingdan() {
    }

    @Test
    public void saveOrderData() {

        String data = "{\"amount\":123.0,\"count\":3,\"inputTime\":1534330470551,\"orderGoods\":[{\"colorCode\":\"001\",\"count\":1,\"goodsCode\":\"XA497686\",\"price\":111.0,\"sizeCode\":\"36\"}],\"orderNum\":\"D123\",\"paid\":121.0,\"userSeq\":\"371001\"}";

        qiangRenBsErpService.saveOrderData(data);


/*        JSONArray orders = JSON.parseArray(data);

        System.out.println("orders = " + orders);

        for (int i = 0; i < orders.size(); i++) {
            OrderVo orderVo = orders.getObject(i, OrderVo.class);

            System.out.println("orderVo = " + orderVo);
            //插入订单明细数据
            for (OrderGoodsVo goodsVo : orderVo.getOrderGoods()) {
                System.out.println("goodsVo = " + goodsVo);
            }

        }*/

    }

    @Test
    public void test() {
        OrderGoodsVo goodsVo = new OrderGoodsVo();
        goodsVo.setGoodsCode("XA497686");
        goodsVo.setColorCode("001");
        goodsVo.setSizeCode("36");
        goodsVo.setPrice(111.0);
        goodsVo.setCount(1);

        List<OrderGoodsVo> ordermx = new ArrayList<>();
        ordermx.add(goodsVo);

        OrderVo orderVo = new OrderVo();
        orderVo.setAmount(123.0);
        orderVo.setCount(3);
        orderVo.setInputTime(new Date());
        orderVo.setOrderNum("D123");
        orderVo.setPaid(121.0);
        orderVo.setOrderGoods(ordermx);
        orderVo.setUserSeq("371001");

        String s = JSON.toJSONString(orderVo);

        System.out.println("s = " + s);

    }


    @Test
    public void configtest() {
        String orderPrefix = erpConfig.getOrderPrefix();
        System.out.println("orderPrefix = " + orderPrefix);
    }

    @Test
    public void saveGoods() {
        ShangPin shangPin=new ShangPin();
        shangPin.setCode("PXJ15Y00191");
        shangPin.setName("春眠不觉晓2");
        shangPin.setCategoryCode("005");
//        shangPin.setSalePrice(85.0000);
        shangPin.setSeasonCode("003");
        shangPin.setYear(2019);
        shangPin.setSx1("001");
        List<ShangPin> goods=new ArrayList<>();
        goods.add(shangPin);

        String s = JSON.toJSONString(goods);
        System.out.println("s = " + s);

        qiangRenBsErpService.saveGoods(s);

    }

    @Test
    public void savaStock(){
        GoodsStock goodsStock=new GoodsStock();
        goodsStock.setGoodsCode("PXJ15Y0019");
        goodsStock.setColorCode("001");
        goodsStock.setSizeCode("35");
        goodsStock.setCKCode("001");
        goodsStock.setKWCode("000");
        qiangRenBsErpService.savaStock(goodsStock);
    }
}