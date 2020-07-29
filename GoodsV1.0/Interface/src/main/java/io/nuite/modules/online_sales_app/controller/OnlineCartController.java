package io.nuite.modules.online_sales_app.controller;

import io.nuite.common.utils.R;
import io.nuite.modules.online_sales_app.annotation.XcxCustom;
import io.nuite.modules.online_sales_app.annotation.XcxLogin;
import io.nuite.modules.online_sales_app.entity.CustomerUserInfo;
import io.nuite.modules.online_sales_app.entity.ShoppingCartEntity;
import io.nuite.modules.online_sales_app.service.OnlineCartService;
import io.nuite.modules.online_sales_app.service.SalesShoppingCartService;
import io.nuite.modules.sr_base.dao.GoodsShoesDao;
import io.nuite.modules.sr_base.entity.GoodsCategoryEntity;
import io.nuite.modules.sr_base.entity.GoodsShoesEntity;
import io.nuite.modules.sr_base.service.GoodsCategoryService;
import io.nuite.modules.sr_base.service.GoodsShoesService;
import io.nuite.modules.sr_base.utils.ConfigUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/online/miniapp/cart")
@Api(tags = "线上销售APP购物车", description = "购物车相关接口")
public class OnlineCartController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OnlineCartService onlineCartService;

    @Autowired
    private ConfigUtils configUtils;
    
    @Autowired
    private SalesShoppingCartService salesShoppingCartService;
    
    @Autowired
    private GoodsShoesService goodsShoesService;

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    /**
     * 加入购物车
     */
    @XcxLogin
    @PostMapping("add")
    @ApiOperation("加入购物车")
    public R addToShoppingCart(@ApiIgnore @XcxCustom CustomerUserInfo customerUserInfo,
                               @ApiParam("鞋子数据Seq") @RequestParam("shoesDataSeq") Integer shoesDataSeq,
                               @ApiParam("数量") @RequestParam("buyCount") Integer buyCount,
                               @ApiParam("总价格") @RequestParam("totalPrice") BigDecimal totalPrice,
                               @ApiParam("商品分享链") @RequestParam("openIDLinks") String openIDLinks) {
        try {
            //查询购物车列表，看是否已经存在此商品
            ShoppingCartEntity shoppingCartEntity = onlineCartService.getShoppingCartByUserSeqShoesDataSeq(customerUserInfo.getSeq().intValue(), shoesDataSeq, openIDLinks);
            //购物车不存在，新增
            if (shoppingCartEntity == null) {
                shoppingCartEntity = new ShoppingCartEntity();
                shoppingCartEntity.setUserSeq(customerUserInfo.getSeq().intValue());
                shoppingCartEntity.setShoesDataSeq(shoesDataSeq);
                shoppingCartEntity.setOpenIDLinks(openIDLinks);
                shoppingCartEntity.setCompanySeq(customerUserInfo.getCompanySeq().intValue());
                shoppingCartEntity.setShopSeq(customerUserInfo.getShopSeq() == null ? null : customerUserInfo.getShopSeq().intValue());
                shoppingCartEntity.setBuyCount(buyCount);
                shoppingCartEntity.setTotalPrice(totalPrice);
                shoppingCartEntity.setIsChecked(1);
                shoppingCartEntity.setInputTime(new Date());
                shoppingCartEntity.setDel(0);
                try {
                    onlineCartService.addToShoppingCart(shoppingCartEntity);
                    return R.ok("加入成功");
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.getMessage(), e);
                    return R.error(HttpStatus.SC_FORBIDDEN, "加入失败");
                }
            } else { //已存在则修改
                shoppingCartEntity.setBuyCount(shoppingCartEntity.getBuyCount() + buyCount);
                totalPrice = shoppingCartEntity.getTotalPrice().add(totalPrice);
                shoppingCartEntity.setTotalPrice(totalPrice);
                try {
                    onlineCartService.updateShoppingCart(shoppingCartEntity);
                    return R.ok("加入成功");
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(e.getMessage(), e);
                    return R.error(HttpStatus.SC_FORBIDDEN, "加入失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }

    }


    //基础库访问目录
    private String baseDir() {
        return configUtils.getPictureBaseUrl() + "/" + configUtils.getBaseDir() + "/";
    }

    //鞋子基本信息图片路径
    private String getGoodsShoesPictureUrl(String folder) {
        return baseDir() + configUtils.getGoodsShoes() + "/" + folder + "/";
    }


    /**
     * 购物车列表
     */
    @XcxLogin
    @GetMapping("list")
    @ApiOperation("购物车列表")
    public R shoppingCartList(@ApiIgnore @RequestAttribute("userSeq") Integer userSeq) {
        try {
            List<Map<String, Object>> shoppingCartList = onlineCartService.getShoppingCartListByUserSeq(userSeq);
            for (Map<String, Object> map : shoppingCartList) {
                map.put("img", getGoodsShoesPictureUrl(map.get("goodId").toString()) + map.get("img"));
            }
            return R.ok(shoppingCartList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }

    }


    /**
     * 修改购物车
     */
    @XcxLogin
    @PostMapping("edit")
    @ApiOperation("修改购物车 （数量、是否选中）")
    public R changeShoppingCart(@ApiParam("购物车Seq") @RequestParam("shoppingCartSeq") Integer shoppingCartSeq,
                                @ApiParam("数量") @RequestParam(value = "buyCount", required = false) Integer buyCount,
                                @ApiParam("总价格") @RequestParam(value = "totalPrice", required = false) BigDecimal totalPrice,
                                @ApiParam("是否选中(0不勾选 1勾选)") @RequestParam(value = "isChecked", required = false) Integer isChecked) {
        try {
            ShoppingCartEntity shoppingCartEntity = new ShoppingCartEntity();
            shoppingCartEntity.setSeq(shoppingCartSeq);
            if (buyCount != null) {
                shoppingCartEntity.setBuyCount(buyCount);
            }
            if (totalPrice != null) {
                shoppingCartEntity.setTotalPrice(totalPrice);
            }
            if (isChecked != null) {
                shoppingCartEntity.setIsChecked(isChecked);
            }
            onlineCartService.updateShoppingCart(shoppingCartEntity);

            return R.ok("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }

    }


    /**
     * 购物车全选/全不选
     */
    @XcxLogin
    @PostMapping("choose")
    @ApiOperation("购物车全选/全不选")
    public R checkAllShoppingCart(@ApiIgnore @RequestAttribute("userSeq") Integer userSeq,
                                  @ApiParam("是否选中(0不勾选 1勾选)") @RequestParam("isChecked") Integer isChecked) {
        try {
            ShoppingCartEntity shoppingCartEntity = new ShoppingCartEntity();
            shoppingCartEntity.setIsChecked(isChecked);
            onlineCartService.checkAllShoppingCart(shoppingCartEntity, userSeq);

            return R.ok("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }

    }


    /**
     * 删除购物车
     */
    @XcxLogin
    @PostMapping("delete")
    @ApiOperation("删除购物车")
    public R deleteShoppingCart(@ApiParam("购物车Seq") @RequestParam("shoppingCartSeq") Integer shoppingCartSeq) {
        try {
            onlineCartService.deleteShoppingCart(shoppingCartSeq);
            return R.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }

    }
    
    @XcxLogin
    @PostMapping("addCart")
    @ApiOperation("删除购物车")
    public R addCart(@ApiIgnore @XcxCustom CustomerUserInfo customerUserInfo,
            @ApiParam("鞋子数据Seq") @RequestParam("goodSeq") Integer goodSeq,
            @ApiParam("客户型号") @RequestParam("userGoodId") String userGoodId,
            @ApiParam("是否配件") @RequestParam("isChart") Integer isChart,
            @ApiParam("配件数") @RequestParam("perBoxNum") Integer perBoxNum,
            @ApiParam("配件列表") @RequestParam("colorArray") String colorArray,
            @ApiParam("购物车seq")@RequestParam("seq") Integer seq
    		) {
    	  try {
    		  salesShoppingCartService.sizeAllot(customerUserInfo,goodSeq,isChart, userGoodId, perBoxNum, colorArray,seq);
              return R.ok("添加成功");
          } catch (Exception e) {
              e.printStackTrace();
              logger.error(e.getMessage(), e);
              return R.error("服务器异常");
          }
    }
    
    /**
     * 购物车列表
     */
    @XcxLogin
    @GetMapping("cartList")
    @ApiOperation("购物车列表")
    public R cartList(@ApiIgnore @RequestAttribute("userSeq") Integer userSeq) {
        try {
            List<Map<String, Object>> shoppingCartList = salesShoppingCartService.getShoppingCartListByUserSeq(userSeq);
            for (Map<String, Object> map : shoppingCartList) {
            	Integer shoesSeq=(Integer) map.get("shoesSeq");
            	//查询品类
            	GoodsShoesEntity goodsShoesEntity=goodsShoesService.selectById(shoesSeq);
            	Integer thrSeq=goodsShoesEntity.getCategorySeq();
            	//三级分类
            	GoodsCategoryEntity thrCate=goodsCategoryService.selectById(thrSeq);
            	Integer secSeq=thrCate.getParentSeq();
            	//二级分类
            	GoodsCategoryEntity secCate=goodsCategoryService.selectById(secSeq);
            	Integer friSeq=secCate.getParentSeq();
            	//一级分类
            	GoodsCategoryEntity friCate=goodsCategoryService.selectById(friSeq);
            	map.put("cateName", friCate.getName()+"-"+secCate.getName()+"-"+thrCate.getName());
            	
            	Integer shoppingCartSeq=(Integer) map.get("seq");
            	
            	Map<String, Object> salesShoppingCartMap=salesShoppingCartService.getCartDetail(shoppingCartSeq);
            	
            	map.put("detail", salesShoppingCartMap);
            	map.put("img", getGoodsShoesPictureUrl(map.get("goodId").toString()) + map.get("img"));
            }
            return R.ok(shoppingCartList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }

    }
    
    /**
     * 删除购物车
     */
    @XcxLogin
    @PostMapping("deleteCart")
    @ApiOperation("删除购物车")
    public R deleteCart(@ApiParam("购物车Seq") @RequestParam("shoppingCartSeq") Integer shoppingCartSeq) {
        try {
        	salesShoppingCartService.deleteShoppingCart(shoppingCartSeq);
            return R.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }

    }

}
