package com.nuite.mobile.token.mapper;

import com.nuite.mobile.dao.BaseDaoService;
import com.nuite.mobile.token.entity.Token;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface TokenMapper extends BaseDaoService<Token> {

    /**
     * 查询 所有用户 最后 一次登陆的 用户 信息 返回 token对象
     *
     * @return
     */
    Token selectLastUseTime(Map map);

}
