package io.nuite.modules.online_sales_app.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.Query;
import io.nuite.modules.online_sales_app.dao.OnlineSalesShoesDataDao;
import io.nuite.modules.online_sales_app.dao.OnlineSalesShoesInfoDao;
import io.nuite.modules.online_sales_app.dao.SalesShoppingCartDao;
import io.nuite.modules.online_sales_app.dao.SalesShoppingCartDetailDao;
import io.nuite.modules.online_sales_app.dao.SalesShoppingCartDistributeBoxDao;
import io.nuite.modules.online_sales_app.entity.CustomerUserInfo;
import io.nuite.modules.online_sales_app.entity.OrderCartEntity;
import io.nuite.modules.online_sales_app.entity.SalesShoppingCartDetailEntity;
import io.nuite.modules.online_sales_app.entity.SalesShoppingCartDistributeBoxEntity;
import io.nuite.modules.online_sales_app.entity.SalesShoppingCartEntity;
import io.nuite.modules.online_sales_app.entity.ShoesDataEntity;
import io.nuite.modules.online_sales_app.service.SalesShoppingCartService;
import io.nuite.modules.sr_base.dao.GoodsColorDao;
import io.nuite.modules.sr_base.dao.GoodsShoesDao;
import io.nuite.modules.sr_base.dao.GoodsSizeDao;
import io.nuite.modules.sr_base.entity.GoodsColorEntity;
import io.nuite.modules.sr_base.entity.GoodsShoesEntity;
import io.nuite.modules.sr_base.entity.GoodsSizeEntity;
import net.sf.json.JSONArray;


@Service
public class SalesShoppingCartServiceImpl extends ServiceImpl<SalesShoppingCartDao, SalesShoppingCartEntity> implements SalesShoppingCartService {

    private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	private SalesShoppingCartDao salesShoppingCartDao;
	
	@Autowired
	private SalesShoppingCartDetailDao salesShoppingCartDetailDao;
	
	@Autowired
	private SalesShoppingCartDistributeBoxDao salesShoppingCartDistributeBoxDao;
	
	@Autowired
	private GoodsShoesDao goodsShoesDao;
	
	@Autowired
	private GoodsSizeDao goodsSizeDao;
	
	@Autowired
	private OnlineSalesShoesDataDao goodsDataDao;

	@Autowired
	private OnlineSalesShoesInfoDao shoesInfoDao;
	
	@Autowired
	private GoodsColorDao goodsColorDao;
	
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SalesShoppingCartEntity> page = this.selectPage(
                new Query<SalesShoppingCartEntity>(params).getPage(),
                new EntityWrapper<SalesShoppingCartEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public void sizeAllot(CustomerUserInfo customerUserInfo,Integer goodSeq,  Integer isChart,String userGoodId, Integer perBoxNum, String colorArray,Integer cartSeq) {
		Date nowDate = new Date();		
		//创建salesShoppingCart
		GoodsShoesEntity goodsShoesEntity=goodsShoesDao.selectById(goodSeq);
		SalesShoppingCartEntity salesShoppingCartEntity=new SalesShoppingCartEntity();
		salesShoppingCartEntity.setGoodsPeriodSeq(goodsShoesEntity.getPeriodSeq());
		salesShoppingCartEntity.setUserSeq(customerUserInfo.getSeq());
		salesShoppingCartEntity.setShoesSeq(goodSeq);
		salesShoppingCartEntity.setPerBoxNum(perBoxNum);
		salesShoppingCartEntity.setIsAllocated(isChart);
		salesShoppingCartEntity.setIsChecked(0);
		salesShoppingCartEntity.setGoodsID(goodsShoesEntity.getGoodID());
		salesShoppingCartEntity.setUserGoodID(userGoodId);
		
		JSONArray colorArrayJson = JSONArray.fromObject(colorArray);
		Integer totalSelectNum=0;
		for (Object object : colorArrayJson) {
			JSONObject colorDetail=JSONObject.parseObject(object.toString());
			totalSelectNum=totalSelectNum+colorDetail.getInteger("colorTotalNum");
		}
		salesShoppingCartEntity.setTotalSelectNum(totalSelectNum);
		salesShoppingCartEntity.setInputTime(nowDate);
		if(cartSeq!=null) {
			salesShoppingCartEntity.setSeq(cartSeq);
			salesShoppingCartDao.updateById(salesShoppingCartEntity);
		}else {
			salesShoppingCartDao.insert(salesShoppingCartEntity);
		}
		
		//创建salesShoppingCartDistributeBox
	
		for (Object object : colorArrayJson) {
			JSONObject colorDetail=JSONObject.parseObject(object.toString());
			Integer boxCount=0;
			
			Integer seq=colorDetail.getInteger("seq");
			Integer colorTotalNum=colorDetail.getInteger("colorTotalNum");
			Integer selectNumTotal=0;
			Integer allocatedType=3;
			com.alibaba.fastjson.JSONArray salesDetails=colorDetail.getJSONArray("goodsSizeArray");
			if(isChart==1) {
				boxCount=colorDetail.getInteger("boxCount");
				for (Object object2 : salesDetails) {
					JSONObject sizeDetail=JSONObject.parseObject(object2.toString());
					Integer selectnum=sizeDetail.getInteger("num");
					selectNumTotal=selectNumTotal+selectnum;
				}
				if(selectNumTotal.equals(colorTotalNum)) {
					allocatedType=2;
				}else {
					allocatedType=1;
				}
			}
			SalesShoppingCartDistributeBoxEntity salesShoppingCartDistributeBoxEntity=salesShoppingCartDistributeBoxDao.getBySalesShoppingCartSeq(salesShoppingCartEntity.getSeq(), seq);
			//根据salesShoppingCartEntity seq 和 colorSeq查询salesShoppingCartDistributeBoxEntity
			if(salesShoppingCartDistributeBoxEntity==null) {
				salesShoppingCartDistributeBoxEntity=new SalesShoppingCartDistributeBoxEntity();
			}
			salesShoppingCartDistributeBoxEntity.setSalesShoppingCartSeq(salesShoppingCartEntity.getSeq());
			salesShoppingCartDistributeBoxEntity.setColorSeq(seq);
			salesShoppingCartDistributeBoxEntity.setBoxCount(boxCount);
			salesShoppingCartDistributeBoxEntity.setColorTotalNum(colorTotalNum);
			salesShoppingCartDistributeBoxEntity.setInputTime(nowDate);
			salesShoppingCartDistributeBoxEntity.setAllocatedType(allocatedType);
			if(salesShoppingCartDistributeBoxEntity.getSeq()==null) {
				salesShoppingCartDistributeBoxDao.insert(salesShoppingCartDistributeBoxEntity);
			}else {
				salesShoppingCartDistributeBoxDao.updateById(salesShoppingCartDistributeBoxEntity);
			}
			
			
			//创建salesShoppingCartDetail
			for (Object object2 : salesDetails) {
				JSONObject sizeDetail=JSONObject.parseObject(object2.toString());
				Integer selectnum=sizeDetail.getInteger("num");
				if(selectnum!=0) {
				Integer size=sizeDetail.getInteger("name");
				//根据尺码等到尺码的seq
				Wrapper<GoodsSizeEntity> wrapper = new EntityWrapper<GoodsSizeEntity>();
				wrapper.where("Company_Seq = {0} AND SizeName={1}",customerUserInfo.getCompanySeq() ,size.toString());
				List<GoodsSizeEntity> goodsSizeEntities=goodsSizeDao.selectList(wrapper);
				GoodsSizeEntity goodsSizeEntity=goodsSizeEntities.get(0);
				//根据鞋子序号，颜色，尺码得到shoeData
				Wrapper<ShoesDataEntity> wrapper2 = new EntityWrapper<ShoesDataEntity>();
				wrapper2.where("Shoes_Seq = {0} AND Size_Seq={1} AND Color_Seq={2}",goodSeq ,goodsSizeEntity.getSeq(),seq);
				List<ShoesDataEntity> shoesDataEntities=goodsDataDao.selectList(wrapper2);
				ShoesDataEntity shoesDataEntity=shoesDataEntities.get(0);
				
				SalesShoppingCartDetailEntity salesShoppingCartDetailEntity=salesShoppingCartDetailDao.getSalesDetailBySalesShoppingCartSeq(salesShoppingCartDistributeBoxEntity.getSeq(), shoesDataEntity.getSeq());
				if(salesShoppingCartDetailEntity==null) {
					salesShoppingCartDetailEntity=new SalesShoppingCartDetailEntity();
				}
				salesShoppingCartDetailEntity.setInputTime(nowDate);
				salesShoppingCartDetailEntity.setSalesShoppingCartDistributeBoxSeq(salesShoppingCartDistributeBoxEntity.getSeq());
				salesShoppingCartDetailEntity.setSelectNum(selectnum);
				salesShoppingCartDetailEntity.setShoeDataSeq(shoesDataEntity.getSeq());
				salesShoppingCartDetailEntity.setSalesShoppingCartSeq(salesShoppingCartEntity.getSeq());
				if(salesShoppingCartDetailEntity.getSeq()==null) {
					salesShoppingCartDetailDao.insert(salesShoppingCartDetailEntity);
				}else {
					salesShoppingCartDetailDao.updateById(salesShoppingCartDetailEntity);
				}
				
				}
			}
			
		}
		
		
	}

	@Override
	public List<Map<String, Object>> getShoppingCartListByUserSeq(Integer userSeq) {
		List<Map<String, Object>> list=salesShoppingCartDao.getShoppingCartListByUserSeq(userSeq);
		for (Map<String, Object> map : list) {
			Integer shoesSeq=(Integer) map.get("shoesSeq");
			Map<String, Object> platformInfo = shoesInfoDao.getPlatformInfoByShoesSeq(shoesSeq);
			map.put("salePrice", platformInfo.get("salePrice"));
		}
		  
		return list;
	}

	@Override
	public void deleteShoppingCart(Integer shoppingCartSeq) {
		salesShoppingCartDao.deleteById(shoppingCartSeq);
	}

	@Override
	public Map<String, Object> getCartDetail(Integer shoppingCartSeq) {
		Map<String, Object> salesShoppingCartMap=new HashMap<String, Object>();
		
		//根据seq查询所有的distributeBox
		EntityWrapper<SalesShoppingCartDistributeBoxEntity> wrapper1=new EntityWrapper<SalesShoppingCartDistributeBoxEntity>();
		wrapper1.where("SalesShoppingCart_Seq = {0}", shoppingCartSeq);
		List<SalesShoppingCartDistributeBoxEntity>  salesCartDistributeBoxEntities=salesShoppingCartDistributeBoxDao.selectList(wrapper1);
		List<Map<String, Object>> salesCartDistributeBoxMapList=new ArrayList<Map<String,Object>>();
		
		for (SalesShoppingCartDistributeBoxEntity salesShoppingCartDistributeBoxEntity : salesCartDistributeBoxEntities) {
			Map<String, Object> salesCartDistributeBoxMap=new HashMap<String, Object>();
			Integer colorSeq=salesShoppingCartDistributeBoxEntity.getColorSeq();
			GoodsColorEntity goodsColorEntity=goodsColorDao.selectById(colorSeq);
			salesCartDistributeBoxMap.put("colorName", goodsColorEntity.getName());
			salesCartDistributeBoxMap.put("salesCartDistributeBoxEntity", salesShoppingCartDistributeBoxEntity);
			Integer salesShoppingCartDistributeBoxSeq=salesShoppingCartDistributeBoxEntity.getSeq();
			EntityWrapper<SalesShoppingCartDetailEntity> wrapper2=new EntityWrapper<SalesShoppingCartDetailEntity>();
			wrapper2.where("SalesShoppingCartDistributeBox_Seq = {0}", salesShoppingCartDistributeBoxSeq);
			List<SalesShoppingCartDetailEntity> salesCartDetailEntities=salesShoppingCartDetailDao.selectList(wrapper2);
			List<Map<String, Object>> salesCartDetailMapList=new ArrayList<Map<String,Object>>();
			for (SalesShoppingCartDetailEntity salesShoppingCartDetailEntity : salesCartDetailEntities) {
				Integer shoeDataSeq=salesShoppingCartDetailEntity.getShoeDataSeq();
				Map<String, Object> salesCartDetailMap=new HashMap<String, Object>();
				ShoesDataEntity shoesDataEntity=goodsDataDao.selectById(shoeDataSeq);
				GoodsSizeEntity goodsSizeEntity=goodsSizeDao.selectById(shoesDataEntity.getSizeSeq());
				salesCartDetailMap.put("size", goodsSizeEntity.getSizeName());
				salesCartDetailMap.put("salesShoppingCartDetailEntity", salesShoppingCartDetailEntity);
				salesCartDetailMapList.add(salesCartDetailMap);
			}
			salesCartDistributeBoxMap.put("salesCartDetailMapList", salesCartDetailMapList);
			salesCartDistributeBoxMapList.add(salesCartDistributeBoxMap);
		}
		salesShoppingCartMap.put("salesCartDistributeBoxMapList", salesCartDistributeBoxMapList);
		
		return salesShoppingCartMap;
	}

	@Override
	public Map<String, Object> getCartList(Integer shoesSeq, Integer userSeq) {
		EntityWrapper<SalesShoppingCartEntity> wrapper=new EntityWrapper<SalesShoppingCartEntity>();
		wrapper.where("User_Seq = {0} AND Shoes_Seq={1}", userSeq,shoesSeq);
		List<SalesShoppingCartEntity> orderList=salesShoppingCartDao.selectList(wrapper);
		if(orderList!=null&&orderList.size()>0) {
			SalesShoppingCartEntity salesShoppingCartEntity=orderList.get(0);
			Map<String, Object> salesShoppingCartMap=new HashMap<String, Object>();
			//根据seq查询所有的distributeBox
			EntityWrapper<SalesShoppingCartDistributeBoxEntity> wrapper1=new EntityWrapper<SalesShoppingCartDistributeBoxEntity>();
			wrapper1.where("SalesShoppingCart_Seq = {0}", salesShoppingCartEntity.getSeq());
			List<SalesShoppingCartDistributeBoxEntity>  salesCartDistributeBoxEntities=salesShoppingCartDistributeBoxDao.selectList(wrapper1);
			List<Map<String, Object>> salesCartDistributeBoxMapList=new ArrayList<Map<String,Object>>();
			
			for (SalesShoppingCartDistributeBoxEntity salesShoppingCartDistributeBoxEntity : salesCartDistributeBoxEntities) {
				Map<String, Object> salesCartDistributeBoxMap=new HashMap<String, Object>();
				Integer colorSeq=salesShoppingCartDistributeBoxEntity.getColorSeq();
				GoodsColorEntity goodsColorEntity=goodsColorDao.selectById(colorSeq);
				salesCartDistributeBoxMap.put("colorName", goodsColorEntity.getName());
				salesCartDistributeBoxMap.put("salesCartDistributeBoxEntity", salesShoppingCartDistributeBoxEntity);
				Integer salesShoppingCartDistributeBoxSeq=salesShoppingCartDistributeBoxEntity.getSeq();
				EntityWrapper<SalesShoppingCartDetailEntity> wrapper2=new EntityWrapper<SalesShoppingCartDetailEntity>();
				wrapper2.where("SalesShoppingCartDistributeBox_Seq = {0}", salesShoppingCartDistributeBoxSeq);
				List<SalesShoppingCartDetailEntity> salesCartDetailEntities=salesShoppingCartDetailDao.selectList(wrapper2);
				List<Map<String, Object>> salesCartDetailMapList=new ArrayList<Map<String,Object>>();
				for (SalesShoppingCartDetailEntity salesShoppingCartDetailEntity : salesCartDetailEntities) {
					Integer shoeDataSeq=salesShoppingCartDetailEntity.getShoeDataSeq();
					Map<String, Object> salesCartDetailMap=new HashMap<String, Object>();
					ShoesDataEntity shoesDataEntity=goodsDataDao.selectById(shoeDataSeq);
					GoodsSizeEntity goodsSizeEntity=goodsSizeDao.selectById(shoesDataEntity.getSizeSeq());
					salesCartDetailMap.put("size", goodsSizeEntity.getSizeName());
					salesCartDetailMap.put("salesShoppingCartDetailEntity", salesShoppingCartDetailEntity);
					salesCartDetailMapList.add(salesCartDetailMap);
				}
				salesCartDistributeBoxMap.put("salesCartDetailMapList", salesCartDetailMapList);
				salesCartDistributeBoxMapList.add(salesCartDistributeBoxMap);
			}
			salesShoppingCartMap.put("salesCartDistributeBoxMapList", salesCartDistributeBoxMapList);
			salesShoppingCartMap.put("salesShoppingCartEntity", salesShoppingCartEntity);
			return salesShoppingCartMap;
		}
		
		return null;
	}

}
