package io.nuite.modules.system.controller;

import io.nuite.common.utils.R;
import io.nuite.modules.system.entity.SizeAllotTemplateEntity;
import io.nuite.modules.system.service.SizeAllotTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 配码模版
 *
 * @author yangchuang
 * @email ${email}
 * @date 2019-04-26 10:18:29
 */
@RestController
@RequestMapping("/system/sizeAllotTemplate")
@Api(tags = "后台-配码模版接口")
public class SizeAllotTemplateController extends AbstractController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private SizeAllotTemplateService sizeAllotTemplateService;
    
    /**
     * 列表
     */
    @ApiOperation(value = "查询配码模版列表")
    @GetMapping("/list")
    public R list() {
        Map<String, List<SizeAllotTemplateEntity>> sizeAllotTemplateEntities = sizeAllotTemplateService.listAllByCompanySeq(getUser().getCompanySeq());
        return R.ok(sizeAllotTemplateEntities);
    }
    
    /**
     * 保存
     */
    @ApiOperation(value = "保存配码模版", notes = "新建配码模版操作")
    @PostMapping("/save")
    public R save(@RequestBody SizeAllotTemplateEntity sizeAllotTemplate) {
        sizeAllotTemplate.setCompanySeq(getUser().getCompanySeq());
        sizeAllotTemplate.setInputTime(new Date());
        sizeAllotTemplateService.saveTemplate(sizeAllotTemplate);
        return R.ok();
    }
    
    /**
     * 删除
     */
    @ApiOperation(value = "删除配码模版")
    @GetMapping("/delete/{templateSeq}")
    public R delete(@ApiParam("配码模版序号") @PathVariable Integer templateSeq) {
        sizeAllotTemplateService.delTemplateBySeq(templateSeq);
        return R.ok();
    }
    
}
