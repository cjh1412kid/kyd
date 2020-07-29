package io.nuite.modules.order_platform_app.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.nuite.common.utils.DateUtils;
import io.nuite.common.utils.R;
import io.nuite.modules.order_platform_app.annotation.Login;
import io.nuite.modules.order_platform_app.annotation.LoginUser;
import io.nuite.modules.order_platform_app.dao.GoodsDataDao;
import io.nuite.modules.order_platform_app.entity.ShoesInfoEntity;
import io.nuite.modules.order_platform_app.entity.ShoesValuateEntity;
import io.nuite.modules.order_platform_app.service.GoodsDataService;
import io.nuite.modules.order_platform_app.service.ShoesInfoService;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.sr_base.entity.GoodsCategoryEntity;
import io.nuite.modules.sr_base.entity.GoodsSXEntity;
import io.nuite.modules.sr_base.entity.GoodsViewEntity;
import io.nuite.modules.sr_base.service.GoodsCategoryService;
import io.nuite.modules.sr_base.service.GoodsSXService;
import io.nuite.modules.sr_base.service.GoodsViewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 鞋子详细信息接口
 * @author fl
 * @date 2018-04-10
 */
@RestController
@RequestMapping("/order/app/goodsData")
@Api(tags = "订货平台 - 鞋子详细信息", description = "鞋子详细信息 + 评价列表 + 评分、收藏、建议")
public class GoodsDataController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private GoodsDataService goodsDataService;
	
	@Autowired
	private GoodsViewService goodsViewService;
	
	@Autowired
	private GoodsCategoryService goodsCategoryService;
	
	@Autowired
	private ShoesInfoService shoesInfoService;
	
	@Autowired
	private GoodsDataDao goodsDataDao;
	
    @Autowired
    private GoodsSXService goodsSXService;

	
	
	/**
	 * 根据鞋子seq获取鞋子详细信息
	 */
	@Login
	@GetMapping("getGoodData")
	@ApiOperation("获取鞋子详细信息")
	public R getGoodDetail(@ApiIgnore @LoginUser BaseUserEntity loginUser,
			@ApiParam("鞋子seq") @RequestParam("seq") Integer shoesSeq,
			@ApiParam("是否搜索(0:不是, 1:是)") @RequestParam(value = "isSearch", required = false, defaultValue="0") Integer isSearch) {
		try {
			//1.查询视图，获取鞋子基本信息
			GoodsViewEntity goodsViewEntity = goodsViewService.getGoodsViewBySeq(shoesSeq);
			Map<String, Object> goodsViewMap = new HashMap<String, Object>();
			goodsViewMap.put("seq", goodsViewEntity.getSeq());
		    goodsViewMap.put("goodId", goodsViewEntity.getGoodID());
		    goodsViewMap.put("goodName", goodsViewEntity.getGoodName());
		    goodsViewMap.put("introduce", goodsViewEntity.getIntroduce());
		    goodsViewMap.put("inputTime", goodsViewEntity.getInputTime());
				//详细图片
		    if(StringUtils.isNotBlank(goodsViewEntity.getDescription())) {
				String[] descriptionArr = goodsViewEntity.getDescription().split(",");
				for (int i = 0; i < descriptionArr.length; i++) {
					descriptionArr[i] = getGoodsShoesPictureUrl(goodsViewEntity.getGoodID()) + descriptionArr[i];
				}
			    goodsViewMap.put("listDescription", descriptionArr);
		    } else {
		    	goodsViewMap.put("listDescription", "");
		    }
				//视频
		    if(StringUtils.isNotBlank(goodsViewEntity.getVideo())) {
		    	 goodsViewMap.put("video",getGoodsShoesPictureUrl(goodsViewEntity.getGoodID()) + goodsViewEntity.getVideo());
		    } else {
		    	goodsViewMap.put("video", "");
		    }
				//主图
			List<String> imgList = new ArrayList<String>();
			if(StringUtils.isNotBlank(goodsViewEntity.getImg1())) {
				imgList.add(getGoodsShoesPictureUrl(goodsViewEntity.getGoodID()) + goodsViewEntity.getImg1());
			}
			if(StringUtils.isNotBlank(goodsViewEntity.getImg2())) {
				imgList.add(getGoodsShoesPictureUrl(goodsViewEntity.getGoodID()) + goodsViewEntity.getImg2());
			}
			if(StringUtils.isNotBlank(goodsViewEntity.getImg3())) {
				imgList.add(getGoodsShoesPictureUrl(goodsViewEntity.getGoodID()) + goodsViewEntity.getImg3());
			}
			if(StringUtils.isNotBlank(goodsViewEntity.getImg4())) {
				imgList.add(getGoodsShoesPictureUrl(goodsViewEntity.getGoodID()) + goodsViewEntity.getImg4());
			}
			if(StringUtils.isNotBlank(goodsViewEntity.getImg5())) {
				imgList.add(getGoodsShoesPictureUrl(goodsViewEntity.getGoodID()) + goodsViewEntity.getImg5());
			}
			goodsViewMap.put("imgList", imgList);

		    	//属性1~20
            List<GoodsSXEntity> goodsSXList = goodsSXService.getGoodsSXListByCompanySeq(loginUser.getCompanySeq());
            		//所有可见的自定义属性
            List<String> visibleSXList = new ArrayList<String>();
            for (GoodsSXEntity goodsSXEntity : goodsSXList) {
            	if(goodsSXEntity.getVisible() == 0) {
            		visibleSXList.add(goodsSXEntity.getSXId());
            	}
            }
	    	Map<String, String> SXmap = new HashMap<String, String>();
		    if(goodsViewEntity.getSX1Name() != null && goodsViewEntity.getSX1Value() != null && visibleSXList.contains("SX1")) {
		    	SXmap.put(goodsViewEntity.getSX1Name(), goodsViewEntity.getSX1Value());
		    }
		    if(goodsViewEntity.getSX2Name() != null && goodsViewEntity.getSX2Value() != null && visibleSXList.contains("SX2")) {
		    	SXmap.put(goodsViewEntity.getSX2Name(), goodsViewEntity.getSX2Value());
		    }
		    if(goodsViewEntity.getSX3Name() != null && goodsViewEntity.getSX3Value() != null && visibleSXList.contains("SX3")) {
		    	SXmap.put(goodsViewEntity.getSX3Name(), goodsViewEntity.getSX3Value());
		    }
		    if(goodsViewEntity.getSX4Name() != null && goodsViewEntity.getSX4Value() != null && visibleSXList.contains("SX4")) {
		    	SXmap.put(goodsViewEntity.getSX4Name(), goodsViewEntity.getSX4Value());
		    }
		    if(goodsViewEntity.getSX5Name() != null && goodsViewEntity.getSX5Value() != null && visibleSXList.contains("SX5")) {
		    	SXmap.put(goodsViewEntity.getSX5Name(), goodsViewEntity.getSX5Value());
		    }
		    if(goodsViewEntity.getSX6Name() != null && goodsViewEntity.getSX6Value() != null && visibleSXList.contains("SX6")) {
		    	SXmap.put(goodsViewEntity.getSX6Name(), goodsViewEntity.getSX6Value());
		    }
		    if(goodsViewEntity.getSX7Name() != null && goodsViewEntity.getSX7Value() != null && visibleSXList.contains("SX7")) {
		    	SXmap.put(goodsViewEntity.getSX7Name(), goodsViewEntity.getSX7Value());
		    }
		    if(goodsViewEntity.getSX8Name() != null && goodsViewEntity.getSX8Value() != null && visibleSXList.contains("SX8")) {
		    	SXmap.put(goodsViewEntity.getSX8Name(), goodsViewEntity.getSX8Value());
		    }
		    if(goodsViewEntity.getSX9Name() != null && goodsViewEntity.getSX9Value() != null && visibleSXList.contains("SX9")) {
		    	SXmap.put(goodsViewEntity.getSX9Name(), goodsViewEntity.getSX9Value());
		    }
		    if(goodsViewEntity.getSX10Name() != null && goodsViewEntity.getSX10Value() != null && visibleSXList.contains("SX10")) {
		    	SXmap.put(goodsViewEntity.getSX10Name(), goodsViewEntity.getSX10Value());
		    }
		    if(goodsViewEntity.getSX11Name() != null && goodsViewEntity.getSX11Value() != null && visibleSXList.contains("SX11")) {
		    	SXmap.put(goodsViewEntity.getSX11Name(), goodsViewEntity.getSX11Value());
		    }
		    if(goodsViewEntity.getSX12Name() != null && goodsViewEntity.getSX12Value() != null && visibleSXList.contains("SX12")) {
		    	SXmap.put(goodsViewEntity.getSX12Name(), goodsViewEntity.getSX12Value());
		    }
		    if(goodsViewEntity.getSX13Name() != null && goodsViewEntity.getSX13Value() != null && visibleSXList.contains("SX13")) {
		    	SXmap.put(goodsViewEntity.getSX13Name(), goodsViewEntity.getSX13Value());
		    }
		    if(goodsViewEntity.getSX14Name() != null && goodsViewEntity.getSX14Value() != null && visibleSXList.contains("SX14")) {
		    	SXmap.put(goodsViewEntity.getSX14Name(), goodsViewEntity.getSX14Value());
		    }
		    if(goodsViewEntity.getSX15Name() != null && goodsViewEntity.getSX15Value() != null && visibleSXList.contains("SX15")) {
		    	SXmap.put(goodsViewEntity.getSX15Name(), goodsViewEntity.getSX15Value());
		    }
		    if(goodsViewEntity.getSX16Name() != null && goodsViewEntity.getSX16Value() != null && visibleSXList.contains("SX16")) {
		    	SXmap.put(goodsViewEntity.getSX16Name(), goodsViewEntity.getSX16Value());
		    }
		    if(goodsViewEntity.getSX17Name() != null && goodsViewEntity.getSX17Value() != null && visibleSXList.contains("SX17")) {
		    	SXmap.put(goodsViewEntity.getSX17Name(), goodsViewEntity.getSX17Value());
		    }
		    if(goodsViewEntity.getSX18Name() != null && goodsViewEntity.getSX18Value() != null && visibleSXList.contains("SX18")) {
		    	SXmap.put(goodsViewEntity.getSX18Name(), goodsViewEntity.getSX18Value());
		    }
		    if(goodsViewEntity.getSX19Name() != null && goodsViewEntity.getSX19Value() != null && visibleSXList.contains("SX19")) {
		    	SXmap.put(goodsViewEntity.getSX19Name(), goodsViewEntity.getSX19Value());
		    }
		    if(goodsViewEntity.getSX20Name() != null && goodsViewEntity.getSX20Value() != null && visibleSXList.contains("SX20")) {
		    	SXmap.put(goodsViewEntity.getSX20Name(), goodsViewEntity.getSX20Value());
		    }
			goodsViewMap.put("SX", SXmap);
			
			
			//2.查询分类，获取鞋子分类名称
			GoodsCategoryEntity goodsCategoryEntity = goodsCategoryService.getGoodsCategoryBySeq(goodsViewEntity.getCategorySeq());
			goodsViewMap.put("categorySeq", goodsViewEntity.getCategorySeq());
			goodsViewMap.put("categoryName", goodsCategoryEntity.getName());
			
			
			//3.查询shoesInfo，获取价格，上下架信息
			ShoesInfoEntity shoesInfoEntity = shoesInfoService.getShoesInfoByShoesSeq(goodsViewEntity.getSeq());
				//价格
			goodsViewMap.put("oemPrice", shoesInfoEntity.getOemPrice());
			goodsViewMap.put("wholesalerPrice", shoesInfoEntity.getWholesalerPrice());
			goodsViewMap.put("storePrice", shoesInfoEntity.getStorePrice());
			goodsViewMap.put("salePrice", shoesInfoEntity.getSalePrice());
				//按下架时间判断鞋子是否已下架，修改onSale状态
			if(shoesInfoEntity.getOffSaleTime() != null && DateUtils.compareDay(new Date(), shoesInfoEntity.getOffSaleTime()) > 0) {
				shoesInfoEntity.setOnSale(0);
			}
			goodsViewMap.put("onSale", shoesInfoEntity.getOnSale());
			
			
			//4.获取平均分、总库存、总订货量
				//平均分
			Float avgScore = goodsDataDao.getAvgScore(shoesSeq);
			goodsViewMap.put("avgScore", avgScore);
				//总库存
			Integer stockQuantity = goodsDataService.getStockQuantity(shoesSeq);
			goodsViewMap.put("stockQuantity", stockQuantity);
				//总订货量
			Integer orderQuantity = goodsDataDao.getOrderQuantity(shoesSeq);
			goodsViewMap.put("orderQuantity", orderQuantity);

			
			//5.查询该用户对该鞋子的评分、收藏、建议
			ShoesValuateEntity shoesValuateEntity = goodsDataService.getUserShoesValuate(loginUser.getSeq(), shoesSeq);
			if (shoesValuateEntity != null) {
				goodsViewMap.put("score", shoesValuateEntity.getScore());  //数据库score有默认值0，无需判断null
				goodsViewMap.put("isCollected", shoesValuateEntity.getIsCollected());  //数据库isCollected有默认值0，无需判断null
				if (shoesValuateEntity.getSuggest() == null || shoesValuateEntity.getSuggest().equals("")) {
					goodsViewMap.put("suggest", 0);// 没有建议过
				} else {
					goodsViewMap.put("suggest", 1);// 有建议过
				}
			} else {
				// 没有记录  收藏默认是0 评分默认是0 没有建议过
				goodsViewMap.put("score", (float) 0);
				goodsViewMap.put("isCollected", 0);
				goodsViewMap.put("suggest", 0);
			}
			
			
			/**添加鞋子搜索次数**/
			if (isSearch != null && isSearch == 1) {
				try {
					goodsDataService.addShoesSearchTimes(shoesSeq);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("添加鞋子搜索次数异常" + e.getMessage(), e);
				}
			}
			
			/**添加或更新鞋子浏览记录**/
			try {
				goodsDataService.addOrUpdateShoesBrowseRecord(loginUser.getSeq(), shoesSeq);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("添加或更新鞋子浏览记录异常" + e.getMessage(), e);
			}

			return R.ok(goodsViewMap);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error(e.getMessage());
		}
	}

	
	
	
	/**
	 * 鞋子评价列表
	 */
	@Login
	@GetMapping("showEvaluateView")
	@ApiOperation("鞋子评价列表")
	public R evaluateView(@ApiParam("鞋子的seq") @RequestParam("seq") Integer seq,
			@ApiParam("起始条数") @RequestParam("start") Integer start, 
			@ApiParam("总条数") @RequestParam("num") Integer num) {
		try {
			List<Map<String, Object>> list = goodsDataService.getAllEvaluate(seq, start - 1, num);
			// 循环拼接其路径
			for (Map<String, Object> map : list) {
				map.put("headImg", getBaseUserPictureUrl(map.get("userSeq").toString()) + map.get("headImg"));
			}
			return R.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error(e.getMessage());
		}

	}

	
	
	
	/**
	 * 修改鞋子评分、收藏、建议
	 */
	@Login
	@PostMapping("updateShoesValuate")
	@ApiOperation("修改鞋子评分、收藏、建议")
	public R updateShoesValuate(@ApiIgnore @RequestAttribute("userSeq") Integer userSeq,
			@ApiParam("鞋子seq") @RequestParam("shoesSeq") Integer shoesSeq,
			@ApiParam("评分") @RequestParam(value = "score", required = false) Float score,
			@ApiParam("是否收藏(0不收藏  1收藏)") @RequestParam(value = "isCollected", required = false) Integer isCollected,
			@ApiParam("建议") @RequestParam(value = "suggest", required = false) String suggest) {
		try {
			ShoesValuateEntity shoesValuateEntity = new ShoesValuateEntity();
			if(score != null) {
				shoesValuateEntity.setScore(score);
			} else if(isCollected != null) {
				shoesValuateEntity.setIsCollected(isCollected);
				shoesValuateEntity.setCollectedTime(new Date());
			} else if(suggest != null && !suggest.equals("")) {
				shoesValuateEntity.setSuggest(suggest);
				shoesValuateEntity.setSuggestTime(new Date());
			}
			goodsDataService.updateShoesValuate(userSeq, shoesSeq, shoesValuateEntity);
			return R.ok();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

	}

}
