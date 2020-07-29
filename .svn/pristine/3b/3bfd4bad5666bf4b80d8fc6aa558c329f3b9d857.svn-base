package io.nuite.modules.online_sales_app.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.Query;
import io.nuite.modules.online_sales_app.dao.OnlineSalesShoesDataDao;
import io.nuite.modules.online_sales_app.dao.OnlineSalesShoesInfoDao;
import io.nuite.modules.online_sales_app.dao.OrderCartDao;
import io.nuite.modules.online_sales_app.dao.OrderCartDetailDao;
import io.nuite.modules.online_sales_app.dao.OrderCartDistributeBoxDao;
import io.nuite.modules.online_sales_app.entity.OrderCartDetailEntity;
import io.nuite.modules.online_sales_app.entity.OrderCartDistributeBoxEntity;
import io.nuite.modules.online_sales_app.entity.OrderCartEntity;
import io.nuite.modules.online_sales_app.entity.ShoesDataEntity;
import io.nuite.modules.online_sales_app.service.OrderCartService;
import io.nuite.modules.order_platform_app.dao.ShoesInfoDao;
import io.nuite.modules.order_platform_app.service.ShoesDataService;
import io.nuite.modules.sr_base.dao.GoodsCategoryDao;
import io.nuite.modules.sr_base.dao.GoodsColorDao;
import io.nuite.modules.sr_base.dao.GoodsShoesDao;
import io.nuite.modules.sr_base.dao.GoodsSizeDao;
import io.nuite.modules.sr_base.entity.GoodsCategoryEntity;
import io.nuite.modules.sr_base.entity.GoodsColorEntity;
import io.nuite.modules.sr_base.entity.GoodsShoesEntity;
import io.nuite.modules.sr_base.entity.GoodsSizeEntity;
import io.nuite.modules.sr_base.utils.ConfigUtils;


@Service
public class OrderCartServiceImpl extends ServiceImpl<OrderCartDao, OrderCartEntity> implements OrderCartService {
	
	@Autowired
	private OrderCartDao orderCartDao;
	
	@Autowired
	private OrderCartDetailDao orderCartDetailDao;
	
	@Autowired
	private OrderCartDistributeBoxDao orderCartDistributeBoxDao;
	
	@Autowired
	private GoodsShoesDao goodsShoesDao;
	
	@Autowired
	private GoodsSizeDao goodsSizeDao;
	
	@Autowired
	private GoodsColorDao goodsColorDao;
	
	@Autowired
	private OnlineSalesShoesDataDao onlineSalesShoesDataDao;
	
	@Autowired
	private ConfigUtils configUtils;
	
	@Autowired
	private GoodsCategoryDao goodsCategoryDao;
	
	@Autowired
	private OnlineSalesShoesInfoDao shoesInfoDao;
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<OrderCartEntity> page = this.selectPage(
                new Query<OrderCartEntity>(params).getPage(),
                new EntityWrapper<OrderCartEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<Map<String, Object>> selectOrderCart(Integer orderSeq) throws Exception {
        Map<String,Object> map = new HashMap<>(10);
        map.put("orderSeq",orderSeq);
        List<Map<String,Object>> result = baseMapper.selectOrderCart(map);
		jointData(result);
        return result;
    }

    @Override
    public List<Map<String, Object>> selectOrderByGood(Integer companySeq, Integer periodSeq) throws Exception {

        Map<String,Object> map = new HashMap<>(10);
        map.put("companySeq",companySeq);
        map.put("periodSeq",periodSeq);
        List<Map<String,Object>> result = baseMapper.selectOrderByGood(map);
        jointData(result);
        return result;
    }

    @Override
    public List<Map<String, Object>> selectOrderByFactory(Integer companySeq, Integer periodSeq) throws Exception {
        Map<String,Object> map = new HashMap<>(10);
        map.put("companySeq",companySeq);
        map.put("periodSeq",periodSeq);
        List<Map<String,Object>> result = baseMapper.selectFactory(map);
        for(Map<String,Object> factory : result) {
            map.put("factory",factory.get("factoryName"));
            List<Map<String,Object>> factoryDetail = baseMapper.selectOrderByFactory(map);
            jointData(factoryDetail);
            factory.put("factoryDetail",factoryDetail);
        }
        return result;
    }

    private void jointData(List<Map<String,Object>> result) throws Exception {
        Map<String,Object> map = new HashMap<>(10);
        for(Map<String,Object> orderCart : result) {
        	BigDecimal goodTotal = new BigDecimal(orderCart.get("total").toString()).multiply(new BigDecimal(orderCart.get("tagPrice").toString()));
        	orderCart.put("goodTotal", goodTotal);
			orderCart.put("imgSrc",configUtils.getPictureBaseUrl() + "/" + configUtils.getBaseDir() + "/" + configUtils.getGoodsShoes() + "/" + orderCart.get("goodID") + "/" + orderCart.get("imgSrc"));
            map.put("shoesSeq",orderCart.get("shoesSeq"));
            map.put("orderCartSeq",orderCart.get("orderCartSeq"));
            List<Map<String,Object>> detail = baseMapper.selectColor(map);
            List<Integer> title = baseMapper.selectSize(map);
            for(Map<String,Object> color : detail) {
                Map<String,Object> size = new HashMap<>(10);
                map.put("shoesSeq",color.get("goodSeq"));
                List<Map<String,Object>> sizeNum = baseMapper.selectGoodsColorNum(map);
                for(Map<String,Object> num : sizeNum) {
                    size.put((String)num.get("sizeName"),num.get("num"));
                }
                color.put("size",size);
            }
            orderCart.put("details",detail);
            orderCart.put("title",title);
        }
    }

	@Override
	public List<Map<String, Object>> getCartDetail(Integer orderSeq) {
		//根据订单seq查询所有的orderCart
		EntityWrapper<OrderCartEntity> wrapper=new EntityWrapper<OrderCartEntity>();
		wrapper.where("OrderSeq = {0}", orderSeq);
		List<OrderCartEntity> orderCartEntities=orderCartDao.selectList(wrapper);
		List<Map<String, Object>> orderShoppingCartMapList=new ArrayList<Map<String,Object>>();
		
		for (OrderCartEntity orderCartEntity : orderCartEntities) {
			Map<String, Object> orderShoppingCartMap=new HashMap<String, Object>();
			
			Integer orderShoppingCartSeq=orderCartEntity.getSeq();
			orderShoppingCartMap.put("orderCartEntity",orderCartEntity);
			//根据seq查询所有的distributeBox
			EntityWrapper<OrderCartDistributeBoxEntity> wrapper1=new EntityWrapper<OrderCartDistributeBoxEntity>();
			wrapper1.where("OrderShoppingCart_Seq = {0}", orderShoppingCartSeq);
			List<OrderCartDistributeBoxEntity>  orderCartDistributeBoxEntities=orderCartDistributeBoxDao.selectList(wrapper1);
			List<Map<String, Object>> orderCartDistributeBoxMapList=new ArrayList<Map<String,Object>>();
			Integer goodSeq=orderCartEntity.getShoesSeq();
			GoodsShoesEntity goodsShoesEntity=goodsShoesDao.selectById(goodSeq);
			String imageName =goodsShoesEntity.getImg1();
					if(imageName!=null) {
		            String goodsId = goodsShoesEntity.getGoodID();
		            String imgSrc = configUtils.getPictureBaseUrl() + "/" + configUtils.getBaseDir() + "/" + configUtils.getGoodsShoes() + "/" + goodsId + "/" + imageName;
		        	orderShoppingCartMap.put("imgSrc",imgSrc);
			}
				      Integer categorySeq3=goodsShoesEntity.getCategorySeq();
				        
				        GoodsCategoryEntity goodsCategoryEntity3=goodsCategoryDao.selectById(categorySeq3);//三级分类
				        
				        GoodsCategoryEntity goodsCategoryEntity2=goodsCategoryDao.selectById(goodsCategoryEntity3.getParentSeq());//二级分类
				        
				        GoodsCategoryEntity goodCategoryEntity1=goodsCategoryDao.selectById(goodsCategoryEntity2.getParentSeq());//一级分类
				        
				        orderShoppingCartMap.put("categoryName", goodCategoryEntity1.getName()+"-"+goodsCategoryEntity2.getName()+"-"+goodsCategoryEntity3.getName());
				        
				        Map<String, Object> platformInfo = shoesInfoDao.getPlatformInfoByShoesSeq(goodSeq);
				        orderShoppingCartMap.putAll(platformInfo);
				     
			for (OrderCartDistributeBoxEntity orderCartDistributeBoxEntity : orderCartDistributeBoxEntities) {
				Map<String, Object> orderCartDistributeBoxMap=new HashMap<String, Object>();
				Integer colorSeq=orderCartDistributeBoxEntity.getColorSeq();
				
				GoodsColorEntity goodsColorEntity=goodsColorDao.selectById(colorSeq);
				orderCartDistributeBoxMap.put("colorName", goodsColorEntity.getName());
				
				orderCartDistributeBoxMap.put("orderCartDistributeBoxEntity", orderCartDistributeBoxEntity);
				Integer orderShoppingCartDistributeBoxSeq=orderCartDistributeBoxEntity.getSeq();
				EntityWrapper<OrderCartDetailEntity> wrapper2=new EntityWrapper<OrderCartDetailEntity>();
				wrapper2.where("OrderShoppingCartDistributeBox_Seq = {0}", orderShoppingCartDistributeBoxSeq);
				List<OrderCartDetailEntity> orderCartDetailEntities=orderCartDetailDao.selectList(wrapper2);
				List<Map<String, Object>> orderCartDetailMapList=new ArrayList<Map<String,Object>>();
				for (OrderCartDetailEntity orderCartDetailEntity : orderCartDetailEntities) {
					Integer shoeDataSeq=orderCartDetailEntity.getShoeDataSeq();
					Map<String, Object> orderCartDetailMap=new HashMap<String, Object>();
					ShoesDataEntity shoesDataEntity=onlineSalesShoesDataDao.selectById(shoeDataSeq);
					GoodsSizeEntity goodsSizeEntity=goodsSizeDao.selectById(shoesDataEntity.getSizeSeq());
					orderCartDetailMap.put("size", goodsSizeEntity.getSizeName());
					orderCartDetailMap.put("orderCartDetailEntity", orderCartDetailEntity);
					orderCartDetailMapList.add(orderCartDetailMap);
				}
				orderCartDistributeBoxMap.put("orderCartDetailMapList", orderCartDetailMapList);
				orderCartDistributeBoxMapList.add(orderCartDistributeBoxMap);
			}
			orderShoppingCartMap.put("orderCartDistributeBoxMapList", orderCartDistributeBoxMapList);
			orderShoppingCartMapList.add(orderShoppingCartMap);
		}
		
		return orderShoppingCartMapList;
	}

	@Override
	public List<Map<String, Object>> selectOrderByCategory(Integer companySeq, Integer periodSeq,Integer categorySeq) throws Exception {
    	Map<String,Object> map = new HashMap<>(10);
    	map.put("companySeq",companySeq);
    	map.put("periodSeq",periodSeq);
    	map.put("categorySeq",categorySeq);
    	List<Map<String,Object>> result = baseMapper.selectCategory(map);
		for(Map<String,Object> category : result) {
			map.put("categorySeq",category.get("categorySeq"));
			List<Map<String,Object>> categoryDetail = baseMapper.selectOrderByCategory(map);
			jointData(categoryDetail);
			category.put("categoryDetail",categoryDetail);
		}
		return result;
	}


	@Override
	public Integer getCountByShoesSeq(Integer shoesSeq, Integer userSeq) {
		EntityWrapper<OrderCartEntity> wrapper=new EntityWrapper<OrderCartEntity>();
		wrapper.where("User_Seq = {0} AND Shoes_Seq={1}", userSeq,shoesSeq);
		List<OrderCartEntity> orderList=orderCartDao.selectList(wrapper);
		return orderList.size();
	}


	@Override
	public List<OrderCartEntity> selectOrderCartByPeriod(Integer periodSeq) throws Exception {
    	Map<String,Object> map = new HashMap<>(10);
    	map.put("periodSeq",periodSeq);
		return baseMapper.selectOrderCartByPeriod(map);
	}

	@Override
	public String selectMinSizeByPeriod(Integer periodSeq) throws Exception {
    	Map<String,Object> map = new HashMap<>(10);
    	map.put("periodSeq",periodSeq);
		return baseMapper.selectMinSizeByPeriod(map);
	}

	@Override
	public String selectMaxSizeByPeriod(Integer periodSeq) throws Exception {
		Map<String,Object> map = new HashMap<>(10);
		map.put("periodSeq",periodSeq);
		return baseMapper.selectMaxSizeByPeriod(map);
	}

}
