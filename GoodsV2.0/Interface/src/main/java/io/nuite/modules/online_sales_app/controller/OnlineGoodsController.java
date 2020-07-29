package io.nuite.modules.online_sales_app.controller;

import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.R;
import io.nuite.modules.online_sales_app.annotation.XcxCustom;
import io.nuite.modules.online_sales_app.annotation.XcxLogin;
import io.nuite.modules.online_sales_app.entity.CustomerUserInfo;
import io.nuite.modules.online_sales_app.entity.ShoesDataEntity;
import io.nuite.modules.online_sales_app.service.OrderCartService;
import io.nuite.modules.online_sales_app.service.RankService;
import io.nuite.modules.online_sales_app.service.SalesShoppingCartService;
import io.nuite.modules.online_sales_app.service.ShoesService;
import io.nuite.modules.online_sales_app.utils.TopicType;
import io.nuite.modules.sr_base.service.GoodsCategoryService;
import io.nuite.modules.sr_base.service.GoodsPeriodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/online/miniapp/goods")
@Api(tags = "线上销售APP商品", description = "商品相关接口")
public class OnlineGoodsController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ShoesService onlineShoesService;

    @Autowired
    private SalesShoppingCartService salesShoppingCartService;
    
    @Autowired
	 private RankService rankService;
	 
	 @Autowired
	 private GoodsPeriodService goodsPeriodService;
	 
	 @Autowired
	 private GoodsCategoryService goodsCategoryService;
	 
    
	 @Autowired
	 private OrderCartService orderCartService;
	 
    /**
     * 商品列表
     */
    @XcxLogin
    @GetMapping("list")
    @ApiOperation("获取商品列表")
    @ResponseBody
    public R goodsList(@ApiIgnore @XcxCustom CustomerUserInfo customerUserInfo,
                       @ApiParam("当前页码") Integer page,
                       @ApiParam("一页个数") Integer limit,
                       @ApiParam("排序字段(0:综合 1:销量 2:价格 3:上架时间)") Integer orderBy,
                       @ApiParam("排序方式(0:降序 1:升序)") Integer orderDir,
                       @ApiParam("商品分类序号") Integer categorySeq,
                       @ApiParam("商品专题") Integer topicType) {
        if (page == null || limit == null) {
            page = 1;
            limit = 10;
        }
        if (categorySeq == -1) {
            categorySeq = null;
        }
        TopicType topicTypeEnum;
        try {
            topicTypeEnum = TopicType.valueOf(topicType);
        } catch (Exception e) {
            topicTypeEnum = null;
        }
        PageUtils pageUtils = onlineShoesService.getShoesInfoPage(page, limit, orderBy, orderDir, topicTypeEnum, customerUserInfo.getBrandSeq(), categorySeq);
        return R.ok().put("page", pageUtils);
    }

    /**
     * 根据商品的id获取商品的信息
     */
    @XcxLogin
    @PostMapping("detail")
    @ApiOperation("获取商品的信息")
    public R getGoodData(@ApiIgnore @XcxCustom CustomerUserInfo customerUserInfo, Integer seq) {
        if (seq == null) {
            return R.error("商品id不能为空！");
        }
        Map<String, Object> shoesInfo = onlineShoesService.getShoesDetail(customerUserInfo.getBrandSeq(), seq);
        
        Integer salesNum=0;
        
        //排行榜
   	 	//获取当前用户的companySeq
		 Integer companySeq=customerUserInfo.getCompanySeq();
		 Integer brandSeq=customerUserInfo.getBrandSeq();
		 Date date=new Date();
		 Integer periodSeq=goodsPeriodService.getPeriodByTime(brandSeq, date);
		 //次数排行榜
		List<Map<String, Object>> countRankList=rankService.getRank(companySeq, periodSeq, null,1);
        Integer countRank=null;
        Integer length=countRankList.size();
		for (Integer i=0;i<countRankList.size();i++) {
			Integer shoesSeq=(Integer) countRankList.get(i).get("shoesSeq");
			if(shoesSeq.equals(seq)) {
				countRank=length-i;
				break;
			}
		}
        
		 //订单数排行榜
		List<Map<String, Object>> numRankList=rankService.getRank(companySeq, periodSeq, null,2);
		 Integer numRank=null;
		 Integer length1=numRankList.size();
		 for (Integer i=0;i<numRankList.size();i++) {
				Integer shoesSeq=(Integer) numRankList.get(i).get("shoesSeq");
				if(shoesSeq.equals(seq)) {
					numRank=length1-i;
					salesNum=(Integer) numRankList.get(i).get("num");
					break;
				}
			}
		 
        //配码详情
        Map<String, Object> salesMap=salesShoppingCartService.getCartList(seq, customerUserInfo.getSeq());
		 
		 
		 
		 //是否有订单
        Integer orderCount=orderCartService.getCountByShoesSeq(seq, customerUserInfo.getSeq());
		 
        
        if (shoesInfo != null) {
            return R.ok().put("detail", shoesInfo).put("countRank", countRank).put("numRank", numRank).put("orderCount", orderCount).put("salesMap", salesMap).put("salesNum", salesNum);
        } else {
            return R.error(-1, "商品不存在");
        }
    }

    /**
     * 根据商品的id获取尺码和颜色分类
     */
    @PostMapping("selectDetail")
    @ApiOperation("获取商品的信息尺码和颜色，用于选择商品")
    public R getGoodSizeAndColorData(Integer seq) {
        if (seq == null) {
            return R.error("商品id不能为空！");
        }
        List<ShoesDataEntity> shoesInfo = onlineShoesService.getShoesCartDetail(seq);
        // --兼容旧版本 2018.07.31-- //
        for(ShoesDataEntity shoesDataEntity : shoesInfo) {
        	shoesDataEntity.setSize(shoesDataEntity.getSizeName());
        }
        // --兼容旧版本 2018.07.31-- //
        return R.ok(shoesInfo);
    }

    /**
     * 获取分类
     */
    @XcxLogin
    @GetMapping("category")
    @ApiOperation("获取分类")
    @ResponseBody
    public R category(@ApiIgnore @XcxCustom CustomerUserInfo customerUserInfo) {
        try {
            Integer companySeq = customerUserInfo.getCompanySeq();

            //2.根据 工厂方的公司编号 查询 鞋子分类列表
            List<Map<String, Object>> rootList = onlineShoesService.getShoesCategory(companySeq.intValue(), 0);
            List<Map<String, Object>> firstChildList;
            List<Map<String, Object>> secondChildList;
            for (Map<String, Object> rootMap : rootList) {
                firstChildList = onlineShoesService.getShoesCategory(companySeq.intValue(), (int) rootMap.get("seq"));
                for (Map<String, Object> firstChildMap : firstChildList) {
                    secondChildList = onlineShoesService.getShoesCategory(companySeq.intValue(), (int) firstChildMap.get("seq"));
                    firstChildMap.put("child", secondChildList);
                }
                rootMap.put("child", firstChildList);
            }
            return R.ok(rootList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }
    
    /**
     * 商品列表
     */
    @XcxLogin
    @GetMapping("rankList")
    @ApiOperation("获取商品列表")
    @ResponseBody
    public R rankList(@ApiIgnore @XcxCustom CustomerUserInfo customerUserInfo
                     ) {
    	 try {
		 //获取当前用户的companySeq
		 Integer companySeq=customerUserInfo.getCompanySeq();
		 Integer brandSeq=customerUserInfo.getBrandSeq();
		 Date date=new Date();
		 Integer periodSeq=goodsPeriodService.getPeriodByTime(brandSeq, date);
		 //排行榜
		List<Map<String, Object>> rankList=rankService.getRankList(companySeq, periodSeq);
		//查询当前公司的一级分类
		
		
		
		return R.ok().put("rankList", rankList);
    	 } catch (Exception e) {
	            logger.error(e.getMessage(), e);
	            return R.error("服务器异常");
	     }
    }
    
    
}
