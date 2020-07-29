package com.nuite.common.utils;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 返回数据
 */
public class Result extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public Result() {
        put("code", 1);
        put("msg", "操作成功");
    }

    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public static Result error() {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "未知异常，请联系管理员");
    }

    public static Result error(String msg) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

    public static Result error(int code, String msg) {
        Result r = new Result();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }


    public static Result ok() {
        return new Result();
    }

    public static Result ok(String msg) {
        Result r = new Result();
        r.put("msg", msg);
        return r;
    }

    public static Result ok(Map<String, Object> map) {
        Result r = new Result();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (map != null)
            list.add(map);
        r.put("result", list);
        return r;
    }

    public static Result ok(List<Map<String, Object>> list) {
        Result r = new Result();
        r.put("result", list);
        return r;
    }

    public static Result ok(Object obj) {
        Result r = new Result();
        r.put("result", obj);
        return r;
    }

}
