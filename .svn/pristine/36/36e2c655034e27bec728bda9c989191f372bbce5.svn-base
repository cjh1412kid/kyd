package io.nuite.modules.job.task;

import io.nuite.modules.sr_base.entity.BaseESmartEntity;
import io.nuite.modules.sr_base.service.BaseESmartService;
import io.nuite.modules.sr_base.service.BaseUserService;
import io.nuite.modules.sr_base.utils.ESmartConfig;
import io.nuite.modules.system.erp.service.impl.ErpServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: yangchuang
 * @Date: 2018/8/3 15:05
 * @Version: 1.0
 * @Description:
 */

@Component("erpTask")
public class ErpTask {

    public Logger log = LoggerFactory.getLogger(ErpTask.class);

    @Autowired
    ESmartConfig eSmartConfig;

    @Autowired
    ErpServiceImpl erpService;

    @Autowired
    BaseUserService baseUserService;

    @Autowired
    BaseESmartService baseESmartService;

    /**
     * 获取所有有key的用户
     *
     * @return
     */
    public List<BaseESmartEntity> getBaseESmartEntities() {
        //获取所有工厂用户序号
        List<Integer> factoryUserSeqs = baseUserService.getSeqsByAttachTypeAndSaleType(1, 1);

        if (factoryUserSeqs == null) {
            log.info("Erp任务异常: 未查询到工厂用户，无法执行同步任务");
            return null;
        }

        List<BaseESmartEntity> eSmartEntitys = new ArrayList<>();
        for (Integer seq : factoryUserSeqs) {
            BaseESmartEntity eSmartEntity = baseESmartService.getByUserSeq(seq);
            if (eSmartEntity != null) {
                eSmartEntitys.add(eSmartEntity);
            }
        }
        return eSmartEntitys;
    }

    public void selectSizes() {
        List<BaseESmartEntity> eSmartEntitys = getBaseESmartEntities();

        if (eSmartEntitys == null) {
            log.info("Erp任务异常: 未查询到key用户，无法执行尺码同步");
            return;
        }

        for (BaseESmartEntity baseESmartEntity : eSmartEntitys) {
            erpService.selectSizes(baseESmartEntity, eSmartConfig.getUrl() + "size/v1.0");
        }
    }

    public void selectBrands() {
        List<BaseESmartEntity> eSmartEntitys = getBaseESmartEntities();

        if (eSmartEntitys == null) {
            log.info("Erp任务异常: 未查询到key用户，无法执行尺码同步");
            return;
        }

        for (BaseESmartEntity baseESmartEntity : eSmartEntitys) {
            erpService.selectBrands(baseESmartEntity, eSmartConfig.getUrl() + "brand/v1.0");
        }
    }

    public void selectColors() {
        //获取所有有key的用户
        List<BaseESmartEntity> eSmartEntitys = getBaseESmartEntities();

        if (eSmartEntitys == null) {
            log.info("Erp任务异常: 未查询到key用户，无法执行颜色同步");
            return;
        }

        for (BaseESmartEntity baseESmartEntity : eSmartEntitys) {
            erpService.selectColors(baseESmartEntity, eSmartConfig.getUrl() + "color/v1.0");
        }
    }

    public void selectCategorys() {
        List<BaseESmartEntity> eSmartEntitys = getBaseESmartEntities();

        if (eSmartEntitys == null) {
            log.info("Erp任务异常: 未查询到key用户，无法执行类型同步");
            return;
        }

        for (BaseESmartEntity baseESmartEntity : eSmartEntitys) {
            erpService.selectCategorys(baseESmartEntity, eSmartConfig.getUrl() + "category/v1.0");
        }
    }

    public void selectSX() {
        //获取所有有key的用户
        List<BaseESmartEntity> eSmartEntitys = getBaseESmartEntities();

        if (eSmartEntitys == null) {
            log.info("Erp任务异常: 未查询到key用户，无法执行属性同步");
            return;
        }

        for (BaseESmartEntity baseESmartEntity : eSmartEntitys) {
            erpService.selectGoodsSX(baseESmartEntity, eSmartConfig.getUrl() + "sx/v1.0");
        }

    }

    public void selectKeHu() {
        //获取所有有key的用户
        List<BaseESmartEntity> eSmartEntitys = getBaseESmartEntities();

        if (eSmartEntitys == null) {
            log.info("Erp任务异常: 未查询到key用户，无法执行客户同步");
            return;
        }

        //有key的用户开始执行同步任务
        for (BaseESmartEntity baseESmartEntity : eSmartEntitys) {
            erpService.selectKEHUs(baseESmartEntity, eSmartConfig.getUrl() + "kehu/v1.0");

        }
    }

    public void selectStock() {
        //获取所有有key的用户
        List<BaseESmartEntity> eSmartEntitys = getBaseESmartEntities();

        if (eSmartEntitys == null) {
            log.info("Erp任务异常: 未查询到key用户，无法执行库存同步");
            return;
        }

        //有key的用户开始执行同步任务
        for (BaseESmartEntity baseESmartEntity : eSmartEntitys) {
            erpService.selectGoodsStock(baseESmartEntity, eSmartConfig.getUrl() + "stock/v1.0");
        }
    }

    public void selectOrder() {
        //获取所有有key的用户
        List<BaseESmartEntity> eSmartEntitys = getBaseESmartEntities();

        if (eSmartEntitys == null) {
            log.info("Erp任务异常: 未查询到key用户，无法执行订单同步");
            return;
        }

        //有key的用户开始执行同步任务
        for (BaseESmartEntity baseESmartEntity : eSmartEntitys) {

            erpService.selectOrder(baseESmartEntity, eSmartConfig.getUrl() + "dingdan/v1.0");
        }
    }

    public void selectGoods() {
        //获取所有有key的用户
        List<BaseESmartEntity> eSmartEntitys = getBaseESmartEntities();

        if (eSmartEntitys == null) {
            log.info("Erp任务异常: 未查询到key用户，无法执行商品同步");
            return;
        }

        //有key的用户开始执行同步任务
        for (BaseESmartEntity baseESmartEntity : eSmartEntitys) {
            erpService.selectGoods(baseESmartEntity, eSmartConfig.getUrl() + "goods/v1.0");
        }

    }

    /**
     * 将当天的订单商品同步到erp
     */
    public void importGoodsToErp() {
        //获取所有有key的用户
        List<BaseESmartEntity> eSmartEntitys = getBaseESmartEntities();

        if (eSmartEntitys == null) {
            log.info("Erp任务异常: 未查询到key用户，无法执行商品同步");
            return;
        }

        //有key的用户开始执行同步任务
        try {
            for (BaseESmartEntity baseESmartEntity : eSmartEntitys) {
                erpService.importGoodsToERP(baseESmartEntity, eSmartConfig.getUrl() + "shangpin.import/v1.0");
            }
        } catch (Exception e) {
            log.info("同步商品到ERP任务异常:");
            log.info(e.getMessage(), e);
        }

    }

    public void selectAll() {
        //获取所有有key的用户
        List<BaseESmartEntity> eSmartEntitys = getBaseESmartEntities();

        if (eSmartEntitys == null) {
            return;
        }

        for (BaseESmartEntity baseESmartEntity : eSmartEntitys) {
            Integer companySeq = baseESmartEntity.getCompanySeq();
            log.info("erp sync start:" + companySeq);

            erpService.selectSizes(baseESmartEntity, eSmartConfig.getUrl() + "size/v1.0");

            erpService.selectColors(baseESmartEntity, eSmartConfig.getUrl() + "color/v1.0");

            erpService.selectBrands(baseESmartEntity, eSmartConfig.getUrl() + "brand/v1.0");

            erpService.selectCategorys(baseESmartEntity, eSmartConfig.getUrl() + "category/v1.0");

            erpService.selectGoodsSX(baseESmartEntity, eSmartConfig.getUrl() + "sx/v1.0");

            erpService.selectKEHUs(baseESmartEntity, eSmartConfig.getUrl() + "kehu/v1.0");

            erpService.selectGoods(baseESmartEntity, eSmartConfig.getUrl() + "goods/v1.0");

            erpService.selectGoodsStock(baseESmartEntity, eSmartConfig.getUrl() + "stock/v1.0");

            erpService.selectOrder(baseESmartEntity, eSmartConfig.getUrl() + "dingdan/v1.0");
            log.info("erp sync end:" + companySeq);
        }

    }

}
