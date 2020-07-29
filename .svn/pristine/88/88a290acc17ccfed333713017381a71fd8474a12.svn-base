package io.nuite.service;

import io.nuite.common.utils.PageUtils;
import io.nuite.modules.online_sales_app.service.ShoesService;
import io.nuite.modules.online_sales_app.utils.TopicType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShoesServiceTest {
    @Autowired
    private ShoesService shoesService;

    @Test
    public void test() {
        PageUtils brandPage = shoesService.getShoesInfoPage(1, 6, null, null, TopicType.BRAND, 4, null);
    }
}
