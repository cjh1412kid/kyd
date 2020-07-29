package io.nuite.modules.order_platform_app.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Joiner;

import io.nuite.common.utils.Constant;
import io.nuite.common.utils.R;
import io.nuite.modules.order_platform_app.annotation.Login;
import io.nuite.modules.order_platform_app.annotation.LoginUser;
import io.nuite.modules.order_platform_app.service.ShoesInfoService;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 鞋子信息接口
 * @author yy
 * @date 2018-04-18 13:47
 */
@RestController
@RequestMapping("/order/app/shoesInfo")
@Api(tags = "订货平台 - 鞋子信息", description = "鞋子分类 + 全部鞋款列表 + 货号查询")
public class ShoesInfoController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ShoesInfoService shoesInfoService;
    
    
    /**
     * 鞋子所有分类
     */
    @Login
    @GetMapping("shoesCategory")
    @ApiOperation("鞋子所有分类")
    public R getShoesCategory(@ApiIgnore @LoginUser BaseUserEntity loginUser) {
    	try {
    		//根据 工厂方的公司编号 查询 鞋子分类列表
    		List<Map<String, Object>> rootList = shoesInfoService.getShoesCategory(loginUser.getCompanySeq(), 0);
    		List<Map<String, Object>> firstChildList;
    		List<Map<String, Object>> secondChildList;
    		for(Map<String, Object> rootMap : rootList) {
    			firstChildList = shoesInfoService.getShoesCategory(loginUser.getCompanySeq(), (int)rootMap.get("seq"));
    			for(Map<String, Object> firstChildMap : firstChildList) {
    				secondChildList = shoesInfoService.getShoesCategory(loginUser.getCompanySeq(), (int)firstChildMap.get("seq"));
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
     * 全部鞋款列表
     */
    @Login
    @GetMapping("shoesList")
    @ApiOperation("全部鞋款列表(支持按多个分类查询)")
    public R getShoesList(@ApiIgnore @LoginUser BaseUserEntity loginUser,
    		@ApiParam("波次序号(逗号分隔)") @RequestParam(value = "periodSeqList", required = false) List<Integer> periodSeqList,
    		@ApiParam("鞋子分类(逗号分隔)") @RequestParam(value = "categorySeqList", required = false) List<Integer> categorySeqList,
    		@ApiParam("鞋子序号(逗号分隔)") @RequestParam(value = "shoesSeqList", required = false) List<Integer> shoesSeqList,
    		@ApiParam("货品名称或货号") @RequestParam(value = "goodNameId", required = false) String goodNameId,
    		@ApiParam("排序字段(0:综合 1:订货数量 2:下单数量)") @RequestParam(value = "orderBy", required = false) Integer orderBy,
    		@ApiParam("排序方式(0:降序 1:升序)") @RequestParam(value = "orderDir", required = false) Integer orderDir,
    		@ApiParam("起始条数") @RequestParam("start") Integer start,
    		@ApiParam("总条数") @RequestParam("num") Integer num) {
    	try {
    		//1.根据 品牌编号 查询 波次编号列表
    		if(periodSeqList == null || periodSeqList.size() == 0) {
    			periodSeqList = baseService.getPeriodListByBrandSeq(loginUser.getBrandSeq());
    		} 
    		String periodSeq = Joiner.on(",").join(periodSeqList);
    		
    		//2.根据 波次编号、能看到的鞋子类型， 获取全部鞋款列表
			List<Map<String,Object>> shoesList = shoesInfoService.getShoesList(periodSeq, loginUser.getSaleType(), categorySeqList, shoesSeqList, goodNameId, orderBy, orderDir, start, num);
			for(Map<String, Object> map : shoesList) {
				map.put("img", getGoodsShoesPictureUrl(map.get("goodId").toString()) + map.get("img"));
				
				//判断鞋子展示哪种价格
				if(loginUser.getSaleType() == Constant.UserSaleType.OEM.getValue()) {
					map.put("purchasePrice", map.get("oemPrice"));
				} else if(loginUser.getSaleType() == Constant.UserSaleType.WHOLESALER.getValue()) {
					map.put("purchasePrice", map.get("wholesalerPrice"));
				} else if(loginUser.getSaleType() == Constant.UserSaleType.STORE.getValue()) {
					map.put("purchasePrice", map.get("storePrice"));
				} else if(loginUser.getSaleType() == Constant.UserSaleType.FACTORY.getValue()){
					map.put("purchasePrice", map.get("salePrice"));
				}
				map.remove("oemPrice");
				map.remove("wholesalerPrice");
				map.remove("storePrice");
				map.remove("salePrice");
			}
			return R.ok(shoesList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}
    }
    
    
    
    /**
     * 获取当前用户能查看的所有鞋子的货号
     */
    @Login
    @GetMapping("getAllGoodIds")
    @ApiOperation("获取当前用户能查看的所有鞋子的货号")
    public R getAllGoodIds(@ApiIgnore @LoginUser BaseUserEntity loginUser) {
    	try {
    		//1.根据 品牌编号 查询 波次编号列表
    		List<Integer> periodSeqList = baseService.getPeriodListByBrandSeq(loginUser.getBrandSeq());
    		String periodSeq = Joiner.on(",").join(periodSeqList);
    		
    		//2.根据 波次编号、能看到的鞋子类型， 获取全部鞋款的货号
			List<Map<String,Object>> goodIdList = shoesInfoService.getAllGoodIds(periodSeq, loginUser.getSaleType());
			return R.ok(goodIdList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}
    }
    

    
    /**
     * 获取6个热搜商品货号
     */
    @Login
    @GetMapping("getHotSearchGoodIds")
    @ApiOperation("获取6个热搜商品货号")
    public R getHotSearchGoodIds(@ApiIgnore @LoginUser BaseUserEntity loginUser) {
    	try {
    		//1.根据 品牌编号 查询 波次编号列表
    		List<Integer> periodSeqList = baseService.getPeriodListByBrandSeq(loginUser.getBrandSeq());
    		String periodSeq = Joiner.on(",").join(periodSeqList);
    		
    		//2.根据 波次编号、能看到的鞋子类型， 获取6个热搜商品货号
			List<Map<String,Object>> goodIdList = shoesInfoService.getHotSearchGoodIds(periodSeq, loginUser.getSaleType());
			return R.ok(goodIdList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}
    }
    
    
}
