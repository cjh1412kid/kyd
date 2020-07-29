package io.nuite.modules.order_platform_app.service;

import java.util.List;
import java.util.Map;

import io.nuite.modules.order_platform_app.entity.AnnouncementEntity;

public interface HomepageService {

	//轮播图列表
	List<Map<String, Object>> getHomeCarouselList(Integer brandSeq);
	
	//订货爆款图片
	List<Map<String, Object>> getHotsaleShoes(List<Integer> periodSeqList, Integer companyTypeSeq);

	//新品推荐图片
	List<Map<String, Object>> getNewestShoes(List<Integer> periodSeqList, Integer companyTypeSeq);

	//订货爆款列表
	List<Map<String, Object>> getHotsaleShoesList(List<Integer> periodSeqList, Integer companyTypeSeq, Integer start, Integer num,Integer meetingSeq,String year,Integer type,Integer sstatus,Integer userSeq);

	//新品推荐列表
	List<Map<String, Object>> getNewestShoesList(List<Integer> periodSeqList, Integer companyTypeSeq, Integer start, Integer num,Integer meetingSeq,String year,Integer type,Integer sstatus,Integer userSeq);

	//公告列表
	List<AnnouncementEntity> getAnnouncementByCompanySeq(Integer companySeq);

	
}
