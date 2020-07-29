package io.nuite;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.nuite.modules.system.erp.entity.KeHu;
import io.nuite.modules.system.erp.utils.HttpRequestUtils;
import io.nuite.modules.system.model.excel.qiangren.Sample;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyTest {
    @Test
    public void test() {
        List<Sample> samples = new ArrayList<>();
        Sample sample1 = new Sample();
        sample1.setGoodsId("1");
        Sample sample2 = new Sample();
        sample2.setGoodsId("1");
        samples.add(sample1);
        samples.add(sample2);
        Set<Sample> set = new HashSet<>(samples);
        System.out.println(set.size());
    }

    @Test
    public void getESmartData() {
        JSONObject data = HttpRequestUtils.getResponseResult("http://221.229.214.60:9191/eSmart/kehu/v1.0", "10000001", "6463155ff1364ebc838eee829ca5179e");
        JSONArray aray = data.getJSONArray("result");
        for (int i = 0; i < aray.size(); i++) {
            KeHu keHu = aray.getObject(i, KeHu.class);
            System.out.println(JSONObject.toJSONString(keHu));
        }
    }
}
