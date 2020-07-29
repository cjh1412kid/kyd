package io.nuite.modules.order_platform_app.service.impl;

import java.util.*;
import java.util.Map.Entry;

import io.nuite.common.utils.ZipUtils;
import io.nuite.modules.order_platform_app.dao.*;
import io.nuite.modules.order_platform_app.entity.*;
import io.nuite.modules.sr_base.dao.GoodsCategoryDao;
import io.nuite.modules.sr_base.entity.GoodsCategoryEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.base.Joiner;

import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.Query;
import io.nuite.modules.order_platform_app.service.MeetingGoodsService;
import io.nuite.modules.sr_base.dao.GoodsColorDao;
import io.nuite.modules.sr_base.entity.GoodsColorEntity;
import io.nuite.modules.sr_base.utils.ConfigUtils;
import io.nuite.modules.system.dao.MeetingDao;
import io.nuite.modules.system.dao.MeetingOrderProductDao;
import io.nuite.modules.system.entity.MeetingEntity;
import io.nuite.modules.system.entity.MeetingOrderProductEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


@Service
public class MeetingGoodsServiceImpl extends ServiceImpl<MeetingGoodsDao, MeetingGoodsEntity> implements MeetingGoodsService {

    @Autowired
    private MeetingGoodsDao meetingGoodsDao;
    
    @Autowired
    private GoodsColorDao goodsColorDao;
    
    @Autowired
    private MeetingDao meetingDao;
    
    @Autowired
    private MeetingShoppingCartDao meetingShoppingCartDao;

	@Autowired
	private MeetingShoppingCartDistributeBoxDao meetingShoppingCartDistributeBoxDao;

	@Autowired
	private MeetingShoppingCartDetailDao meetingShoppingCartDetailDao;
    
    @Autowired
    private MeetingOrderCartDao meetingOrderCartDao;
    
    @Autowired
    private MeetingOrderProductDao meetingOrderProductDao;
    
    @Autowired
    private ConfigUtils configUtils;

    @Autowired
	private GoodsCategoryDao goodsCategoryDao;
    
    
    /**
     * 录入订货会鞋子
     */
	@Override
	public Integer addMeetingGoods(MeetingGoodsEntity meetingGoodsEntity) {
		meetingGoodsDao.insert(meetingGoodsEntity);
		return meetingGoodsEntity.getSeq();
	}



	
	/**
	 * 已录入鞋子列表
	 */
	@Override
	public List<MeetingGoodsEntity> getMeetingGoodsList(Integer meetingSeq, Integer start, Integer num, String searchParam) {
		Wrapper<MeetingGoodsEntity> wrapper = new EntityWrapper<MeetingGoodsEntity>();
		wrapper.where("Meeting_Seq = {0}", meetingSeq).orderBy("InputTime DESC");
		
		if(searchParam != null) {
			if(StringUtils.isNumeric(searchParam)) {
				wrapper.where("( Seq = {0} OR GoodID = {0} ) ", searchParam);
			} else {
				wrapper.where("GoodID = {0} ", searchParam);
			}
		}
		
		if(start != null) {
			return meetingGoodsDao.selectPage(new RowBounds(start - 1, num), wrapper);
		} else {
			return meetingGoodsDao.selectList(wrapper);
		}
		
	}



	/**
	 * 查询公司所有颜色
	 */
	@Override
	public List<Map<String, Object>> getAllColorList(Integer companySeq) {
		Wrapper<GoodsColorEntity> wrapper = new EntityWrapper<GoodsColorEntity>();
		wrapper.setSqlSelect("Seq AS colorSeq, Name AS colorName").where("Company_Seq = {0}", companySeq);
		return goodsColorDao.selectMaps(wrapper);
	}


	
	/**
	 * 查询本次订货会常用颜色
	 */
	@Override
	public List<Map<String, Object>> getOftenColorList(Integer meetingSeq) {
		
		//1.查询本次订货会鞋子所有的可选颜色seq 的List
		Wrapper<MeetingGoodsEntity> wrapper = new EntityWrapper<MeetingGoodsEntity>();
		wrapper.setSqlSelect("OptionalColorSeqs").where("Meeting_Seq = {0}", meetingSeq);
		List<Object> optionalColorSeqsList = meetingGoodsDao.selectObjs(wrapper);
		
		
		Map<Integer, Integer> colorUsedTimeMap = new HashMap<Integer, Integer>();
		for(Object optionalColorSeqs : optionalColorSeqsList) {
			if(optionalColorSeqs != null && StringUtils.isNotBlank(optionalColorSeqs.toString())) {
				String[] colorSeqsArr = optionalColorSeqs.toString().split(",");
				for(String colorSeqStr : colorSeqsArr) {
					Integer colorSeq = Integer.parseInt(colorSeqStr);
					if(colorUsedTimeMap.containsKey(colorSeq)) {
						colorUsedTimeMap.put(colorSeq, colorUsedTimeMap.get(colorSeq) + 1);
					} else {
						colorUsedTimeMap.put(colorSeq, 1);
					}
				}
			}
		}
		
		
		List<Map<String, Object>> oftenUserdColorList = new ArrayList<Map<String, Object>>();
		
		//对map中的value进行排序
		colorUsedTimeMap = MeetingGoodsServiceImpl.sortMapByValue(colorUsedTimeMap);
		
		if(colorUsedTimeMap != null) {
			for(Integer key : colorUsedTimeMap.keySet()) {
				Map<String, Object> oftenUserdColorMap = new HashMap<String, Object>();
				//查询颜色名称
				GoodsColorEntity goodsColorEntity = goodsColorDao.selectById(key);
				if(goodsColorEntity == null) {
					continue;
				}
				oftenUserdColorMap.put("colorSeq", key);
				oftenUserdColorMap.put("colorName", goodsColorEntity.getName());
				oftenUserdColorMap.put("usedTimes", colorUsedTimeMap.get(key));
				oftenUserdColorList.add(oftenUserdColorMap);
			}
		}
		
		return oftenUserdColorList;
	}
	
	
    public static Map<Integer, Integer> sortMapByValue(Map<Integer, Integer> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<Integer, Integer> sortedMap = new LinkedHashMap<Integer, Integer>();
        List<Map.Entry<Integer, Integer>> entryList = new ArrayList<Map.Entry<Integer, Integer>>(oriMap.entrySet());
        Collections.sort(entryList, new MapValueComparator());

        Iterator<Map.Entry<Integer, Integer>> iter = entryList.iterator();
        Map.Entry<Integer, Integer> tmpEntry = null;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }
    
    private static final class MapValueComparator implements Comparator<Map.Entry<Integer, Integer>> {

        @Override
        public int compare(Entry<Integer, Integer> me1, Entry<Integer, Integer> me2) {

            return -me1.getValue().compareTo(me2.getValue());
        }
    }

    


	/**
	 * 查询鞋子总数
	 */
	@Override
	public Integer getMeetingGoodsNum(Integer meetingSeq) {
		Wrapper<MeetingGoodsEntity> wrapper = new EntityWrapper<MeetingGoodsEntity>();
		wrapper.where("Meeting_Seq = {0}", meetingSeq);
		return meetingGoodsDao.selectCount(wrapper);
	}




	/**
	 * 删除鞋子
	 */
	@Override
	public void deleteMeetingGoods(List<Integer> meetingGoodsSeqs) {
		meetingGoodsDao.deleteBatchIds(meetingGoodsSeqs);
	}




	/**
	 * 获取某订货会所有鞋子序号
	 */
	@Override
	public List<Object> getAllMeetingGoodsSeqsByMeetingSeq(Integer meetingSeq) {
		Wrapper<MeetingGoodsEntity> wrapper = new EntityWrapper<MeetingGoodsEntity>();
		wrapper.setSqlSelect("Seq").where("Meeting_Seq = {0}", meetingSeq);
		return meetingGoodsDao.selectObjs(wrapper);
	}




	/**
	 * 查询订货会所有鞋子
	 */
	@Override
	public List<MeetingGoodsEntity> getAllMeetingGoodsByMeetingSeq(Integer meetingSeq) {
		Wrapper<MeetingGoodsEntity> wrapper = new EntityWrapper<MeetingGoodsEntity>();
		wrapper.where("Meeting_Seq = {0}", meetingSeq).orderBy("Seq ASC");
		return meetingGoodsDao.selectList(wrapper);
	}




	@Override
	public MeetingGoodsEntity getMeetingGoodsByGoodId(String goodid,Integer meetingSeq) {
		MeetingGoodsEntity meetingGoodsEntity = new MeetingGoodsEntity();
		meetingGoodsEntity.setGoodID(goodid);
		meetingGoodsEntity.setMeetingSeq(meetingSeq);
        return meetingGoodsDao.selectOne(meetingGoodsEntity);
	}




	@Override
	public PageUtils getMeetingGoodsList(Long UserSeq, Map<String, Object> params) {
        List<Integer> meetingList = new ArrayList<>();
        		
        Object meetingSeqObj = params.get("meetingSeq");
        Object meetingGoodsId=params.get("keywords");
        // 如果订货会为空 则查找该用户所有的订货会
        if (meetingSeqObj == null || StringUtils.isEmpty(meetingSeqObj.toString()) || meetingSeqObj.toString().equals("-1")) {
        	meetingList = meetingDao.getMeetingSeqList(UserSeq);
        } else {
        	meetingList.add(Integer.valueOf(meetingSeqObj.toString()));
        }
        Page<MeetingGoodsEntity> page = new Query<MeetingGoodsEntity>(params).getPage();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("meetingSeqs", Joiner.on(",").join(meetingList));
        map.put("meetingGoodsId", meetingGoodsId);
        List<MeetingGoodsEntity> list = meetingGoodsDao.selectGoodsList(page, map);

        for (MeetingGoodsEntity meetingGoodsEntity : list) {
            //图片路径
            String goodId = meetingGoodsEntity.getGoodID();
            String img1 = meetingGoodsEntity.getImg();
            String imageUrl =configUtils.getPictureBaseUrl() + "/" + configUtils.getOrderPlatformApp().getOrderPlatformDir() + "/"+ configUtils.getOrderPlatformApp().getMeetingGoods() +"/" +goodId+"/"+ img1;
            meetingGoodsEntity.setImg(imageUrl);
            
            String colorSeqs=meetingGoodsEntity.getOptionalColorSeqs();
            String[] seqs=colorSeqs.split(",");
            StringBuffer colorNames=new StringBuffer();
			List<String> categories = getCategory(meetingGoodsEntity.getCategorySeq(),new ArrayList<>(10));
			if(categories != null) {
				if(categories.size() == 1) {
					meetingGoodsEntity.setFirstCategory(categories.get(0));
				}else if(categories.size() == 2) {
					meetingGoodsEntity.setSecondCategory(categories.get(0));
					meetingGoodsEntity.setFirstCategory(categories.get(1));
				}else if(categories.size() == 3) {
					meetingGoodsEntity.setThirdCategory(categories.get(0));
					meetingGoodsEntity.setSecondCategory(categories.get(1));
					meetingGoodsEntity.setFirstCategory(categories.get(2));
				}
			}
		  	for (String Colorseq : seqs) {
			  	GoodsColorEntity goodsColorEntity=goodsColorDao.selectById(Integer.parseInt(Colorseq));
			  	if(goodsColorEntity == null) {
					continue;
			  	}
			  	String colorName=goodsColorEntity.getName();
			  	if(colorNames.toString().equals("")) {
			  		colorNames.append(colorName);
		  		}else {
					colorNames.append(","+colorName);
				}
			}
 		 	meetingGoodsEntity.setOptionalColorNames(colorNames.toString());
        }
        return new PageUtils(list, page.getTotal(), page.getSize(), page.getCurrent());
	}

	@Override
	public List<MeetingGoodsEntity> selectMeetingGoodsList(Long UserSeq, Map<String, Object> params) {
		List<Integer> meetingList = new ArrayList<>();

		Object meetingSeqObj = params.get("meetingSeq");
		Object meetingGoodsId=params.get("keywords");
		// 如果订货会为空 则查找该用户所有的订货会
		if (meetingSeqObj == null || StringUtils.isEmpty(meetingSeqObj.toString()) || meetingSeqObj.toString().equals("-1")) {
			meetingList = meetingDao.getMeetingSeqList(UserSeq);
		} else {
			meetingList.add(Integer.valueOf(meetingSeqObj.toString()));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("meetingSeqs", Joiner.on(",").join(meetingList));
		map.put("meetingGoodsId", meetingGoodsId);
		List<MeetingGoodsEntity> list = meetingGoodsDao.selectMeetingGoodsList(map);

		for (MeetingGoodsEntity meetingGoodsEntity : list) {
			//图片路径
			String goodId = meetingGoodsEntity.getGoodID();
			String img1 = meetingGoodsEntity.getImg();
			String imageUrl =configUtils.getPictureBaseUrl() + "/" + configUtils.getOrderPlatformApp().getOrderPlatformDir() + "/"+ configUtils.getOrderPlatformApp().getMeetingGoods() +"/" +goodId+"/"+ img1;
			meetingGoodsEntity.setImg(imageUrl);

			String colorSeqs=meetingGoodsEntity.getOptionalColorSeqs();
			String[] seqs=colorSeqs.split(",");
			StringBuffer colorNames=new StringBuffer();
			List<String> categories = getCategory(meetingGoodsEntity.getCategorySeq(),new ArrayList<>(10));
			if(categories != null) {
				if(categories.size() == 1) {
					meetingGoodsEntity.setFirstCategory(categories.get(0));
				}else if(categories.size() == 2) {
					meetingGoodsEntity.setSecondCategory(categories.get(0));
					meetingGoodsEntity.setFirstCategory(categories.get(1));
				}else if(categories.size() == 3) {
					meetingGoodsEntity.setThirdCategory(categories.get(0));
					meetingGoodsEntity.setSecondCategory(categories.get(1));
					meetingGoodsEntity.setFirstCategory(categories.get(2));
				}
			}
			for (String Colorseq : seqs) {
				GoodsColorEntity goodsColorEntity=goodsColorDao.selectById(Integer.parseInt(Colorseq));
				String colorName=goodsColorEntity.getName();
				if(colorNames.toString().equals("")) {
					colorNames.append(colorName);
				}else {
					colorNames.append(","+colorName);
				}
			}
			meetingGoodsEntity.setOptionalColorNames(colorNames.toString());
		}
		return list;
	}


	@Override
	public Map<String, Object> edit(Integer seq) {
		Map<String, Object> map=new HashMap<String, Object>();
		  MeetingGoodsEntity meetingGoodsEntity = super.selectById(seq);
		  String ColorSeqs=meetingGoodsEntity.getOptionalColorSeqs();
		  String[] seqs=ColorSeqs.split(",");
		 StringBuffer colorNames=new StringBuffer();
		  for (String Colorseq : seqs) {
			  GoodsColorEntity goodsColorEntity=goodsColorDao.selectById(Integer.parseInt(Colorseq));
			  String colorName=goodsColorEntity.getName();
			  if(colorNames.toString().equals("")) {
				  colorNames.append(colorName);
			  }else {
				  colorNames.append(","+colorName); 
			  }
		  }
		  meetingGoodsEntity.setOptionalColorNames(colorNames.toString());
	      Integer meetingSeq=meetingGoodsEntity.getMeetingSeq();
	      MeetingEntity meeting=meetingDao.selectById(meetingSeq);
	      map.put("goods", meetingGoodsEntity);
	      map.put("meetingName", meeting.getName());
		return map;
	}




	/**
	 * 判断货品是否已加入购物车
	 */
	@Override
	public boolean isInMeetingShoppingCart(Integer meetingGoodsSeq) {
		Wrapper<MeetingShoppingCartEntity> wrapper = new EntityWrapper<MeetingShoppingCartEntity>();
		wrapper.setSqlSelect("Top 1 Seq").where("MeetingGoods_Seq = {0}", meetingGoodsSeq);
		List<Object> list = meetingShoppingCartDao.selectObjs(wrapper);
		if(list != null && list.size() > 0 && list.get(0) != null) {
			return true;
		} else {
			return false;
		}
	}




	/**
	 * 判断货品是否已提交订单
	 */
	@Override
	public boolean isInMeetingOrder(Integer meetingGoodsSeq) {
		Wrapper<MeetingOrderProductEntity> wrapper = new EntityWrapper<MeetingOrderProductEntity>();
		wrapper.setSqlSelect("Top 1 Seq").where("MeetingGoods_Seq = {0}", meetingGoodsSeq);
		List<Object> list = meetingOrderProductDao.selectObjs(wrapper);
		if(list != null && list.size() > 0 && list.get(0) != null) {
			return true;
		} else {
			return false;
		}
	}



	/**
	 * 修改货品货号时，要同时修改订货会购物车，订货会订单购物车两张表中的冗余货号字段
	 */
	@Override
    @Transactional(propagation = Propagation.REQUIRED)
	public void updateCartMeetingGoodsId(Integer meetingGoodsSeq, String goodId) {
		MeetingGoodsEntity meetingGoodsEntity = new MeetingGoodsEntity();
		meetingGoodsEntity.setSeq(meetingGoodsSeq);
		meetingGoodsEntity.setGoodID(goodId);
		meetingGoodsDao.updateById(meetingGoodsEntity);

		
		MeetingShoppingCartEntity meetingShoppingCartEntity = new MeetingShoppingCartEntity();
		meetingShoppingCartEntity.setMeetingGoodsID(goodId);
		Wrapper<MeetingShoppingCartEntity> wrapper1 = new EntityWrapper<MeetingShoppingCartEntity>();
		wrapper1.where("MeetingGoods_Seq = {0}", meetingGoodsSeq);
		meetingShoppingCartDao.update(meetingShoppingCartEntity, wrapper1);
		
		
		MeetingOrderCartEntity meetingOrderCartEntity = new MeetingOrderCartEntity();
		meetingOrderCartEntity.setMeetingGoodsID(goodId);
		Wrapper<MeetingOrderCartEntity> wrapper2 = new EntityWrapper<MeetingOrderCartEntity>();
		wrapper2.where("MeetingGoods_Seq = {0}", meetingGoodsSeq);
		meetingOrderCartDao.update(meetingOrderCartEntity, wrapper2);
		
	}




	@Override
	public Integer getMinSizeByMeetingSeq(Integer meetingSeq) {
		Wrapper<MeetingGoodsEntity> wrapper = new EntityWrapper<MeetingGoodsEntity>();
		wrapper.setSqlSelect("min(MinSize)").where("Meeting_Seq = {0}", meetingSeq);
		List<Object> list=meetingGoodsDao.selectObjs(wrapper);
		return (Integer) list.get(0);
	}




	@Override
	public Integer getMaxSizeByMeetingSeq(Integer meetingSeq) {
		Wrapper<MeetingGoodsEntity> wrapper = new EntityWrapper<MeetingGoodsEntity>();
		wrapper.setSqlSelect("max(MaxSize)").where("Meeting_Seq = {0}", meetingSeq);
		List<Object> list=meetingGoodsDao.selectObjs(wrapper);
		return (Integer) list.get(0);
	}

	private List<String> getCategory(Integer categorySeq,List<String> categories) {
		GoodsCategoryEntity goodsCategoryEntity = goodsCategoryDao.selectById(categorySeq);
		if(goodsCategoryEntity != null && goodsCategoryEntity.getParentSeq() != null) {
			categories.add(goodsCategoryEntity.getName());
			if(goodsCategoryEntity.getParentSeq() != 0) {
				getCategory(goodsCategoryEntity.getParentSeq(),categories);
			}
		}
		return categories;
	}




	@Override
	public List<Map<String, Object>> getParentCategory(Integer companySeq, Integer meetingSeq, Integer categorySeq,Integer showType) {
		List<Map<String, Object>> categoryRankList=meetingGoodsDao.getCategory(companySeq, meetingSeq, categorySeq,showType);
		for (Map<String, Object> map : categoryRankList) {
			Integer cateSeq=(Integer) map.get("categorySeq");
			GoodsCategoryEntity goodsCategoryEntity=goodsCategoryDao.selectById(cateSeq);
			map.put("categoryName", goodsCategoryEntity.getName());
		}
		return categoryRankList;
	}




	@Override
	public List<Map<String, Object>> getCategoryList(Integer companySeq, Integer meetingSeq, Integer categorySeq) {
		List<Map<String, Object>> categoryList=meetingGoodsDao.getCategoryList(companySeq, meetingSeq, categorySeq);
		for (Map<String, Object> map : categoryList) {
			Integer cateSeq=(Integer) map.get("seq");
			GoodsCategoryEntity goodsCategoryEntity=goodsCategoryDao.selectById(cateSeq);
			map.put("categoryName", goodsCategoryEntity.getName());
		}
		return categoryList;
	}




	@Override
	public Boolean hasGoodsInOrders(Integer meetingGoods) {
		Wrapper<MeetingShoppingCartEntity> wrapper=new EntityWrapper<MeetingShoppingCartEntity>();
		wrapper.where("MeetingGoods_Seq ={0}", meetingGoods);
		List<MeetingShoppingCartEntity> list=meetingShoppingCartDao.selectList(wrapper);
		
		Wrapper<MeetingOrderCartEntity> wrapper2=new EntityWrapper<MeetingOrderCartEntity>();
		wrapper2.where("MeetingGoods_Seq ={0}", meetingGoods);
		List<MeetingOrderCartEntity> list2=meetingOrderCartDao.selectList(wrapper2);
		   if ((list != null && list.size() > 0)||(list2!=null&&list2.size()>0)) {
	            return true;
	        } else {
	            return false;
	        }
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean insertMeetingGoods(Integer userSeq, MeetingGoodsEntity meetingGoodsEntity,String uploadUrl) throws Exception {
		Wrapper<MeetingGoodsEntity> wrapper = new EntityWrapper<>();
		wrapper.eq("GoodID",meetingGoodsEntity.getGoodID());
		wrapper.eq("Meeting_Seq",meetingGoodsEntity.getMeetingSeq());
		List<MeetingGoodsEntity> meetingGoodsEntities = meetingGoodsDao.selectList(wrapper);
		if(meetingGoodsEntities.size() > 0) {
			throw new RuntimeException("货号已存在");
		}
		String colorSeqs = "";
		Integer color1 = meetingGoodsEntity.getColor1();
		if(color1 != null) {
			colorSeqs += color1 + ",";
		}
		Integer color2 = meetingGoodsEntity.getColor2();
		if(color2 != null) {
			colorSeqs += color2 + ",";
		}
		Integer color3 = meetingGoodsEntity.getColor3();
		if(color3 != null) {
			colorSeqs += color3 + ",";
		}
		Integer color4 = meetingGoodsEntity.getColor4();
		if(color4 != null) {
			colorSeqs += color4 + ",";
		}
		Integer color5 = meetingGoodsEntity.getColor5();
		if(color5 != null) {
			colorSeqs += color5 + ",";
		}
		Integer color6 = meetingGoodsEntity.getColor6();
		if(color6 != null) {
			colorSeqs += color6 + ",";
		}
		Integer color7 = meetingGoodsEntity.getColor7();
		if(color7 != null) {
			colorSeqs += color7 + ",";
		}
		Integer color8 = meetingGoodsEntity.getColor8();
		if(color8 != null) {
			colorSeqs += color8 + ",";
		}
		colorSeqs = colorSeqs.substring(0,colorSeqs.length() - 1);
		meetingGoodsEntity.setOptionalColorSeqs(colorSeqs);
		if(meetingGoodsEntity.getGoodImg() != null) {
			List<MultipartFile> multipartFiles = new ArrayList<>(10);
			multipartFiles.add(meetingGoodsEntity.getGoodImg());
			List<Map<String,Object>> imgUrls = ZipUtils.uploadPicture(uploadUrl,multipartFiles,userSeq);
			if(imgUrls.size() > 0) {
				for(Map<String,Object> map : imgUrls) {
					for(Map.Entry<String,Object> entry : map.entrySet()) {
						String goodId = entry.getKey();
						String imgUrl = entry.getValue().toString();
						meetingGoodsEntity.setImg(imgUrl);
					}
				}

			}
		}
		boolean isSuccess = retBool(meetingGoodsDao.insert(meetingGoodsEntity));
		MeetingShoppingCartEntity meetingShoppingCartEntity = new MeetingShoppingCartEntity();
		if(meetingGoodsEntity.getAttention() != null) {
			meetingShoppingCartEntity.setUserSeq(userSeq);
			meetingShoppingCartEntity.setMeetingGoodsID(meetingGoodsEntity.getGoodID());
			meetingShoppingCartEntity.setMeetingGoodsSeq(meetingGoodsEntity.getSeq());
			meetingShoppingCartEntity.setMeetingSeq(meetingGoodsEntity.getMeetingSeq());
			meetingShoppingCartEntity.setInputTime(new Date());
			meetingShoppingCartEntity.setDel(0);
			Integer length = meetingGoodsEntity.getAttention();
			for(int i = 0;i < length;i++) {
				if(i == length - 1 && meetingGoodsEntity.getOrderQuantity() != null) {
					meetingShoppingCartEntity.setTotalSelectNum(meetingGoodsEntity.getOrderQuantity());
				}
				meetingShoppingCartDao.insert(meetingShoppingCartEntity);
			}
		}
		return isSuccess;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateMeetingGoods(Integer userSeq,MeetingGoodsEntity meetingGoodsEntity,String uploadUrl) throws Exception {
		String colorSeqs = "";
		Integer color1 = meetingGoodsEntity.getColor1();
		if(color1 != null) {
			colorSeqs += color1 + ",";
		}
		Integer color2 = meetingGoodsEntity.getColor2();
		if(color2 != null) {
			colorSeqs += color2 + ",";
		}
		Integer color3 = meetingGoodsEntity.getColor3();
		if(color3 != null) {
			colorSeqs += color3 + ",";
		}
		Integer color4 = meetingGoodsEntity.getColor4();
		if(color4 != null) {
			colorSeqs += color4 + ",";
		}
		Integer color5 = meetingGoodsEntity.getColor5();
		if(color5 != null) {
			colorSeqs += color5 + ",";
		}
		Integer color6 = meetingGoodsEntity.getColor6();
		if(color6 != null) {
			colorSeqs += color6 + ",";
		}
		Integer color7 = meetingGoodsEntity.getColor7();
		if(color7 != null) {
			colorSeqs += color7 + ",";
		}
		Integer color8 = meetingGoodsEntity.getColor8();
		if(color8 != null) {
			colorSeqs += color8 + ",";
		}
		colorSeqs = colorSeqs.substring(0,colorSeqs.length() - 1);
		meetingGoodsEntity.setOptionalColorSeqs(colorSeqs);
		if(meetingGoodsEntity.getGoodImg() != null) {
			List<MultipartFile> multipartFiles = new ArrayList<>(10);
			multipartFiles.add(meetingGoodsEntity.getGoodImg());
			List<Map<String,Object>> imgUrls = ZipUtils.uploadPicture(uploadUrl,multipartFiles,userSeq);
			if(imgUrls.size() > 0) {
				for(Map<String,Object> map : imgUrls) {
					for(Map.Entry<String,Object> entry : map.entrySet()) {
						String goodId = entry.getKey();
						String imgUrl = entry.getValue().toString();
						meetingGoodsEntity.setImg(imgUrl);
					}
				}

			}
		}
		boolean isSuccess = retBool(meetingGoodsDao.updateById(meetingGoodsEntity));
		Wrapper<MeetingShoppingCartEntity> meetingShoppingCartEntityWrapper = new EntityWrapper<>();
		meetingShoppingCartEntityWrapper.eq("Meeting_Seq",meetingGoodsEntity.getMeetingSeq());
		meetingShoppingCartEntityWrapper.eq("User_Seq",userSeq);
		meetingShoppingCartEntityWrapper.eq("MeetingGoods_Seq",meetingGoodsEntity.getSeq());
		meetingShoppingCartDao.delete(meetingShoppingCartEntityWrapper);
		MeetingShoppingCartEntity meetingShoppingCartEntity = new MeetingShoppingCartEntity();
		if(meetingGoodsEntity.getAttention() != null) {
			meetingShoppingCartEntity.setUserSeq(userSeq);
			meetingShoppingCartEntity.setMeetingGoodsID(meetingGoodsEntity.getGoodID());
			meetingShoppingCartEntity.setMeetingGoodsSeq(meetingGoodsEntity.getSeq());
			meetingShoppingCartEntity.setMeetingSeq(meetingGoodsEntity.getMeetingSeq());
			meetingShoppingCartEntity.setInputTime(new Date());
			meetingShoppingCartEntity.setDel(0);
			Integer length = meetingGoodsEntity.getAttention();
			for(int i = 0;i < length;i++) {
				if(i == length - 1 && meetingGoodsEntity.getOrderQuantity() != null) {
					meetingShoppingCartEntity.setTotalSelectNum(meetingGoodsEntity.getOrderQuantity());
				}
				meetingShoppingCartDao.insert(meetingShoppingCartEntity);
			}
		}
		return isSuccess;
	}
}
