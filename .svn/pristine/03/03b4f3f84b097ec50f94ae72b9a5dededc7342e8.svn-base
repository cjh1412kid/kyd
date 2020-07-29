package io.nuite.modules.order_platform_app.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import io.nuite.common.utils.Constant;
import io.nuite.common.utils.FileUtils;
import io.nuite.modules.order_platform_app.service.BaseService;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.modules.sr_base.utils.ConfigUtils;
import io.nuite.modules.system.entity.MeetingEntity;
import io.nuite.modules.system.entity.MeetingPermissionEntity;
import io.nuite.modules.system.service.MeetingPermissionService;
import net.coobird.thumbnailator.Thumbnails;

public class BaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected BaseService baseService;

	@Autowired
	protected ConfigUtils configUtils;
	
    @Autowired
    private MeetingPermissionService meetingPermissionService;

	
	//基础库访问目录
	private String baseDir() {
		return configUtils.getPictureBaseUrl() + "/" + configUtils.getBaseDir() + "/";
	}
	
	//基础库上传目录
	private String baseUploadDir(HttpServletRequest request) {
//		//tomcat实际路径
//		String path = request.getSession().getServletContext().getRealPath("/");
//		//上传项目路径
//		String uploadProject1 = path.substring(0, path.length() - 11) + configUtils.getPictureBaseUploadProject() +"/";
		String uploadProject = FileUtils.getWebAppsPath() + configUtils.getPictureBaseUploadProject() +"/";
		return uploadProject + configUtils.getBaseDir() + "/";
	}
	
	//订货平台访问目录
	private String orderPlatformDir() {
		return configUtils.getPictureBaseUrl() + "/" + configUtils.getOrderPlatformApp().getOrderPlatformDir() + "/";
	}
	
	//订货平台上传目录
	private String orderPlatformUploadDir(HttpServletRequest request) {
//		//tomcat实际路径
//		String path = request.getSession().getServletContext().getRealPath("/");
//		//上传项目路径
//		String uploadProject1 = path.substring(0, path.length() - 11) + configUtils.getPictureBaseUploadProject() +"/";
		String uploadProject = FileUtils.getWebAppsPath() + configUtils.getPictureBaseUploadProject() +"/";
		return uploadProject + configUtils.getOrderPlatformApp().getOrderPlatformDir() + "/";
	}
	
	
	
	/**
	 * 品牌图片路径
	 * @param folder
	 * @return
	 */
	protected String getBaseBrandPictureUrl(String folder) {
		return baseDir() + configUtils.getBaseBrand() + "/" + folder + "/";
	}
	
	
	/**
	 * 鞋子基本信息图片路径
	 * @param folder
	 * @return
	 */
	protected String getGoodsShoesPictureUrl(String folder) {
		return baseDir() + configUtils.getGoodsShoes() + "/" + folder + "/";
	}
	
	
	/**
	 * 用户信息图片路径
	 * @param folder
	 * @return
	 */
	protected String getBaseUserPictureUrl(String folder) {
		return baseDir() + configUtils.getBaseUser() + "/" + folder + "/";
	}
	
	
	/**
	 * 主页展示图图片路径
	 * @param folder
	 * @return
	 */
	protected String getPublicityPicPictureUrl(String folder) {
		return orderPlatformDir() + configUtils.getOrderPlatformApp().getPublicityPic() + "/" + folder + "/";
	}
	
	
	/**
	 * 社区图片路径
	 * @param folder
	 * @return
	 */
	protected String getCommunityCONTENTPictureUrl(String folder) {
		return orderPlatformDir() + configUtils.getOrderPlatformApp().getCommunityContent() + "/" + folder + "/";
	}
	
	
	/**
	 * 在线沟通图片路径
	 * @param folder
	 * @return
	 */
	protected String getOnlineMessagePictureUrl(String folder) {
		return orderPlatformDir() + configUtils.getOrderPlatformApp().getOnlineMessage() + "/" + folder + "/";
	}
	
	
	/**
	 * 订单的图片路径
	 * @param folder
	 * @return
	 */
	protected String getOrderPictureUrl(String folder) {
		return orderPlatformDir() + configUtils.getOrderPlatformApp().getOrder() + "/" + folder + "/";
	}
	
	
	/**
	 * 订单快递的图片路径
	 * @param folder
	 * @return
	 */
	protected String getOrderExpressPictureUrl(String folder) {
		return orderPlatformDir() + configUtils.getOrderPlatformApp().getOrderExpress() + "/" + folder + "/";
	}

	
	/**
	 * 轮播图图片路径
	 * @param folder
	 * @return
	 */
	protected String getHomeCarouselPictureUrl(String folder) {
		return baseDir() + configUtils.getSowingMap() + "/" + folder + "/";
	}
	
	
	/**
	 * 订货会商品图片路径
	 * @param folder
	 * @return
	 */
	protected String getMeetingGoodsPictureUrl(String folder) {
		return orderPlatformDir() + configUtils.getOrderPlatformApp().getMeetingGoods() + "/" + folder + "/";
	}
	
	
	/**
	 * 订货会商品各个颜色图片路径
	 * @param folder
	 * @return
	 */
	protected String getMeetingGoodsColorImgsPictureUrl(String goodId, Integer colorSeq) {
		return orderPlatformDir() + configUtils.getOrderPlatformApp().getMeetingGoods() + "/" + goodId + "/" + colorSeq + "/";
	}
	
	
	
	
	//上传路径
	/**
	 * 用户图片上传路径
	 * @param request
	 * @param folder
	 * @return
	 */
	protected String getBaseUserUploadUrl(HttpServletRequest request, String folder) {
		return baseUploadDir(request) + configUtils.getBaseUser() +"/"+ folder + "/";
	}
	
	
	/**
	 * 社区图片上传路径
	 * @param request
	 * @param folder
	 * @return
	 */
	protected String getCommunityCONTENTUploadUrl(HttpServletRequest request, String folder) {
		return orderPlatformUploadDir(request) + configUtils.getOrderPlatformApp().getCommunityContent() +"/"+ folder + "/";
	}

	
	/**
	 * 在线沟通图片上传路径
	 * @param request
	 * @param folder
	 * @return
	 */
	protected String getOnlineMessageUploadUrl(HttpServletRequest request, String folder) {
		return orderPlatformUploadDir(request) + configUtils.getOrderPlatformApp().getOnlineMessage() +"/"+ folder + "/";
	}
	
	
	/**
	 * 订单图片上传路径
	 * @param request
	 * @param folder
	 * @return
	 */
	protected String getOrderUploadUrl(HttpServletRequest request, String folder) {
		return orderPlatformUploadDir(request) + configUtils.getOrderPlatformApp().getOrder() +"/"+ folder + "/";
	}
	
	
	/**
	 * 快递单图片上传路径
	 * @param request
	 * @param folder
	 * @return
	 */
	protected String getOrderExpressUploadUrl(HttpServletRequest request, String folder) {
		return orderPlatformUploadDir(request) + configUtils.getOrderPlatformApp().getOrderExpress() +"/"+ folder + "/";
	}
	
	
	/**
	 * 订货会商品图片上传路径
	 * @param request
	 * @param folder
	 * @return
	 */
	protected String getMeetingGoodsUploadUrl(HttpServletRequest request, String folder) {
		return orderPlatformUploadDir(request) + configUtils.getOrderPlatformApp().getMeetingGoods() +"/"+ folder + "/";
	}
	
	
	/**
	 * 订货会商品各个颜色图片上传路径
	 * @param request
	 * @param folder
	 * @param colorSeq 
	 * @return
	 */
	protected String getMeetingGoodsColorImgsUploadUrl(HttpServletRequest request, String goodId, Integer colorSeq) {
		return orderPlatformUploadDir(request) + configUtils.getOrderPlatformApp().getMeetingGoods() +"/"+ goodId + "/" + colorSeq + "/";
	}
	
	
	
	/**
	 * 上传文件，返回文件名
	 * 
	 * @param userSeq
	 * @param dir
	 * @param img
	 * @return
	 * @throws IOException
	 */
	protected String upLoadFile(Integer userSeq, String imgDir, MultipartFile img) throws IOException {

		// 存放目录
		File fileDir = new File(imgDir);
		// 如果目录不存在就创建
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}

		// 原文件名
		String originalFilename = img.getOriginalFilename();
		logger.info("originalFilename:" + originalFilename);
		// 重新定义文件名
		String fileName = userSeq + "_" + System.currentTimeMillis() + "_" + originalFilename;
		logger.info("fileName:" + fileName);
		// 上传文件
		File file = new File(fileDir, fileName);
		file.createNewFile();
		img.transferTo(file);
		
		String fileName1 =userSeq+ "_" + System.currentTimeMillis() + "_" + originalFilename;
		fileName1=fileName1.replace("png", "jpg");
		File file1 = new File(fileDir, fileName1);
		file1.createNewFile();
		 Thumbnails.of(file).scale(1f).outputQuality(0.75f).outputFormat("jpg").toFile(file1);
		logger.info("AbsolutePath:" + file.getAbsolutePath());

		return fileName1;
	}
	
	
	/**
	 * 判断用户是否是工厂管理员
	 * @param user
	 * @return
	 */
	protected boolean isFactoryAdmin(BaseUserEntity user) {
		if(user.getAttachType() == Constant.UserAttachType.FACTORY.getValue() && user.getSaleType() == Constant.UserSaleType.FACTORY.getValue()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断用户是否是工厂用户
	 * @param user
	 * @return
	 */
	protected boolean isFactoryUser(BaseUserEntity user) {
		if((user.getAttachType() == Constant.UserAttachType.FACTORY.getValue() || user.getAttachType() == Constant.UserAttachType.SUBACCOUNT.getValue()) 
				&& user.getSaleType() == Constant.UserSaleType.FACTORY.getValue()) {
			return true;
		} else {
			return false;
		}
	}

	
	/**
	 * 判断有无本次订货会权限
	 * @param user
	 * @param meetingEntity 
	 * @return
	 */
	protected boolean hasNowMeetingPermission(BaseUserEntity user, MeetingEntity meetingEntity) {
		MeetingPermissionEntity meetingPermissionEntity = meetingPermissionService.getMeetingPermission(user.getSeq(), meetingEntity.getSeq());
		if(meetingPermissionEntity == null) {
			return false;
		} else {
			return true;
		}
	}
	
}
