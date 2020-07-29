package io.nuite.modules.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.nuite.common.utils.R;
import io.nuite.modules.sr_base.entity.GoodsSXEntity;
import io.nuite.modules.sr_base.entity.GoodsSXOptionEntity;
import io.nuite.modules.sr_base.service.GoodsSXOptionService;
import io.nuite.modules.sr_base.service.GoodsSXService;
import io.nuite.modules.sr_base.service.GoodsShoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: yangchuang
 * @Date: 2018/8/13 17:44
 * @Version: 1.0
 * @Description:
 */

@RestController
@RequestMapping("/sx")
public class GoodsSxController extends AbstractController {

    @Autowired
    GoodsSXService goodsSXService;

    @Autowired
    GoodsShoesService goodsShoesService;

    @Autowired
    GoodsSXOptionService goodsSXOptionService;

    @RequestMapping("/list")
    public R getSxList() {

        try {
            List<GoodsSXEntity> sxList = goodsSXService.getGoodsSXListByCompanySeq(getUser().getCompanySeq());

            if (sxList == null || sxList.isEmpty()) {
                return R.error(-1, "未查询到属性");
            }
            return R.ok().put("list", sxList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    @RequestMapping("/update")
    public R updateSX(GoodsSXEntity sx) {

        try {
            sx.setInputTime(new Date());
            goodsSXService.updateById(sx);
            return R.ok("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    /**
     * 属性与商品有关联则不删除
     *
     * @param seq
     * @return
     */
    @RequestMapping("/delete")
    public R deleteSX(Integer seq, String sxid) {

        try {
            List<GoodsSXOptionEntity> opts = goodsSXOptionService.selectList(new EntityWrapper<GoodsSXOptionEntity>().eq("SX_Seq", seq));
            for (GoodsSXOptionEntity opt : opts) {
                if (!opt.getCode().equals("000")) {
                    int count = goodsShoesService.getCountBySXAndOption(getUser().getCompanySeq(), sxid, opt.getCode());
                    if (count > 0) {
                        return R.error("该属性与商品有关联，不能删除");
                    }
                }
            }
            goodsSXService.deleteById(seq);
            return R.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    @PostMapping("/save")
    public R saveSX(@RequestBody Map<String, String> sxs) {

        try {
            Integer companySeq = getUser().getCompanySeq();
            for (Map.Entry<String, String> sx : sxs.entrySet()) {

                //先判断属性对象是否存在
                GoodsSXEntity goodsSXEntity = goodsSXService.selectOne(new EntityWrapper<GoodsSXEntity>()
                        .eq("Company_Seq", companySeq)
                        .eq("SXID", sx.getKey()));

                //属性不存在则创建
                if (goodsSXEntity == null) {
                    goodsSXEntity = new GoodsSXEntity();
                    goodsSXEntity.setCompanySeq(companySeq);
                    goodsSXEntity.setSXId(sx.getKey());
                    goodsSXEntity.setSXName(sx.getValue());

                    goodsSXService.insert(goodsSXEntity);
                } else {
                    //属性存在且名称不同则更新
                    if (!goodsSXEntity.getSXName().equals(sx.getValue())) {
                        goodsSXEntity.setSXName(sx.getValue());
                        goodsSXEntity.setInputTime(new Date());
                        goodsSXService.updateById(goodsSXEntity);
                    }

                }
            }

            return R.ok("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }


    @RequestMapping("visible")
    public R updateSxVisible(GoodsSXEntity sx) {

        goodsSXService.updateById(sx);
        return R.ok("可见状态更改成功");
    }

}
