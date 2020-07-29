package io.nuite.modules.system.controller.home_page_management;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.nuite.common.utils.MapUtils;
import io.nuite.common.utils.PageInfo;
import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.R;
import io.nuite.modules.system.controller.AbstractController;
import io.nuite.modules.system.entity.online_sale.OlsSowingEntity;
import io.nuite.modules.system.service.online_sale.OlsSowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 小程序轮播管理
 */
@RestController
@RequestMapping("/system/ols/sowing")
public class SysOlsSowingController extends AbstractController {

    @Autowired
    private OlsSowingService olsSowingService;

    @RequestMapping("/list")
    public R list() {
        try {
            PageUtils sowingPage = olsSowingService.sowingPageList(getUser().getBrandSeq());
            return R.ok().put("page", sowingPage);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    @RequestMapping("/goodList")
    public R goodList(@RequestParam Map<String, Object> params) {
        try {
            PageInfo pageInfo = new PageInfo(params);
            PageUtils goodsPage = olsSowingService.olsGoodsList(getUser().getBrandSeq(), pageInfo);
            return R.ok().put("page", goodsPage);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    @RequestMapping("/edit")
    public R edit(@RequestParam("seq") Integer seq) {
        try {
            OlsSowingEntity olsSowingEntity = olsSowingService.selectSowing(seq);
            return R.ok().put("sowing", olsSowingEntity);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    @RequestMapping("/save")
    public R save(OlsSowingEntity olsSowingEntity) {
        try {
            EntityWrapper<OlsSowingEntity> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("Brand_Seq", getUser().getBrandSeq());
            Integer i = olsSowingService.selectCount(entityWrapper);
            if (i < 5) {
                olsSowingEntity.setBrandSeq(getUser().getBrandSeq());
                olsSowingService.saveSowing(olsSowingEntity);
                return R.ok();
            }
            return R.error("轮播图最多只能创建5个");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error("添加失败！");
        }
    }

    @RequestMapping("/update")
    public R update(OlsSowingEntity olsSowingEntity) {
        try {
            if (olsSowingEntity != null) {
                olsSowingService.updateSowing(getUser().getBrandSeq(), olsSowingEntity);
                return R.ok();
            }
            return R.error("参数错误！");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    @PostMapping("del")
    public R delete(Integer seq) {
        if (seq == null) {
            return R.error("参数错误！");
        }
        try {
            olsSowingService.deleteByMap(new MapUtils().put("Brand_Seq", getUser().getBrandSeq()).put("Seq", seq));
            return R.ok();
        } catch (Exception e) {
            return R.error("添加失败！");
        }
    }
}
