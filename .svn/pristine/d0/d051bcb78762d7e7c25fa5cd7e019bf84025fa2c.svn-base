package io.nuite.modules.system.controller.online_sale;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;

import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.R;
import io.nuite.modules.system.controller.AbstractController;
import io.nuite.modules.system.service.online_sale.CustomUserService;
import io.swagger.annotations.ApiParam;


/**
 * 小程序用户管理
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/online/customUser")
public class CustomUserController extends AbstractController {
    @Autowired
    private CustomUserService customUserService;

    /**
     * 小程序用户列表
     */
    @GetMapping("/list")
    public R list(@ApiParam("页码") @RequestParam("page") Integer pageNum,
            @ApiParam("每页条数") @RequestParam("limit") Integer pageSize,
            @ApiParam("昵称状态0已授权1未授权") @RequestParam("nickNameState") Integer nickNameState,
            @ApiParam("手机号状态0已授权1未授权") @RequestParam("telephoneState") Integer telephoneState,
            @ApiParam("开始时间") @RequestParam("startTime") String startTime,
            @ApiParam("结束时间") @RequestParam("endTime") String endTime) {
    	
        //公司序号
        Integer companySeq = getUser().getCompanySeq();

        //分页查询用户列表
        Page<Map<String, Object>> page = customUserService.getCustomUserList(companySeq, nickNameState, telephoneState, startTime, endTime, pageNum, pageSize);

        List<Map<String, Object>> list = page.getRecords();
        if(list != null && list.size() > 0) {
	        for(Map<String, Object> map : list) {
	        	Integer customUserSeq = (Integer) map.get("seq");
	        	map.putAll(customUserService.getCustomUserOrderSumMap(customUserSeq));
	        }
        }
        
        PageUtils pageUtils = new PageUtils(page);
        return R.ok().put("page", pageUtils);
    }


    /**
     * 启用用户
     */
    @GetMapping("/enableUser")
    public R enableUser(@ApiParam("用户seq") @RequestParam("seq") Integer seq) {
        customUserService.enableUser(seq);
        return R.ok();
    }


    /**
     * 停用用户
     */
    @GetMapping("/disableUser")
    public R disableUser(@ApiParam("用户seq") @RequestParam("seq") Integer seq) {
        customUserService.disableUser(seq);
        return R.ok();
    }
    
}
