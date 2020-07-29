package io.nuite.modules.system.service.order_platform;

import io.nuite.modules.sr_base.entity.*;

import java.util.List;
import java.util.Map;

import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.R;

public interface OrderPartyManagementService {

    /**
     * 根据用户seq删除订货方
     */
    Integer deleteOrderParty(Integer seq);

    /**
     * 根据当前工厂的用户seq和订货方类型返回订货方列表
     */
    PageUtils orderPartyList(BaseUserEntity loginUser, Integer companyType, Integer page, Integer limit);

    /**
     * 根据用户seq获得订货方的具体信息(修改信息)
     */
    BaseUserEntity edit(Integer seq);

    /**
     * 修改账户信息
     */
    int updateOrderParty(BaseUserEntity baseUserEntity, BaseUserEntity userEntity);

    /**
     * 添加账户
     * 
     * @param i
     */
    void addOrderParty(BaseUserEntity baseUser, BaseUserEntity baseUserEntity)  throws Exception;

    /**
     * 查询该用户当前剩余的创建用户的名额
     */
    int restOfQuota(Long userSeq);

    /**
     * 判断该账号是否存在
     * 
     * @param userSeq
     */
    boolean judgeUserOrCompanyExistence(BaseUserEntity baseUserEntity);

    /**
     * 是否禁用
     * 
     * @param disable2
     */
    int disable(Integer seq, Integer disable);

    /**
     * 返回工厂或某一分公司的门店列表
     */
    List<BaseShopEntity> shopList(BaseUserEntity baseUserEntity, Integer attachSeq);

    /**
     * 返回代理商列表
     */
    List<BaseAgentEntity> agentList(Integer brandSeq);

    /**
     * 返回分公司列表
     */
    List<BaseAreaEntity> branchOfficeList(Integer brandSeq);

    PageUtils  meetingPartyList(BaseUserEntity loginUser, Integer saleType, Integer page, Integer limit,Integer seq);
    
    PageUtils  meetingAreaUserList(BaseUserEntity loginUser, Integer saleType, Integer page, Integer limit,Integer seq);
    
    Boolean hasUserInOthers(Integer userSeq);
}
