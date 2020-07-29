package io.nuite.modules.system.controller.online_sale;

import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.R;
import io.nuite.modules.online_sales_app.entity.TopicEntity;
import io.nuite.modules.online_sales_app.service.TopicService;
import io.nuite.modules.system.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Map;

@Controller
@RequestMapping("/online/topic")
public class OnlineTopicController extends AbstractController {

    @Autowired
    private TopicService topicService;

    @GetMapping("list")
    @ResponseBody
    public R list(@RequestParam Map<String, Object> params) {
        Integer brandSeq = getUser().getBrandSeq();
        if (brandSeq == null) {
            return R.error("当前用户品牌为空，请增加品牌");
        }

        PageUtils pageUtils = topicService.queryPage(params, brandSeq);
        return R.ok().put("page", pageUtils);
    }

    @GetMapping("edit")
    @ResponseBody
    public R edit(Long seq) {
        if (seq == null) {
            return R.error("编辑的数据不可为空");
        }
        TopicEntity topicEntity = topicService.selectOneBySeq(seq);
        return R.ok().put("detail", topicEntity);
    }

    @PostMapping("save")
    @ResponseBody
    public R save(TopicEntity topicEntity) {
        Integer brandSeq = getUser().getBrandSeq();
        if (brandSeq == null) {
            return R.error("当前用户品牌为空，请增加品牌");
        }

        topicEntity.setBrandSeq(brandSeq.longValue());

        try {
            String oldImagePath = topicService.saveTopic(topicEntity);
            if (oldImagePath != null) {
                File file = new File(oldImagePath);
                file.delete();
            }
            return R.ok();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return R.error(e.getMessage());
        }
    }
}
