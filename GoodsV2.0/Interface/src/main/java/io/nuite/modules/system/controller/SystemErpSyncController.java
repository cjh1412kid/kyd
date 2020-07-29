package io.nuite.modules.system.controller;

import io.nuite.common.utils.R;
import io.nuite.modules.sr_base.entity.BaseESmartEntity;
import io.nuite.modules.sr_base.service.BaseESmartService;
import io.nuite.modules.sr_base.utils.ESmartConfig;
import io.nuite.modules.system.erp.service.impl.ErpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/erp/sync")
public class SystemErpSyncController extends AbstractController {
    @Autowired
    private ErpServiceImpl erpService;

    @Autowired
    private ESmartConfig eSmartConfig;

    @Autowired
    private BaseESmartService baseESmartService;

    @GetMapping("size")
    public R size() {
        try {
            BaseESmartEntity baseESmartEntity = baseESmartService.getByUserSeq(getUserSeq().intValue());
            erpService.selectSizes(baseESmartEntity, eSmartConfig.getUrl() + "size/v1.0");
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    @GetMapping("color")
    public R color() {
        try {
            BaseESmartEntity baseESmartEntity = baseESmartService.getByUserSeq(getUserSeq().intValue());
            erpService.selectColors(baseESmartEntity, eSmartConfig.getUrl() + "color/v1.0");
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    @GetMapping("category")
    public R category() {
        try {
            BaseESmartEntity baseESmartEntity = baseESmartService.getByUserSeq(getUserSeq().intValue());
            erpService.selectCategorys(baseESmartEntity, eSmartConfig.getUrl() + "category/v1.0");
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    @GetMapping("sx")
    public R sx() {
        try {
            BaseESmartEntity baseESmartEntity = baseESmartService.getByUserSeq(getUserSeq().intValue());
            erpService.selectGoodsSX(baseESmartEntity, eSmartConfig.getUrl() + "sx/v1.0");
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    @GetMapping("kehu")
    public R kehu() {
        try {
            BaseESmartEntity baseESmartEntity = baseESmartService.getByUserSeq(getUserSeq().intValue());
            erpService.selectKEHUs(baseESmartEntity, eSmartConfig.getUrl() + "kehu/v1.0");
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    @GetMapping("goods")
    public R goods() {
        try {
            BaseESmartEntity baseESmartEntity = baseESmartService.getByUserSeq(getUserSeq().intValue());

            erpService.selectBrands(baseESmartEntity, eSmartConfig.getUrl() + "brand/v1.0");

            erpService.selectGoods(baseESmartEntity, eSmartConfig.getUrl() + "goods/v1.0");

            erpService.selectGoodsStock(baseESmartEntity, eSmartConfig.getUrl() + "stock/v1.0");
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    @GetMapping("order")
    public R order() {
        try {
            BaseESmartEntity baseESmartEntity = baseESmartService.getByUserSeq(getUserSeq().intValue());
            erpService.selectOrder(baseESmartEntity, eSmartConfig.getUrl() + "dingdan/v1.0");
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    @GetMapping("goodsImport")
    public R goodsImport() {
        try {
            BaseESmartEntity baseESmartEntity = baseESmartService.getByUserSeq(getUserSeq().intValue());
            erpService.importGoodsToERP(baseESmartEntity, eSmartConfig.getUrl() + "shangpin.import/v1.0");
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }
}
