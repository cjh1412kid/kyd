package io.nuite.modules.sr_base.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.nuite.modules.sr_base.dao.GoodsViewDao;
import io.nuite.modules.sr_base.entity.GoodsViewEntity;
import io.nuite.modules.sr_base.service.GoodsViewService;

@Service
public class GoodsViewServiceImpl extends ServiceImpl<GoodsViewDao, GoodsViewEntity> implements GoodsViewService {
	
    @Autowired
    private GoodsViewDao goodsViewDao;
    
    
	/**
	 * 根据seq获取GoodsView对象
	 */
	@Override
	public GoodsViewEntity getGoodsViewBySeq(Integer shoesSeq) {
		return goodsViewDao.selectById(shoesSeq);
	}
	
	
	/**
	 * 根据seq获取GoodsView的Map对象
	 */
	@Override
	public Map<String, Object> getGoodsViewMapBySeq(Integer shoesSeq) {
		Wrapper<GoodsViewEntity> wrapper = new EntityWrapper<GoodsViewEntity>();
		wrapper.where("Seq = {0} ", shoesSeq);
		return goodsViewDao.selectMaps(wrapper).get(0);
	}
}
