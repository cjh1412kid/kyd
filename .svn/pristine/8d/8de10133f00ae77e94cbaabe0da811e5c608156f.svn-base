package com.nuite.modules.erp.dao;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.nuite.datasources.DataSourceNames;
import com.nuite.datasources.annotation.DataSource;
import com.nuite.modules.erp.bserp.dao.GoodsStockDao;
import com.nuite.modules.erp.bserp.dao.SizesDao;
import com.nuite.modules.erp.bserp.entity.GoodsStock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: yangchuang
 * @Date: 2018/8/9 13:55
 * @Version: 1.0
 * @Description:
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class GoodsStockDaoTest {

    @Autowired
    GoodsStockDao goodsStockDao;

    @Autowired
    SizesDao sizesDao;

    @DataSource(name= DataSourceNames.QIANGREN)
    @Test
    public void test1(){
        List<GoodsStock> goodsStocks = goodsStockDao.selectList(new EntityWrapper<GoodsStock>().eq("SPDM", "XD499274"));
        System.out.println("goodId = XD499274" + "库存记录：" + goodsStocks.size());

    }


    @DataSource(name= DataSourceNames.QIANGREN)
    @Test
    public void test3(){

    }



}