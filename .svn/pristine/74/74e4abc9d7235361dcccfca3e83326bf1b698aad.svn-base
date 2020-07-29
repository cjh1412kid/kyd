package io.nuite.modules.order_platform_app.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.nuite.common.utils.R;
import io.nuite.modules.order_platform_app.annotation.Login;
import io.nuite.modules.order_platform_app.annotation.LoginUser;
import io.nuite.modules.order_platform_app.entity.CodeHistoryVO;
import io.nuite.modules.order_platform_app.entity.MeetingUserSizeAllotCodeHistoryEntity;
import io.nuite.modules.order_platform_app.service.MeetingUserSizeAllotCodeHistoryService;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 配码历史Controller类
 * @author: jxj
 * @create: 2019-06-12 13:21
 */
@RestController
@Api(tags = "配码历史")
@RequestMapping("/order/app/meetingOrderExcel")
public class MeetingUserSizeAllotCodeHistoryController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MeetingUserSizeAllotCodeHistoryService meetingUserSizeAllotCodeHistoryService;

    @Login
    @ApiOperation("新增配码历史")
    @ApiImplicitParams(@ApiImplicitParam(name = "meetingUserSizeAllotCodeHistoryEntities",value = "配码历史列表",paramType = "query",required = true))
    @PostMapping("/insertMeetingUserSizeAllotCodeHistory")
    public R insertMeetingUserSizeAllotCodeHistory(@ApiIgnore @LoginUser BaseUserEntity loginUser,
            @RequestParam String meetingUserSizeAllotCodeHistoryEntities) {
        try {
            JSONArray jsonArray = JSONArray.parseArray(meetingUserSizeAllotCodeHistoryEntities);
            for(int i = 0;i < jsonArray.size();i++) {
                MeetingUserSizeAllotCodeHistoryEntity history = JSONObject.toJavaObject((JSONObject)jsonArray.get(i),MeetingUserSizeAllotCodeHistoryEntity.class);
                List<CodeHistoryVO> codeHistoryVOs = history.getSizeAndNum();
                List<Integer> sizeCode = new ArrayList<>(10);
                for(int j = 0;j < codeHistoryVOs.size();j++) {
                    sizeCode.add(codeHistoryVOs.get(j).getNum());
                }
                meetingUserSizeAllotCodeHistoryService.addUserSizeAllotCodeHistory(loginUser.getSeq(),codeHistoryVOs.get(0).getSize(),codeHistoryVOs.get(codeHistoryVOs.size() - 1).getSize(),sizeCode);
            }
            return R.ok("新增成功");
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
            e.printStackTrace();
        }
        return R.error("新增失败");
    }

    @Login
    @ApiOperation("查询配码历史")
    @GetMapping("/getMeetingUserSizeAllotCodeHistory")
    public R getMeetingUserSizeAllotCodeHistory(@ApiIgnore @LoginUser BaseUserEntity loginUser) {
        try {
            Wrapper<MeetingUserSizeAllotCodeHistoryEntity> wrapper = new EntityWrapper<>();
            wrapper.eq("User_Seq",loginUser.getSeq());
            return R.ok(meetingUserSizeAllotCodeHistoryService.selectList(wrapper));
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
            e.printStackTrace();
        }
        return R.error();
    }
}
