package io.nuite.modules.order_platform_app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.nuite.modules.order_platform_app.dao.MeetingShoppingCartDao;
import io.nuite.modules.order_platform_app.dao.MeetingShoppingCartDetailDao;
import io.nuite.modules.order_platform_app.dao.MeetingShoppingCartDistributeBoxDao;
import io.nuite.modules.order_platform_app.entity.MeetingShoppingCartDetailEntity;
import io.nuite.modules.order_platform_app.entity.MeetingShoppingCartDistributeBoxEntity;
import io.nuite.modules.order_platform_app.entity.MeetingShoppingCartEntity;
import io.nuite.modules.order_platform_app.service.MeetingShoppingCartExcelService;
import io.nuite.modules.sr_base.dao.GoodsColorDao;
import io.nuite.modules.sr_base.entity.GoodsColorEntity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service
public class MeetingShoppingCartExcelServiceImpl extends ServiceImpl<MeetingShoppingCartDistributeBoxDao, MeetingShoppingCartDistributeBoxEntity> implements MeetingShoppingCartExcelService {

	@Autowired
	private MeetingShoppingCartDao meetingShoppingCartDao;
	
    @Autowired
    private MeetingShoppingCartDetailDao meetingShoppingCartDetailDao;
    
    @Autowired
    private MeetingShoppingCartDistributeBoxDao meetingShoppingCartDistributeBoxDao;
    
    @Autowired
    private GoodsColorDao goodsColorDao;
    
	
    
	/**
	 * 查询用户是否加入此货号到购物车
	 */
	@Override
	public MeetingShoppingCartEntity getUserMeetingShoppingCart(Integer userSeq, Integer meetingGoodsSeq) {
		MeetingShoppingCartEntity meetingShoppingCartEntity = new MeetingShoppingCartEntity();
		meetingShoppingCartEntity.setUserSeq(userSeq);
		meetingShoppingCartEntity.setMeetingGoodsSeq(meetingGoodsSeq);
		return meetingShoppingCartDao.selectOne(meetingShoppingCartEntity);
	}
	
	
	
	
	/**
	 * 查询货号所有用户已加入购物车总量
	 */
	@Override
	public List<Map<String, Object>> getAllUsersSelectNum(Integer meetingGoodsSeq) {
		Wrapper<MeetingShoppingCartDistributeBoxEntity> wrapper = new EntityWrapper<MeetingShoppingCartDistributeBoxEntity>();
		wrapper.setSqlSelect("Color_Seq AS colorSeq, SUM (ColorTotalNum) AS num").where("MeetingGoods_Seq = {0}", meetingGoodsSeq).groupBy("Color_Seq");
		return meetingShoppingCartDistributeBoxDao.selectMaps(wrapper);
	}
	
	

	
	/**
	 * 根据购物车序号获取配箱详情列表
	 */
	@Override
	public List<MeetingShoppingCartDistributeBoxEntity> getMeetingShoppingCartDistributeBoxListByShoppingCartSeq(Integer meetingShoppingCartSeq) {
		Wrapper<MeetingShoppingCartDistributeBoxEntity> wrapper = new EntityWrapper<MeetingShoppingCartDistributeBoxEntity>();
		wrapper.where("MeetingShoppingCart_Seq = {0}", meetingShoppingCartSeq);
		return meetingShoppingCartDistributeBoxDao.selectList(wrapper);
	}




	/**
	 * 配码
	 */
	@Override
    @Transactional(propagation = Propagation.REQUIRED)
	public void sizeAllot(Integer meetingShoppingCartSeq, String userGoodId, Integer perBoxNum, String sizeAllotDetail) {
    	Date nowDate = new Date();
        MeetingShoppingCartEntity meetingShoppingCartEntity = meetingShoppingCartDao.selectById(meetingShoppingCartSeq);
    	
		//1.删除可能已存在的配箱、配码数据
		Wrapper<MeetingShoppingCartDistributeBoxEntity> wrapper1 = new EntityWrapper<MeetingShoppingCartDistributeBoxEntity>();
		wrapper1.where("MeetingShoppingCart_Seq = {0}", meetingShoppingCartSeq);
		meetingShoppingCartDistributeBoxDao.delete(wrapper1);
		
		Wrapper<MeetingShoppingCartDetailEntity> wrapper2 = new EntityWrapper<MeetingShoppingCartDetailEntity>();
		wrapper2.where("MeetingShoppingCart_Seq = {0}", meetingShoppingCartSeq);
		meetingShoppingCartDetailDao.delete(wrapper2);
		
		
		//2.解析配码详情，验证配码数量是否有误,同时插入配箱、配码数据
		//[{colorSeq:1, colorNum:100, boxCount:10, allocatedType:1, sizeAndNum:[{size:35,num:2},{size:36,num:3}...]}, {}]
        JSONArray sizeAllotDetailArray = JSONArray.fromObject(sizeAllotDetail);
        int totalSelectNum = 0;
        for (int i = 0; i < sizeAllotDetailArray.size(); i++) {
            //{colorSeq:1, colorNum:100, boxCount:10, allocatedType:1, sizeAndNum:[{size:35,num:2},{size:36,num:3}...]}
        	JSONObject colorAllotObject = sizeAllotDetailArray.getJSONObject(i);
            int colorSeq = colorAllotObject.getInt("colorSeq");
            int colorNum = colorAllotObject.getInt("colorNum");
            totalSelectNum += colorNum;
            Integer boxCount = colorAllotObject.getInt("boxCount");
            int allocatedType = colorAllotObject.getInt("allocatedType");
            
            MeetingShoppingCartDistributeBoxEntity meetingShoppingCartDistributeBoxEntity = new MeetingShoppingCartDistributeBoxEntity();
//          Seq						int	0	0	0	-1	0	0		0	0	0	0	序号(主键)		-1			
//          MeetingShoppingCart_Seq	int	0	0	0	0	0	0		0	0	0	0	购物车序号		0			
//          MeetingGoods_Seq		int	0	0	0	0	0	0		0	0	0	0	订货会鞋子序号(冗余字段)		0			
//          Color_Seq				int	0	0	0	0	0	0		0	0	0	0	颜色seq		0			
//          PerBoxNum				int	0	0	0	0	0	0		0	0	0	0	每箱数量（配件） （冗余字段）		0			
//          BoxCount				int	0	0	-1	0	0	0		0	0	0	0	箱数（件数）		0			
//          ColorTotalNum			int	0	0	0	0	0	0		0	0	0	0	本颜色总数量		0			
//          InputTime				datetime	0	0	-1	0	0	0		0	0	0	0	插入时间		0			
//          AllocatedType			int	0	0	0	0	0	0		0	0	0	0	配码类型 1：代码 2：具体数值		0			
            meetingShoppingCartDistributeBoxEntity.setMeetingShoppingCartSeq(meetingShoppingCartSeq);
            meetingShoppingCartDistributeBoxEntity.setMeetingGoodsSeq(meetingShoppingCartEntity.getMeetingGoodsSeq());
            meetingShoppingCartDistributeBoxEntity.setColorSeq(colorSeq);
            meetingShoppingCartDistributeBoxEntity.setPerBoxNum(perBoxNum);
            meetingShoppingCartDistributeBoxEntity.setBoxCount(boxCount);
            meetingShoppingCartDistributeBoxEntity.setColorTotalNum(colorNum);
            meetingShoppingCartDistributeBoxEntity.setInputTime(nowDate);
            meetingShoppingCartDistributeBoxEntity.setAllocatedType(allocatedType);
            meetingShoppingCartDistributeBoxDao.insert(meetingShoppingCartDistributeBoxEntity);
            
            
            //[{size:35,num:2},{size:36,num:3}...]
            JSONArray sizeAndNumArray = colorAllotObject.getJSONArray("sizeAndNum");
            int colorTotalNum = 0;
            String errorMessage = "";
            for(int j = 0; j < sizeAndNumArray.size(); j++) {
            	//{size:35,num:2}
                JSONObject sizeAndNumObject = sizeAndNumArray.getJSONObject(j);
                Integer size = sizeAndNumObject.getInt("size");
                Integer num = sizeAndNumObject.getInt("num");
                colorTotalNum += num;
                
                MeetingShoppingCartDetailEntity meetingShoppingCartDetailEntity = new MeetingShoppingCartDetailEntity();
//              Seq						int	0	0	0	-1	0	0		0	0	0	0	序号(主键)		-1			
//              MeetingShoppingCart_Seq	int	0	0	0	0	0	0		0	0	0	0	购物车序号		0			
//              MeetingGoods_Seq		int	0	0	0	0	0	0		0	0	0	0	订货会鞋子序号(冗余字段)		0			
//              Color_Seq				int	0	0	0	0	0	0		0	0	0	0	颜色seq		0			
//              Size					int	0	0	0	0	0	0		0	0	0	0	尺码		0			
//              SelectNum				int	0	0	-1	0	0	0		0	0	0	0	选款数量		0			
//              InputTime				datetime	0	0	-1	0	0	0		0	0	0	0	插入时间		0			
//              MeetingShoppingCartDistributeBox_Seq	int	0	0	0	0	0	0		0	0	0	0	购物车配箱序号		0			
                meetingShoppingCartDetailEntity.setMeetingShoppingCartSeq(meetingShoppingCartSeq);
                meetingShoppingCartDetailEntity.setMeetingGoodsSeq(meetingShoppingCartEntity.getMeetingGoodsSeq());
                meetingShoppingCartDetailEntity.setColorSeq(colorSeq);
                meetingShoppingCartDetailEntity.setSize(size);
                meetingShoppingCartDetailEntity.setSelectNum(num);
                meetingShoppingCartDetailEntity.setInputTime(nowDate);
                meetingShoppingCartDetailEntity.setMeetingShoppingCartDistributeBoxSeq(meetingShoppingCartDistributeBoxEntity.getSeq());
                meetingShoppingCartDetailDao.insert(meetingShoppingCartDetailEntity);
                
                errorMessage = errorMessage + num + " ";
            }
			if(perBoxNum != null && perBoxNum != 0) {
				if(colorNum%perBoxNum!= 0) {
					throw new RuntimeException("数量" + colorNum + "错误，不是配件数量的整数倍");
				}
				boxCount = colorNum/perBoxNum;
			}else {
				boxCount = 0;
			}
            meetingShoppingCartDistributeBoxEntity.setBoxCount(boxCount);
            
            if(colorTotalNum == perBoxNum) { //按代码比例配码
            	allocatedType = 1;
            } else if(colorTotalNum == colorNum) { //自由配码
            	allocatedType = 2;
            } else {  //数量有误
        		GoodsColorEntity goodsColorEntity = goodsColorDao.selectById(colorSeq);
        		throw new RuntimeException(goodsColorEntity.getName() + errorMessage+ "配码数有误，总和与配件数量和数量都不一致");
            }
            meetingShoppingCartDistributeBoxEntity.setAllocatedType(allocatedType);
            meetingShoppingCartDistributeBoxDao.updateAllColumnById(meetingShoppingCartDistributeBoxEntity);
        }

    	
    	//3.将购物车表，数量，箱数，配码状态，用户货号
        meetingShoppingCartEntity.setTotalSelectNum(totalSelectNum);
        meetingShoppingCartEntity.setPerBoxNum(perBoxNum);
		meetingShoppingCartEntity.setIsAllocated(1);
		meetingShoppingCartEntity.setUserGoodID(userGoodId);
		meetingShoppingCartDao.updateById(meetingShoppingCartEntity);
    	
	}




	/**
	 * 查询用户购物车中某一配箱颜色某一尺码的已选数量
	 */
	@Override
	public Integer getUserShoppingCartSelectNum(Integer distributeBoxSeq, Integer size) {
		MeetingShoppingCartDetailEntity meetingShoppingCartDetailEntity = new MeetingShoppingCartDetailEntity();
		meetingShoppingCartDetailEntity.setMeetingShoppingCartDistributeBoxSeq(distributeBoxSeq);
		meetingShoppingCartDetailEntity.setSize(size);
		meetingShoppingCartDetailEntity = meetingShoppingCartDetailDao.selectOne(meetingShoppingCartDetailEntity);
		if(meetingShoppingCartDetailEntity == null) {
			return null;
		} else {
			return meetingShoppingCartDetailEntity.getSelectNum();
		}
	}

}
