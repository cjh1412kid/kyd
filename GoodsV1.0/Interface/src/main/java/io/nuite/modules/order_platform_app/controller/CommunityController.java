package io.nuite.modules.order_platform_app.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.nuite.modules.order_platform_app.entity.CommunityCOMMENTEntity;
import io.nuite.modules.order_platform_app.entity.CommunityCONTENTEntity;
import io.nuite.modules.order_platform_app.entity.CommunityRECORDEntity;
import io.nuite.modules.order_platform_app.service.CommunityService;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import io.nuite.common.utils.R;
import io.nuite.modules.order_platform_app.annotation.Login;
import io.nuite.modules.order_platform_app.annotation.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 社区接口
 * 
 * @author yy
 * @date 2018-04-11 13:47
 */
@RestController
@RequestMapping("/order/app/community")
@Api(tags = "订货平台 - 社区", description = "社区的功能")
public class CommunityController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CommunityService communityService;


	/**
	 * 社区内容列表（包含所有的用户的 工厂名+名字+内容+点赞数目+评论信息+二级评论）
	 */
	@Login
	@GetMapping("communityList")
	@ApiOperation("社区内容列表")
	public R communityList(@ApiIgnore @LoginUser BaseUserEntity loginUser,
			@ApiParam("内容类型") @RequestParam("contentTypeSeq") Integer contentTypeSeq,
			@ApiParam("起始条数") @RequestParam("start") Integer start,
			@ApiParam("总条数") @RequestParam("num") Integer num) {
		try {
			// 1.工厂的所有有效用户seq
			List<Integer> allUserSeqList = communityService.getAllValidUser(loginUser.getCompanySeq(), loginUser.getBrandSeq());

			// 2.根据所有有效用户Seq，分页查询社区内容列表
			List<CommunityCONTENTEntity> contentList = communityService.getCommunityCONTENTList(allUserSeqList, contentTypeSeq, start, num);

			// 3.组装结果集
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			Map<String, Object> resultMap;
			List<String> imgList;
			String communityPictureUrl;
			List<CommunityCOMMENTEntity> commentList;
			List<CommunityCOMMENTEntity> firstCommentList;
			List<CommunityCOMMENTEntity> secondCommentList;
			BaseUserEntity user;
			Map<String, Object> recordMap;
			for (CommunityCONTENTEntity content : contentList) {
				resultMap = new HashMap<String, Object>();

				/* 互动内容 */
				communityPictureUrl = getCommunityCONTENTPictureUrl(content.getUserSeq().toString());
				imgList = new ArrayList<String>();
				if (StringUtils.isNotEmpty(content.getImg1())) {
//					content.setImg1(communityPictureUrl + content.getImg1());
					imgList.add(communityPictureUrl + content.getImg1());
				}
				if (StringUtils.isNotEmpty(content.getImg2())) {
//					content.setImg2(communityPictureUrl + content.getImg2());
					imgList.add(communityPictureUrl + content.getImg2());
				}
				if (StringUtils.isNotEmpty(content.getImg3())) {
//					content.setImg3(communityPictureUrl + content.getImg3());
					imgList.add(communityPictureUrl + content.getImg3());
				}
				if (StringUtils.isNotEmpty(content.getImg4())) {
//					content.setImg4(communityPictureUrl + content.getImg4());
					imgList.add(communityPictureUrl + content.getImg4());
				}
				if (StringUtils.isNotEmpty(content.getImg5())) {
//					content.setImg5(communityPictureUrl + content.getImg5());
					imgList.add(communityPictureUrl + content.getImg5());
				}
				if (StringUtils.isNotEmpty(content.getImg6())) {
//					content.setImg6(communityPictureUrl + content.getImg6());
					imgList.add(communityPictureUrl + content.getImg6());
				}
				if (StringUtils.isNotEmpty(content.getImg7())) {
//					content.setImg7(communityPictureUrl + content.getImg7());
					imgList.add(communityPictureUrl + content.getImg7());
				}
				if (StringUtils.isNotEmpty(content.getImg8())) {
//					content.setImg8(communityPictureUrl + content.getImg8());
					imgList.add(communityPictureUrl + content.getImg8());
				}
				if (StringUtils.isNotEmpty(content.getImg9())) {
//					content.setImg9(communityPictureUrl + content.getImg9());
					imgList.add(communityPictureUrl + content.getImg9());
				}
				content.setImgList(imgList);
				resultMap.put("content", content);

				/* 用户信息 */
				user = communityService.getBaseUserBySeq(content.getUserSeq());
				if(StringUtils.isNotEmpty(user.getHeadImg())) {
					user.setHeadImg(getBaseUserPictureUrl(user.getSeq().toString()) + user.getHeadImg());
				}
				resultMap.put("user", user);
				
				/* 评论信息 */
				commentList = new ArrayList<CommunityCOMMENTEntity>();
				// 一级评论，给互动内容的评论
				firstCommentList = communityService.getFirstCommentList(content.getSeq());
				// 如果一级评论不为空，尝试查询二级评论
				if (firstCommentList != null && firstCommentList.size() > 0) {
					for (CommunityCOMMENTEntity firstComment : firstCommentList) {
						secondCommentList = communityService.getSecondCommentList(firstComment.getSeq());
						//此处为了方便把secondCommentList作为自定义属性加入了CommunityCONTENTEntity实体
						firstComment.setSecondCommentList(secondCommentList);
						commentList.add(firstComment);
					}
				}
				resultMap.put("comment", commentList);

				/* 记录信息 */
				// 浏览数量
				int seeNum = communityService.getContentSeeNum(content.getSeq());
				// 点赞数量
				int upNum = communityService.getContentUpNum(content.getSeq());
				// 当前用户是否点赞
				boolean isUp = communityService.getIsUpByUserSeq(content.getSeq(), loginUser.getSeq());
				recordMap = new HashMap<String, Object>();
				recordMap.put("seeNum", seeNum);
				recordMap.put("upNum", upNum);
				recordMap.put("isUp", isUp);
				resultMap.put("record", recordMap);

				resultList.add(resultMap);
			}

			return R.ok(resultList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

	}

	
	
	/**
	 * 发布新的社区内容
	 */
//	@Login
//	@PostMapping("addCommunity")
//	@ApiOperation("发布新的社区内容")
//	public R addCommunity(@ApiIgnore @RequestAttribute("userSeq") Integer userSeq,
//			@ApiParam("分享内容（文本）") @RequestParam("content") String content,
//			@ApiParam("内容类型序") @RequestParam("contentTypeSeq") Integer contentTypeSeq,
//			@ApiParam("图片1") @RequestParam(value = "img1", required = false) MultipartFile img1,
//			@ApiParam("图片2") @RequestParam(value = "img2", required = false) MultipartFile img2,
//			@ApiParam("图片3") @RequestParam(value = "img3", required = false) MultipartFile img3,
//			@ApiParam("图片4") @RequestParam(value = "img4", required = false) MultipartFile img4,
//			@ApiParam("图片5") @RequestParam(value = "img5", required = false) MultipartFile img5,
//			@ApiParam("图片6") @RequestParam(value = "img6", required = false) MultipartFile img6,
//			@ApiParam("图片7") @RequestParam(value = "img7", required = false) MultipartFile img7,
//			@ApiParam("图片8") @RequestParam(value = "img8", required = false) MultipartFile img8,
//			@ApiParam("图片9") @RequestParam(value = "img9", required = false) MultipartFile img9, HttpServletRequest request) {
//		try {
//			CommunityCONTENTEntity communityCONTENT = new CommunityCONTENTEntity();
//			communityCONTENT.setUserSeq(userSeq);
//			communityCONTENT.setContent(content);
//			communityCONTENT.setContentTypeSeq(contentTypeSeq);
//			communityCONTENT.setInputTime(new Date());
//			communityCONTENT.setState(1);
//			communityCONTENT.setDel(0);
//			String uploadUrl = getUploadUrl(request, communityCONTENTDir);
//			if (img1 != null) {
//				communityCONTENT.setImg1(upLoadFile(userSeq, uploadUrl, img1));
//			}
//			if (img2 != null) {
//				communityCONTENT.setImg2(upLoadFile(userSeq, uploadUrl, img2));
//			}
//			if (img3 != null) {
//				communityCONTENT.setImg3(upLoadFile(userSeq, uploadUrl, img3));
//			}
//			if (img4 != null) {
//				communityCONTENT.setImg4(upLoadFile(userSeq, uploadUrl, img4));
//			}
//			if (img5 != null) {
//				communityCONTENT.setImg5(upLoadFile(userSeq, uploadUrl, img5));
//			}
//			if (img6 != null) {
//				communityCONTENT.setImg6(upLoadFile(userSeq, uploadUrl, img6));
//			}
//			if (img7 != null) {
//				communityCONTENT.setImg7(upLoadFile(userSeq, uploadUrl, img7));
//			}
//			if (img8 != null) {
//				communityCONTENT.setImg8(upLoadFile(userSeq, uploadUrl, img8));
//			}
//			if (img9 != null) {
//				communityCONTENT.setImg9(upLoadFile(userSeq, uploadUrl, img9));
//			}
//			communityService.addCommunityCONTENT(communityCONTENT);
//			return R.ok("发布成功");
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e.getMessage(), e);
//			return R.error("服务器异常");
//		}
//
//	}
	
	@Login
	@PostMapping("addCommunity")
	@ApiOperation("发布新的社区内容")
	public R addCommunity(@ApiIgnore @RequestAttribute("userSeq") Integer userSeq,
			@ApiParam("分享内容（文本）") @RequestParam(value = "content", required = false) String content,
			@ApiParam("内容类型序") @RequestParam("contentTypeSeq") Integer contentTypeSeq,
			@ApiParam("图片列表") @RequestParam(value = "imgList", required = false) List<MultipartFile> imgList,
			HttpServletRequest request) {
		try {
			CommunityCONTENTEntity communityCONTENT = new CommunityCONTENTEntity();
			communityCONTENT.setUserSeq(userSeq);
			communityCONTENT.setContent(content);
			communityCONTENT.setContentTypeSeq(contentTypeSeq);
			communityCONTENT.setInputTime(new Date());
			communityCONTENT.setState(1);
			communityCONTENT.setDel(0);
			
	        if(imgList != null && imgList.size() > 0){
				String uploadUrl = getCommunityCONTENTUploadUrl(request, userSeq.toString());
				for(int i = 0; i < imgList.size(); i++) {
					if (i == 0) {
						communityCONTENT.setImg1(upLoadFile(userSeq, uploadUrl, imgList.get(0)));
					}
					if (i == 1) {
						communityCONTENT.setImg2(upLoadFile(userSeq, uploadUrl, imgList.get(1)));
					}
					if (i == 2) {
						communityCONTENT.setImg3(upLoadFile(userSeq, uploadUrl, imgList.get(2)));
					}
					if (i == 3) {
						communityCONTENT.setImg4(upLoadFile(userSeq, uploadUrl, imgList.get(3)));
					}
					if (i == 4) {
						communityCONTENT.setImg5(upLoadFile(userSeq, uploadUrl, imgList.get(4)));
					}
					if (i == 5) {
						communityCONTENT.setImg6(upLoadFile(userSeq, uploadUrl, imgList.get(5)));
					}
					if (i == 6) {
						communityCONTENT.setImg7(upLoadFile(userSeq, uploadUrl, imgList.get(6)));
					}
					if (i == 7) {
						communityCONTENT.setImg8(upLoadFile(userSeq, uploadUrl, imgList.get(7)));
					}
					if (i == 8) {
						communityCONTENT.setImg9(upLoadFile(userSeq, uploadUrl, imgList.get(8)));
					}
				}
	        }
			communityService.addCommunityCONTENT(communityCONTENT);
			return R.ok("发布成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}
    }
    
	

	/**
	 * 给发布的社区内容点赞
	 */
	@Login
	@PostMapping("upCommunity")
	@ApiOperation("给发布的社区内容点赞")
	public R upCommunity(@ApiIgnore @RequestAttribute("userSeq") Integer userSeq,
			@ApiParam("社区内容Seq") @RequestParam("contentSeq") Integer contentSeq, HttpServletRequest request) {
		try {
			//判断是否点过赞了
			boolean isUp = communityService.getIsUpByUserSeq(contentSeq, userSeq);
			if(!isUp) {
				CommunityRECORDEntity communityRECORD = new CommunityRECORDEntity();
				communityRECORD.setTypeName(2);
				communityRECORD.setUserSeq(userSeq);
				communityRECORD.setContentSeq(contentSeq);
				communityRECORD.setInputTime(new Date());
				communityRECORD.setDel(0);
				communityService.addCommunityRECORD(communityRECORD);
				return R.ok("点赞成功");
			} else {
				return R.error(HttpStatus.SC_CONFLICT, "你已经点过赞了");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

	}

	
	/**
	 * 给发布的社区内容评论
	 */
	@Login
	@PostMapping("addCommunityComment")
	@ApiOperation("给发布的社区内容评论")
	public R addCommunityComment(@ApiIgnore @RequestAttribute("userSeq") Integer userSeq,
			@ApiParam("社区内容Seq") @RequestParam("contentSeq") Integer contentSeq,
			@ApiParam("评论内容") @RequestParam("content") String content, HttpServletRequest request) {
		try {
			CommunityCOMMENTEntity communityCOMMENT = new CommunityCOMMENTEntity();
			communityCOMMENT.setUserSeq(userSeq);
			communityCOMMENT.setContentSeq(contentSeq);
			communityCOMMENT.setParentSeq(0);
			communityCOMMENT.setContent(content);
			communityCOMMENT.setInputTime(new Date());
			communityCOMMENT.setState(1);
			communityCOMMENT.setDel(0);
			communityService.addCommunityCOMMENT(communityCOMMENT);
			return R.ok("评论成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

	}

	
	/**
	 * 给评论做评论
	 */
	@Login
	@PostMapping("addSecondComment")
	@ApiOperation("给评论做评论")
	public R addSecondComment(@ApiIgnore @RequestAttribute("userSeq") Integer userSeq,
			@ApiParam("上级评论的Seq") @RequestParam("parentSeq") Integer parentSeq,
			@ApiParam("评论内容") @RequestParam("content") String content, HttpServletRequest request) {
		try {
			CommunityCOMMENTEntity communityCOMMENT = new CommunityCOMMENTEntity();
			communityCOMMENT.setUserSeq(userSeq);
			communityCOMMENT.setParentSeq(parentSeq);
			communityCOMMENT.setContent(content);
			communityCOMMENT.setInputTime(new Date());
			communityCOMMENT.setState(1);
			communityCOMMENT.setDel(0);
			communityService.addCommunityCOMMENT(communityCOMMENT);
			return R.ok("评论成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

	}

	
	/**
	 * 删除社区发布的内容
	 */
	@Login
	@PostMapping("deleteCommunity")
	@ApiOperation("删除社区发布的内容")
	public R deleteCommunity(@ApiIgnore @RequestAttribute("userSeq") Integer userSeq,
			@ApiParam("内容Seq") @RequestParam("seq") Integer seq, HttpServletRequest request) {
		try {
			communityService.deleteCommunityCONTENT(seq);
			return R.ok("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

	}

	
	/**
	 * 取消社区内容点赞
	 */
	@Login
	@PostMapping("cancelUpCommunity")
	@ApiOperation("取消社区内容点赞")
	public R cancelUpCommunity(@ApiIgnore @RequestAttribute("userSeq") Integer userSeq,
			@ApiParam("社区内容Seq") @RequestParam("contentSeq") Integer contentSeq, HttpServletRequest request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("TypeName", 2);
			map.put("User_Seq", userSeq);
			map.put("Content_Seq", contentSeq);
			communityService.cancelUpCommunity(map);
			return R.ok("取消点赞成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

	}
	
	
	/**
	 * 删除社区评论
	 */
	@Login
	@PostMapping("deleteCommunityComment")
	@ApiOperation("删除社区评论")
	public R deleteCommunityComment(@ApiIgnore @RequestAttribute("userSeq") Integer userSeq,
			@ApiParam("评论Seq") @RequestParam("seq") Integer seq, HttpServletRequest request) {
		try {
			communityService.deleteCommunityCOMMENT(seq);
			return R.ok("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return R.error("服务器异常");
		}

	}

}
