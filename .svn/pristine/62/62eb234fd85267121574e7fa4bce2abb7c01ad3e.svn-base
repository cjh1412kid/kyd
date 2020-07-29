package io.nuite.modules.order_platform_app.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.common.base.Joiner;

import io.nuite.modules.order_platform_app.dao.AnnouncementDao;
import io.nuite.modules.order_platform_app.dao.PublicityPicDao;
import io.nuite.modules.order_platform_app.entity.AnnouncementEntity;
import io.nuite.modules.order_platform_app.service.HomepageService;
import io.nuite.modules.sr_base.dao.HomeCarouselDao;
import io.nuite.modules.sr_base.entity.HomeCarouselEntity;

@Service
public class HomepageServiceImpl implements HomepageService {
    
    @Autowired
    private PublicityPicDao publicityPicDao;
    
    @Autowired
    private HomeCarouselDao homeCarouselDao;
    
    @Autowired
    private AnnouncementDao announcementDao;
    
	

	/**
	 * 根据品牌编号获取轮播图
	 */
	@Override
	public List<Map<String, Object>> getHomeCarouselList(Integer brandSeq){
		Wrapper<HomeCarouselEntity> wrapper = new EntityWrapper<HomeCarouselEntity>();
		wrapper.setSqlSelect("Seq AS seq, Brand_Seq AS brandSeq, Image AS image, Type AS type, Link_Seq AS linkSeq").where("Brand_Seq = {0}", brandSeq);
		return homeCarouselDao.selectMaps(wrapper);
	}
	
	
	/**
	 * 订货爆款图片
	 */
	@Override
	public List<Map<String, Object>> getHotsaleShoes(List<Integer> periodSeqList, Integer companyTypeSeq) {
		String periodSeq = Joiner.on(",").join(periodSeqList);
		List<Map<String, Object>> list = publicityPicDao.getHotsaleShoes(periodSeq, companyTypeSeq);
		return list;
	}
	
	
	/**
	 * 新品推荐图片
	 */
	@Override
	public List<Map<String, Object>> getNewestShoes(List<Integer> periodSeqList, Integer companyTypeSeq) {
		String periodSeq = Joiner.on(",").join(periodSeqList);
		List<Map<String, Object>> list = publicityPicDao.getNewestShoes(periodSeq, companyTypeSeq);
		return list;
	}


	/**
	 * 订货爆款列表
	 */
	@Override
	public List<Map<String, Object>> getHotsaleShoesList(List<Integer> periodSeqList, Integer companyTypeSeq, Integer start, Integer num,Integer meetingSeq,String year,Integer type,Integer status,Integer userSeq) {
		String periodSeq = Joiner.on(",").join(periodSeqList);
		List<Map<String, Object>> list = publicityPicDao.getHotsaleShoesList(periodSeq, companyTypeSeq, start - 1, num,meetingSeq,year,type,status,userSeq);
		return list;
	}


	/**
	 * 新品推荐列表
	 */
	@Override
	public List<Map<String, Object>> getNewestShoesList(List<Integer> periodSeqList, Integer companyTypeSeq, Integer start, Integer num,Integer meetingSeq,String year,Integer type,Integer status,Integer userSeq) {
		String periodSeq = Joiner.on(",").join(periodSeqList);
		List<Map<String, Object>> list = publicityPicDao.getNewestShoesList(periodSeq, companyTypeSeq, start - 1, num,meetingSeq,year,type,status,userSeq);
		return list;
	}


	/**
	 * 公告列表
	 */
	@Override
	public List<AnnouncementEntity> getAnnouncementByCompanySeq(Integer companySeq) {
		Wrapper<AnnouncementEntity> wrapper = new EntityWrapper<AnnouncementEntity>();
		wrapper.where("Company_Seq = {0} AND ExpirationTime >= GETDATE()", companySeq);
		return announcementDao.selectList(wrapper);
	}

    
}
