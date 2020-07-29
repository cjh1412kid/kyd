package io.nuite.modules.system.controller.order_platform;

import com.alibaba.fastjson.JSON;
import io.nuite.common.utils.FileUtils;
import io.nuite.common.utils.R;
import io.nuite.modules.sr_base.entity.UeditorRecordEntity;
import io.nuite.modules.sr_base.service.BaseCompanyService;
import io.nuite.modules.sr_base.utils.ConfigUtils;
import io.nuite.modules.system.controller.AbstractController;
import io.nuite.modules.system.entity.Ueditor;
import io.nuite.modules.system.service.order_platform.UeditorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author: yangchuang
 * @Date: 2018/7/4 18:12
 * @Version: 1.0
 * @Description:
 */

@RestController
@RequestMapping("/ueditor")
public class UEditorController extends AbstractController {
    private final static Logger logger = LoggerFactory.getLogger(UEditorController.class);

    @Autowired
    BaseCompanyService baseCompanyService;

    @Autowired
    UeditorService ueditorService;

    @Autowired
    private ConfigUtils configUtils;

    /**
     * 图片上传
     *
     * @param action
     * @param upfile
     * @return
     */
    @RequestMapping(value = "/imgUpload")
    public String imgUpload(@RequestParam(value = "action", required = true) String action, MultipartFile upfile) {

        Ueditor ueditor = new Ueditor();
        if (action == null || upfile == null) {
            ueditor.setState("上传失败");
            return JSON.toJSONString(ueditor);
        }

        if (action.equals("uploadimage") || action.equals("uploadscrawl")) {
            if (upfile != null) {
                Integer company_Seq = getUser().getCompanySeq();
                String commonPath = "ueditor" + File.separator + company_Seq + File.separator + "image" + File.separator;
                /*图片存放地址*/
                String imgDir = FileUtils.getWebAppsPath() + configUtils.getPictureBaseUploadProject() + File.separator + commonPath;
                String filename = null;
                try {
                    filename = FileUtils.upLoadFile(imgDir, null, upfile);
                    /*图片读取地址*/
                    ueditor.setUrl(configUtils.getPictureBaseUrl() + File.separator + commonPath + filename);
                    ueditor.setState("SUCCESS");
                    ueditor.setOriginal(filename);
                    ueditor.setTitle(filename);
                    return JSON.toJSONString(ueditor);
                } catch (IOException e) {
                    e.printStackTrace();
                    ueditor.setState("ERROR");
                    return JSON.toJSONString(ueditor);
                }
            } else {
                ueditor.setState("上传文件为null");
            }
        }
        return JSON.toJSONString(ueditor);
    }

    /**
     * 视频上传
     * @param action
     * @param upfile
     * @return
     */
    @RequestMapping(value = "/videoUpload")
    public String videoUpload(@RequestParam(value = "action", required = true) String action, MultipartFile upfile) {

        Ueditor ueditor = new Ueditor();
        if (action == null || upfile == null) {
            ueditor.setState("上传失败");
            return JSON.toJSONString(ueditor);
        }

        if ("uploadvideo".equals(action)) {

            if (upfile != null) {
                Integer company_Seq = getUser().getCompanySeq();
                String commonPath = "ueditor" + File.separator + company_Seq + File.separator + "video" + File.separator;
                /*视频存放地址*/
                String videoDir = FileUtils.getWebAppsPath() + configUtils.getPictureBaseUploadProject() + File.separator + commonPath;
                String vfilename = null;
                try {
                    vfilename = FileUtils.upLoadFile(videoDir, null, upfile);
                    /*视频读取地址*/
                    ueditor.setUrl(configUtils.getPictureBaseUrl() + File.separator + commonPath + vfilename);
                    ueditor.setState("SUCCESS");
                    ueditor.setOriginal(vfilename);
                    ueditor.setTitle(vfilename);
                } catch (IOException e) {
                    e.printStackTrace();
                    ueditor.setState("上传失败");
                    return JSON.toJSONString(ueditor);
                }
            } else {
                ueditor.setState("上传文件为null");
            }
        }
        return JSON.toJSONString(ueditor);
    }

    /**
     * 根据当前用户获取所有相关模版记录
     * @return
     */
    @RequestMapping("/getUeditorRecords")
    public R getUeditorRecords() {

        try {
            Integer company_Seq = getUser().getCompanySeq();
            List result = ueditorService.getList(company_Seq);
            return R.ok().put("records", result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    /**
     * 保存上传的编辑内容
     * @param ur
     * @return
     */
    @RequestMapping("/save")
    public R saveRecord(UeditorRecordEntity ur) {

        try {
            if (ur.getName() != null && ur.getContent() != null && ur.getContent().trim().length() > 0) {
                ur.setCompanySeq(getUser().getCompanySeq());
                ueditorService.saveOrUpdate(ur);
                return R.ok();
            } else {
                return R.error("名称或内容为空");
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    /**
     * 根据模板id查询模板记录
     *
     * @param seq
     * @return
     */

    @RequestMapping("/getBySeq")
    public R getBySeq(Integer seq) {

        try {
            UeditorRecordEntity ur = ueditorService.getById(seq);
            return R.ok().put("content", ur.getContent());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    /**
     * 获取被设定为【主模版】的模版
     *
     * @return
     */
    @RequestMapping("/getByUsed")
    public R getByUserd() {

        try {
            UeditorRecordEntity ur = ueditorService.getByCompanySeqAndUsed(getUser().getCompanySeq());
            if (ur != null) {
                return R.ok().put("seq", ur.getSeq()).put("result", ur.getContent());
            }
            return R.error("未加载数据");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器加载异常");
        }
    }

    /**
     * 删除指定id的模版
     * @param seq
     * @return
     */
    @RequestMapping("/delete")
    public R deleteById(Integer seq) {

        try {
            Boolean b = ueditorService.delete(seq);
            if (b) {
                return R.ok();
            } else {
                return R.error("服务器异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    /**
     * 更新选中节点的模版
     * @param ur
     * @return
     */
    @RequestMapping("/update")
    public R updateById(UeditorRecordEntity ur) {
        try {

            Boolean b = ueditorService.update(ur);
            if (b) {
                return R.ok();
            } else {
                return R.error("修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    /**
     * 设置主模板(设置或取消)
     *
     * @param ur
     * @return
     */
    @RequestMapping("/setUsed")
    public R setUsed(UeditorRecordEntity ur) {
        try {

            if (ur.getUsed() == 0) {
                ueditorService.setUsed(ur);
            } else if (ur.getUsed() == 1) {
                ueditorService.cancelUsed(ur);
            }
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return R.error("服务器异常");
        }
    }

    /**
     * 上传视频缩略图
     * @param file
     * @return
     */
    @RequestMapping("/uploadVideoPoster")
    public R uploadVideoPoster(@RequestParam("file")MultipartFile file) {

        if (file != null) {
            Integer company_Seq = getUser().getCompanySeq();
            String commonPath = "ueditor" + File.separator + company_Seq + File.separator + "videoposter" + File.separator;
            /*图片存放地址*/
            String imgDir = FileUtils.getWebAppsPath() + configUtils.getPictureBaseUploadProject() + File.separator + commonPath;
            String filename = null;
            try {
                filename = FileUtils.upLoadFile(imgDir, null, file);
                /*图片读取地址*/
                String imgPath = configUtils.getPictureBaseUrl() + File.separator + commonPath + filename;
                return R.ok().put("imgPath", imgPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return R.error("上传文件为空");
        }

        return R.error("服务器异常");
    }

}
