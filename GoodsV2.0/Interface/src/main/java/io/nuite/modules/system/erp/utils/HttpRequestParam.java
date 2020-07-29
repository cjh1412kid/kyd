package io.nuite.modules.system.erp.utils;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Author: yangchuang
 * @Date: 2018/8/2 10:38
 * @Version: 1.0
 * @Description:
 */
public class HttpRequestParam extends TreeMap<String, Object> {

    public static HttpRequestParam newInstance(){
        return new HttpRequestParam();
    }

    public HttpRequestParam put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    /**
     * 按key的自然顺序拼接key，value
     * @return
     */
    public  String toParamStr(){
        if (this.size()>0){
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Object> e : this.entrySet()) {
                sb.append(e.getKey()).append(e.getValue());
            }
            return sb.toString();
        }
        return null;
    }

    /**
     * 转换成get参数格式的字符串
     * @return
     */
    public  String toGetParamStr(){
        if (this.size()>0){
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Object> e : this.entrySet()) {
                sb.append(e.getKey())
                        .append("=")
                        .append(e.getValue())
                        .append("&");
            }
            String str=sb.toString();
            return str.substring(0,str.length()-1);
        }
        return null;
    }



}
