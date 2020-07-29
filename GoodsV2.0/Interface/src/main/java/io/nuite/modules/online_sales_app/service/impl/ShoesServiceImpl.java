package io.nuite.modules.online_sales_app.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.nuite.common.utils.PageUtils;
import io.nuite.modules.online_sales_app.dao.OnlineSalesShoesDataDao;
import io.nuite.modules.online_sales_app.dao.OnlineSalesShoesInfoDao;
import io.nuite.modules.online_sales_app.dao.TopicDao;
import io.nuite.modules.online_sales_app.entity.ShoesDataEntity;
import io.nuite.modules.online_sales_app.entity.ShoesInfoEntity;
import io.nuite.modules.online_sales_app.entity.TopicEntity;
import io.nuite.modules.online_sales_app.service.ShoesService;
import io.nuite.modules.online_sales_app.utils.TopicType;
import io.nuite.modules.sr_base.dao.GoodsCategoryDao;
import io.nuite.modules.sr_base.dao.GoodsPeriodDao;
import io.nuite.modules.sr_base.dao.GoodsShoesDao;
import io.nuite.modules.sr_base.dao.GoodsViewDao;
import io.nuite.modules.sr_base.entity.GoodsCategoryEntity;
import io.nuite.modules.sr_base.entity.GoodsPeriodEntity;
import io.nuite.modules.sr_base.entity.GoodsShoesEntity;
import io.nuite.modules.sr_base.entity.GoodsViewEntity;
import io.nuite.modules.sr_base.utils.ConfigUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("onlineShoesService")
public class ShoesServiceImpl implements ShoesService {
    @Autowired
    private ConfigUtils configUtils;

    @Autowired
    private TopicDao topicDao;

    @Autowired
    private GoodsPeriodDao goodsPeriodDao;

    @Autowired
    private GoodsShoesDao goodsShoesDao;
    
    @Autowired
    private GoodsViewDao goodsViewDao;

    @Autowired
    private GoodsCategoryDao goodsCategoryDao;

    @Autowired
    private OnlineSalesShoesDataDao shoesDataDao;

    @Autowired
    private OnlineSalesShoesInfoDao shoesInfoDao;

    @Override
    public Map<String, Object> getShoesDetail(Integer brandSeq, Integer seq) {
        List<GoodsPeriodEntity> goodsPeriodEntities = goodsPeriodDao.selectList(new EntityWrapper<GoodsPeriodEntity>().eq("Brand_Seq", brandSeq));
        List<Integer> seqList = new ArrayList<>();
        for (GoodsPeriodEntity goodsPeriodEntity : goodsPeriodEntities) {
            seqList.add(goodsPeriodEntity.getSeq());
        }
        EntityWrapper<GoodsShoesEntity> wrapper = new EntityWrapper<>();
        wrapper.in("Period_Seq", seqList).eq("Seq", seq);
        List<Map<String, Object>> results = goodsShoesDao.selectMaps(wrapper);
        if (results.size() == 0) {
            return null;
        }
        ShoesInfoEntity shoesInfoEntity=shoesInfoDao.getShoeInfoByShoesSeq(seq);
        Integer browseNum=shoesInfoEntity.getBrowseNum();
        if(browseNum==null) {
        	browseNum=1;
        }else {
        	browseNum=browseNum+1;
        }
        shoesInfoEntity.setBrowseNum(browseNum);
        shoesInfoDao.updateById(shoesInfoEntity);
        
        Map<String, Object> baseInfo = results.get(0);
        
        /** 添加属性1~20 **/
        GoodsViewEntity goodsViewEntity = new GoodsViewEntity();
        goodsViewEntity.setSeq(seq);
	    goodsViewEntity = goodsViewDao.selectOne(goodsViewEntity);
		Map<String, String> SXmap = new HashMap<String, String>();
	    if(goodsViewEntity.getSX1Name() != null && goodsViewEntity.getSX1Value() != null) {
	    	SXmap.put(goodsViewEntity.getSX1Name(), goodsViewEntity.getSX1Value());
	    }
	    if(goodsViewEntity.getSX2Name() != null && goodsViewEntity.getSX2Value() != null) {
	    	SXmap.put(goodsViewEntity.getSX2Name(), goodsViewEntity.getSX2Value());
	    }
	    if(goodsViewEntity.getSX3Name() != null && goodsViewEntity.getSX3Value() != null) {
	    	SXmap.put(goodsViewEntity.getSX3Name(), goodsViewEntity.getSX3Value());
	    }
	    if(goodsViewEntity.getSX4Name() != null && goodsViewEntity.getSX4Value() != null) {
	    	SXmap.put(goodsViewEntity.getSX4Name(), goodsViewEntity.getSX4Value());
	    }
	    if(goodsViewEntity.getSX5Name() != null && goodsViewEntity.getSX5Value() != null) {
	    	SXmap.put(goodsViewEntity.getSX5Name(), goodsViewEntity.getSX5Value());
	    }
	    if(goodsViewEntity.getSX6Name() != null && goodsViewEntity.getSX6Value() != null) {
	    	SXmap.put(goodsViewEntity.getSX6Name(), goodsViewEntity.getSX6Value());
	    }
	    if(goodsViewEntity.getSX7Name() != null && goodsViewEntity.getSX7Value() != null) {
	    	SXmap.put(goodsViewEntity.getSX7Name(), goodsViewEntity.getSX7Value());
	    }
	    if(goodsViewEntity.getSX8Name() != null && goodsViewEntity.getSX8Value() != null) {
	    	SXmap.put(goodsViewEntity.getSX8Name(), goodsViewEntity.getSX8Value());
	    }
	    if(goodsViewEntity.getSX9Name() != null && goodsViewEntity.getSX9Value() != null) {
	    	SXmap.put(goodsViewEntity.getSX9Name(), goodsViewEntity.getSX9Value());
	    }
	    if(goodsViewEntity.getSX10Name() != null && goodsViewEntity.getSX10Value() != null) {
	    	SXmap.put(goodsViewEntity.getSX10Name(), goodsViewEntity.getSX10Value());
	    }
	    if(goodsViewEntity.getSX11Name() != null && goodsViewEntity.getSX11Value() != null) {
	    	SXmap.put(goodsViewEntity.getSX11Name(), goodsViewEntity.getSX11Value());
	    }
	    if(goodsViewEntity.getSX12Name() != null && goodsViewEntity.getSX12Value() != null) {
	    	SXmap.put(goodsViewEntity.getSX12Name(), goodsViewEntity.getSX12Value());
	    }
	    if(goodsViewEntity.getSX13Name() != null && goodsViewEntity.getSX13Value() != null) {
	    	SXmap.put(goodsViewEntity.getSX13Name(), goodsViewEntity.getSX13Value());
	    }
	    if(goodsViewEntity.getSX14Name() != null && goodsViewEntity.getSX14Value() != null) {
	    	SXmap.put(goodsViewEntity.getSX14Name(), goodsViewEntity.getSX14Value());
	    }
	    if(goodsViewEntity.getSX15Name() != null && goodsViewEntity.getSX15Value() != null) {
	    	SXmap.put(goodsViewEntity.getSX15Name(), goodsViewEntity.getSX15Value());
	    }
	    if(goodsViewEntity.getSX16Name() != null && goodsViewEntity.getSX16Value() != null) {
	    	SXmap.put(goodsViewEntity.getSX16Name(), goodsViewEntity.getSX16Value());
	    }
	    if(goodsViewEntity.getSX17Name() != null && goodsViewEntity.getSX17Value() != null) {
	    	SXmap.put(goodsViewEntity.getSX17Name(), goodsViewEntity.getSX17Value());
	    }
	    if(goodsViewEntity.getSX18Name() != null && goodsViewEntity.getSX18Value() != null) {
	    	SXmap.put(goodsViewEntity.getSX18Name(), goodsViewEntity.getSX18Value());
	    }
	    if(goodsViewEntity.getSX19Name() != null && goodsViewEntity.getSX19Value() != null) {
	    	SXmap.put(goodsViewEntity.getSX19Name(), goodsViewEntity.getSX19Value());
	    }
	    if(goodsViewEntity.getSX20Name() != null && goodsViewEntity.getSX20Value() != null) {
	    	SXmap.put(goodsViewEntity.getSX20Name(), goodsViewEntity.getSX20Value());
	    }
	    baseInfo.put("SX", SXmap);
	    /** 添加属性1~20**/

        Map<String, Object> stockInfo = shoesDataDao.getStockByShoesSeq(seq);

        Map<String, Object> platformInfo = shoesInfoDao.getPlatformInfoByShoesSeq(seq);
        if (stockInfo != null)
            baseInfo.putAll(stockInfo);

        if (platformInfo != null)
            baseInfo.putAll(platformInfo);

        String goodsId = baseInfo.get("goodID").toString();
        // description 描述图片url拼接
        Object description;
        if (baseInfo.containsKey("description") && (description = baseInfo.get("description")) != null) {
            List<String> descriptions = new ArrayList<String>();
            String[] descriptionArray = description.toString().trim().split(",");
            for (String desImg : descriptionArray) {
                String desUrlImg = configUtils.getPictureBaseUrl() + "/" + configUtils.getBaseDir() + "/" + configUtils.getGoodsShoes() + "/" + goodsId + "/" + desImg;
                descriptions.add(desUrlImg);
            }
            baseInfo.put("description", descriptions);
        }

        Object video;
        if (baseInfo.containsKey("video") && (video = baseInfo.get("video")) != null) {
            String videoUrl = configUtils.getPictureBaseUrl() + "/" + configUtils.getBaseDir() + "/" + configUtils.getGoodsShoes() + "/" + goodsId + "/" + video.toString().trim();
            baseInfo.put("video", videoUrl);
        }

        List<String> imageUrls = new ArrayList<String>(5);
        for (int i = 1; i <= 5; i++) {
            Object imagei;
            if (baseInfo.containsKey("img" + i) && (imagei = baseInfo.get("img" + i)) != null && StringUtils.isNotBlank(baseInfo.get("img" + i).toString())) {
                String imgiUrl = configUtils.getPictureBaseUrl() + "/" + configUtils.getBaseDir() + "/" + configUtils.getGoodsShoes() + "/" + goodsId + "/" + imagei.toString().trim();
                imageUrls.add(imgiUrl);
            }
        }
        baseInfo.put("images", imageUrls);

        Integer periodSeq=(Integer) baseInfo.get("periodSeq");
        
        GoodsPeriodEntity goodsPeriodEntity=goodsPeriodDao.selectById(periodSeq);
        
        baseInfo.put("periodName", goodsPeriodEntity.getName());
        
        Integer categorySeq3=(Integer) baseInfo.get("categorySeq");
        
        GoodsCategoryEntity goodsCategoryEntity3=goodsCategoryDao.selectById(categorySeq3);//三级分类
        
        GoodsCategoryEntity goodsCategoryEntity2=goodsCategoryDao.selectById(goodsCategoryEntity3.getParentSeq());//二级分类
        
        GoodsCategoryEntity goodCategoryEntity1=goodsCategoryDao.selectById(goodsCategoryEntity2.getParentSeq());//一级分类
        
        baseInfo.put("categoryName1", goodCategoryEntity1.getName());
        
        baseInfo.put("categoryName2", goodsCategoryEntity2.getName());
        
        baseInfo.put("categoryName3", goodsCategoryEntity3.getName());
        
        return baseInfo;
    }

    @Override
    public List<ShoesDataEntity> getShoesCartDetail(Integer seq) {
        return shoesDataDao.queryDataAndColorName(seq);
    }

    /**
     * 查询鞋子信息列表
     *
     * @param page        页码
     * @param limit       一页个数
     * @param orderBy     排序字段(0:综合 1:销量 2:价格 3:上架时间)
     * @param orderDir    排序方式(0:降序 1:升序)
     * @param type        鞋子分类
     * @param brandSeq    品牌seq
     * @param categorySeq 鞋子分类
     * @return
     */
    @Override
    public PageUtils getShoesInfoPage(int page, int limit, Integer orderBy, Integer orderDir, TopicType type, Integer brandSeq, Integer categorySeq) {
        Integer topicSeq = null;
        if (type != null) {
            Integer typeNum = type.ordinal();
            EntityWrapper<TopicEntity> ew = new EntityWrapper<>();
            ew.eq("Brand_Seq", brandSeq).eq("TopicType", typeNum);
            List<TopicEntity> topicEntities = topicDao.selectList(ew);
            if (topicEntities != null && topicEntities.size() > 0) {
                topicSeq = topicEntities.get(0).getSeq();
            }
            if (topicSeq == null) {
                return new PageUtils(new ArrayList<>(1), 0, limit, page);
            }
        }
        String orderStr = null;
        if (orderDir != null && orderDir == 1) {
            orderStr = "asc";
        } else if (orderDir != null && orderDir == 0) {
            orderStr = "desc";
        }
        List<Map<String, Object>> shoesList;
        if (orderBy != null && orderBy == 1) {
            shoesList = shoesInfoDao.queryShoesPageOrderBySales(page, limit, orderStr, brandSeq, topicSeq, categorySeq);
        } else if (orderBy != null && orderBy == 2) {
            shoesList = shoesInfoDao.queryShoesPageOrderByPrice(page, limit, orderStr, brandSeq, topicSeq, categorySeq);
        } else if (orderBy != null && orderBy == 3) {
            shoesList = shoesInfoDao.queryShoesPageOrderByTime(page, limit, orderStr, brandSeq, topicSeq, categorySeq);
        } else {
            shoesList = shoesInfoDao.queryShoesPageOrderBySales(page, limit, null, brandSeq, topicSeq, categorySeq);
        }
        for (Map<String, Object> mapOne : shoesList) {
        	if(mapOne.get("img")!=null) {
            String imageName = mapOne.get("img").toString();
            String goodsId = mapOne.get("goodId").toString();
            String imgSrc = configUtils.getPictureBaseUrl() + "/" + configUtils.getBaseDir() + "/" + configUtils.getGoodsShoes() + "/" + goodsId + "/" + imageName;
            mapOne.put("imgSrc", imgSrc);
        	}
        }
        int totalCount = shoesInfoDao.queryShoesPageTotal(brandSeq, topicSeq);
        return new PageUtils(shoesList, totalCount, limit, page);
    }

    @Override
    public List<Map<String, Object>> getShoesCategory(Integer companySeq, Integer parentSeq) {
        return goodsCategoryDao.getShoesCategory(companySeq, parentSeq);
    }
}
