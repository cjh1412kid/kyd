package io.nuite.modules.system.service.impl.online_sale;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.nuite.common.utils.PageUtils;
import io.nuite.modules.system.dao.online_sale.OnlineUserManagementDao;
import io.nuite.modules.system.service.online_sale.OnlineUserManagementService;

@Service
public class OnlineUserManagementServiceImpl implements OnlineUserManagementService {
    @Autowired
    private OnlineUserManagementDao onlineUserManagementDao;
    
    //客户列表
    public PageUtils getUsersList(Long userSeq, String keywords, Integer page, Integer limit) {
        List<Map<String,Object>>  usersList = onlineUserManagementDao.getUsersList(userSeq,keywords,page,limit);
         return new PageUtils(usersList, usersList.size(), limit, page);
    }

}
