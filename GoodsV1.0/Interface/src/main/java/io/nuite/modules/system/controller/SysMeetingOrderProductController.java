package io.nuite.modules.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.nuite.common.utils.R;
import io.nuite.modules.system.entity.MeetingOrderProductEntity;
import io.nuite.modules.system.service.SysMeetingOrderProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订货会订单货品
 *
 * @author yangchuang
 * @email ${email}
 * @date 2019-04-17 13:42:11
 */
@RestController
@RequestMapping("/system/meetingorderproduct")
@Api(tags = "后台-订货会订单货品")
public class SysMeetingOrderProductController extends AbstractController {
    
    @Autowired
    private SysMeetingOrderProductService sysMeetingOrderProductService;
    

    
    /**
     * 取消 一个订单 中  一个货品 的 一个颜色
     * @param meetingOrderSeq
     * @param goodSeq
     * @param colorSeq
     * @param isCancel
     * @return
     */
    @PostMapping("/cancel")
    @ApiOperation(value = "修改货品的取消状态")
    public R modifyCancelState(@ApiParam("订货会订单序号") @RequestParam Integer meetingOrderSeq,
                               @ApiParam("订货会货品序号") @RequestParam Integer goodSeq,
                               @ApiParam("颜色序号") @RequestParam Integer colorSeq,
                               @ApiParam("是的取消") @RequestParam Integer isCancel) {
        
        sysMeetingOrderProductService.modifyCancel(meetingOrderSeq, goodSeq, colorSeq, isCancel);
        
        return R.ok();
    }
    
    
    
    /**
     * 取消订货会订单
     * @param meetingOrderSeq
     * @return
     */
    @GetMapping("/cancelAll/{seq}")
    @ApiOperation(value = "修改货品的取消状态")
    public R cancelAllProducts(@ApiParam("订货会订单序号") @PathVariable("seq") Integer meetingOrderSeq) {
        MeetingOrderProductEntity meetingOrderProductEntity = new MeetingOrderProductEntity();
        meetingOrderProductEntity.setCancel(1);
        sysMeetingOrderProductService.update(meetingOrderProductEntity, new EntityWrapper<MeetingOrderProductEntity>()
            .eq("MeetingOrder_Seq", meetingOrderSeq)
        );
        
        return R.ok();
    }
    
    
    
    /**
     * 取消货品的某个颜色 （所有订单里都取消）
     * @param goodSeq
     * @param colorSeq
     * @param isCancel
     * @return
     */
    @PostMapping("/cancel/color")
    @ApiOperation(value = "修改货品颜色的取消状态")
    public R modifyGoodColorCancelState(@ApiParam("订货会货品序号") @RequestParam Integer goodSeq,
                                        @ApiParam("颜色序号") @RequestParam Integer colorSeq,
                                        @ApiParam("是的取消") @RequestParam Integer isCancel) {
        
        sysMeetingOrderProductService.modifyGoodColorCancel(getUser().getCompanySeq(), goodSeq, colorSeq, isCancel);
        
        return R.ok();
    }
    
    
    /**
     * 取消一个货品
     * @param goodSeq
     * @param isCancel
     * @return
     */
    @PostMapping("/cancel/goodID")
    @ApiOperation(value = "修改货品颜色的取消状态")
    public R modifyGoodCancelState(@ApiParam("订货会货品序号") @RequestParam Integer goodSeq,
                                   @ApiParam("是的取消") @RequestParam Integer isCancel) {
        
        sysMeetingOrderProductService.modifyGoodCancel(getUser().getCompanySeq(), goodSeq, isCancel);
        
        return R.ok();
    }
    
    @RequestMapping("/save")
    public R saveOrderProduct(@RequestParam Map<String, Object> paramMap,Integer meetingOrderSeq) {
    	String productsStr=(String) paramMap.get("products");
    	JSONArray products=JSONArray.parseArray(productsStr);
    	for (Object object : products) {
        	JSONObject product=JSONObject.parseObject(object.toString());
        	JSONArray details=product.getJSONArray("details");
        	for (Object map : details) {
        		JSONObject detail=JSONObject.parseObject(map.toString());
    			Integer goodSeq=detail.getInteger("goodSeq");
    			Integer colorSeq=detail.getInteger("colorSeq");
    			JSONObject sizes=detail.getJSONObject("size");
    			 for (String key : sizes.keySet()) {
    				  Integer num = sizes.getInteger(key);
    				  Integer size=Integer.parseInt(key);
    			   //根据meetingOrderSeq,meetingGoods_Seq,Color_Seq,Size 修改BuyCount
    				   MeetingOrderProductEntity meetingOrderProductEntity=new MeetingOrderProductEntity();
    				   meetingOrderProductEntity.setMeetingOrderSeq(meetingOrderSeq);
    				   meetingOrderProductEntity.setMeetingGoodsSeq(goodSeq);
    				   meetingOrderProductEntity.setColorSeq(colorSeq);
    				   meetingOrderProductEntity.setSize(size);
    				   meetingOrderProductEntity.setBuyCount(num);
    			   sysMeetingOrderProductService.updateBySize(meetingOrderProductEntity);
    			 }
    		}
		}
    	return R.ok();
    }
    
}
