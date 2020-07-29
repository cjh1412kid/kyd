package io.nuite.modules.system.erp.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

/**
 * @Author: yangchuang
 * @Date: 2018/8/28 16:15
 * @Version: 1.0
 * @Description: TODO 地图工具
 */

public class MapUtils {

    /**
     * 根据输入的地址字符串，获取详细的地址信息
     * @param address
     * @return 发生异常返回null
     */
    public static Address getPosition(String address){
        try {
            String url="http://api.map.baidu.com/geocoder/v2/";
            String param="output=json&ak=kjGkdWqUxeYs6hI69vBOMQAOzVbQlIQh&address="+address.replaceAll(" +","");

            JSONObject jsonObj = HttpRequestUtils.sendGet(url, param);
            JSONObject location = jsonObj.getJSONObject("result").getJSONObject("location");
            String lng=location.getString("lng");
            String lat=location.getString("lat");

            String param2="output=json&pois=0&ak=kjGkdWqUxeYs6hI69vBOMQAOzVbQlIQh&qq-pf-to=pcqq.c2c&location="+lat+","+lng;
            JSONObject jsonObj2 = HttpRequestUtils.sendGet(url, param2);
            JSONObject addressComponent = jsonObj2.getJSONObject("result").getJSONObject("addressComponent");

            Address addr = new Address();
            addr.setProvince(addressComponent.getString("province"));
            addr.setCity(addressComponent.getString("city"));
            addr.setDistrict(addressComponent.getString("district"));
            addr.setDistrictCode(addressComponent.getString("adcode"));

            return addr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static class Address{

        private String provinceCode;

        private String province;

        private String cityCode;

        private String city;

        private String districtCode;

        private String district;

        public String getProvinceCode() {
            return provinceCode;
        }

        public void setProvinceCode(String provinceCode) {
            this.provinceCode = provinceCode;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrictCode() {
            return districtCode;
        }

        public void setDistrictCode(String districtCode) {
            this.districtCode = districtCode;

            if (StringUtils.isNotBlank(districtCode)) {
                this.cityCode=districtCode.substring(0,4)+"00";
                this.provinceCode=districtCode.substring(0,2)+"0000";
            }
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Address{");
            sb.append("provinceCode='").append(provinceCode).append('\'');
            sb.append(", province='").append(province).append('\'');
            sb.append(", cityCode='").append(cityCode).append('\'');
            sb.append(", city='").append(city).append('\'');
            sb.append(", districtCode='").append(districtCode).append('\'');
            sb.append(", district='").append(district).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
