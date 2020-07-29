package io.nuite.modules.system.controller;

import io.nuite.modules.sr_base.entity.BaseAreaEntity;
import io.nuite.modules.sr_base.entity.BaseShopEntity;
import io.nuite.modules.system.service.SystemShopMmanageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.nuite.common.utils.Constant.TypeOfStore;
import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.R;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("system/shopManage")
@Api(tags = "后台  - 门店管理接口", description = "门店管理")
public class SystemShopMmanageController extends AbstractController {

    @Autowired
    private SystemShopMmanageService systemShopMmanageService;

    @GetMapping("list")
    @ApiOperation("门店列表")
    public R list(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
        try {
            PageUtils companyBrandPage = systemShopMmanageService.queryShopByUser(getUserSeq(), page, limit);
            return R.ok().put("page", companyBrandPage);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("门店列表获失败！");
        }
    }

    /**
     * 删除门店
     */
    @GetMapping("del")
    public R del(@RequestParam("seq") Integer seq) {
        try {
            systemShopMmanageService.delete(seq);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("删除失败");
        }
    }

    /**
     * 根据该门店的Seq返回该门店的信息
     */
    @GetMapping("edit")
    public R edit(@RequestParam("seq") Integer seq) {
        try {
            BaseShopEntity baseShopEntity = systemShopMmanageService.getShopBySeq(seq);
            return R.ok().put("shop", baseShopEntity);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("获取失败");
        }
    }

    /**
     * 添加门店
     */
    @PostMapping("save")
    public R saveShop(BaseShopEntity baseShopEntity) {
        try {
            systemShopMmanageService.saveShop(baseShopEntity);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("新增门店失败");
        }
    }

    /**
     * 更新当前门店
     */
    @PostMapping("update")
    public R updateShop(BaseShopEntity baseShopEntity) {
        try {
            if (baseShopEntity.getAreaSeq() == null && baseShopEntity.getSeq() == null) {
                return R.error("信息不完整！");
            }
            systemShopMmanageService.updateShop(baseShopEntity);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("修改门店失败");
        }
    }

    /**
     * 获得当前的区域列表
     */
    @GetMapping("areaList")
    public R areaList() {
        try {
            List<BaseAreaEntity> areaList = systemShopMmanageService.areaList(getUserSeq());
            return R.ok().put("areaList", areaList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("区域列表获取失败");
        }
    }

}
