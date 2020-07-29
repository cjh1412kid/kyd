package io.nuite.modules.order_platform_app.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.base.Joiner;
import io.nuite.modules.order_platform_app.dao.ShoesInfoDao;
import io.nuite.modules.order_platform_app.entity.ShoesInfoEntity;
import io.nuite.modules.order_platform_app.service.ShoesInfoService;
import io.nuite.modules.sr_base.dao.GoodsCategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ShoesInfoServiceImpl extends ServiceImpl<ShoesInfoDao, ShoesInfoEntity> implements ShoesInfoService {

    @Autowired
    private GoodsCategoryDao goodsCategoryDao;

    @Autowired
    private ShoesInfoDao shoesInfoDao;


    /**
     * 根据公司编号获取鞋子所有分类
     */
    @Override
    public List<Map<String, Object>> getShoesCategory(Integer companySeq, Integer parentSeq) {
        return goodsCategoryDao.getShoesCategory(companySeq, parentSeq);
    }


    /**
     * 全部鞋款列表
     */
    @Override
    public List<Map<String, Object>> getShoesList(String periodSeq, Integer companyTypeSeq, List<Integer> categorySeqList, List<Integer> shoesSeqList, String goodNameId,
                                                  Integer orderBy, Integer orderDir, Integer start, Integer num,Integer meetingSeq,String year,Integer type,Integer status,Integer userSeq) {
        //多个分类
        String categorySeqs = null;
        if (categorySeqList != null && categorySeqList.size() > 0) {
            categorySeqs = Joiner.on(",").join(categorySeqList);
        }
        //多个鞋子序号
        String shoesSeqs = null;
        if (shoesSeqList != null && shoesSeqList.size() > 0) {
            shoesSeqs = Joiner.on(",").join(shoesSeqList);
        }

        List<Map<String, Object>> list = shoesInfoDao.getShoesList(periodSeq, companyTypeSeq, categorySeqs, shoesSeqs, goodNameId, orderBy, orderDir, start - 1, num,meetingSeq,year,type,status,userSeq);
        return list;
    }


    /**
     * 根据 波次编号、能看到的鞋子类型， 获取全部鞋款的货号
     */
    @Override
    public List<Map<String, Object>> getAllGoodIds(String periodSeq, Integer companyTypeSeq) {
        return shoesInfoDao.getAllGoodIds(periodSeq, companyTypeSeq);

    }


    /**
     * 根据 波次编号、能看到的鞋子类型， 获取6个热搜商品货号
     */
    @Override
    public List<Map<String, Object>> getHotSearchGoodIds(String periodSeq, Integer companyTypeSeq) {
        return shoesInfoDao.getHotSearchGoodIds(periodSeq, companyTypeSeq);
    }


    /**
     * 根据shoesSeq获取ShoesInfo对象
     */
    @Override
    public ShoesInfoEntity getShoesInfoByShoesSeq(Integer shoesSeq) {
        ShoesInfoEntity shoesInfoEntity = new ShoesInfoEntity();
        shoesInfoEntity.setShoesSeq(shoesSeq);
        return shoesInfoDao.selectOne(shoesInfoEntity);
    }


}
