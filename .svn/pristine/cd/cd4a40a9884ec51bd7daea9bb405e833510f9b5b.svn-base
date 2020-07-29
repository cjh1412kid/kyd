package io.nuite.modules.order_platform_app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.nuite.common.utils.R;
import io.nuite.modules.order_platform_app.annotation.Login;
import io.nuite.modules.order_platform_app.annotation.LoginUser;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.sr_base.entity.GoodsCategoryEntity;
import io.nuite.modules.sr_base.service.GoodsCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/order/app/meetingCategory")
@Api(tags = "订货平台4期 - 订货会分类接口")
public class MeetingCategoryController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private GoodsCategoryService goodsCategoryService;
	
	 @Login
	 @GetMapping("getAllMeetingDevice")
	 @ApiOperation("获取所有分类")
	  public R meetingCategoryList(@ApiIgnore @LoginUser BaseUserEntity loginUser) {
		try {
			Integer companySeq=loginUser.getCompanySeq();
			List<Map<String, Object>> categorys=new ArrayList<Map<String,Object>>();
			//根据公司序号获取1级分类
			List<GoodsCategoryEntity> firstCategorys=goodsCategoryService.getGoodsCategoryByParent(0, companySeq);
			for (GoodsCategoryEntity goodsCategoryEntity : firstCategorys) {
				Map<String, Object> category=new HashMap<String, Object>();
				Integer parentSeq=goodsCategoryEntity.getSeq();
				String name=goodsCategoryEntity.getName();
 				List<GoodsCategoryEntity> secCategorys=goodsCategoryService.getGoodsCategoryByParent(parentSeq, companySeq);
 				category.put("seq", parentSeq);
 				category.put("name", name);
 				category.put("secCategorys", secCategorys);
 				categorys.add(category);
			}
			return R.ok(categorys);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}
		
	  }
}
