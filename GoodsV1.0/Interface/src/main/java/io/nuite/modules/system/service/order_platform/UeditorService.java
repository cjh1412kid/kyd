package io.nuite.modules.system.service.order_platform;

import com.baomidou.mybatisplus.service.IService;
import io.nuite.modules.sr_base.entity.UeditorRecordEntity;

import java.util.List;

/**
 * @Author: yangchuang
 * @Date: 2018/7/17 14:24
 * @Version: 1.0
 * @Description:
 */
public interface UeditorService extends IService<UeditorRecordEntity> {

    /**
     * 根据企业id获取所有相关ueditor记录
     *
     * @param companySeq
     * @return
     */
    List<UeditorRecordEntity> getList(Integer companySeq);

    /**
     * 根据记录id查询记录
     *
     * @param seq
     * @return
     */
    UeditorRecordEntity getById(Integer seq);

    /**
     * 根据企业ID和被使用状态查询记录 唯一
     *
     * @param companySeq
     * @return
     */
    UeditorRecordEntity getByCompanySeqAndUsed(Integer companySeq);

    /**
     * 保存用户编辑的内容
     *
     * @param ur
     */
    Boolean saveOrUpdate(UeditorRecordEntity ur);

    /**
     * 根据模版id删除记录
     *
     * @param seq
     */
    Boolean delete(Integer seq);

    /**
     * 根据id更新用户信息
     *
     * @param ur
     * @return
     */
    Boolean update(UeditorRecordEntity ur);

    /**
     * 根据companySeq查询所有记录used置为0，传入的置为1
     *
     * @param ur
     * @return
     */
    Boolean setUsed(UeditorRecordEntity ur);

    /**
     * 取消用户的主模版设置
     * @param ur
     * @return
     */
    Boolean cancelUsed(UeditorRecordEntity ur);

}
