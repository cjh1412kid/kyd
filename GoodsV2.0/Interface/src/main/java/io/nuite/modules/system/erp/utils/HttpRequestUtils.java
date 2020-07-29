package io.nuite.modules.system.erp.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.sf.json.util.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Author: yangchuang
 * @Date: 2018/7/26 14:48
 * @Version: 1.0
 * @Description:
 */
public class HttpRequestUtils {
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数形式： name1=value1&name2=value2
     * @return URL所代表远程资源的响应结果
     */
    public static JSONObject sendGet(String url, String param) {
        JSONObject jsonResult = null;
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
//            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
/*            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }*/
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            String responseStr = sb.toString();
            //转成Json对象
            jsonResult = JSON.parseObject(responseStr);
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            releaseSource(null, in);
        }
        return jsonResult;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数形式： name1=value1&name2=value2
     * @return 所代表远程资源的响应结果
     */
    public static JSONObject sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        URLConnection conn = null;
        String responseStr = null;
        JSONObject jsonResult = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //设置超时时间
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(new String(param.getBytes(), "UTF-8"));
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            //读取响应数据
            String line;
            StringBuffer result = new StringBuffer();
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            responseStr = result.toString();
            //将unicode码转换成汉字
            responseStr = new JSONTokener(responseStr).nextValue().toString();
            //转成Json对象
            jsonResult = JSON.parseObject(responseStr);
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            releaseSource(out, in);
        }
        return jsonResult;
    }

    private static void releaseSource(PrintWriter out, BufferedReader in) {
        try {
            if (null != in) {
                in.close();
            }
            if (null != out) {
                out.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 根据请求路径发送post请求，返回响应数据
     *
     * @param
     * @return
     */
    public static JSONObject getResponseResult(String url, String key, String secret) {
        return getResponseResult(url, key, secret, null);
    }

    /**
     * 根据请求路径发送post请求，返回响应数据
     *
     * @param url
     * @param key
     * @param secret
     * @param data   任何类型数据，将转换成json字符串
     * @return
     */
    public static JSONObject getResponseResult(String url, String key, String secret, Object data) {
        //时间戳
        long time = System.currentTimeMillis();

        //请求参数
        HttpRequestParam postParam = HttpRequestParam.newInstance()
                .put("key", key)
                .put("time", time);

        //md5加密，获得签名
        String sign = MD5Utils.sign(postParam, secret);
        postParam.put("sign", sign);

        postParam.put("data", JSON.toJSONString(data));

        //发送post请求
        return HttpRequestUtils.sendPost(url, postParam.toGetParamStr());
    }

}
