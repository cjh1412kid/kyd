package io.nuite.modules.system.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.R;
import io.nuite.modules.system.entity.MeetingAreaEntity;
import io.nuite.modules.system.service.MeetingAreaService;
import io.nuite.modules.system.service.MeetintUserAreaService;
import io.nuite.modules.system.service.order_platform.OrderPartyManagementService;


@RestController
@RequestMapping("/system/meetingArea" )
public class SysMeetingAreaController   extends AbstractController {

	  private Logger logger = LoggerFactory.getLogger(getClass());
	  
	  @Autowired
	  private MeetingAreaService meetingAreaService;
	  
	  @Autowired
	  private OrderPartyManagementService OrderPartyManagementService;
	  
	  @Autowired
	  private MeetintUserAreaService meetintUserAreaService;
	    
	    /**
	     * 列表
	     */
	    @RequestMapping("/list" )
	    public R list(@RequestParam Map<String, Object> params) {
	        Integer companySeq = getUser().getCompanySeq();
	        
	        PageUtils page = meetingAreaService.queryPage(params, companySeq);
	        
	        return R.ok().put("page", page);
	    }
	    
	    /**
	     * 信息
	     */
	    @GetMapping("/info/{seq}" )
	    public R info(@PathVariable("seq" ) Integer seq) {
	        MeetingAreaEntity  meetingArea = meetingAreaService.selectById(seq);
	        
	        return R.ok().put("meetingArea", meetingArea);
	    }
	    
	    /**
	     * 保存或更新
	     */
	    @RequestMapping("/save" )
	    public R save(MeetingAreaEntity meetingArea) {
	        System.out.println("meeting = [" + meetingArea + "]" );
	        List<MeetingAreaEntity> meetingAreas=meetingAreaService.getMeetingAreaByName(meetingArea.getAreaName());
	        if(meetingAreas!=null&&meetingAreas.size()>0) {
	        	return R.error("当前区域已存在");
	        }
	        
	        if (meetingArea.getSeq() != null) {
	        	meetingAreaService.updateById(meetingArea);
	        } else {
	        	meetingArea.setCompanySeq(getUser().getCompanySeq());
	            meetingAreaService.insert(meetingArea);
	        }
	        
	        return R.ok();
	    }
	    
	    /**
	     * 删除
	     */

	    @GetMapping("/delete/{seq}" )
	    public R delete( @PathVariable Integer seq) {
	    	try {
	    		Boolean flag = meetingAreaService.hasAreaInUsers(seq);
	            if (flag) {
	                   return R.error("该区域已被使用，不可删除");
	             }
	    	meetingAreaService.deleteById(seq);
	    	
	        return R.ok();
	    	} catch (Exception e) {
	               e.printStackTrace();
	               logger.error(e.getMessage(), e);
	               return R.error("服务器开小差啦~");
	        }
	    }
	    
	    /**
	     * 查询当前地区的订货会用户
	     */
	    @PostMapping("/meetingList")

	    public R meetingPartyList( @RequestParam("companyType") Integer saleType,
	                           @RequestParam("page") Integer page,
	                            @RequestParam("limit") Integer limit, @RequestParam("seq")  Integer seq) {
	        try {
	            PageUtils companyBrandPage = OrderPartyManagementService.meetingAreaUserList(getUser(), saleType, page,
	                    limit,seq);
	            return R.ok().put("page", companyBrandPage);
	        } catch (Exception e) {
	            e.printStackTrace();
	            logger.error(e.getMessage(), e);
	            return R.error("获取列表失败");
	        }
	    }
	    
	    /**
	     * 订货会用户授权
	     */
	    @RequestMapping("/saveArea")
	    public R savePermission(@RequestParam("areaSeq") Integer areaSeq,@RequestParam(value = "seqs[]",required = false) List<Integer> seqs,@RequestParam("allSeqs[]") List<Integer> allSeqs) {
	    	//查询当前meetingSeq下的所有用户
	    	meetintUserAreaService.getAllUserByMeetingSeq(areaSeq, seqs,allSeqs);
	    	 return R.ok();
	    }
	    
}
