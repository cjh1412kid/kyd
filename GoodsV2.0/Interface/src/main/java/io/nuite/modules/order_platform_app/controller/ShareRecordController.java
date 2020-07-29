package io.nuite.modules.order_platform_app.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.nuite.common.utils.R;
import io.nuite.modules.order_platform_app.annotation.Login;
import io.nuite.modules.order_platform_app.annotation.LoginUser;
import io.nuite.modules.order_platform_app.entity.MeetingGoodsEntity;
import io.nuite.modules.order_platform_app.service.ShareRecordService;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.system.entity.MeetingOrderProductEntity;
import io.nuite.modules.system.service.SysMeetingOrderProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 分享记录Contorller类
 * @author: jxj
 * @create: 2019-09-23 17:29
 */
@RestController
@RequestMapping("/order/app/shareRecord")
@Api(tags = "订货平台 - 分享记录")
public class ShareRecordController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ShareRecordService shareRecordService;

    @Autowired
    private SysMeetingOrderProductService sysMeetingOrderProductService;

    @Login
    @GetMapping("/getShareRecordList")
    @ApiOperation("分享记录列表")
    public R getShareRecordList(@ApiIgnore @LoginUser BaseUserEntity userEntity,
                                @ApiParam("订货会序号") @RequestParam(value = "meetingSeqs", required = false) Integer meetingSeq,
                                @ApiParam("订货会年份") @RequestParam(value = "year", required = false) String year,
                                @ApiParam("货品类型:1主推,2新品,不传查询全部") @RequestParam(value = "type", required = false) Integer type,
                                @ApiParam("状态:1已订,不传查询全部") @RequestParam(value = "status", required = false) Integer status,
                                @ApiParam("起始条数") @RequestParam("start") Integer start,
                                @ApiParam("总条数") @RequestParam("num") Integer num) {
        try {
            Page<MeetingGoodsEntity> page = new Page<>(start / num + 1,num);
            List<MeetingGoodsEntity> meetingGoodsEntities = shareRecordService.getShareRecordList(userEntity,page,meetingSeq,year,type,status);
            for(MeetingGoodsEntity meetingGoodsEntity : meetingGoodsEntities) {
                meetingGoodsEntity.setImg(getMeetingGoodsPictureUrl(meetingGoodsEntity.getGoodID()) + meetingGoodsEntity.getImg());
                Wrapper<MeetingOrderProductEntity> wrapper = new EntityWrapper<>();
                wrapper.eq("MeetingGoods_Seq",meetingGoodsEntity.getSeq());
                List<MeetingOrderProductEntity> list = sysMeetingOrderProductService.selectList(wrapper);
                if(list != null && list.size() > 0) {
                    meetingGoodsEntity.setIsOrder(1);
                }else {
                    meetingGoodsEntity.setIsOrder(0);
                }
            }
            return R.ok().put("result",meetingGoodsEntities);
        }catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }
        return R.error();
    }

    @Login
    @PostMapping("/insertShareRecord")
    @ApiOperation("新增分享记录")
    public R insertShareRecord(@ApiIgnore @LoginUser BaseUserEntity userEntity,
                               @ApiParam("订货会鞋子序号(逗号分隔)") @RequestParam("meetingGoodsSeqs") List<Integer> meetingGoodsSeqs) {
        try {
            if(meetingGoodsSeqs == null) {
                logger.error("=========================   鞋子序号列表为null");
            }else {
                if(meetingGoodsSeqs.size() == 0) {
                    logger.error("=========================   鞋子序号列表为空");
                }else {
                    logger.error("=========================   " + meetingGoodsSeqs.get(0));
                }
            }

            if(shareRecordService.insertShareRecord(userEntity,meetingGoodsSeqs)) {
                return R.ok("新增成功");
            }else {
                return R.ok("新增失败");
            }
        }catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }
        return R.error("新增失败");
    }
}
