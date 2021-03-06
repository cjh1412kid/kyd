package io.nuite.modules.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.nuite.modules.order_platform_app.entity.MeetingGoodsValuateEntity;
import io.nuite.modules.order_platform_app.entity.MeetingOrderCartDetailEntity;
import io.nuite.modules.order_platform_app.entity.MeetingOrderCartDistributeBoxEntity;
import io.nuite.modules.order_platform_app.service.MeetingGoodsService;
import io.nuite.modules.order_platform_app.service.MeetingGoodsValuateService;
import io.nuite.modules.sr_base.utils.ConfigUtils;
import io.nuite.modules.system.dao.MeetingOrderDao;
import io.nuite.modules.system.dao.MeetingOrderProductDao;
import io.nuite.modules.system.entity.MeetingOrderEntity;
import io.nuite.modules.system.entity.MeetingOrderProductEntity;
import io.nuite.modules.system.model.MeetingOrderProductDO;
import io.nuite.modules.system.service.SysMeetingOrderProductService;
import io.nuite.modules.system.service.SysMeetingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SysMeetingOrderServiceImpl extends ServiceImpl<MeetingOrderDao, MeetingOrderEntity> implements SysMeetingOrderService {
    
    @Autowired
    MeetingOrderDao meetingOrderDao;
    
    @Autowired
    SysMeetingOrderProductService sysMeetingOrderProductService;
    
    @Autowired
    MeetingOrderProductDao meetingOrderProductDao;
    
    @Autowired
    MeetingGoodsService meetingGoodsService;
    
    @Autowired
    ConfigUtils configUtils;
    
    @Autowired
    MeetingGoodsValuateService meetingGoodsValuateService;
    
    /**
     * 获取订货会货品图片访问路径前缀,后拼接： 货号/图片名称
     *
     * @return
     */
    private String getMeetingImageSrcPrefix() {
        
        return configUtils.getPictureBaseUrl() + "/"
            + configUtils.getOrderPlatformApp().getOrderPlatformDir() +
            "/" + configUtils.getOrderPlatformApp().getMeetingGoods() + "/";
    }
    
    @Override
    public Page queryPageByCompanySeq(Page page, Integer companySeq, Integer year, Integer meetingSeq, Integer meetingUserSeq,String sortColumn,String sortType) {
        
        List<MeetingOrderEntity> meetingOrderEntities = meetingOrderDao.selectPageByCompanySeq(page, companySeq, year, meetingSeq, meetingUserSeq,sortColumn,sortType);
        
        for (MeetingOrderEntity orderEntity : meetingOrderEntities) {
            //计算每个订单包含货品的订货总量
            /*Integer total = meetingOrderProductDao.computedSumByMeetingOrderSeq(orderEntity.getSeq());
            orderEntity.setTotal(total);*/
            
            //设置备注信息
            List<MeetingOrderProductEntity> productEntities = meetingOrderProductDao.selectProductInfoByMeetingOrderSeq(orderEntity.getSeq(),"%''%");
            if (productEntities == null || productEntities.size() == 0) {
                orderEntity.setMessage("没有关联货品");
                continue;
            }
            StringBuilder msg = new StringBuilder();
            int count = 0;
            for (MeetingOrderProductEntity productEntity : productEntities) {
                if (productEntity.getCancel() == 1) {
                    count++;
                    
                    msg.append(productEntity.getColorName() + "/")
                        .append(productEntity.getSize() + "/")
                        .append(productEntity.getBuyCount() + "双 ");
                }
            }
            
            if (count == productEntities.size()) {
                orderEntity.setMessage("全部货品取消");
            } else if (count > 0) {
                orderEntity.setMessage("部分货品取消：" + msg.toString());
            }
            
        }
        
        page.setRecords(meetingOrderEntities);
        return page;
    }
    
    @Override
    public List<MeetingOrderEntity> queryOrderByMeetingSeq(Integer meetingSeq, Integer meetingUserSeq) {
        return meetingOrderDao.selectOrderByMeetingSeq(meetingSeq, meetingUserSeq);
    }
    
    @Override
    public List<Map<String, Object>> queryProductInfoByMeetingOrderSeq(Integer meetingOrderSeq,String keywords) {
        
    	MeetingOrderEntity meetingOrderEntity=meetingOrderDao.selectById(meetingOrderSeq);
    	
    	keywords = "%" + keywords + "%";
        List<MeetingOrderProductEntity> orderProductEntities = meetingOrderProductDao.selectProductInfoByMeetingOrderSeq(meetingOrderSeq,keywords);
        
        //key为goodID+color
        Map<String, MeetingOrderProductDO> goodIDAndColorMap = new HashMap<>();
        
        for (MeetingOrderProductEntity productEntity : orderProductEntities) {
            
            if (goodIDAndColorMap.containsKey(productEntity.getGoodID() + productEntity.getColorName())) {
                MeetingOrderProductDO productForm = goodIDAndColorMap.get(productEntity.getGoodID() + productEntity.getColorName());
                Map<Integer, Integer> size = productForm.getSize();
                Integer prevCount = size.get(productEntity.getSize());
                if (prevCount == null) {
                    prevCount = 0;
                }
                size.put(productEntity.getSize(), prevCount + productEntity.getBuyCount());
                
            } else {
                MeetingOrderProductDO form = new MeetingOrderProductDO();
                form.setGoodID(productEntity.getGoodID());
                form.setPicName(productEntity.getPicName());
                form.setCancel(productEntity.getCancel());
                form.setColor(productEntity.getColorName());
                form.setMeetingGoodSeq(productEntity.getMeetingGoodsSeq());
                form.setColorSeq(productEntity.getColorSeq());
                
                //获取最大码和最小码之间的所有码
                List<Integer> minAndMaxSize = new ArrayList<>();
                Map<Integer, Integer> sizeAndCountMap = new HashMap<>();
                for (int i = productEntity.getMinSize(); i <= productEntity.getMaxSize(); i++) {
                    minAndMaxSize.add(i);
                }
                form.setTitles(minAndMaxSize);
                
                sizeAndCountMap.put(productEntity.getSize(), productEntity.getBuyCount());
                form.setSize(sizeAndCountMap);
                
                goodIDAndColorMap.put(productEntity.getGoodID() + productEntity.getColorName(), form);
            }
        }
        
                /*        *
        {
        goodID: 'A1111',
        color: '米白'
        size:{34: 12, 35: 13, 36: 14},
       cancel: true
       title:[34,35,36],
       meetingGoodSeq:1,
       colorSeq:1
      }
      
      
      [
      {
        goodID: 'A1111',
        details: [
          {color: '米白', size: {34: 12, 35: 13, 36: 14}, cancel: true},
          {color: '黑色', size: {34: 18, 35: 17, 36: 14}, cancel: false}
        ],
        title: [34, 35, 36],
        total:100
      },
     
    ]
      */
        String imageSrcPrefix = getMeetingImageSrcPrefix();
        
        //重构数据
        Map<String, List<Map<String, Object>>> goodIDAndDetailsMap = new HashMap<>();
        Map<String, List<Integer>> goodIDAndTitlesMap = new HashMap<>();
        Map<String, String> goodIDAndPicMap = new HashMap<>();
        Map<String, Integer> goodIDAndTotalMap = new HashMap<>();
        
        for (Map.Entry<String, MeetingOrderProductDO> entry : goodIDAndColorMap.entrySet()) {
            MeetingOrderProductDO value = entry.getValue();
            String goodID = value.getGoodID();
            
            List<Map<String, Object>> details;
            if (goodIDAndDetailsMap.containsKey(goodID)) {
                details = goodIDAndDetailsMap.get(goodID);
                Map<String, Object> detail = new HashMap<>();
                detail.put("color", value.getColor());
                detail.put("size", value.getSize());
                detail.put("cancel", value.getCancel());
                detail.put("goodSeq", value.getMeetingGoodSeq());
                detail.put("colorSeq", value.getColorSeq());
                detail.put("cancel", value.getCancel());
                Map<String, Object> params=new HashMap<String, Object>();
                params.put("MeetingGoods_Seq", value.getMeetingGoodSeq());
                params.put("User_Seq", meetingOrderEntity.getUserSeq());
                //根据goodSeq查询所有建议
                List<MeetingGoodsValuateEntity> valuates=meetingGoodsValuateService.selectByMap(params);
                String valuate="";
                for (MeetingGoodsValuateEntity meetingGoodsValuateEntity : valuates) {
                	if(valuate=="") {
                		valuate=meetingGoodsValuateEntity.getSuggest();
                	}else {
                		valuate=valuate+";"+meetingGoodsValuateEntity.getSuggest();
                	}
				}
                detail.put("valuate", valuate);
                //统计每个颜色的订货量
                int total = 0;
                for (Map.Entry<Integer, Integer> sizeEntry : value.getSize().entrySet()) {
                    total += sizeEntry.getValue();
                }
                detail.put("colorTotal", total);
                detail.put("address", meetingOrderEntity.getAddress());
                details.add(detail);
                
                //统计货号订货量
                goodIDAndTotalMap.put(goodID, goodIDAndTotalMap.get(goodID) + total);
                
            } else {
                details = new ArrayList<>();
                Map<String, Object> detail = new HashMap<>();
                detail.put("color", value.getColor());
                detail.put("size", value.getSize());
                detail.put("cancel", value.getCancel());
                detail.put("goodSeq", value.getMeetingGoodSeq());
                detail.put("colorSeq", value.getColorSeq());
                detail.put("cancel", value.getCancel());
                Map<String, Object> params=new HashMap<String, Object>();
                params.put("MeetingGoods_Seq", value.getMeetingGoodSeq());
                params.put("User_Seq", meetingOrderEntity.getUserSeq());
                //根据goodSeq查询所有建议
                List<MeetingGoodsValuateEntity> valuates=meetingGoodsValuateService.selectByMap(params);
                String valuate="";
                for (MeetingGoodsValuateEntity meetingGoodsValuateEntity : valuates) {
                	if(valuate=="") {
                		valuate=meetingGoodsValuateEntity.getSuggest();
                	}else {
                		valuate=valuate+";"+meetingGoodsValuateEntity.getSuggest();
                	}
				}
                detail.put("valuate", valuate);
                //标题
                goodIDAndTitlesMap.put(goodID, value.getTitles());
                //货品图片
                goodIDAndPicMap.put(goodID, imageSrcPrefix + goodID + "/" + value.getPicName());
                //统计货号订货量
                int total = 0;
                for (Map.Entry<Integer, Integer> sizeEntry : value.getSize().entrySet()) {
                    total += sizeEntry.getValue();
                }
                
                detail.put("colorTotal", total);
                detail.put("address", meetingOrderEntity.getAddress());
                details.add(detail);
                goodIDAndDetailsMap.put(goodID, details);
                goodIDAndTotalMap.put(goodID, total);
            }
            
        }
        
        List<Map<String, Object>> result = new ArrayList<>();
        
        Set<String> keySet = goodIDAndDetailsMap.keySet();
        for (String goodID : keySet) {
            
            Map<String, Object> goodIDMap = new HashMap<>();
            goodIDMap.put("goodID", goodID);
            goodIDMap.put("details", goodIDAndDetailsMap.get(goodID));
            goodIDMap.put("title", goodIDAndTitlesMap.get(goodID));
            goodIDMap.put("imgSrc", goodIDAndPicMap.get(goodID));
            goodIDMap.put("total", goodIDAndTotalMap.get(goodID));
            
            result.add(goodIDMap);
            
            Collections.sort(goodIDAndDetailsMap.get(goodID), new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    return (Integer) o2.get("colorTotal") - (Integer) o1.get("colorTotal");
                }
            });
            
        }
        
        Collections.sort(result, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                return (Integer) o2.get("total") - (Integer) o1.get("total");
            }
        });
        
        return result;
    }
    
    @Override
    public List<Integer> queryOrderExistYears(Integer companySeq) {
        return meetingOrderDao.selectOrderExistYears(companySeq);
    }
    
    @Override
    public List<Map<String, Object>> queryOrderExistMeetings(Integer companySeq, Integer year) {
        return meetingOrderDao.selectOrderExistMeetings(companySeq, year);
    }
    
    @Override
    public List<Map<String, Object>> queryOrderExistMeetingUsers(Integer companySeq, Integer meetingSeq) {
        return meetingOrderDao.selectOrderExistMeetingUsers(companySeq, meetingSeq);
    }
    
    @Override
    public List<Map<String, Object>> queryGoodsOrderByMeetingSeq(Integer companySeq, Integer meetingSeq,String keywords,Integer isCancel) {
        keywords = "%" + keywords + "%";
        //查询订货会所有货品
        List<MeetingOrderProductEntity> orderProductEntities = meetingOrderProductDao.selectGoodsOrderByMeetingSeq(companySeq, meetingSeq,keywords,isCancel);
        
        //按货品+颜色分组 key为goodID+color
        Map<String, MeetingOrderProductDO> goodIDAndColorMap = new HashMap<>();
        
        for (MeetingOrderProductEntity productEntity : orderProductEntities) {
            
            if (goodIDAndColorMap.containsKey(productEntity.getGoodID() + productEntity.getColorName())) {
                MeetingOrderProductDO productForm = goodIDAndColorMap.get(productEntity.getGoodID() + productEntity.getColorName());
                Map<Integer, Integer> size = productForm.getSize();
                Integer prevCount = size.get(productEntity.getSize());
                if (prevCount == null) {
                    prevCount = 0;
                }
                size.put(productEntity.getSize(), prevCount + productEntity.getBuyCount());
                
            } else {
                MeetingOrderProductDO form = new MeetingOrderProductDO();
                form.setGoodID(productEntity.getGoodID());
                form.setPicName(productEntity.getPicName());
                form.setCancel(productEntity.getCancel());
                form.setColor(productEntity.getColorName());
                form.setMeetingGoodSeq(productEntity.getMeetingGoodsSeq());
                form.setColorSeq(productEntity.getColorSeq());
                form.setUserName(productEntity.getUserName());
                form.setPrice(productEntity.getPrice());

                //获取最大码和最小码之间的所有码
                List<Integer> minAndMaxSize = new ArrayList<>();
                Map<Integer, Integer> sizeAndCountMap = new HashMap<>();
                for (int i = productEntity.getMinSize(); i <= productEntity.getMaxSize(); i++) {
                    minAndMaxSize.add(i);
                }
                form.setTitles(minAndMaxSize);
                
                sizeAndCountMap.put(productEntity.getSize(), productEntity.getBuyCount());
                form.setSize(sizeAndCountMap);
                
                goodIDAndColorMap.put(productEntity.getGoodID() + productEntity.getColorName(), form);
            }
        }
        
                /*        *
        {
        goodID: 'A1111',
        color: '米白'
        size:{34: 12, 35: 13, 36: 14},
       title:[34,35,36],
      }
      
      
      [
      {
        goodID: 'A1111',
        details: [
          {color: '米白', size: {34: 12, 35: 13, 36: 14}},
          {color: '黑色', size: {34: 18, 35: 17, 36: 14}}
        ],
        title: [34, 35, 36],
        imgSrc:'http://'
      }
    ]
      */
        String imageSrcPrefix = getMeetingImageSrcPrefix();
        
        List<Map<String, Object>> result = new ArrayList<>();
        
        //按货品分组
        Map<String, List<Map<String, Object>>> goodIDAndDetailsMap = new HashMap<>();
        Map<String, List<Integer>> goodIDAndTitlesMap = new HashMap<>();
        Map<String, String> goodIDAndPicMap = new HashMap<>();
        Map<String, Integer> goodIDAndTotalMap = new HashMap<>();
        
        for (Map.Entry<String, MeetingOrderProductDO> entry : goodIDAndColorMap.entrySet()) {
            MeetingOrderProductDO value = entry.getValue();
            String goodID = value.getGoodID();
            Integer goodSeq = value.getMeetingGoodSeq();
            String goodIDAndSeq = goodID + "," + goodSeq;
            List<Map<String, Object>> details;
            if (goodIDAndDetailsMap.containsKey(goodIDAndSeq)) {
                details = goodIDAndDetailsMap.get(goodIDAndSeq);
                Map<String, Object> detail = new HashMap<>();
                detail.put("color", value.getColor());
                detail.put("size", value.getSize());
                detail.put("cancel", value.getCancel());
                detail.put("goodSeq", goodSeq);
                detail.put("colorSeq", value.getColorSeq());
                detail.put("userName",value.getUserName());
                detail.put("price",value.getPrice());
                Map<String, Object> params=new HashMap<String, Object>();
                params.put("MeetingGoods_Seq",goodSeq);
                //根据goodSeq查询所有建议
                List<MeetingGoodsValuateEntity> valuates=meetingGoodsValuateService.selectByMap(params);
                String valuate="";
                for (MeetingGoodsValuateEntity meetingGoodsValuateEntity : valuates) {
                	if(valuate=="") {
                		valuate=meetingGoodsValuateEntity.getSuggest();
                	}else {
                		valuate=valuate+";"+meetingGoodsValuateEntity.getSuggest();
                	}
				}
                detail.put("valuate", valuate);
                //统计颜色订货量
                int total = 0;
                for (Map.Entry<Integer, Integer> sizeEntry : value.getSize().entrySet()) {
                    total += sizeEntry.getValue();
                }
                detail.put("colorTotal", total);
                details.add(detail);
                //统计货号订货量
                
                goodIDAndTotalMap.put(goodIDAndSeq, goodIDAndTotalMap.get(goodIDAndSeq) + total);
            } else {
                details = new ArrayList<>();
                Map<String, Object> detail = new HashMap<>();
                detail.put("color", value.getColor());
                detail.put("size", value.getSize());
                detail.put("cancel", value.getCancel());
                detail.put("goodSeq", goodSeq);
                detail.put("colorSeq", value.getColorSeq());
                detail.put("userName",value.getUserName());
                detail.put("price",value.getPrice());
                Map<String, Object> params=new HashMap<String, Object>();
                params.put("MeetingGoods_Seq",goodSeq);
                //根据goodSeq查询所有建议
                List<MeetingGoodsValuateEntity> valuates=meetingGoodsValuateService.selectByMap(params);
                String valuate="";
                for (MeetingGoodsValuateEntity meetingGoodsValuateEntity : valuates) {
                	if(valuate=="") {
                		valuate=meetingGoodsValuateEntity.getSuggest();
                	}else {
                		valuate=valuate+";"+meetingGoodsValuateEntity.getSuggest();
                	}
				}
                detail.put("valuate", valuate);
                
                
                //标题
                goodIDAndTitlesMap.put(goodIDAndSeq, value.getTitles());
                //货品图片
                goodIDAndPicMap.put(goodIDAndSeq, imageSrcPrefix + goodID + "/" + value.getPicName());
                //统计颜色订货量
                int total = 0;
                for (Map.Entry<Integer, Integer> sizeEntry : value.getSize().entrySet()) {
                    total += sizeEntry.getValue();
                }
                detail.put("colorTotal", total);
                details.add(detail);
                goodIDAndDetailsMap.put(goodIDAndSeq, details);
                
                //统计货号订货量
                goodIDAndTotalMap.put(goodIDAndSeq, total);
            }
            
        }
        
        Set<String> keySet = goodIDAndDetailsMap.keySet();
        for (String goodIDAndSeq : keySet) {
            String[] str2 = goodIDAndSeq.split(",");
            String goodID = str2[0];
            String goodSeq = str2[1];
            
            Map<String, Object> goodIDMap = new HashMap<>();
            goodIDMap.put("goodID", goodID);
            goodIDMap.put("goodSeq", goodSeq);
            goodIDMap.put("userName", goodSeq);
            List<Map<String, Object>> details = goodIDAndDetailsMap.get(goodIDAndSeq);
            goodIDMap.put("details", details);
            goodIDMap.put("title", goodIDAndTitlesMap.get(goodIDAndSeq));
            goodIDMap.put("imgSrc", goodIDAndPicMap.get(goodIDAndSeq));
            goodIDMap.put("total", goodIDAndTotalMap.get(goodIDAndSeq));
            goodIDMap.put("goodCancel", 0);//货号是否全部取消标记 1全部取消 0全未取消或部分取消
            
            int flag = 0;
            int count = 0;
            for (int i = 0; i < details.size(); i++) {
                Map<String, Object> detail = details.get(i);
                Integer cancel = (Integer) detail.get("cancel");
                if (i == 0) {
                    flag = cancel;
                    count++;
                } else {
                    if (flag != cancel) {
                        break;
                    } else {
                        count++;
                    }
                }
            }
            
            if (flag == 1 && count == details.size()) {
                //货号全部货品取消
                goodIDMap.put("goodCancel", 1);
            }
            
            result.add(goodIDMap);
            
            //颜色订货量排序
            Collections.sort(details, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    return (Integer) o2.get("colorTotal") - (Integer) o1.get("colorTotal");
                }
            });
        }
        
        Collections.sort(result, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                return (Integer) o2.get("total") - (Integer) o1.get("total");
            }
        });
        
        return result;
    }
    
    @Override
    public List<Map<String, Object>> queryAreaOrderByMeetingSeq(Integer companySeq, Integer meetingSeq,Integer areaSeq) {
        //查询订货会所有货品
        List<MeetingOrderProductEntity> orderProductEntities = meetingOrderProductDao.selectAreaOrderByMeetingSeq(companySeq, meetingSeq,areaSeq);
        
        //按区域+货品+颜色分组，统计尺码数量 key为goodID+color
        Map<String, MeetingOrderProductDO> areaAndGoodIDAndColorMap = new HashMap<>();
        
        for (MeetingOrderProductEntity productEntity : orderProductEntities) {
            
            if (areaAndGoodIDAndColorMap.containsKey(productEntity.getAreaName() + productEntity.getGoodID() + productEntity.getColorName())) {
                MeetingOrderProductDO productForm = areaAndGoodIDAndColorMap.get(productEntity.getAreaName() + productEntity.getGoodID() + productEntity.getColorName());
                Map<Integer, Integer> size = productForm.getSize();
                Integer prevCount = size.get(productEntity.getSize());
                if (prevCount == null) {
                    prevCount = 0;
                }
                size.put(productEntity.getSize(), prevCount + productEntity.getBuyCount());
                
            } else {
                MeetingOrderProductDO form = new MeetingOrderProductDO();
                form.setGoodID(productEntity.getGoodID());
                form.setPicName(productEntity.getPicName());
                form.setAreaName(productEntity.getAreaName());
                form.setCancel(productEntity.getCancel());
                form.setColor(productEntity.getColorName());
                //form.setMeetingGoodSeq(productEntity.getMeetingGoodsSeq());
                //form.setColorSeq(productEntity.getColorSeq());
                
                //获取最大码和最小码之间的所有码
                List<Integer> minAndMaxSize = new ArrayList<>();
                Map<Integer, Integer> sizeAndCountMap = new HashMap<>();
                for (int i = productEntity.getMinSize(); i <= productEntity.getMaxSize(); i++) {
                    minAndMaxSize.add(i);
                    //sizeAndCountMap.put(i, 0);
                }
                form.setTitles(minAndMaxSize);
                
                sizeAndCountMap.put(productEntity.getSize(), productEntity.getBuyCount());
                form.setSize(sizeAndCountMap);
                
                areaAndGoodIDAndColorMap.put(productEntity.getAreaName() + productEntity.getGoodID() + productEntity.getColorName(), form);
            }
        }
        
                /*        *
        {
        areaName:'郑州',
        goodID: 'A1111',
        color: '米白'
        size:{34: 12, 35: 13, 36: 14},
       title:[34,35,36],
      }

      */
        String imageSrcPrefix = getMeetingImageSrcPrefix();
        
        //按区域+货号分组
        Map<String, List<Map<String, Object>>> areaGoodIDMap = new HashMap<>();
        Map<String, List<Integer>> areaGoodIDAndTitlesMap = new HashMap<>();
        Map<String, String> areaGoodIDAndPicMap = new HashMap<>();
        Map<String, Integer> goodIDAndTotalMap = new HashMap<>();
        
        for (Map.Entry<String, MeetingOrderProductDO> entry : areaAndGoodIDAndColorMap.entrySet()) {
            MeetingOrderProductDO value = entry.getValue();
            String areaName = value.getAreaName();
            String goodID = value.getGoodID();
            
            List<Map<String, Object>> details;
            if (areaGoodIDMap.containsKey(areaName + ',' + goodID)) {
                details = areaGoodIDMap.get(areaName + ',' + goodID);
                Map<String, Object> detail = new HashMap<>();
                detail.put("color", value.getColor());
                detail.put("size", value.getSize());
                detail.put("cancel", value.getCancel());
                //detail.put("goodSeq", value.getMeetingGoodSeq());
                //detail.put("colorSeq", value.getColorSeq());
                
                //统计颜色订货量
                int total = 0;
                for (Map.Entry<Integer, Integer> sizeEntry : value.getSize().entrySet()) {
                    total += sizeEntry.getValue();
                }
                detail.put("colorTotal", total);
                details.add(detail);
                
                //统计货号订货量
                goodIDAndTotalMap.put(areaName + ',' + goodID, goodIDAndTotalMap.get(areaName + ',' + goodID) + total);
            } else {
                details = new ArrayList<>();
                Map<String, Object> detail = new HashMap<>();
                detail.put("color", value.getColor());
                detail.put("size", value.getSize());
                detail.put("cancel", value.getCancel());
                //detail.put("goodSeq", value.getMeetingGoodSeq());
                //detail.put("colorSeq", value.getColorSeq());
                
                //标题
                areaGoodIDAndTitlesMap.put(areaName + ',' + goodID, value.getTitles());
                //货品图片
                areaGoodIDAndPicMap.put(areaName + ',' + goodID, imageSrcPrefix + goodID + "/" + value.getPicName());
                
                //统计颜色订货量
                int total = 0;
                for (Map.Entry<Integer, Integer> sizeEntry : value.getSize().entrySet()) {
                    total += sizeEntry.getValue();
                }
                detail.put("colorTotal", total);
                details.add(detail);
                areaGoodIDMap.put(areaName + ',' + goodID, details);
                
                //统计货号订货量
                goodIDAndTotalMap.put(areaName + ',' + goodID, total);
                
            }
        }
        
        //按区域分组
        Map<String, List<Map<String, Object>>> areaAndGoodIDsMap = new HashMap<>();
        
        Set<String> keySet = areaGoodIDMap.keySet();
        for (String areaGoodID : keySet) {
            String[] split = areaGoodID.split(",");
            String areaName = split[0];
            String goodID = split[1];
            if (areaAndGoodIDsMap.containsKey(areaName)) {
                List<Map<String, Object>> list = areaAndGoodIDsMap.get(areaName);
                Map<String, Object> goodIDMap = new HashMap<>();
                goodIDMap.put("goodID", goodID);
                goodIDMap.put("details", areaGoodIDMap.get(areaGoodID));
                goodIDMap.put("title", areaGoodIDAndTitlesMap.get(areaGoodID));
                goodIDMap.put("imgSrc", areaGoodIDAndPicMap.get(areaGoodID));
                goodIDMap.put("total", goodIDAndTotalMap.get(areaGoodID));
                //区域-货号  颜色订货量排序
                Collections.sort(areaGoodIDMap.get(areaGoodID), new Comparator<Map<String, Object>>() {
                    @Override
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        return (Integer) o2.get("colorTotal") - (Integer) o1.get("colorTotal");
                    }
                });
                
                list.add(goodIDMap);
                
            } else {
                List<Map<String, Object>> areaDetails = new ArrayList<>();
                Map<String, Object> goodIDMap = new HashMap<>();
                goodIDMap.put("goodID", goodID);
                goodIDMap.put("details", areaGoodIDMap.get(areaGoodID));
                goodIDMap.put("title", areaGoodIDAndTitlesMap.get(areaGoodID));
                goodIDMap.put("imgSrc", areaGoodIDAndPicMap.get(areaGoodID));
                goodIDMap.put("total", goodIDAndTotalMap.get(areaGoodID));
                
                //区域-货号  颜色订货量排序
                Collections.sort(areaGoodIDMap.get(areaGoodID), new Comparator<Map<String, Object>>() {
                    @Override
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        return (Integer) o2.get("colorTotal") - (Integer) o1.get("colorTotal");
                    }
                });
                areaDetails.add(goodIDMap);
                
                areaAndGoodIDsMap.put(areaName, areaDetails);
            }
        }
        
        /*
        * 返回的数据结构
       
      {
          '郑州':[
                  {
                    goodID: 'A1111',
                    details: [
                      {color: '米白', size: {34: 12, 35: 13, 36: 14}},
                      {color: '黑色', size: {34: 18, 35: 17, 36: 14}}
                    ],
                    title: [34, 35, 36],
                    imgSrc:'http://',
                    total:110,
                  }
        ]
    }
    * */
        
        List<Map<String, Object>> resultList = new ArrayList<>();
        
        for (String areaName : areaAndGoodIDsMap.keySet()) {
            Map<String, Object> areaMap = new HashMap<>();
            areaMap.put("areaName", areaName);
            List<Map<String, Object>> goodIDs = areaAndGoodIDsMap.get(areaName);
            areaMap.put("areaDetail", goodIDs);
            //获取区域下所有货号的总数之和
            int areaTotal = 0;
            for (Map<String, Object> goodIDObj : goodIDs) {
                areaTotal += Integer.parseInt(goodIDObj.get("total").toString());
            }
            
            areaMap.put("areaTotal", areaTotal);
            
            resultList.add(areaMap);
            //区域-货号排序
            Collections.sort(goodIDs, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    return (Integer) o2.get("total") - (Integer) o1.get("total");
                }
            });
        }
        
        //区域订货量排序
        Collections.sort(resultList, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                return (Integer) o2.get("areaTotal") - (Integer) o1.get("areaTotal");
            }
        });
        
         /*
        * 返回的数据结构
       
      [
          {areaName:'郑州',
           areaTotal:101,
           areaDetail:[
                  {
                    goodID: 'A1111',
                    details: [
                      {color: '米白', size: {34: 12, 35: 13, 36: 14}},
                      {color: '黑色', size: {34: 18, 35: 17, 36: 14}}
                    ],
                    title: [34, 35, 36],
                    imgSrc:'http://',
                    total:110,
                  }
                ]
        }
    ]
    * */
        return resultList;
    }

	@Override
	public List<MeetingOrderEntity> getAllListByMeeingSeq(Integer meetingSeq) {
		Wrapper<MeetingOrderEntity> wrapper = new EntityWrapper<MeetingOrderEntity>();
		wrapper.where("Meeting_Seq = {0} ", meetingSeq);
		return meetingOrderDao.selectList(wrapper);
	}

	@Override
	public Integer getTotalCountByMeetingSeq(Integer meetingSeq,Integer meetingUserSeq) {
		Integer TotalBuy=meetingOrderProductDao.getTotalCountByMeetingSeq(meetingSeq, meetingUserSeq);
		return TotalBuy;
	}

	@Override
	public List<Map<String, Object>> getSizeNum(Integer meetingGoodsSeq, Integer colorSeq) {
		Wrapper<MeetingOrderProductEntity> wrapper = new EntityWrapper<MeetingOrderProductEntity>();
		wrapper.setSqlSelect("Size,SUM(BuyCount) as totalNum").where("MeetingGoods_Seq ={0} AND Color_Seq = {1}", meetingGoodsSeq,colorSeq)
		.groupBy("Size");
		return meetingOrderProductDao.selectMaps(wrapper);
	}

    @Override
    public List<Integer> selectUserSeqList(Integer companySeq,Integer meetingSeq, Integer userSeq) throws Exception {
        Map<String,Object> map = new HashMap<>(10);
        map.put("companySeq",companySeq);
        map.put("meetingSeq",meetingSeq);
        map.put("userSeq",userSeq);
        return meetingOrderDao.selectUserSeqList(map);
    }

    @Override
    public List<MeetingOrderEntity> selectCustomMeetingOrder(Integer companySeq,Integer meetingSeq, Integer userSeq) throws Exception {
        Map<String,Object> map = new HashMap<>(10);
        map.put("companySeq",companySeq);
        map.put("meetingSeq",meetingSeq);
        map.put("userSeq",userSeq);
        return meetingOrderDao.selectCustomMeetingOrder(map);
    }

    @Override
    public List<MeetingOrderCartDetailEntity> getOrderCartDetail(Integer distributeBoxSeq) throws Exception {
        Map<String,Object> map = new HashMap<>(10);
        map.put("distributeBoxSeq",distributeBoxSeq);
        return meetingOrderDao.getOrderCartDetail(map);
    }

    @Override
    public MeetingOrderEntity selectOrderTotalData(Integer companySeq,Integer meetingSeq, Integer userSeq,String keywords,Integer areaSeq) throws Exception {
        Map<String,Object> map = new HashMap<>(10);
        map.put("companySeq",companySeq);
        map.put("meetingSeq",meetingSeq);
        map.put("userSeq",userSeq);
        map.put("keywords",keywords);
        map.put("areaSeq",areaSeq);
        return meetingOrderDao.selectOrderTotalData(map);
    }

    @Override
    public List<Integer> selectMinMaxSize(Integer meetingSeq) throws Exception {
        Map<String,Object> map = new HashMap<>(1);
        map.put("meetingSeq",meetingSeq);
        return meetingOrderDao.selectMinMaxSize(map);
    }

    @Override
    public MeetingOrderEntity selectTotalDataByOrderSeq(Integer orderSeq) throws Exception {
        return meetingOrderDao.selectTotalDataByOrderSeq(orderSeq);
    }

    @Override
    public List<Map<String, Object>> getOrderDetailList(Integer orderSeq, Integer categorySeq) {
        return meetingOrderDao.getOrderDetailList(orderSeq, categorySeq);
    }

    @Override
    public MeetingOrderCartDistributeBoxEntity getBoxByOrderSeq(Integer cartSeq, Integer colorSeq, Integer goodsSeq) {
        return meetingOrderDao.getBoxByOrderSeq(cartSeq, colorSeq, goodsSeq);
    }

    @Override
    public List<MeetingOrderEntity> getOrderList(Integer companySeq, Integer meetingSeq, Integer userSeq) {
        Wrapper<MeetingOrderEntity> wrapper = new EntityWrapper<MeetingOrderEntity>();
        wrapper.where("Meeting_Seq = {0} AND User_Seq={1} AND Company_Seq ={2} ", meetingSeq,userSeq,companySeq);
        return meetingOrderDao.selectList(wrapper);
    }

    @Override
    public List<Integer> selectMinMaxSizeByOrder(Integer orderSeq, Integer categorySeq) throws Exception {
        Map<String,Object> map = new HashMap<>(1);
        map.put("orderSeq",orderSeq);
        map.put("categorySeq",categorySeq);
        return meetingOrderDao.selectMinMaxSizeByOrder(map);
    }

    @Override
    public List<Integer> selectCategory(Integer orderSeq) {
        return meetingOrderDao.selectCategory(orderSeq);
    }
}
