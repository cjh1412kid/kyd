package io.nuite.modules.online_sales_app.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.R;
import io.nuite.modules.online_sales_app.annotation.XcxCustom;
import io.nuite.modules.online_sales_app.annotation.XcxLogin;
import io.nuite.modules.online_sales_app.entity.CustomerUserInfo;
import io.nuite.modules.online_sales_app.entity.TopicEntity;
import io.nuite.modules.online_sales_app.service.ShoesService;
import io.nuite.modules.online_sales_app.service.TopicService;
import io.nuite.modules.online_sales_app.utils.TopicType;
import io.nuite.modules.sr_base.entity.BaseBrandEntity;
import io.nuite.modules.sr_base.service.BaseBrandService;
import io.nuite.modules.sr_base.utils.ConfigUtils;
import io.nuite.modules.system.service.online_sale.OlsSowingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/online/miniapp/home")
@Api(tags = "线上销售APP首页", description = "首页相关接口")
public class OnlineHomeController {

    @Autowired
    private BaseBrandService baseBrandService;

    @Autowired
    private OlsSowingService olsSowingService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private ShoesService shoesService;

    @Autowired
    private ConfigUtils configUtils;

    @XcxLogin
    @GetMapping("topic")
    @ResponseBody
    @ApiOperation("获取首页主题样式")
    public R getHomeTopic(@ApiIgnore @XcxCustom CustomerUserInfo customerUserInfo) {
        Integer companySeq = customerUserInfo.getCompanySeq();
        Integer brandSeq = customerUserInfo.getBrandSeq();
        Integer shopSeq = customerUserInfo.getShopSeq();
        JSONObject jsonObject = new JSONObject();
        BaseBrandEntity baseBrandEntity = baseBrandService.selectById(brandSeq);
        if (baseBrandEntity != null) {
            String brandLogo = configUtils.getPictureBaseUrl() + "/" + configUtils.getBaseDir() + "/"
                    + configUtils.getBaseBrand() + "/" + brandSeq + "/" + baseBrandEntity.getImage();
            String brandName = baseBrandEntity.getName();
            jsonObject.put("brandLogo", brandLogo);
            jsonObject.put("brandName", brandName);
        }

        List sowingList = olsSowingService.sowingPageList(brandSeq.intValue()).getList();
        jsonObject.put("sowing", sowingList);

        EntityWrapper<TopicEntity> ew = new EntityWrapper<TopicEntity>();
        ew.eq("Brand_Seq", brandSeq);
        List<TopicEntity> topicEntities = topicService.selectList(ew);
        for (TopicEntity topicEntity : topicEntities) {
            String topicPic = configUtils.getPictureBaseUrl() + "/"
                    + configUtils.getOnlineSalesApp().getOnlineDir() + "/"
                    + configUtils.getOnlineSalesApp().getTopicDir() + "/" + brandSeq + "/" + topicEntity.getTopicImage();
            jsonObject.put("topic_" + topicEntity.getTopicType(), topicPic);
        }

        // 品牌活动：首页六双鞋
        PageUtils brandPage = shoesService.getShoesInfoPage(1, 6, null, null, TopicType.BRAND, brandSeq, null);
        jsonObject.put("brandGoods", brandPage.getList());

        // 热销爆款：首页六双鞋
        PageUtils hotPage = shoesService.getShoesInfoPage(1, 6, null, null, TopicType.HOT, brandSeq, null);
        jsonObject.put("hotGoods", hotPage.getList());

        return R.ok().put("result", jsonObject);
    }

    @XcxLogin
    @GetMapping("like")
    @ResponseBody
    @ApiOperation("获取首页：猜你喜欢")
    public R getYourLike(@ApiIgnore @XcxCustom CustomerUserInfo customerUserInfo,
                         @ApiParam("当前页码") Integer page,
                         @ApiParam("一页个数") Integer limit) {
        if (page == null || limit == null) {
            page = 1;
            limit = 10;
        }
        Integer brandSeq = customerUserInfo.getBrandSeq();

        PageUtils pageUtils = shoesService.getShoesInfoPage(page, limit, null, null, null, brandSeq, null);
        return R.ok().put("page", pageUtils);
    }
}
