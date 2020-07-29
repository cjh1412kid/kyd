package io.nuite.modules.order_platform_app.service.impl;

import java.io.File;
import java.util.*;

import com.baomidou.mybatisplus.plugins.Page;
import io.nuite.common.utils.FileUtils;
import io.nuite.modules.order_platform_app.dao.*;
import io.nuite.modules.order_platform_app.entity.*;
import io.nuite.modules.sr_base.dao.*;
import io.nuite.modules.sr_base.entity.*;
import io.nuite.modules.sr_base.utils.ConfigUtils;
import io.nuite.modules.system.dao.MeetingDao;
import io.nuite.modules.system.entity.MeetingEntity;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import io.nuite.modules.order_platform_app.service.MeetingOrderExcelService;
import io.nuite.modules.system.dao.MeetingOrderDao;
import io.nuite.modules.system.dao.MeetingOrderProductDao;
import io.nuite.modules.system.entity.MeetingOrderEntity;
import io.nuite.modules.system.entity.MeetingOrderProductEntity;

@Service
public class MeetingOrderExcelServiceImpl implements MeetingOrderExcelService {

    @Autowired
    private MeetingOrderDao meetingOrderDao;
    
    @Autowired
    private BaseUserDao baseUserDao;
    
    @Autowired
    private MeetingOrderCartDetailDao meetingOrderCartDetailDao;
    
    @Autowired
    private MeetingOrderCartDistributeBoxDao meetingOrderCartDistributeBoxDao;

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

	@Autowired
	private GoodsPeriodDao goodsPeriodDao;

	@Autowired
	private GoodsSizeDao goodsSizeDao;

	@Autowired
	private GoodsSXDao goodsSXDao;

	@Autowired
	private GoodsSXOptionDao goodsSXOptionDao;

	@Autowired
	private GoodsShoesDao goodsShoesDao;

	@Autowired
	private ShoesDataDao shoesDataDao;

	@Autowired
	private ShoesInfoDao shoesInfoDao;

	@Autowired
	private ShoesCompanyTypeDao shoesCompanyTypeDao;

	@Autowired
	private BaseCompanyTypeDao baseCompanyTypeDao;
    
    
    /**
     * 根据购物车序号获取购物车详情列表
     */
	@Override
	public List<MeetingShoppingCartDetailEntity> getMeetingShoppingCartDetailListByShoppingCartSeq(Integer meetingShoppingCartSeq) {
		return meetingShoppingCartDetailDao.selectGroupedMeetingShoppingCartDetail(meetingShoppingCartSeq);
	}
	
	
	
    /**
     * 提交订单
     */
	@Override
    @Transactional(propagation = Propagation.REQUIRED)
	public Integer submitMeetingOrder(List<Integer> meetingShoppingCartSeqs, MeetingOrderEntity meetingOrderEntity,
			List<MeetingOrderProductEntity> orderProductList, Date nowDate, BaseUserEntity baseUserEntity) throws Exception {
		
        //1.新增订单
        meetingOrderDao.insert(meetingOrderEntity);
        
        //2.新增订单关联产品
        for (MeetingOrderProductEntity orderProductEntity : orderProductList) {
        	orderProductEntity.setMeetingOrderSeq(meetingOrderEntity.getSeq());
        	Wrapper<GoodsShoesEntity> goodsShoesEntityWrapper = new EntityWrapper<>();
        	goodsShoesEntityWrapper.eq("MeetingGoods_Seq",orderProductEntity.getMeetingGoodsSeq());
        	List<GoodsShoesEntity> goodsShoesEntities = goodsShoesDao.selectList(goodsShoesEntityWrapper);
        	if(goodsShoesEntities.size() == 0) {
        	    transferMeetingGoodsToGoodsShoes(orderProductEntity.getMeetingGoodsSeq(),baseUserEntity);
            }
            meetingOrderProductDao.insert(orderProductEntity);
        }
        
		//3.复制购物车到订单购物车
		copyMeetingShoppingCartToMeetingOrderCart(meetingOrderEntity.getSeq(), meetingShoppingCartSeqs, nowDate);
		
        //4.删除购物车
        if (meetingShoppingCartSeqs.size() > 0) {
    		//1).删除购物车
    		meetingShoppingCartDao.deleteBatchIds(meetingShoppingCartSeqs);
    		//2).删除对应购物车详情
    		Wrapper<MeetingShoppingCartDetailEntity> wrapper1 = new EntityWrapper<MeetingShoppingCartDetailEntity>();
    		wrapper1.in("MeetingShoppingCart_Seq", meetingShoppingCartSeqs);
    		meetingShoppingCartDetailDao.delete(wrapper1);
    		//3).删除购物车配箱详情
    		Wrapper<MeetingShoppingCartDistributeBoxEntity> wrapper2 = new EntityWrapper<MeetingShoppingCartDistributeBoxEntity>();
    		wrapper2.in("MeetingShoppingCart_Seq", meetingShoppingCartSeqs);
    		meetingShoppingCartDistributeBoxDao.delete(wrapper2);
        }
        
        return meetingOrderEntity.getSeq();
	}



	//复制购物车3张表的数据到订单购物车3张表
	private void copyMeetingShoppingCartToMeetingOrderCart(Integer meetingOrderSeq, List<Integer> meetingShoppingCartSeqs, Date nowDate) {
		for(Integer meetingShoppingCartSeq : meetingShoppingCartSeqs) {
			
			//1.复制购物车表
			MeetingShoppingCartEntity meetingShoppingCartEntity = meetingShoppingCartDao.selectById(meetingShoppingCartSeq);
			MeetingOrderCartEntity meetingOrderCartEntity = new MeetingOrderCartEntity();
//			Seq					int	0	0	0	-1	0	0		0	0	0	0	序号(主键)		-1			
//			Meeting_Seq			int	0	0	0	0	0	0		0	0	0	0	订货会序号		0			
//			User_Seq			int	0	0	0	0	0	0		0	0	0	0	用户Seq(外键:YHSR_Base_User表)		0			
//			MeetingGoods_Seq	int	0	0	0	0	0	0		0	0	0	0	订货会鞋子序号		0			
//			TotalSelectNum		int	0	0	-1	0	0	0		0	0	0	0	总选款数量		0			
//			PerBoxNum			int	0	0	-1	0	0	0		0	0	0	0	每箱数量（配件）		0			
//			IsAllocated			int	0	0	0	0	0	0	((0))	0	0	0	0	是否已配码0：否 1：是		0			
//			IsChecked			int	0	0	0	0	0	0	((1))	0	0	0	0	是否勾选，0不勾选 1勾选		0			
//			InputTime			datetime	0	0	-1	0	0	0		0	0	0	0	插入时间		0			
//			Del					int	0	0	0	0	0	0	((0))	0	0	0	0	删除标识(0:未删除,1:已删除)		0			
//			MeetingGoodsID		varchar	50	0	0	0	0	0		0	0	0	0	货号	Chinese_PRC_CI_AS	0			
//			UserGoodID			varchar	50	0	-1	0	0	0		0	0	0	0	用户贴牌货号	Chinese_PRC_CI_AS	0			
//			MeetingOrderSeq		int	0	0	-1	0	0	0		0	0	0	0	订单Seq		0			
			meetingOrderCartEntity.setMeetingSeq(meetingShoppingCartEntity.getMeetingSeq());
			meetingOrderCartEntity.setUserSeq(meetingShoppingCartEntity.getUserSeq());
			meetingOrderCartEntity.setMeetingGoodsSeq(meetingShoppingCartEntity.getMeetingGoodsSeq());
			meetingOrderCartEntity.setTotalSelectNum(meetingShoppingCartEntity.getTotalSelectNum());
			meetingOrderCartEntity.setPerBoxNum(meetingShoppingCartEntity.getPerBoxNum());
			meetingOrderCartEntity.setIsAllocated(meetingShoppingCartEntity.getIsAllocated());
			meetingOrderCartEntity.setIsChecked(meetingShoppingCartEntity.getIsChecked());
			meetingOrderCartEntity.setInputTime(nowDate);
			meetingOrderCartEntity.setDel(0);
			meetingOrderCartEntity.setMeetingGoodsID(meetingShoppingCartEntity.getMeetingGoodsID());
			meetingOrderCartEntity.setUserGoodID(meetingShoppingCartEntity.getUserGoodID());
			meetingOrderCartEntity.setMeetingOrderSeq(meetingOrderSeq);
			meetingOrderCartDao.insert(meetingOrderCartEntity);
			
			
			//2.复制购物车配箱表
			Wrapper<MeetingShoppingCartDistributeBoxEntity> wrapper = new EntityWrapper<MeetingShoppingCartDistributeBoxEntity>();
			wrapper.where("MeetingShoppingCart_Seq = {0}", meetingShoppingCartSeq);
			List<MeetingShoppingCartDistributeBoxEntity> meetingShoppingCartDistributeBoxList = meetingShoppingCartDistributeBoxDao.selectList(wrapper);
			for(MeetingShoppingCartDistributeBoxEntity meetingShoppingCartDistributeBoxEntity : meetingShoppingCartDistributeBoxList) {
				MeetingOrderCartDistributeBoxEntity meetingOrderCartDistributeBoxEntity = new MeetingOrderCartDistributeBoxEntity();
//				Seq						int	0	0	0	-1	0	0		0	0	0	0	序号(主键)		-1			
//				MeetingOrderCart_Seq	int	0	0	0	0	0	0		0	0	0	0	购物车序号		0			
//				MeetingGoods_Seq		int	0	0	0	0	0	0		0	0	0	0	订货会鞋子序号(冗余字段)		0			
//				Color_Seq				int	0	0	0	0	0	0		0	0	0	0	颜色seq		0			
//				PerBoxNum				int	0	0	0	0	0	0		0	0	0	0	每箱数量（配件） （冗余字段）		0			
//				BoxCount				int	0	0	-1	0	0	0		0	0	0	0	箱数（件数）		0			
//				ColorTotalNum			int	0	0	0	0	0	0		0	0	0	0	本颜色总数量		0			
//				InputTime				datetime	0	0	-1	0	0	0		0	0	0	0	插入时间		0			
//				AllocatedType			int	0	0	0	0	0	0		0	0	0	0	配码类型 1：代码 2：具体数值		0			
				meetingOrderCartDistributeBoxEntity.setMeetingOrderCartSeq(meetingOrderCartEntity.getSeq());
				meetingOrderCartDistributeBoxEntity.setMeetingGoodsSeq(meetingShoppingCartDistributeBoxEntity.getMeetingGoodsSeq());
				meetingOrderCartDistributeBoxEntity.setColorSeq(meetingShoppingCartDistributeBoxEntity.getColorSeq());
				meetingOrderCartDistributeBoxEntity.setPerBoxNum(meetingShoppingCartDistributeBoxEntity.getPerBoxNum());
				meetingOrderCartDistributeBoxEntity.setBoxCount(meetingShoppingCartDistributeBoxEntity.getBoxCount());
				meetingOrderCartDistributeBoxEntity.setColorTotalNum(meetingShoppingCartDistributeBoxEntity.getColorTotalNum());
				meetingOrderCartDistributeBoxEntity.setInputTime(nowDate);
				meetingOrderCartDistributeBoxEntity.setAllocatedType(meetingShoppingCartDistributeBoxEntity.getAllocatedType());
				meetingOrderCartDistributeBoxDao.insert(meetingOrderCartDistributeBoxEntity);
				
				
				//3.复制购物车配码详情表
				Wrapper<MeetingShoppingCartDetailEntity> wrapper2 = new EntityWrapper<MeetingShoppingCartDetailEntity>();
				wrapper2.where("MeetingShoppingCartDistributeBox_Seq = {0}", meetingShoppingCartDistributeBoxEntity.getSeq());
				List<MeetingShoppingCartDetailEntity> meetingShoppingCartDetailList = meetingShoppingCartDetailDao.selectList(wrapper2);
				for(MeetingShoppingCartDetailEntity meetingShoppingCartDetailEntity : meetingShoppingCartDetailList) {
					MeetingOrderCartDetailEntity meetingOrderCartDetailEntity = new MeetingOrderCartDetailEntity();
//					Seq						int	0	0	0	-1	0	0		0	0	0	0	序号(主键)		-1			
//					MeetingOrderCart_Seq	int	0	0	0	0	0	0		0	0	0	0	购物车序号		0			
//					MeetingGoods_Seq		int	0	0	0	0	0	0		0	0	0	0	订货会鞋子序号(冗余字段)		0			
//					Color_Seq				int	0	0	0	0	0	0		0	0	0	0	颜色seq		0			
//					Size					int	0	0	0	0	0	0		0	0	0	0	尺码		0			
//					SelectNum				int	0	0	-1	0	0	0		0	0	0	0	选款数量		0			
//					InputTime				datetime	0	0	-1	0	0	0		0	0	0	0	插入时间		0			
//					MeetingOrderCartDistributeBox_Seq	int	0	0	0	0	0	0		0	0	0	0	购物车配箱序号		0			
					meetingOrderCartDetailEntity.setMeetingOrderCartSeq(meetingOrderCartEntity.getSeq());
					meetingOrderCartDetailEntity.setMeetingGoodsSeq(meetingShoppingCartDetailEntity.getMeetingGoodsSeq());
					meetingOrderCartDetailEntity.setColorSeq(meetingShoppingCartDetailEntity.getColorSeq());
					meetingOrderCartDetailEntity.setSize(meetingShoppingCartDetailEntity.getSize());
					meetingOrderCartDetailEntity.setSelectNum(meetingShoppingCartDetailEntity.getSelectNum());
					meetingOrderCartDetailEntity.setInputTime(nowDate);
					meetingOrderCartDetailEntity.setMeetingOrderCartDistributeBoxSeq(meetingOrderCartDistributeBoxEntity.getSeq());
					meetingOrderCartDetailDao.insert(meetingOrderCartDetailEntity);
				}
			}
		}
	}



	/**
	 * 公司订货会订单列表
	 */
	@Override
	public List<MeetingOrderEntity> getCompanyMeetingOrderList(Integer meetingSeq, Integer start, Integer num) {
		Wrapper<MeetingOrderEntity> wrapper = new EntityWrapper<MeetingOrderEntity>();
		wrapper.where("Meeting_Seq = {0}", meetingSeq).orderBy("Seq DESC");
		return meetingOrderDao.selectPage(new RowBounds(start - 1, num), wrapper);
	}



	/**
	 * 用户订货会订单列表
	 */
	@Override
	public List<MeetingOrderEntity> getUserMeetingOrderList(Integer userSeq, Integer meetingSeq, Integer start, Integer num) {
		Wrapper<MeetingOrderEntity> wrapper = new EntityWrapper<MeetingOrderEntity>();
		wrapper.where("Meeting_Seq = {0} AND User_Seq = {1}", meetingSeq, userSeq).orderBy("Seq DESC");
		return meetingOrderDao.selectPage(new RowBounds(start - 1, num), wrapper);
	}



	/**
	 * 根据订单序号获取订单详细货品
	 */
	@Override
	public List<MeetingOrderProductEntity> getMeetingOrderProductListByMeetingOrderSeq(Integer meetingOrderSeq) {
		Wrapper<MeetingOrderProductEntity> wrapper = new EntityWrapper<MeetingOrderProductEntity>();
		wrapper.where("MeetingOrder_Seq = {0}", meetingOrderSeq);
		return meetingOrderProductDao.selectList(wrapper);
	}



	/**
	 * 根据seq获取订单Map
	 */
	@Override
	public Map<String, Object> getMeetingOrderMapBySeq(Integer meetingOrderSeq) {
        Wrapper<MeetingOrderEntity> wrapper = new EntityWrapper<MeetingOrderEntity>();
        wrapper.setSqlSelect("Seq AS orderSeq, Company_Seq AS companySeq, Meeting_Seq AS meetingSeq, User_Seq AS userSeq, OrderNum AS orderNum,"
                + "ReceiverName AS receiverName, Telephone AS telephone, Address AS address, InputTime AS inputTime,ExpressName AS expressName,ExpressPhone AS expressPhone");
        wrapper.where("Seq = {0}", meetingOrderSeq);
        return meetingOrderDao.selectMaps(wrapper).get(0);
    
	}



	/**
	 * 查询订单中所有商品的seq
	 */
	@Override
	public List<Object> getMeetingOrderGoodsSeqList(Integer meetingOrderSeq) {
		Wrapper<MeetingOrderProductEntity> wrapper = new EntityWrapper<MeetingOrderProductEntity>();
		wrapper.setSqlSelect("DISTINCT MeetingGoods_Seq").where("MeetingOrder_Seq = {0}", meetingOrderSeq);
		return meetingOrderProductDao.selectObjs(wrapper);
	}



	/**
	 * 查询订单中某一商品的所有颜色、取消状态
	 */
	@Override
	public List<Map<String, Object>> getMeetingOrderGoodsColorDetailList(Integer meetingOrderSeq, Integer meetingGoodsSeq) {
		Wrapper<MeetingOrderProductEntity> wrapper = new EntityWrapper<MeetingOrderProductEntity>();
		wrapper.setSqlSelect("DISTINCT Color_Seq AS colorSeq, Cancel AS isCancelled").where("MeetingOrder_Seq = {0} AND MeetingGoods_Seq = {1}", meetingOrderSeq, meetingGoodsSeq);
		return meetingOrderProductDao.selectMaps(wrapper);
	}



	/**
	 * 查询订单中某一商品的某一颜色的尺码、购买量
	 */
	@Override
	public List<Map<String, Object>> getMeetingOrderGoodsColorSizeNumDetailList(Integer meetingOrderSeq, Integer meetingGoodsSeq,
			Integer colorSeq) {
		Wrapper<MeetingOrderProductEntity> wrapper = new EntityWrapper<MeetingOrderProductEntity>();
		wrapper.setSqlSelect("Size AS size, BuyCount AS buyCount")
		.where("MeetingOrder_Seq = {0} AND MeetingGoods_Seq = {1} AND Color_Seq = {2}", meetingOrderSeq, meetingGoodsSeq, colorSeq);
		return meetingOrderProductDao.selectMaps(wrapper);
	}



	/**
	 * 查询用户信息Map
	 */
	@Override
	public Map<String, Object> getUserMapByUserSeq(Integer userSeq) {
        Wrapper<BaseUserEntity> wrapper = new EntityWrapper<BaseUserEntity>();
        wrapper.setSqlSelect("Seq AS userSeq, UserName AS userName, Telephone AS telephone");
        wrapper.where("Seq = {0}", userSeq);
        return baseUserDao.selectMaps(wrapper).get(0);
	}


	@Override
	public List<MeetingOrderEntity> getOrderList(Integer companySeq, Integer userSeq, Integer meetingSeq,Integer type, Page<MeetingOrderEntity> page) throws Exception {
		Map<String,Object> map = new HashMap<>(10);
		map.put("periodName",meetingDao.selectById(meetingSeq).getName());
		map.put("meetingSeq",meetingSeq);
		map.put("userSeq",userSeq);
		map.put("companySeq",companySeq);
		map.put("type",type);
		return meetingOrderDao.getOrderList(map,page);
	}

	private void transferMeetingGoodsToGoodsShoes(Integer meetingGoodsSeq, BaseUserEntity baseUserEntity) throws Exception {
		//根据seq获取订货会货品
		MeetingGoodsEntity meetingGoodsEntity = meetingGoodsDao.selectById(meetingGoodsSeq);
		//订货平台货品
		GoodsShoesEntity goodsShoesEntity = new GoodsShoesEntity();
		//订货平台货品库存
		List<ShoesDataEntity> shoesDataEntityList = new ArrayList<>(10);
		//订货平台货品其他信息
		ShoesInfoEntity shoesInfoEntity = new ShoesInfoEntity();
		//根据seq获取订货会
		MeetingEntity meetingEntity = meetingDao.selectById(meetingGoodsEntity.getMeetingSeq());
		String meetingName = meetingEntity.getName();
		Integer year = meetingEntity.getYear();
		String goodId = meetingGoodsEntity.getGoodID();
		goodsShoesEntity.setCompanySeq(baseUserEntity.getCompanySeq());
		//订货会作为订货平台货品波次
		goodsShoesEntity.setPeriodSeq(getPeriodSeq(year,meetingName,baseUserEntity.getBrandSeq()));
		//订货会货品类别作为订货平台货品类别
		goodsShoesEntity.setCategorySeq(meetingGoodsEntity.getCategorySeq());
		//订货会货品货号作为订货平台货品货号
		goodsShoesEntity.setGoodID(goodId);
		//订货会货品序号作为订货平台货品和订货会货品的关联序号
		goodsShoesEntity.setMeetingGoodsSeq(meetingGoodsSeq);
		//订货会货品颜色作为订货平台货品颜色
		String[] colorSeqs = meetingGoodsEntity.getOptionalColorSeqs().split(",");
		List<String> colorSeqList = new ArrayList<>(10);
		for(String colorSeq : colorSeqs) {
			colorSeqList.add(colorSeq);
		}
		goodsShoesEntity.setColor(getColorNames(colorSeqList,baseUserEntity.getCompanySeq()));
		//复制订货会货品图片到订货平台
		if(meetingGoodsEntity.getImg() != null && !"".equals(meetingGoodsEntity.getImg())) {
			String fileName = copyPicture(meetingGoodsEntity.getImg(),goodId);
			goodsShoesEntity.setImg1(fileName);
		}
		//插入时间
		goodsShoesEntity.setInputTime(new Date());
		//拼接货品的类别作为货品名称
		goodsShoesEntity.setGoodName(getGoodName(meetingGoodsEntity.getCategorySeq()));
		//订货会货品的鞋面鞋里鞋底材质作为订货平台货品属性
		String surfaceMaterial = meetingGoodsEntity.getSurfaceMaterial();
		String innerMaterial = meetingGoodsEntity.getInnerMaterial();
		String soleMaterial = meetingGoodsEntity.getSoleMaterial();
		if(surfaceMaterial != null) {
			String firstSx = getSXCode("鞋面材质",surfaceMaterial,baseUserEntity.getCompanySeq());
			setGoodsSx(firstSx,goodsShoesEntity);
		}
		if(innerMaterial != null) {
			String secondSx = getSXCode("鞋里材质",innerMaterial,baseUserEntity.getCompanySeq());
			setGoodsSx(secondSx,goodsShoesEntity);
		}
		if(soleMaterial != null) {
			String thirdSx = getSXCode("鞋底材质",soleMaterial,baseUserEntity.getCompanySeq());
			setGoodsSx(thirdSx,goodsShoesEntity);
		}
		goodsShoesEntity.setDel(0);
		goodsShoesDao.insert(goodsShoesEntity);
		Integer shoesSeq = goodsShoesEntity.getSeq();
		//订货会货品最小尺码
		Integer minSize = meetingGoodsEntity.getMinSize();
		//订货会货品最大尺码
		Integer maxSize = meetingGoodsEntity.getMaxSize();
		//订货会货品颜色作为订货平台货品库存的货品颜色
		String[] colorSeqArray = colorSeqs;
		//循环每一个颜色和每一个尺码
		for(int i = minSize;i <= maxSize;i++) {
			Integer sizeSeq = getSizeSeq(i,baseUserEntity.getCompanySeq());
			for(String colorSeq : colorSeqArray) {
				ShoesDataEntity shoesDataEntity = new ShoesDataEntity();
				shoesDataEntity.setStock(1);
				shoesDataEntity.setStockDate(new Date());
				shoesDataEntity.setShoesSeq(shoesSeq);
				shoesDataEntity.setSizeSeq(sizeSeq);
				shoesDataEntity.setColorSeq(Integer.parseInt(colorSeq));
				shoesDataEntity.setDel(0);
				shoesDataEntityList.add(shoesDataEntity);
			}
		}
		if(shoesDataEntityList.size() > 0) {
			shoesDataDao.insertShoesData(shoesDataEntityList);
		}
		shoesInfoEntity.setShoesSeq(goodsShoesEntity.getSeq());
		shoesInfoEntity.setIsHotSale(0);
		shoesInfoEntity.setIsNewest(0);
		shoesInfoEntity.setDel(0);
		shoesInfoEntity.setSearchTimes(0);
		//订货会货品的价格作为订货平台货品的贴牌商价格
		shoesInfoEntity.setOemPrice(meetingGoodsEntity.getPrice());
		//订货会货品的价格作为订货平台货品的批发商价格
		shoesInfoEntity.setWholesalerPrice(meetingGoodsEntity.getPrice());
		//订货会货品的价格作为订货平台货品的直营店价格
		shoesInfoEntity.setStorePrice(meetingGoodsEntity.getPrice());
		//订货会货品的价格作为订货平台货品的销售价格
		shoesInfoEntity.setSalePrice(meetingGoodsEntity.getPrice());
		shoesInfoEntity.setOnSale(1);
		Wrapper<BaseCompanyTypeEntity> baseCompanyTypeEntityWrapper = new EntityWrapper<>();
		//公司类型
		List<BaseCompanyTypeEntity> baseCompanyTypeEntities = baseCompanyTypeDao.selectList(baseCompanyTypeEntityWrapper);
		//循环每一个公司类型插入鞋子公司类型
		for(BaseCompanyTypeEntity baseCompanyTypeEntity : baseCompanyTypeEntities) {
			ShoesCompanyTypeEntity shoesCompanyTypeEntity = new ShoesCompanyTypeEntity();
			shoesCompanyTypeEntity.setShoesSeq(goodsShoesEntity.getSeq());
			shoesCompanyTypeEntity.setCompanyTypeSeq(baseCompanyTypeEntity.getSeq());
			shoesCompanyTypeEntity.setDel(0);
			shoesCompanyTypeDao.insert(shoesCompanyTypeEntity);
		}
		shoesInfoDao.insert(shoesInfoEntity);
	}

	/**
	 * 获取波次seq
	 * @param year
	 * @param name
	 * @return
	 */
	private Integer getPeriodSeq(Integer year,String name,Integer brandSeq) {
		Wrapper<GoodsPeriodEntity> wrapper = new EntityWrapper<>();
		wrapper.eq("Year",year);
		wrapper.eq("Name",name);
		wrapper.eq("Brand_Seq",brandSeq);
		List<GoodsPeriodEntity> goodsPeriodEntities = goodsPeriodDao.selectList(wrapper);
		if(goodsPeriodEntities.size() > 0) {
			return goodsPeriodEntities.get(0).getSeq();
		}else {
			GoodsPeriodEntity goodsPeriodEntity = new GoodsPeriodEntity();
			goodsPeriodEntity.setYear(year);
			goodsPeriodEntity.setName(name);
			goodsPeriodEntity.setBrandSeq(brandSeq);
			goodsPeriodDao.insert(goodsPeriodEntity);
			return goodsPeriodEntity.getSeq();
		}
	}

	/**
	 * 拼接颜色
	 * @param colorSeqs
	 * @return
	 */
	private String getColorNames(List<String> colorSeqs,Integer companySeq) {
		Wrapper<GoodsColorEntity> wrapper = new EntityWrapper<>();
		wrapper.in("Seq",colorSeqs);
		List<GoodsColorEntity> goodsColorEntities = goodsColorDao.selectList(wrapper);
		String colorNames = "";
		for(GoodsColorEntity goodsColorEntity : goodsColorEntities) {
			colorNames += goodsColorEntity.getName() + ",";
		}
		colorNames = colorNames.substring(0,colorNames.length() -1);
		return colorNames;
	}

	/**
	 * 拷贝图片
	 * @param fileName
	 * @param goodId
	 * @return
	 * @throws Exception
	 */
	private String copyPicture(String fileName,String goodId) throws Exception {
		//订货会货品图片路径
		String meetingGoodsDir = FileUtils.getWebAppsPath() + configUtils.getPictureBaseUploadProject() + "/" + configUtils.getOrderPlatformApp().getOrderPlatformDir() + "/"  + configUtils.getOrderPlatformApp().getMeetingGoods() +"/" + goodId + "/";
		//订货平台图片路径
		String imageGoodsDir = FileUtils.getWebAppsPath() + configUtils.getPictureBaseUploadProject() + File.separator
				+ configUtils.getBaseDir() + File.separator + configUtils.getGoodsShoes() + File.separator
				+ goodId + File.separator;
		//订货会货品图片文件
		File file = new File(meetingGoodsDir,fileName);
		//订货平台货品图片名
		String newFileName = System.currentTimeMillis() + "_" + goodId + ".jpg";
		//拷贝文件
		FileUtils.copyFile(imageGoodsDir,newFileName,file);
		return newFileName;
	}

	private String getSXCode(String sxName,String sxOptionName,Integer companySeq) {
		Wrapper<GoodsSXEntity> wrapper = new EntityWrapper<>();
		wrapper.eq("Company_Seq",companySeq);
		wrapper.orderBy("SXID",false);
		List<GoodsSXEntity> allGoodsSXList = goodsSXDao.selectList(wrapper);
		wrapper.eq("SXName",sxName);
		List<GoodsSXEntity> goodsSXEntities = goodsSXDao.selectList(wrapper);
		Integer sxSeq = 0;
		String sxId = "";
		if(goodsSXEntities.size() > 0) {
			sxSeq = goodsSXEntities.get(0).getSeq();
			sxId = goodsSXEntities.get(0).getSXId();
		}else {
			if(allGoodsSXList.size() > 0) {
				String lastSxId = allGoodsSXList.get(0).getSXId();
				Integer id = Integer.parseInt(lastSxId.substring(2,lastSxId.length()));
				sxId = "SX" + (id + 1);
			}else {
				sxId = "SX1";
			}
			GoodsSXEntity goodsSXEntity = new GoodsSXEntity();
			goodsSXEntity.setCompanySeq(companySeq);
			goodsSXEntity.setSXName(sxName);
			goodsSXEntity.setSXId(sxId);
			goodsSXDao.insert(goodsSXEntity);
			sxSeq = goodsSXEntity.getSeq();
		}
		Wrapper<GoodsSXOptionEntity> optionWrapper = new EntityWrapper<>();
		optionWrapper.eq("SX_Seq",sxSeq);
		optionWrapper.orderBy("Code",false);
		List<GoodsSXOptionEntity> allGoodsSXOptionList = goodsSXOptionDao.selectList(optionWrapper);
		optionWrapper.eq("Value",sxOptionName);
		List<GoodsSXOptionEntity> goodsSXOptionEntities = goodsSXOptionDao.selectList(optionWrapper);
		String sxOptionCode = "";
		if(goodsSXOptionEntities.size() > 0) {
			sxOptionCode = goodsSXOptionEntities.get(0).getCode();
		}else {
			if(allGoodsSXOptionList.size() > 0) {
				Integer code = Integer.parseInt(allGoodsSXOptionList.get(0).getCode()) + 1;
				sxOptionCode = code + "";
				while(sxOptionCode.length() < 3) {
					sxOptionCode = 0 + sxOptionCode;
				}
			}else {
				sxOptionCode = "001";
			}
			GoodsSXOptionEntity goodsSXOptionEntity = new GoodsSXOptionEntity();
			goodsSXOptionEntity.setCode(sxOptionCode);
			goodsSXOptionEntity.setValue(sxOptionName);
			goodsSXOptionEntity.setSXSeq(sxSeq);
			goodsSXOptionDao.insert(goodsSXOptionEntity);
		}
		return sxId + "," + sxOptionCode;
	}

	private void setGoodsSx(String sxCode,GoodsShoesEntity goodsShoesEntity) {
		String[] sxIdCode = sxCode.split(",");
		String sxId = sxIdCode[0];
		String optionCode = sxIdCode[1];
		if("SX1".equals(sxId)) {
			goodsShoesEntity.setSX1(optionCode);
		}else if("SX2".equals(sxId)) {
			goodsShoesEntity.setSX2(optionCode);
		}else if("SX3".equals(sxId)) {
			goodsShoesEntity.setSX3(optionCode);
		}else if("SX4".equals(sxId)) {
			goodsShoesEntity.setSX4(optionCode);
		}else if("SX5".equals(sxId)) {
			goodsShoesEntity.setSX5(optionCode);
		}else if("SX6".equals(sxId)) {
			goodsShoesEntity.setSX6(optionCode);
		}else if("SX7".equals(sxId)) {
			goodsShoesEntity.setSX7(optionCode);
		}else if("SX8".equals(sxId)) {
			goodsShoesEntity.setSX8(optionCode);
		}else if("SX9".equals(sxId)) {
			goodsShoesEntity.setSX9(optionCode);
		}else if("SX10".equals(sxId)) {
			goodsShoesEntity.setSX10(optionCode);
		}else if("SX11".equals(sxId)) {
			goodsShoesEntity.setSX11(optionCode);
		}else if("SX12".equals(sxId)) {
			goodsShoesEntity.setSX12(optionCode);
		}else if("SX13".equals(sxId)) {
			goodsShoesEntity.setSX13(optionCode);
		}else if("SX14".equals(sxId)) {
			goodsShoesEntity.setSX14(optionCode);
		}else if("SX15".equals(sxId)) {
			goodsShoesEntity.setSX15(optionCode);
		}else if("SX16".equals(sxId)) {
			goodsShoesEntity.setSX16(optionCode);
		}else if("SX17".equals(sxId)) {
			goodsShoesEntity.setSX17(optionCode);
		}else if("SX18".equals(sxId)) {
			goodsShoesEntity.setSX18(optionCode);
		}else if("SX19".equals(sxId)) {
			goodsShoesEntity.setSX19(optionCode);
		}else if("SX20".equals(sxId)) {
			goodsShoesEntity.setSX20(optionCode);
		}
	}

	private Integer getSizeSeq(Integer size,Integer companySeq) {
		Wrapper<GoodsSizeEntity> wrapper = new EntityWrapper<>();
		wrapper.eq("SizeName",size);
		wrapper.eq("Company_Seq",companySeq);
		List<GoodsSizeEntity> goodsSizeEntities = goodsSizeDao.selectList(wrapper);
		if(goodsSizeEntities.size() > 0) {
			return goodsSizeEntities.get(0).getSeq();
		}else {
			GoodsSizeEntity goodsSizeEntity = new GoodsSizeEntity();
			goodsSizeEntity.setSizeName(size.toString());
			goodsSizeEntity.setCompanySeq(companySeq);
			goodsSizeDao.insert(goodsSizeEntity);
			return goodsSizeEntity.getSeq();
		}
	}

	private String getGoodName(Integer categorySeq) {
		String goodName = "";
		List<String> categoryList = new ArrayList<>(10);
		categoryList = getCategory(categorySeq,categoryList);
		for(String category : categoryList) {
			goodName += category + "/";
		}
		if(goodName.length() > 0) {
			goodName = goodName.substring(0,goodName.length() - 1);
		}
		return goodName;
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
}
