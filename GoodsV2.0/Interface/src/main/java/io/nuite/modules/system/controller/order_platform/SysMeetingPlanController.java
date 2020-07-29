package io.nuite.modules.system.controller.order_platform;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.nuite.common.utils.ImportMultipartExcelUtil;
import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.R;
import io.nuite.modules.order_platform_app.entity.MeetingPlanEntity;
import io.nuite.modules.sr_base.dao.GoodsCategoryDao;
import io.nuite.modules.sr_base.dao.GoodsSXDao;
import io.nuite.modules.sr_base.dao.GoodsSXOptionDao;
import io.nuite.modules.sr_base.entity.GoodsCategoryEntity;
import io.nuite.modules.sr_base.entity.GoodsPeriodEntity;
import io.nuite.modules.sr_base.entity.GoodsSXEntity;
import io.nuite.modules.sr_base.entity.GoodsSXOptionEntity;
import io.nuite.modules.system.controller.AbstractController;
import io.nuite.modules.system.service.order_platform.SysMeetingPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 订货会计划
 */
@RestController
@RequestMapping("/order/meetingPlan")
@Api(tags = "后台  - 订货会计划", description = "订货会计划")
public class SysMeetingPlanController extends AbstractController {

    @Autowired
    private SysMeetingPlanService sysMeetingPlanService;
    
    @Autowired
    private GoodsCategoryDao goodsCategoryDao;
    
    @Autowired
    private GoodsSXDao goodsSXDao;
    
    @Autowired
    private GoodsSXOptionDao goodsSXOptionDao;
    
    /**
     * 订货方列表   (全部、已上传、未上传订货计划的订货方)
     */
    @GetMapping("/list")
    @ApiOperation("订货方的列表")
    public R orderPartyList(@ApiParam("销售类型") @RequestParam("saleType") Integer saleType,
    						@ApiParam("波次") @RequestParam("periodSeq") Integer periodSeq,
    						@ApiParam("订货计划上传状态（0:全部 1:已上传 2:未上传）") @RequestParam("uploadState") Integer uploadState,
                            @ApiParam("页码") @RequestParam("page") Integer pageNo,
                            @ApiParam("每页条数") @RequestParam("limit") Integer pageSize) {
        try {
            PageUtils pageUtils = sysMeetingPlanService.getUserPlanList(getUser().getCompanySeq(), saleType, periodSeq, uploadState, pageNo, pageSize);
            return R.ok().put("page", pageUtils);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("获取列表失败");
        }
    }

    
    /**
     * 波次列表
     */
    @GetMapping("/periodList")
    public R periodList() {
        try {
            List<GoodsPeriodEntity> list = sysMeetingPlanService.getPeriodListByBrandSeq(getUser().getBrandSeq());
            return R.ok().put("list", list);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("获取波次列表失败");
        }
    }
    
    
    /**
     * 订货计划详细列表
     */
    @GetMapping("/detail")
    @ApiOperation("订货计划详细列表")
    public R detail(@ApiParam("用户Seq") @RequestParam("userSeq") Integer userSeq,
    				@ApiParam("波次") @RequestParam("periodSeq") Integer periodSeq,
                    @ApiParam("页码") @RequestParam("page") Integer pageNo,
                    @ApiParam("每页条数") @RequestParam("limit") Integer pageSize) {
        try {
            PageUtils pageUtils = sysMeetingPlanService.getUserPlanDetailsList(getUser().getCompanySeq(), userSeq, periodSeq, pageNo, pageSize);
            return R.ok().put("page", pageUtils);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("获取列表失败");
        }
    }
    
    /**
     * 删除用户的订货计划
     */
    @GetMapping("/delete")
    public R delete(@ApiParam("波次序号") @RequestParam("periodSeq") Integer periodSeq,
    				@ApiParam("用户Seq数组") @RequestParam("userSeqArr") Integer[] userSeqArr) {
        try {
        	for(Integer userSeq : userSeqArr) {
                sysMeetingPlanService.deleteMeetingPlan(periodSeq, userSeq);
        	}
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("删除失败");
        }
    }
    
    
    
    /**
     * 导入订货计划
     */
    @PostMapping("upload")
    @ApiOperation("导入订货计划")
    public R uploadExcel(@ApiParam("用户Seq数组") @RequestParam("userSeqArr") Integer[] userSeqArr,
    					@ApiParam("波次序号") @RequestParam("periodSeq") Integer periodSeq,
    					@ApiParam("excel文件") @RequestParam(value = "excelFile", required = false) MultipartFile excelFile) {
        try {
        	if(userSeqArr.length == 0) {
        		return R.error("请选择订货方！");
        	}
            if(excelFile.isEmpty()){
            	return R.error("文件不存在！");
            }
            
            //解析excel文件为二维list
            List<List<Object>> list = ImportMultipartExcelUtil.importExcel(excelFile);
            
            if(list != null && list.size() > 0) {
            	//检查excel列标题是否正确
            	List<Object> head = list.get(0);
            	if(head.size() != 6 || head.get(0) == null || !head.get(0).toString().equals("大类") 
            						|| head.get(1) == null || !head.get(1).toString().equals("品类")
            						|| head.get(2) == null || !head.get(2).toString().equals("风格")
            						|| head.get(3) == null || !head.get(3).toString().equals("订货量")
            						|| head.get(4) == null || !head.get(4).toString().equals("订货金额")
            						|| head.get(5) == null || !head.get(5).toString().equals("订货款数")) {
            		return R.error("excel表内容不符合要求， 第一行为标题行“大类 品类 风格 订货量 订货金额 订货款数“");
            	}
            	
            	//获取每一行数据，创建一个计划实体
                List<MeetingPlanEntity> meetingPlanList = new ArrayList<MeetingPlanEntity>();
                MeetingPlanEntity meetingPlanEntity;
                Integer companySeq = getUser().getCompanySeq();
                Date nowDate = new Date();
                //验证重复Map
                Map<String, Integer> duplicateMap = new HashMap<String, Integer>();
	            for(int i = 1; i < list.size(); i++) {
	            	List<Object> plan = list.get(i);
	            	if(plan.size() != 6 || plan.get(0) == null || StringUtils.isBlank(plan.get(0).toString())
	            						|| plan.get(1) == null || StringUtils.isBlank(plan.get(1).toString())
	            						|| plan.get(2) == null || StringUtils.isBlank(plan.get(2).toString())
	            						|| plan.get(3) == null || StringUtils.isBlank(plan.get(3).toString())
	            						|| plan.get(4) == null || StringUtils.isBlank(plan.get(4).toString())
	            						|| plan.get(5) == null || StringUtils.isBlank(plan.get(5).toString())) {
	            		return R.error("excel表内容不符合要求，第" + (i+1) + "行数据不能为空");
	            	}
	            	
	            	//验证重复
	                String key = plan.get(0).toString() + plan.get(1).toString() + plan.get(2).toString();
	                if(duplicateMap.containsKey(key)) {
	                	int i1 = duplicateMap.get(key);
	                	return R.error("excel表内容不符合要求，第" + (i+1) + "行数据和第" + (i1+1) + "行数据重复");
	                } else {
		            	duplicateMap.put(key, i);
	                }
	            	
	            	//创建实体
	            	meetingPlanEntity = new MeetingPlanEntity();
	            	meetingPlanEntity.setCompanySeq(companySeq);
	            	meetingPlanEntity.setPeriodSeq(periodSeq);
	            	meetingPlanEntity.setUserSeq(null);
	            	
	            		//分类seq
	            	GoodsCategoryEntity goodsCategoryEntity = new GoodsCategoryEntity();
	            	goodsCategoryEntity.setCompanySeq(companySeq);
	            	goodsCategoryEntity.setName(plan.get(0).toString());
	            	goodsCategoryEntity = goodsCategoryDao.selectOne(goodsCategoryEntity);
	            	if(goodsCategoryEntity == null) {
	            		return R.error("导入失败: 分类“" + plan.get(0).toString() + "”不存在");
	            	}
	            	meetingPlanEntity.setCategorySeq(goodsCategoryEntity.getSeq());
	            	
	            		//SX2对应code
	            	GoodsSXEntity goodsSX2Entity = new GoodsSXEntity();
	            	goodsSX2Entity.setCompanySeq(companySeq);
	            	goodsSX2Entity.setSXId("SX2");
	            	goodsSX2Entity = goodsSXDao.selectOne(goodsSX2Entity);
                	
                	GoodsSXOptionEntity goodsSX2OptionEntity = new GoodsSXOptionEntity();
                	goodsSX2OptionEntity.setSXSeq(goodsSX2Entity.getSeq());
                	goodsSX2OptionEntity.setValue(plan.get(1).toString());
                	goodsSX2OptionEntity = goodsSXOptionDao.selectOne(goodsSX2OptionEntity);
	            	if(goodsSX2OptionEntity == null) {
	            		return R.error("导入失败: 属性“" + plan.get(1).toString() + "”不存在");
	            	}
	            	meetingPlanEntity.setSX2(goodsSX2OptionEntity.getCode());
	            	
	            		//SX3对应code
	            	GoodsSXEntity goodsSX3Entity = new GoodsSXEntity();
	            	goodsSX3Entity.setCompanySeq(companySeq);
	            	goodsSX3Entity.setSXId("SX3");
	            	goodsSX3Entity = goodsSXDao.selectOne(goodsSX3Entity);
	            	
                	GoodsSXOptionEntity goodsSX3OptionEntity = new GoodsSXOptionEntity();
                	goodsSX3OptionEntity.setSXSeq(goodsSX3Entity.getSeq());
                	goodsSX3OptionEntity.setValue(plan.get(2).toString());
                	goodsSX3OptionEntity = goodsSXOptionDao.selectOne(goodsSX3OptionEntity);
	            	if(goodsSX3OptionEntity == null) {
	            		return R.error("导入失败: 属性“" + plan.get(2).toString() + "”不存在");
	            	}
	            	meetingPlanEntity.setSX3(goodsSX3OptionEntity.getCode());
	            	
	            	meetingPlanEntity.setPlanNum(Integer.parseInt(plan.get(3).toString()));
	            	meetingPlanEntity.setPlanMoney(new BigDecimal(plan.get(4).toString()));
	            	meetingPlanEntity.setPlanGoodsIDs(Integer.parseInt(plan.get(5).toString()));
	            	meetingPlanEntity.setInputTime(nowDate);
	            	meetingPlanList.add(meetingPlanEntity);
	                
	            }
	            //批量插入订货计划
	            sysMeetingPlanService.addBatchMeetingPlan(userSeqArr, periodSeq, meetingPlanList);
	            
	            return R.ok("导入成功");
            } else {
            	return R.error("文件内容为空！");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("导入失败:" + e.getMessage());
        }

    }
    
}
