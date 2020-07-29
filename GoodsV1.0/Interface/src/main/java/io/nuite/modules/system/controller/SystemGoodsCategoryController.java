package io.nuite.modules.system.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.nuite.common.utils.R;
import io.nuite.modules.sr_base.entity.GoodsCategoryEntity;
import io.nuite.modules.system.service.SystemGoodsCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 后台 - 鞋子分类
 * 
 * @author yy
 * @date 2018-05-28 13:47
 */
@RestController
@RequestMapping("/system/goodsCategory")
@Api(tags = "后台 - 鞋子分类", description = "鞋子分类")
public class SystemGoodsCategoryController extends AbstractController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SystemGoodsCategoryService systemGoodsCategoryService;

    /**
     * 全部分类
     */
    @GetMapping("list")
    @ApiOperation("全部分类")
    public R list() {
        try {
            // 公司序号
            Integer companySeq = getUser().getCompanySeq();
            List<GoodsCategoryEntity> list = systemGoodsCategoryService.getAllGoodsCategoryByCompanySeq(companySeq);
            return R.ok().put("list", list);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    /**
     * 新增分类
     */
    @PostMapping("add")
    @ApiOperation("新增分类")
    public R add(@ApiParam("父节点Seq") @RequestParam(value = "parentSeq", required = false) Integer parentSeq,
            @ApiParam("分类名称") @RequestParam(value = "name", required = false) String name,
            @ApiParam("是否可见（0:可见 1:不可见）") @RequestParam(value = "visible", required = false) Integer visible) {
        try {
            // 公司序号
            Integer companySeq = getUser().getCompanySeq();

            GoodsCategoryEntity goodsCategoryEntity = new GoodsCategoryEntity();
            goodsCategoryEntity.setParentSeq(parentSeq);
            goodsCategoryEntity.setCompanySeq(companySeq);
            goodsCategoryEntity.setName(name);
            goodsCategoryEntity.setInputTime(new Date());
            goodsCategoryEntity.setVisible(visible);
            goodsCategoryEntity.setDel(0);
            Integer seq = systemGoodsCategoryService.addGoodsCategory(goodsCategoryEntity);
            return R.ok("新增成功").put("seq", seq);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }

    }

    /**
     * 修改分类
     */
    @PostMapping("update")
    @ApiOperation("修改分类")
    public R update(@ApiParam("分类seq") @RequestParam("seq") Integer seq,
            @ApiParam("父节点Seq") @RequestParam(value = "parentSeq", required = false) Integer parentSeq,
            @ApiParam("分类名称") @RequestParam(value = "name", required = false) String name,
            @ApiParam("是否可见（0:可见 1:不可见）") @RequestParam(value = "visible", required = false) Integer visible) {
        try {
            GoodsCategoryEntity goodsCategoryEntity = new GoodsCategoryEntity();
            goodsCategoryEntity.setSeq(seq);
            if (parentSeq != null) {
                goodsCategoryEntity.setParentSeq(parentSeq);
            }
            if (name != null && !name.equals("")) {
                goodsCategoryEntity.setName(name);
            }
            if (visible != null && (visible == 0 || visible == 1)) {
                goodsCategoryEntity.setVisible(visible);
            }
            systemGoodsCategoryService.updateGoodsCategory(goodsCategoryEntity);
            return R.ok("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }

    }

    /**
     * 删除分类
     */
    @GetMapping("delete")
    @ApiOperation("修改分类")
    public R delete(@ApiParam("分类seq") @RequestParam("seq") Integer seq) {
        try {
            // 判断该分类下是否有鞋子
            Boolean flag = systemGoodsCategoryService.hasCategoryInCategory(seq);
            if (flag) {
                return R.error("该分类已被使用，不可删除");
            }
            systemGoodsCategoryService.deleteGoodsCategoryAll(seq);
            return R.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器开小差啦~");
        }

    }

    /**
     * 全部分类
     */
    @GetMapping("nOcategoryLsit")
    @ApiOperation("底分类")
    public R nOcategoryLsit() {
        try {
            List<Integer> list = systemGoodsCategoryService.nOcategoryLsit(getUser().getCompanySeq());
            return R.ok().put("list", list);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

}
