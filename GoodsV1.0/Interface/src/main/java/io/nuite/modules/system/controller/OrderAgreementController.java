package io.nuite.modules.system.controller;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

import io.nuite.common.utils.FileUtils;
import io.nuite.common.utils.R;
import io.nuite.modules.sr_base.utils.ConfigUtils;
import io.nuite.modules.system.entity.OrderAgreementEntity;
import io.nuite.modules.system.entity.Ueditor;
import io.nuite.modules.system.service.OrderAgreementService;
import io.swagger.annotations.Api;


@RestController
@RequestMapping("/system/orderAgreement")
@Api(tags="OrderAgreement接口")
public class OrderAgreementController extends AbstractController  {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	  @Autowired
	  private OrderAgreementService orderAgreementService;
	  
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
	                String commonPath = "orderAgreement" + File.separator + company_Seq + File.separator + "image" + File.separator;
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
	                String commonPath = "orderAgreement" + File.separator + company_Seq + File.separator + "video" + File.separator;
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
	     * @return
	     */
	    @RequestMapping("/getAgreementByUser")
	    public R getByUserd() {
	        try {
	            OrderAgreementEntity oa = orderAgreementService.getCompanyOrderAgreement(getUser().getCompanySeq());
	            if (oa != null) {
	                return R.ok().put("seq", oa.getSeq()).put("result", oa.getAgreementContent()).put("name", oa.getName());
	            }
	            return R.error("未加载数据");
	        } catch (Exception e) {
	            e.printStackTrace();
	            logger.error(e.getMessage(), e);
	            return R.error("服务器加载异常");
	        }
	    }
	    
	    /**
	     * 保存上传的编辑内容
	     * @param ur
	     * @return
	     */
	    @RequestMapping("/save")
	    public R saveRecord(OrderAgreementEntity oa) {

	        try {
	            if (oa.getName() != null && oa.getAgreementContent() != null && oa.getAgreementContent().trim().length() > 0) {
	                oa.setCompanySeq(getUser().getCompanySeq());
	                orderAgreementService.saveOrUpdate(oa);
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
	     * 更新选中节点的模版
	     * @param ur
	     * @return
	     */
	    @RequestMapping("/update")
	    public R updateById(OrderAgreementEntity oa) {
	        try {

	            Boolean b = orderAgreementService.update(oa);
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
	     * 上传视频缩略图
	     * @param file
	     * @return
	     */
	    @RequestMapping("/uploadVideoPoster")
	    public R uploadVideoPoster(@RequestParam("file")MultipartFile file) {

	        if (file != null) {
	            Integer company_Seq = getUser().getCompanySeq();
	            String commonPath = "orderAgreement" + File.separator + company_Seq + File.separator + "videoposter" + File.separator;
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
