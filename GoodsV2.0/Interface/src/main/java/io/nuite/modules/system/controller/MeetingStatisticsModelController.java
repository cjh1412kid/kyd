package io.nuite.modules.system.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;

import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.R;
import io.nuite.modules.order_platform_app.entity.MeetingStatisticsModelEntity;
import io.nuite.modules.order_platform_app.service.MeetingOrderService;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.system.entity.MeetingEntity;
import io.nuite.modules.system.service.MeetingService;
import io.nuite.modules.system.service.MeetingStatisticsModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


/**
 * 后台 - 订单统计分析模板管理
 *
 * @author yy
 * @date 2018-09-25 16:47
 */
@RestController
@RequestMapping("/system/meetingStatisticsModel")
@Api(tags = "后台 - 订单统计分析模板", description = "订单统计分析模板")
public class MeetingStatisticsModelController extends AbstractController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MeetingStatisticsModelService meetingStatisticsModelService;

    @Autowired
    private MeetingService meetingService;
    
    @Autowired
    private MeetingOrderService meetingOrderService;

    /**
     * 全部模板列表
     */
    @GetMapping("allList")
    @ApiOperation("模板列表")
    public R allList() {
        try {
            //公司序号
            Integer companySeq = getUser().getCompanySeq();

            //分页查询拆单模板
            List<MeetingStatisticsModelEntity> list = meetingStatisticsModelService.getMeetingStatisticsModel(companySeq);

            return R.ok(list);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }

    }

    /**
     * 全部订货会列表
     */
    @GetMapping("allMeetingList")
    public R allMeetingList() {
        try {
            //公司序号
            Integer companySeq = getUser().getCompanySeq();

            //分页查询拆单模板
            List<MeetingEntity> list = meetingService.getMeetingListByCompanySeq(companySeq);

            return R.ok(list);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }

    }

    /**
     * 订货会全部用户列表
     */
    @GetMapping("allUserList")
    public R allUserList(@ApiParam("订货会seq") @RequestParam("meetingSeq") Integer meetingSeq) {
        try {

            //分页查询拆单模板
            List<BaseUserEntity> list = meetingOrderService.getAllUserInMeeting(meetingSeq);

            return R.ok(list);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }

    }
    

    
    /**
     * 模板列表
     */
    @GetMapping("list")
    @ApiOperation("模板列表")
    public R list(@ApiParam("页码") @RequestParam("page") Integer pageNum,
                  @ApiParam("每页条数") @RequestParam("limit") Integer pageSize) {
        try {
            //公司序号
            Integer companySeq = getUser().getCompanySeq();

            //分页查询拆单模板
            Page<MeetingStatisticsModelEntity> page = meetingStatisticsModelService.getMeetingStatisticsModelPage(companySeq, pageNum, pageSize);

            PageUtils pageUtils = new PageUtils(page);
            return R.ok().put("page", pageUtils);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }

    }


    
    /**
     * 编辑
     *
     * @param seq
     * @return
     */
    @GetMapping("edit")
    public R edit(@RequestParam("seq") Integer seq) {
        try {
        	MeetingStatisticsModelEntity meetingStatisticsModelEntity = meetingStatisticsModelService.selectById(seq);
            return R.ok().put("meetingStatisticsModelEntity", meetingStatisticsModelEntity);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return R.error("服务器异常");
        }
    }


    
    /**
     * 新增模板
     */
    @PostMapping("add")
    @ApiOperation("新增模板")
    public R add(@ApiParam("模板名称") @RequestParam("modelName") String modelName,
    		@ApiParam("行字段") @RequestParam("lineField") String lineField,
    		@ApiParam("汇总字段") @RequestParam("summaryField") String summaryField) {
        try {
            //公司序号
            Integer companySeq = getUser().getCompanySeq();
            if (meetingStatisticsModelService.modelNameExisted(null, companySeq, modelName)) {
                return R.error("模板已存在");
            }

            //新增模板
            MeetingStatisticsModelEntity meetingStatisticsModelEntity = new MeetingStatisticsModelEntity();
            meetingStatisticsModelEntity.setCompanySeq(companySeq);
            meetingStatisticsModelEntity.setModelName(modelName);
            meetingStatisticsModelEntity.setLineField(lineField);
            meetingStatisticsModelEntity.setSummaryField(summaryField);
            meetingStatisticsModelEntity.setIsDefault(0);
            meetingStatisticsModelEntity.setInputTime(new Date());
            meetingStatisticsModelEntity.setDel(0);
            Integer seq = meetingStatisticsModelService.addMeetingStatisticsModel(meetingStatisticsModelEntity);

            return R.ok("新增成功").put("seq", seq);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }

    }


    
    /**
     * 修改模板
     */
    @PostMapping("update")
    @ApiOperation("修改模板")
    public R update(@ApiParam("模板seq") @RequestParam("seq") Integer seq,
                    @ApiParam("模板名称") @RequestParam("modelName") String modelName,
                    @ApiParam("行字段") @RequestParam("lineField") String lineField,
                    @ApiParam("汇总字段") @RequestParam("summaryField") String summaryField) {
        try {
            //公司序号
            Integer companySeq = getUser().getCompanySeq();
            if (meetingStatisticsModelService.modelNameExisted(seq, companySeq, modelName)) {
                return R.error("模板已存在");
            }

            //修改模板
            MeetingStatisticsModelEntity meetingStatisticsModelEntity = new MeetingStatisticsModelEntity();
            meetingStatisticsModelEntity.setSeq(seq);
            meetingStatisticsModelEntity.setModelName(modelName);
            meetingStatisticsModelEntity.setLineField(lineField);
            meetingStatisticsModelEntity.setSummaryField(summaryField);
            meetingStatisticsModelService.updateMeetingStatisticsModel(meetingStatisticsModelEntity);

            return R.ok("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }

    }


    
    /**
     * 删除模板
     */
    @GetMapping("delete")
    @ApiOperation("删除模板")
    public R delete(@ApiParam("模板seq") @RequestParam("seq") Integer seq) {
        try {
        	meetingStatisticsModelService.deleteMeetingStatisticsModel(seq);
            return R.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }

    }



}
