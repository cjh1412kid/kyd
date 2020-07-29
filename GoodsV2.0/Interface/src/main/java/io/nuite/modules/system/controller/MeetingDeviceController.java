package io.nuite.modules.system.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.R;
import io.nuite.modules.system.entity.MeetingDeviceEntity;
import io.nuite.modules.system.service.MeetingDeviceService;



/**
 * ${comments}
 *
 * @author wanghao
 * @email barryhippo@163.com
 * @date 2019-04-23 17:38:15
 */
@RestController
@RequestMapping("/system/meetingDevice")
public class MeetingDeviceController  extends AbstractController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    private MeetingDeviceService meetingDeviceService;
    
    /**
     * 列表
     */
    @RequestMapping("/list" )
    public R list(@RequestParam Map<String, Object> params) {
        Integer companySeq = getUser().getCompanySeq();
        
        PageUtils page = meetingDeviceService.queryPage(params, companySeq);
        return R.ok().put("page", page);
    }
    
    /**
     * 保存或更新
     */
    @RequestMapping("/save" )
    public R save(MeetingDeviceEntity meetingDevice) {
        System.out.println("meeting = [" + meetingDevice + "]" );
        
        if (meetingDevice.getSeq() != null) {
        	meetingDeviceService.updateById(meetingDevice);
        } else {
        	meetingDevice.setCompanySeq(getUser().getCompanySeq());
            meetingDeviceService.insert(meetingDevice);
        }
        return R.ok();
    }
    
    /**
     * 信息
     */
    @GetMapping("/info/{seq}" )
    public R info(@PathVariable("seq" ) Integer seq) {
    	MeetingDeviceEntity meetingDevice = meetingDeviceService.selectById(seq);
        
        return R.ok().put("meetingDevice", meetingDevice);
    }
    
    /**
     * 删除
     */
    @GetMapping("/delete/{seq}" )
    public R delete( @PathVariable Integer seq) {
    	meetingDeviceService.deleteById(seq);
        return R.ok();
    }
}
