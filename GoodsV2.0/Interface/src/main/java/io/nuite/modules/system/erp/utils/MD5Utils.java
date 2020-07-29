package io.nuite.modules.system.erp.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Author: yangchuang
 * @Date: 2018/7/27 9:31
 * @Version: 1.0
 * @Description:
 */
public class MD5Utils {

    /**
     * 将字符串MD5加密 生成32位md5码
     *
     * @param inStr
     * @return
     */
    public static String md5(String inStr) {
        try {
            return DigestUtils.md5Hex(inStr.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误");
        }
    }

    /**
     * 获取签名(efast)
     *
     * @param param  post参数 格式：name1=value1&name2=value2
     * @param secret 加密密钥
     * @return
     */
    public static String sign(String param, String secret) {
        if (param == null) {
            return null;
        }
        String[] paramList = param.split("&");
        TreeMap<String, String> map = new TreeMap<>();

        for (int i = 0; i < paramList.length; i++) {
            map.put(paramList[i].split("=")[0], paramList[i].split("=")[1]);
        }

        //组装源字符串
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> e : map.entrySet()) {
            sb.append(e.getKey()).append(e.getValue());
        }
        String s1 = sb.toString();
        //首尾加上secret
        String s2 = secret + s1 + secret;
        //字符串加密并且大写
        String s3 = md5(s2).toUpperCase();
        return s3;
    }

    /**
     * 将参数集合md5加密后 返回签名
     *
     * @param param
     * @param secret
     * @return
     */
    public static String sign(TreeMap<String, Object> param, String secret) {
        //拼接参数
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> e : param.entrySet()) {
            sb.append(e.getKey()).append(e.getValue());
        }
        String s1 = sb.toString();
        //首尾加上secret
        String s2 = secret + s1 + secret;
        //字符串加密并且大写
        String s3 = md5(s2).toUpperCase();
        return s3;
    }

}
