package io.nuite.modules.job.task;

import io.nuite.modules.system.erp.service.ErpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: yangchuang
 * @Date: 2018/8/5 16:49
 * @Version: 1.0
 * @Description:
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class ErpTaskTest {

    @Autowired
    ErpTask erpTask;

    @Autowired
    ErpService erpService;

    @Test
    public void selectSizes() {
        erpTask.selectSizes();
    }

    @Test
    public void selectColors() {
        erpTask.selectColors();
    }

    @Test
    public void selectCategorys() {
        erpTask.selectCategorys();
    }

    @Test
    public void selectSX() {
        erpTask.selectSX();
    }

    @Test
    public void selectKeHu() {
        erpTask.selectKeHu();
    }

    @Test
    public void selectStock() {
        erpTask.selectStock();
    }

    @Test
    public void selectAll() {
        erpTask.selectAll();
    }

    @Test
    public void selectOrder() {
        erpTask.selectOrder();
    }

    @Test
    public void selectGoods() {
        erpTask.selectGoods();
    }

    @Test
    public void importOrder() {
        erpService.importOrder(null, 46);
    }

    @Test
    public void importGoodsToErp() {

        erpTask.importGoodsToErp();
    }

    @Test
    public void selectBrands() {
        erpTask.selectBrands();
    }
}