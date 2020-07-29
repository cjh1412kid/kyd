package io.nuite.modules.order_platform_app.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.nuite.modules.system.entity.MeetingOrderProductEntity;
import io.nuite.modules.system.service.SysMeetingOrderProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.nuite.common.utils.Constant;
import io.nuite.common.utils.R;
import io.nuite.modules.order_platform_app.annotation.Login;
import io.nuite.modules.order_platform_app.annotation.LoginUser;
import io.nuite.modules.order_platform_app.entity.AnnouncementEntity;
import io.nuite.modules.order_platform_app.service.HomepageService;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.sr_base.entity.GoodsCategoryEntity;
import io.nuite.modules.sr_base.entity.GoodsPeriodEntity;
import io.nuite.modules.sr_base.service.GoodsCategoryService;
import io.nuite.modules.sr_base.service.GoodsPeriodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 首页接口
 *
 * @author yy
 * @date 2018-04-09 13:47
 */
@RestController
@RequestMapping("/order/app/homepage")
@Api(tags = "订货平台 - 首页", description = "轮播图 + 主推 + 新品 + 公告")
public class HomepageController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private HomepageService homepageService;

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @Autowired
    private GoodsPeriodService goodsPeriodService;

    @Autowired
    private SysMeetingOrderProductService sysMeetingOrderProductService;


    /**
     * 轮播图列表
     */
    @Login
    @GetMapping("carousel")
    @ApiOperation("轮播图列表")
    public R getImgMainUrl(@ApiIgnore @LoginUser BaseUserEntity loginUser) {
        try {
            List<Map<String, Object>> carouselList = homepageService.getHomeCarouselList(loginUser.getBrandSeq());

            for (Map<String, Object> carousel : carouselList) {
                //图片路径
                carousel.put("image", getHomeCarouselPictureUrl(carousel.get("brandSeq").toString()) + carousel.get("image"));

                int type = (int) carousel.get("type");
                //关联分类或波次，添加标题
                if (type == 2 || type == 3) {
                    StringBuilder title = new StringBuilder();
                    List<String> linkSeq = Arrays.asList(carousel.get("linkSeq").toString().split(","));
                    if (type == 2) {
                        //拼接分类名称
                        List<GoodsCategoryEntity> goodsCategoryList = goodsCategoryService.selectBatchIds(linkSeq);
                        for (GoodsCategoryEntity goodsCategory : goodsCategoryList) {
                            title.append(goodsCategory.getName()).append(",");
                        }
                    } else if (type == 3) {
                        //拼接波次名称
                        List<GoodsPeriodEntity> goodsPeriodList = goodsPeriodService.selectBatchIds(linkSeq);
                        for (GoodsPeriodEntity goodsPeriod : goodsPeriodList) {
                            title.append(goodsPeriod.getName()).append(",");
                        }
                    }
                    title.deleteCharAt(title.length() - 1);

                    carousel.put("title", title);
                }
            }

            return R.ok(carouselList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }

    }


    /**
     * 订货爆款图片
     */
    @Login
    @GetMapping("hotsaleImg")
    @ApiOperation("订货爆款图片")
    public R getHotsaleImg(@ApiIgnore @LoginUser BaseUserEntity loginUser) {
        try {
            //1.根据 品牌编号 查询 波次编号列表
            List<Integer> periodSeqList = baseService.getPeriodListByBrandSeq(loginUser.getBrandSeq());

            //2.根据 波次编号列表、用户销售类型 获取订货爆款
            List<Map<String, Object>> shoesSeqImglist = homepageService.getHotsaleShoes(periodSeqList, loginUser.getSaleType());
            for (Map<String, Object> map : shoesSeqImglist) {
                map.put("img", getGoodsShoesPictureUrl(map.get("goodId").toString()) + map.get("img"));
            }
            return R.ok(shoesSeqImglist);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }


    /**
     * 新品推荐图片
     */
    @Login
    @GetMapping("newestImg")
    @ApiOperation("新品推荐图片")
    public R getNewestImg(@ApiIgnore @LoginUser BaseUserEntity loginUser) {
        try {
            //1.根据 品牌编号 查询 波次编号列表
            List<Integer> periodSeqList = baseService.getPeriodListByBrandSeq(loginUser.getBrandSeq());

            //2.根据 波次编号列表 获取新品推荐
            List<Map<String, Object>> shoesSeqImglist = homepageService.getNewestShoes(periodSeqList, loginUser.getSaleType());
            for (Map<String, Object> map : shoesSeqImglist) {
                map.put("img", getGoodsShoesPictureUrl(map.get("goodId").toString()) + map.get("img"));
            }

            return R.ok(shoesSeqImglist);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }


    /**
     * 订货爆款列表
     */
    @Login
    @GetMapping("hotsaleList")
    @ApiOperation("订货爆款列表")
    public R getHotsaleList(@ApiIgnore @LoginUser BaseUserEntity loginUser,
                            @ApiParam("订货会序号") @RequestParam(value = "meetingSeqs", required = false) Integer meetingSeq,
                            @ApiParam("订货会年份") @RequestParam(value = "year", required = false) String year,
                            @ApiParam("货品类型:1主推,2新品,不传查询全部") @RequestParam(value = "type", required = false) Integer type,
                            @ApiParam("状态:1已订,不传查询全部") @RequestParam(value = "status", required = false) Integer status,
                            @ApiParam("起始条数") @RequestParam("start") Integer start,
                            @ApiParam("总条数") @RequestParam("num") Integer num) {
        try {
            //1.根据 品牌编号 查询 波次编号列表
            List<Integer> periodSeqList = baseService.getPeriodListByBrandSeq(loginUser.getBrandSeq());

            //2.根据 波次编号列表 获取订货爆款列表
            List<Map<String, Object>> hotsaleShoeslist = homepageService.getHotsaleShoesList(periodSeqList, loginUser.getSaleType(), start, num,meetingSeq,year,type,status,loginUser.getSeq());
            for (Map<String, Object> map : hotsaleShoeslist) {
                map.put("img", getGoodsShoesPictureUrl(map.get("goodId").toString()) + map.get("img"));

                //判断鞋子展示哪种价格
                if (loginUser.getSaleType() == Constant.UserSaleType.OEM.getValue()) {
                    map.put("purchasePrice", map.get("oemPrice"));
                } else if (loginUser.getSaleType() == Constant.UserSaleType.WHOLESALER.getValue()) {
                    map.put("purchasePrice", map.get("wholesalerPrice"));
                } else if (loginUser.getSaleType() == Constant.UserSaleType.STORE.getValue()) {
                    map.put("purchasePrice", map.get("storePrice"));
                } else if (loginUser.getSaleType() == Constant.UserSaleType.FACTORY.getValue()) {
                    map.put("purchasePrice", map.get("salePrice"));
                }
                Wrapper<MeetingOrderProductEntity> wrapper = new EntityWrapper<>();
                wrapper.eq("MeetingGoods_Seq",Integer.parseInt(map.get("meetingGoodsSeq").toString()));
                List<MeetingOrderProductEntity> list = sysMeetingOrderProductService.selectList(wrapper);
                if(list != null && list.size() > 0) {
                    map.put("isOrder",1);
                }else {
                    map.put("isOrder",0);
                }
                map.remove("oemPrice");
                map.remove("wholesalerPrice");
                map.remove("storePrice");
                map.remove("salePrice");
            }
            return R.ok(hotsaleShoeslist);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }


    /**
     * 新品推荐列表
     */
    @Login
    @GetMapping("newestList")
    @ApiOperation("新品推荐列表")
    public R getNewestList(@ApiIgnore @LoginUser BaseUserEntity loginUser,
                           @ApiParam("订货会序号") @RequestParam(value = "meetingSeqs", required = false) Integer meetingSeq,
                           @ApiParam("订货会年份") @RequestParam(value = "year", required = false) String year,
                           @ApiParam("货品类型:1主推,2新品,不传查询全部") @RequestParam(value = "type", required = false) Integer type,
                           @ApiParam("状态:1已订,不传查询全部") @RequestParam(value = "status", required = false) Integer status,
                           @ApiParam("起始条数") @RequestParam("start") Integer start,
                           @ApiParam("总条数") @RequestParam("num") Integer num) {
        try {
            //1.根据 品牌编号 查询 波次编号列表
            List<Integer> periodSeqList = baseService.getPeriodListByBrandSeq(loginUser.getBrandSeq());

            //2.根据 波次编号列表 获取新品推荐列表
            List<Map<String, Object>> newestShoesList = homepageService.getNewestShoesList(periodSeqList, loginUser.getSaleType(), start, num,meetingSeq,year,type,status,loginUser.getSeq());
            for (Map<String, Object> map : newestShoesList) {
                map.put("img", getGoodsShoesPictureUrl(map.get("goodId").toString()) + map.get("img"));

                //判断鞋子展示哪种价格
                if (loginUser.getSaleType() == Constant.UserSaleType.OEM.getValue()) {
                    map.put("purchasePrice", map.get("oemPrice"));
                } else if (loginUser.getSaleType() == Constant.UserSaleType.WHOLESALER.getValue()) {
                    map.put("purchasePrice", map.get("wholesalerPrice"));
                } else if (loginUser.getSaleType() == Constant.UserSaleType.STORE.getValue()) {
                    map.put("purchasePrice", map.get("storePrice"));
                } else if (loginUser.getSaleType() == Constant.UserSaleType.FACTORY.getValue()) {
                    map.put("purchasePrice", map.get("salePrice"));
                }
                Wrapper<MeetingOrderProductEntity> wrapper = new EntityWrapper<>();
                wrapper.eq("MeetingGoods_Seq",Integer.parseInt(map.get("meetingGoodsSeq").toString()));
                List<MeetingOrderProductEntity> list = sysMeetingOrderProductService.selectList(wrapper);
                if(list != null && list.size() > 0) {
                    map.put("isOrder",1);
                }else {
                    map.put("isOrder",0);
                }
                map.remove("oemPrice");
                map.remove("wholesalerPrice");
                map.remove("storePrice");
                map.remove("salePrice");
            }

            return R.ok(newestShoesList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }


    /**
     * 公告
     */
    @Login
    @GetMapping("announcement")
    @ApiOperation("公告列表")
    public R getAnnouncement(@ApiIgnore @LoginUser BaseUserEntity loginUser) {
        try {
            //根据Company_Seq获取公告
            List<AnnouncementEntity> announcementList = homepageService.getAnnouncementByCompanySeq(loginUser.getCompanySeq());
            if (announcementList != null && announcementList.size() > 0) {
                for (AnnouncementEntity announcement : announcementList) {
                    if (announcement.getType() == 1) {
                        announcement.setTypeName("新品");
                    } else if (announcement.getType() == 2) {
                        announcement.setTypeName("直播");
                    }
                }
            }
            return R.ok(announcementList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }

    }


}
