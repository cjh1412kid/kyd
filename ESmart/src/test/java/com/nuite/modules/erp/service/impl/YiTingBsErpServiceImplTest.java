package com.nuite.modules.erp.service.impl;

import com.nuite.modules.erp.bserp.entity.ShangPin;
import com.nuite.modules.erp.service.CommonErpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: yangchuang
 * @Date: 2018/8/20 14:16
 * @Version: 1.0
 * @Description:
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class YiTingBsErpServiceImplTest {

    @Autowired
        @Qualifier("YitingBsErpService")
    CommonErpService commonErpService;

    @Test
    public void getAllGoodsSizes() {
    }

    @Test
    public void getAllGoodsColor() {
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
    }

    @Test
    public void getAllGoods() {
        List<ShangPin> allGoods = commonErpService.getAllGoods();

        System.out.println("allGoods = " + allGoods);
    }

    @Test
    public void getAllKeHu(){
        List allKeHu = commonErpService.getAllKeHu();

        allKeHu.stream().forEach(System.out::println);
    }
}