package io.nuite.modules.online_sales_app.wx;

import io.nuite.modules.online_sales_app.entity.MiniAppEntity;

import java.util.HashMap;
import java.util.Map;

public class WxServiceUtils {
    private static final Object mLock = new Object();
    private static Map<String, WxMyService> allWxMaServiceMap = new HashMap<>();

    public static WxMyService getWxMyService(MiniAppEntity miniAppEntity) {
        if (miniAppEntity == null || miniAppEntity.getCompanySeq() == null) {
            return null;
        }
        WxMyService wxMyService;
        String companySeq = miniAppEntity.getCompanySeq().toString();
        if (allWxMaServiceMap.containsKey(companySeq)) {
            wxMyService = allWxMaServiceMap.get(companySeq);
        } else {
            synchronized (mLock) {
                wxMyService = new WxMyService(miniAppEntity);
                allWxMaServiceMap.put(companySeq, wxMyService);
            }
        }
        return wxMyService;
    }
    
    public static WxMyService getWxMyService(String appId,String appSecret) {
      
        WxMyService wxMyService;
       
        if (allWxMaServiceMap.containsKey(appId)) {
            wxMyService = allWxMaServiceMap.get(appId);
        } else {
            synchronized (mLock) {
                wxMyService = new WxMyService(appId,appSecret);
                allWxMaServiceMap.put(appId, wxMyService);
            }
        }
        return wxMyService;
    }
}
