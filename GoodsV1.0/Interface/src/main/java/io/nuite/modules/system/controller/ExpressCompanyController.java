package io.nuite.modules.system.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import io.nuite.modules.order_platform_app.entity.ExpressCompanyEntity;
import io.nuite.modules.system.entity.MeetingDeviceEntity;
import io.nuite.modules.system.service.ExpressCompanyService;

@RestController
@RequestMapping("/system/expressCompany" )
public class ExpressCompanyController  extends AbstractController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ExpressCompanyService expressCompanyService;
	
	  @PostMapping("/getExpressList")
	    public R getExpressList(@RequestParam Map<String, Object> params,HttpServletRequest request) {
	        try {
	            PageUtils expressPage = expressCompanyService.getExpressList(params);
	            return R.ok().put("page", expressPage);
	        } catch (Exception e) {
	            e.printStackTrace();
	            logger.error(e.getMessage(), e);
	            return R.error("服务器异常");
	        }
	    }
	  
	  @GetMapping("/info/{seq}" )
	    public R info(@PathVariable("seq" ) Integer seq) {
		  ExpressCompanyEntity expressCompanyEntity = expressCompanyService.selectById(seq);
	        
	        return R.ok().put("express", expressCompanyEntity);
	    }
	  
	    /**
	     * 保存或更新
	     */
	    @RequestMapping("/save" )
	    public R save(ExpressCompanyEntity expressCompanyEntity) {
	        System.out.println("meeting = [" + expressCompanyEntity + "]" );
	        
	        if (expressCompanyEntity.getSeq() != null) {
	        	expressCompanyService.updateAllColumnById(expressCompanyEntity);
	        } else {
	        	expressCompanyService.insert(expressCompanyEntity);
	        }
	        return R.ok();
	    }
	    
	    /**
	     * 删除
	     */
	    @GetMapping("/delete/{seq}" )
	    public R delete( @PathVariable Integer seq) {
	    	try {
	    	expressCompanyService.deleteById(seq);
	        return R.ok();
	    	} catch (Exception e) {
	               e.printStackTrace();
	               logger.error(e.getMessage(), e);
	               return R.error("服务器开小差啦~");
	        }
	    }
}
