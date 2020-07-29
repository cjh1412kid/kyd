package io.nuite.modules.online_sales_app.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/online/miniapp/myself")
@Api(tags = "线上销售APP我的", description = "我的相关接口")
public class OnlineMyselfController {
}
