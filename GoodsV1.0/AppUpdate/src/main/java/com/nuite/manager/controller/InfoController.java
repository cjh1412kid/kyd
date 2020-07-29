package com.nuite.manager.controller;

import com.nuite.manager.entity.Info;
import com.nuite.manager.entity.Page;
import com.nuite.manager.service.InfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;


@Controller
@RequestMapping(value = "/info")
public class InfoController {

    @Resource
    private InfoService infoService;

    @RequestMapping
    public String info(Model model, Page page) {
        List<Info> infoList = infoService.listPageInfo(page);
        model.addAttribute("infoList", infoList);
        model.addAttribute("page", page);
        return "info";
    }

}
