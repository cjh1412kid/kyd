package io.nuite.modules.system.service.impl.order_platform;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import io.nuite.common.utils.Constant.UserAttachType;
import io.nuite.common.utils.PageUtils;
import io.nuite.common.utils.R;
import io.nuite.modules.order_platform_app.dao.MeetingShoppingCartDao;
import io.nuite.modules.order_platform_app.entity.MeetingShoppingCartEntity;
import io.nuite.modules.order_platform_app.utils.RongCloudUtils;
import io.nuite.modules.sr_base.dao.*;
import io.nuite.modules.sr_base.entity.*;
import io.nuite.modules.system.dao.MeetingOrderDao;
import io.nuite.modules.system.dao.MeetingPermissionDao;
import io.nuite.modules.system.dao.MeetintUserAreaDao;
import io.nuite.modules.system.dao.order_platform.OrderPartyManagementDao;
import io.nuite.modules.system.dao.order_platform.OrderUserJurisdictionDao;
import io.nuite.modules.system.entity.MeetingOrderEntity;
import io.nuite.modules.system.entity.MeetingPermissionEntity;
import io.nuite.modules.system.entity.MeetintUserAreaEntity;
import io.nuite.modules.system.entity.order_platform.OrderUserJurisdictionEntity;
import io.nuite.modules.system.service.order_platform.OrderPartyManagementService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderPartyManagementServiceImpl implements OrderPartyManagementService {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private OrderPartyManagementDao orderPartyManagementDao;

    @Autowired
    private BaseUserDao baseUserDao;

    @Autowired
    private OrderUserJurisdictionDao userJurisdictionDao;

    @Autowired
    private RongCloudUtils rongCloudUtils;

    @Autowired
    private BaseShopDao baseShopDao;

    @Autowired
    private BaseAreaDao baseAreaDao;

    @Autowired
    private BaseAgentDao baseAgentDao;

    @Autowired
    private BaseCompanyDao baseCompanyDao;
    
    @Autowired
    private MeetingOrderDao meetingOrderDao;
    
    @Autowired
    private MeetingPermissionDao meetingPermissionDao;
    
    @Autowired
    private MeetingShoppingCartDao meetingShoppingCartDao;
    
    @Autowired
    private MeetintUserAreaDao meetintUserAreaDao;

    // 根据当前工厂的用户和订货方类型返回订货方列表
    @Override
    public PageUtils orderPartyList(BaseUserEntity loginUser, Integer saleType, Integer page, Integer limit) {
        List<BaseUserEntity> userList = new ArrayList<BaseUserEntity>();
        Integer totalCount = null;
        Integer start = new Integer(0);
        Integer num = new Integer(0);

        if (page >= 1 && limit > 0) {
            start = (page - 1) * limit;
            num = page * limit;
        }

        userList = baseUserDao.selectBySeqList(loginUser.getCompanySeq(), saleType, start, num);
        totalCount = baseUserDao.getTotalCount(loginUser.getCompanySeq(), saleType);

        if (userList != null && !userList.isEmpty()) {
            for (BaseUserEntity baseUserEntity : userList) {
                if (baseUserEntity != null) {
                	//工厂信息
                    if (baseUserEntity.getAttachType() == 1) {
                        baseUserEntity.setAttachTypeName(UserAttachType.FACTORY.getName());
                    }
                    
                	//分公司信息
                    if (baseUserEntity.getAttachType() == 2) {
                        baseUserEntity.setAttachTypeName(UserAttachType.OFFICE.getName());
                        
                        BaseAreaEntity baseAreaEntity = new BaseAreaEntity();
                        baseAreaEntity.setSeq(baseUserEntity.getAttachSeq());
                        baseAreaEntity = baseAreaDao.selectById(baseAreaEntity);
                        if (baseAreaEntity != null) {
                            baseUserEntity.setAttachCompanyName(baseAreaEntity.getName());
                        }
                    }
                    //代理商信息
                    if (baseUserEntity.getAttachType() == 3) {
                        baseUserEntity.setAttachTypeName(UserAttachType.AGENT.getName());
                        
                        Integer agentSeq = baseUserEntity.getAttachSeq();
                        BaseAgentEntity BaseAgentEntity = new BaseAgentEntity();
                        BaseAgentEntity.setSeq(agentSeq);
                        BaseAgentEntity = baseAgentDao.selectById(BaseAgentEntity);
                        if (BaseAgentEntity != null) {
                            baseUserEntity.setAttachRemark(BaseAgentEntity.getRemark());
                            baseUserEntity.setAttachCompanyName(BaseAgentEntity.getAgentName());
                        }
                    }
                    // 去除账号中的密码
                    baseUserEntity.setPassword("");
                }
            }
        }
        return new PageUtils(userList, totalCount, limit, page);
    }

    // 根据用户的序号删除用户
    @Override
    public Integer deleteOrderParty(Integer seq) {
        List<OrderUserJurisdictionEntity> list = userJurisdictionDao
                .selectList(new EntityWrapper<OrderUserJurisdictionEntity>().eq("User_Seq", seq));
        for (OrderUserJurisdictionEntity orderUserJurisdictionEntity : list) {
            orderUserJurisdictionEntity.setDel(1);
            userJurisdictionDao.updateById(orderUserJurisdictionEntity);
        }
        return orderPartyManagementDao.deleteOrderParty(seq);
    }

    // 根据用户seq获得订货方的具体信息(修改信息)
    @Override
    public BaseUserEntity edit(Integer seq) {
        BaseUserEntity baseUserEntity = new BaseUserEntity();
        baseUserEntity.setSeq(seq);
        baseUserEntity = baseUserDao.selectById(baseUserEntity);
        if (baseUserEntity != null) {
//            if (baseUserEntity.getShopSeq() != null && baseUserEntity.getShopSeq() != 0) {// 首先是否是门店
//                BaseShopEntity baseShopEntity = new BaseShopEntity();
//                baseShopEntity.setSeq(baseUserEntity.getShopSeq());
//                baseShopEntity = baseShopDao.selectById(baseShopEntity);
//                baseUserEntity.setShopName(baseShopEntity.getName());
//            }
            
        	//工厂信息
            if (baseUserEntity.getAttachType() == 1) {
                baseUserEntity.setAttachTypeName(UserAttachType.FACTORY.getName());
            }
            
        	//分公司信息
            if (baseUserEntity.getAttachType() == 2) {
                baseUserEntity.setAttachTypeName(UserAttachType.OFFICE.getName());
                
                BaseAreaEntity baseAreaEntity = new BaseAreaEntity();
                baseAreaEntity.setSeq(baseUserEntity.getAttachSeq());
                baseAreaEntity = baseAreaDao.selectById(baseAreaEntity);
                if (baseAreaEntity != null) {
                    baseUserEntity.setAttachCompanyName(baseAreaEntity.getName());
                }
            }
            //代理商信息
            if (baseUserEntity.getAttachType() == 3) {
                baseUserEntity.setAttachTypeName(UserAttachType.AGENT.getName());
                
                Integer agentSeq = baseUserEntity.getAttachSeq();
                BaseAgentEntity BaseAgentEntity = new BaseAgentEntity();
                BaseAgentEntity.setSeq(agentSeq);
                BaseAgentEntity = baseAgentDao.selectById(BaseAgentEntity);
                if (BaseAgentEntity != null) {
                    baseUserEntity.setAttachRemark(BaseAgentEntity.getRemark());
                    baseUserEntity.setAttachCompanyName(BaseAgentEntity.getAgentName());
                }
            }
            
            // 去除账号中的密码
            baseUserEntity.setPassword("");
        }
        return baseUserEntity;
    }

    // 修改账户信息
    @Override
    public int updateOrderParty(BaseUserEntity baseUserEntity, BaseUserEntity userEntity) {
        if (baseUserEntity.getSaleType() == 2 || baseUserEntity.getSaleType() == 3) {
            baseUserEntity.setShopSeq("");
        }
        if (baseUserEntity.getAttachType() == 1) {
            baseUserEntity.setAttachSeq(userEntity.getCompanySeq());
        }
        if(StringUtils.isNotBlank(baseUserEntity.getPassword())) {
        	baseUserEntity.setPassword(DigestUtils.sha256Hex(baseUserEntity.getPassword()));
        }
        return baseUserDao.updateById(baseUserEntity);
    }

    // 添加账户
    @Override
    @Transactional
    public void addOrderParty(BaseUserEntity baseUser, BaseUserEntity baseUserEntity) throws Exception {
        if (baseUserEntity.getSaleType() == 1) {
            throw new RuntimeException("不能创建该类型的用户！");
        }
        if (baseUserEntity.getSaleType() == 4 && baseUserEntity.getAttachType() != 3) {
            if (baseUserEntity.getShopSeq() == null || baseUserEntity.getShopSeq().equals("")) {
                throw new RuntimeException("请选择门店！");
            }
        } else {
            baseUserEntity.setShopSeq("");
        }
        if (baseUserEntity.getAttachType() == 1) {
            baseUserEntity.setAttachSeq(null);
        }
        baseUserEntity.setCompanySeq(baseUser.getCompanySeq());
        baseUserEntity.setBrandSeq(baseUser.getBrandSeq());
        baseUserEntity.setPassword(DigestUtils.sha256Hex(baseUserEntity.getPassword()));
        baseUserDao.insert(baseUserEntity);
        BaseCompanyEntity baseCompanyEntity = baseCompanyDao.selectById(baseUser);
        /*if("依婷公主".equals(baseCompanyEntity.getName())) {
            // 注册融云生成token
            String rongCloudToken = rongCloudUtils.registerUser(baseUserEntity.getSeq(), baseUserEntity.getUserName(),
                    "1.jpg");
            if (rongCloudToken == null) {
                throw new RuntimeException("获取融云Token失败");
            }
            baseUserEntity.setRongCloudToken(rongCloudToken);
        }*/
        baseUserDao.updateById(baseUserEntity);

        // 4.创建用户权限
        OrderUserJurisdictionEntity userJurisdictionEntity = new OrderUserJurisdictionEntity();
        userJurisdictionEntity.setUserSeq(baseUserEntity.getSeq());
        userJurisdictionEntity.setCreateUserSeq(baseUser.getSeq());
        // 时间处理
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 3);
        Date effectiveDate = calendar.getTime();
        userJurisdictionEntity.setEffectiveDate(effectiveDate);
        userJurisdictionDao.insert(userJurisdictionEntity);
        
        //已创建用户+1
        OrderUserJurisdictionEntity orderUserJurisdictionEntity = new OrderUserJurisdictionEntity();
        orderUserJurisdictionEntity.setUserSeq(baseUser.getSeq());
        orderUserJurisdictionEntity = userJurisdictionDao.selectOne(orderUserJurisdictionEntity);
        orderUserJurisdictionEntity.setAlreadyCreateUsers(orderUserJurisdictionEntity.getAlreadyCreateUsers() + 1);
        userJurisdictionDao.updateById(orderUserJurisdictionEntity);
    }

    // 查询该用户当前剩余的创建用户的名额
    @Override
    public int restOfQuota(Long userSeq) {
        return orderPartyManagementDao.restOfQuota(userSeq);
    }

    // 判断该账号是否存在
    @Override
    public boolean judgeUserOrCompanyExistence(BaseUserEntity baseUserEntity) {
        List<BaseUserEntity> list = baseUserDao.selectList(
                new EntityWrapper<BaseUserEntity>().eq("AccountName", baseUserEntity.getAccountName()).eq("Del", 0));
        if (list != null) {
            for (BaseUserEntity UserEntity : list) {
                if (!UserEntity.getSeq().equals(baseUserEntity.getSeq())) {
                    return true;
                }
            }
        }
        return false;
    }

    
    @Override
    public int disable(Integer seq, Integer disable) {
        if (disable == 0) {// 禁用
            userJurisdictionDao.updateDisable(seq, 1);
        }
        if (disable == 1) {// 解禁
            userJurisdictionDao.updateDisable(seq, 0);
        }
        return 0;
    }

    // 返回工厂或某一分公司的门店列表
    @Override
    public List<BaseShopEntity> shopList(BaseUserEntity baseUserEntity, Integer attachSeq) {

        //分公司序号
        List<Integer> areaSeqList = new ArrayList<Integer>();
        
        if(attachSeq == null) {
        	//查询工厂所有区域
	        List<BaseAreaEntity> areaList = baseAreaDao.selectList(new EntityWrapper<BaseAreaEntity>().eq("brand_Seq", baseUserEntity.getBrandSeq()));
	        for (BaseAreaEntity baseAreaEntity : areaList) {
	            areaSeqList.add(baseAreaEntity.getSeq());
	        }
        } else {
        	//指定某一分公司
        	areaSeqList.add(attachSeq);
        }
        List<BaseShopEntity> shopList = baseShopDao.selectList(new EntityWrapper<BaseShopEntity>().in("Area_seq", areaSeqList).orderBy("Area_seq ASC"));
        return shopList;
    }

    @Override
    public List<BaseAgentEntity> agentList(Integer brandSeq) {
        return baseAgentDao.selectList(new EntityWrapper<BaseAgentEntity>().eq("Brand_Seq", brandSeq).eq("Del", 0));
    }

    @Override
    public List<BaseAreaEntity> branchOfficeList(Integer brandSeq) {
        return baseAreaDao
                .selectList(new EntityWrapper<BaseAreaEntity>().eq("Brand_Seq", brandSeq).eq("Del", 0));
    }

	@Override
	public PageUtils meetingPartyList(BaseUserEntity loginUser, Integer saleType, Integer page, Integer limit,Integer seq) {
		 List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();
	        Integer totalCount = null;
	        Integer start = new Integer(0);
	        Integer num = new Integer(0);

	        if (page >= 1 && limit > 0) {
	            start = (page - 1) * limit;
	            num = page * limit;
	        }

	        userList = baseUserDao.selectByMeetingSeqList(loginUser.getCompanySeq(), saleType, start, num,seq);
	        totalCount = baseUserDao.getMeetingTotalCount(loginUser.getCompanySeq(), saleType,seq);

	        if (userList != null && !userList.isEmpty()) {
	            for (Map<String, Object> baseUserEntity : userList) {
	            	
	                if (baseUserEntity != null) {
	                	logger.error(baseUserEntity.toString());
	                	//工厂信息
	                    if (baseUserEntity.get("AttachType").equals(1)) {
	                        baseUserEntity.put("attachTypeName",UserAttachType.FACTORY.getName());
	                    }
	                    
	                	//分公司信息
	                    if (baseUserEntity.get("AttachType").equals(2)) {
	                        baseUserEntity.put("attachTypeName",UserAttachType.OFFICE.getName());
	                        
	                        BaseAreaEntity baseAreaEntity = new BaseAreaEntity();
	                        baseAreaEntity.setSeq((int)(long)baseUserEntity.get("Attach_Seq"));
	                        baseAreaEntity = baseAreaDao.selectById(baseAreaEntity);
	                        if (baseAreaEntity != null) {
	                            baseUserEntity.put("attachComapnyName",baseAreaEntity.getName());
	                        }
	                    }
	                    //代理商信息
	                    if (baseUserEntity.get("AttachType").equals(3)) {
	                        baseUserEntity.put("attachTypeName",UserAttachType.AGENT.getName());
	                        
	                        long agentSeq =(Long)baseUserEntity.get("Attach_Seq");
	                        BaseAgentEntity BaseAgentEntity = new BaseAgentEntity();
	                        BaseAgentEntity.setSeq((int)agentSeq);
	                        BaseAgentEntity = baseAgentDao.selectById(BaseAgentEntity);
	                        if (BaseAgentEntity != null) {
	                            baseUserEntity.put("attachRemark",BaseAgentEntity.getRemark());
	                            baseUserEntity.put("attachComapnyName",BaseAgentEntity.getAgentName());
	                        }
	                    }
	                    // 去除账号中的密码
	                    baseUserEntity.put("password","");
	                }
	            }
	        }
	        return new PageUtils(userList, totalCount, limit, page);
	}
	
	@Override
	public PageUtils meetingAreaUserList(BaseUserEntity loginUser, Integer saleType, Integer page, Integer limit,Integer seq) {
		 List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();
	        Integer totalCount = null;
	        Integer start = new Integer(0);
	        Integer num = new Integer(0);

	        if (page >= 1 && limit > 0) {
	            start = (page - 1) * limit;
	            num = page * limit;
	        }

	        userList = baseUserDao.selectByAreaSeqList(loginUser.getCompanySeq(), saleType, start, num,seq);
	        totalCount = baseUserDao.getAreaTotalCount(loginUser.getCompanySeq(), saleType,seq);

	        if (userList != null && !userList.isEmpty()) {
	            for (Map<String, Object> baseUserEntity : userList) {
	            	
	                if (baseUserEntity != null) {
	                	
	                	//工厂信息
	                    if (baseUserEntity.get("AttachType").equals(1)) {
	                        baseUserEntity.put("attachTypeName",UserAttachType.FACTORY.getName());
	                    }
	                    
	                	//分公司信息
	                    if (baseUserEntity.get("AttachType").equals(2)) {
	                        baseUserEntity.put("attachTypeName",UserAttachType.OFFICE.getName());
	                        
	                        BaseAreaEntity baseAreaEntity = new BaseAreaEntity();
	                        baseAreaEntity.setSeq((int)(long)baseUserEntity.get("Attach_Seq"));
	                        baseAreaEntity = baseAreaDao.selectById(baseAreaEntity);
	                        if (baseAreaEntity != null) {
	                            baseUserEntity.put("attachComapnyName",baseAreaEntity.getName());
	                        }
	                    }
	                    //代理商信息
	                    if (baseUserEntity.get("AttachType").equals(3)) {
	                        baseUserEntity.put("attachTypeName",UserAttachType.AGENT.getName());
	                        
	                        long agentSeq =(Long)baseUserEntity.get("Attach_Seq");
	                        BaseAgentEntity BaseAgentEntity = new BaseAgentEntity();
	                        BaseAgentEntity.setSeq((int)agentSeq);
	                        BaseAgentEntity = baseAgentDao.selectById(BaseAgentEntity);
	                        if (BaseAgentEntity != null) {
	                            baseUserEntity.put("attachRemark",BaseAgentEntity.getRemark());
	                            baseUserEntity.put("attachComapnyName",BaseAgentEntity.getAgentName());
	                        }
	                    }
	                    // 去除账号中的密码
	                    baseUserEntity.put("password","");
	                }
	            }
	        }
	        return new PageUtils(userList, totalCount, limit, page);
	}

	@Override
	public Boolean hasUserInOthers(Integer userSeq) {
		Wrapper<MeetingOrderEntity> wrapper=new EntityWrapper<MeetingOrderEntity>();
		wrapper.where("User_Seq = {0}", userSeq);
		List<MeetingOrderEntity> list=meetingOrderDao.selectList(wrapper);
		
		Wrapper<MeetingPermissionEntity> wrapper2=new EntityWrapper<MeetingPermissionEntity>();
		wrapper2.where("User_Seq = {0}", userSeq);
		List<MeetingPermissionEntity> list2=meetingPermissionDao.selectList(wrapper2);
		
		Wrapper<MeetingShoppingCartEntity> wrapper3=new EntityWrapper<MeetingShoppingCartEntity>();
		wrapper3.where("User_Seq = {0}", userSeq);
		List<MeetingShoppingCartEntity> list3=meetingShoppingCartDao.selectList(wrapper3);
		
		Wrapper<MeetintUserAreaEntity> wrapper4=new EntityWrapper<MeetintUserAreaEntity>();
		wrapper4.where("User_Seq = {0}", userSeq);
		List<MeetintUserAreaEntity> list4=meetintUserAreaDao.selectList(wrapper4);
		if ((list != null && list.size() > 0)||(list2 != null && list2.size() > 0)||(list3 != null && list3.size() > 0)||(list4 != null && list4.size() > 0)) {
            return true;
        } else {
            return false;
        }
	}


}
