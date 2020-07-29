package io.nuite.modules.sr_base.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.nuite.modules.sr_base.entity.BaseUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author admin
 * @email
 * @date 2018-04-11 11:38:09
 */
@Mapper
public interface BaseUserDao extends BaseMapper<BaseUserEntity> {

    /**
     * 根据seqs获取多个用户的基础信息(名字、公司、品牌、头像)Map
     */
    List<Map<String, Object>> getBaseUserInfoBySeqs(@Param("userSeqs") String userSeqs);

    /**
     * 根据用户名，查询系统用户 只查询后台用户：工厂账号或者工厂子账号
     */
    BaseUserEntity queryByUserName(@Param("username") String username);

    /**
     * 查询用户的所有权限
     *
     * @param userId 用户ID
     */
    List<String> queryAllPerms(Long userId);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userSeq);

    /**
     * 根据用户的seq获取创建人的seq(即对应的工厂方的seq)
     */
    Integer getCreateUserSeq(@Param("userSeq") Integer userSeq);

    Map<String, Object> getUserInOrderPlatform(@Param(value = "userId") Long userId);

    Map<String, Object> getUserInOnlineSales(@Param(value = "userId") Long userId);



    void updateUserBySeq(BaseUserEntity baseUserEntity);

    List<Integer> selectSeqByAttachTypeAndSaleType(@Param("attachType") int attachType , @Param("saleType") int saleType);

	List<BaseUserEntity> selectBySeqList(@Param("companySeq")Integer companySeq,
										@Param("saleType")Integer saleType,
										@Param("start")Integer start,
										@Param("num")Integer num);

    Integer getTotalCount(@Param("companySeq") Integer companySeq,
    						@Param("saleType") Integer saleType);
    
	List<Map<String, Object>> selectByMeetingSeqList(@Param("companySeq")Integer companySeq,
			@Param("saleType")Integer saleType,
			@Param("start")Integer start,
			@Param("num")Integer num,
			@Param("meetingSeq")Integer meetingSeq);

    Integer getMeetingTotalCount(@Param("companySeq") Integer companySeq,
			@Param("saleType") Integer saleType,@Param("meetingSeq")Integer meetingSeq);
    
    List<Map<String, Object>> selectByAreaSeqList(@Param("companySeq")Integer companySeq,
			@Param("saleType")Integer saleType,
			@Param("start")Integer start,
			@Param("num")Integer num,
			@Param("areaSeq")Integer areaSeq);

    Integer getAreaTotalCount(@Param("companySeq") Integer companySeq,
			@Param("saleType") Integer saleType,@Param("areaSeq")Integer areaSeq);
    
}
