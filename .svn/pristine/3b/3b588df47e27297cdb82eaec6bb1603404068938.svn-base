package io.nuite.modules.system.service.impl.order_platform;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.nuite.modules.sr_base.entity.UeditorRecordEntity;
import io.nuite.modules.system.dao.order_platform.UeditorDao;
import io.nuite.modules.system.service.order_platform.UeditorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author: yangchuang
 * @Date: 2018/7/17 14:27
 * @Version: 1.0
 * @Description:
 */

@Service
public class UeditorServiceImpl extends ServiceImpl<UeditorDao, UeditorRecordEntity> implements UeditorService {

    @Override
    public List<UeditorRecordEntity> getList(Integer companySeq) {

        EntityWrapper<UeditorRecordEntity> wrapper = new EntityWrapper();
        wrapper.eq("Company_Seq", companySeq).eq("Del", 0);

        return super.selectList(wrapper);
    }

    @Override
    public UeditorRecordEntity getById(Integer seq) {

        return super.selectById(seq);
    }

    /**
     * 根据企业ID和被使用状态查询记录 唯一
     *
     * @param companySeq
     * @return
     */
    @Override
    public UeditorRecordEntity getByCompanySeqAndUsed(Integer companySeq) {

        EntityWrapper<UeditorRecordEntity> wrapper = new EntityWrapper();
        wrapper.eq("Company_Seq", companySeq).eq("Del", 0).eq("used", 1);
        return super.selectOne(wrapper);
    }

    /**
     * 保存用户编辑的内容
     *
     * @param ur
     */
    @Override
    public Boolean saveOrUpdate(UeditorRecordEntity ur) {

        ur.setUsed(0);
        ur.setDel(0);
        ur.setInputTime(new Date());
        return super.insertOrUpdate(ur);
    }

    /**
     * 根据模版id删除记录
     *
     * @param seq
     */
    @Override
    public Boolean delete(Integer seq) {
        return super.deleteById(seq);
    }

    /**
     * 根据id更新用户信息
     *
     * @param ur
     * @return
     */
    @Override
    public Boolean update(UeditorRecordEntity ur) {

        ur.setInputTime(new Date());
        return super.updateById(ur);
    }

    /**
     * 根据companySeq查询所有记录used 置为0，传入的 置为1
     *
     * @param ur
     * @return
     */
    @Transactional
    @Override
    public Boolean setUsed(UeditorRecordEntity ur) {

        EntityWrapper ew = new EntityWrapper<UeditorRecordEntity>();
        ew.eq("Company_Seq", ur.getCompanySeq()).eq("Used", 1);
        List<UeditorRecordEntity> urs = super.selectList(ew);

        for (UeditorRecordEntity ue : urs) {
            ue.setUsed(0);
            super.updateById(ue);
        }
        ur.setUsed(1);
        super.updateById(ur);

        return true;
    }

    /**
     * 取消用户的主模版设置
     *
     * @param ur
     * @return
     */
    @Override
    public Boolean cancelUsed(UeditorRecordEntity ur) {
        ur.setUsed(0);
        super.updateById(ur);
        return true;
    }

}
