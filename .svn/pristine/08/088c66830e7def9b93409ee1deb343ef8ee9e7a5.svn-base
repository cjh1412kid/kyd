package io.nuite.modules.sr_base.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.nuite.modules.sr_base.dao.BaseAreaDao;
import io.nuite.modules.sr_base.entity.BaseAreaEntity;
import io.nuite.modules.sr_base.service.BaseAreaService;

@Service
public class BaseAreaServiceImpl extends ServiceImpl<BaseAreaDao, BaseAreaEntity> implements BaseAreaService {

    @Autowired
    BaseAreaDao baseAreaDao;

    @Override
    public List<Integer> nOcategoryList(Integer brand_Seq) {
        List<BaseAreaEntity> list=baseAreaDao.selectList(new EntityWrapper<BaseAreaEntity>().eq("Brand_Seq",brand_Seq).eq("ParentSeq", 0).eq("Del", 0));
        List<Integer> nOcategoryList=new ArrayList<>();

        if (list !=null && !list.isEmpty()){
            for (BaseAreaEntity baseAreaEntity : list){
                List<BaseAreaEntity> nextList=baseAreaDao.selectList(new EntityWrapper<BaseAreaEntity>().eq("ParentSeq", baseAreaEntity.getSeq()).eq("Del", 0));

                if (nextList !=null && !nextList.isEmpty()){
                    for (BaseAreaEntity nextBaseAreaEntity : nextList){
                        nOcategoryList.add(nextBaseAreaEntity.getSeq());
                    }
                }
            }
        }
        return nOcategoryList;
    }
}
