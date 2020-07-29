package io.nuite.modules.online_sales_app.wx;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaKefuMessage;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import cn.binarywang.wx.miniapp.message.WxMaMessageHandler;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.google.common.collect.Lists;
import io.nuite.modules.online_sales_app.entity.MiniAppEntity;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;

import java.io.File;

public class WxMyService {
    private WxMaService wxMaService;
    private WxMaMessageRouter wxMaMessageRouter;
    private WxPayService wxPayService;

    WxMyService(MiniAppEntity miniAppEntity) {
        WxMaInMemoryConfig maConfig = new WxMaInMemoryConfig();
        maConfig.setAppid(miniAppEntity.getAppId());
        maConfig.setSecret(miniAppEntity.getAppSecret());
        maConfig.setToken("111");
        maConfig.setAesKey("111");
        maConfig.setMsgDataFormat("JSON");

        wxMaService = new WxMaServiceImpl();
        wxMaService.setWxMaConfig(maConfig);

        wxMaMessageRouter = new WxMaMessageRouter(wxMaService);
        WxMaMessageHandler templateMsgHandler = (wxMessage, context, service, sessionManager) ->
                service.getMsgService().sendTemplateMsg(WxMaTemplateMessage.builder()
                        .templateId("此处更换为自己的模板id")
                        .formId("自己替换可用的formid")
                        .data(Lists.newArrayList(
                                new WxMaTemplateMessage.Data("keyword1", "339208499", "#173177")))
                        .toUser(wxMessage.getFromUser())
                        .build());
        WxMaMessageHandler logHandler = (wxMessage, context, service, sessionManager) -> {
            System.out.println("收到消息：" + wxMessage.toString());
            service.getMsgService().sendKefuMsg(WxMaKefuMessage.newTextBuilder().content("收到信息为：" + wxMessage.toJson())
                    .toUser(wxMessage.getFromUser()).build());
        };
        WxMaMessageHandler textHandler = (wxMessage, context, service, sessionManager) ->
                service.getMsgService().sendKefuMsg(WxMaKefuMessage.newTextBuilder().content("回复文本消息")
                        .toUser(wxMessage.getFromUser()).build());
        WxMaMessageHandler picHandler = (wxMessage, context, service, sessionManager) -> {
            try {
                WxMediaUploadResult uploadResult = service.getMediaService()
                        .uploadMedia("image", "png",
                                ClassLoader.getSystemResourceAsStream("tmp.png"));
                service.getMsgService().sendKefuMsg(
                        WxMaKefuMessage
                                .newImageBuilder()
                                .mediaId(uploadResult.getMediaId())
                                .toUser(wxMessage.getFromUser())
                                .build());
            } catch (WxErrorException e) {
                e.printStackTrace();
            }
        };
        WxMaMessageHandler qrcodeHandler = (wxMessage, context, service, sessionManager) -> {
            try {
                final File file = service.getQrcodeService().createQrcode("123", 430);
                WxMediaUploadResult uploadResult = service.getMediaService().uploadMedia("image", file);
                service.getMsgService().sendKefuMsg(
                        WxMaKefuMessage
                                .newImageBuilder()
                                .mediaId(uploadResult.getMediaId())
                                .toUser(wxMessage.getFromUser())
                                .build());
            } catch (WxErrorException e) {
                e.printStackTrace();
            }
        };
        wxMaMessageRouter.rule().handler(logHandler).next()
                .rule().async(false).content("模板").handler(templateMsgHandler).end()
                .rule().async(false).content("文本").handler(textHandler).end()
                .rule().async(false).content("图片").handler(picHandler).end()
                .rule().async(false).content("二维码").handler(qrcodeHandler).end();

        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(miniAppEntity.getAppId());
        payConfig.setMchId(miniAppEntity.getMchId());
        payConfig.setMchKey(miniAppEntity.getMchKey());
        payConfig.setKeyPath(miniAppEntity.getKeyPath());
        payConfig.setTradeType("JSAPI");
        payConfig.setNotifyUrl("https://www.fyweather.com/SmartSale/interface/online/miniapp/order/payNotify");

        wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
    }
    WxMyService(String appId,String appSecret) {
        WxMaInMemoryConfig maConfig = new WxMaInMemoryConfig();
        maConfig.setAppid(appId);
        maConfig.setSecret(appSecret);
        maConfig.setToken("111");
        maConfig.setAesKey("111");
        maConfig.setMsgDataFormat("JSON");

        wxMaService = new WxMaServiceImpl();
        wxMaService.setWxMaConfig(maConfig);
        }
    public WxMaService getWxMaService() {
        return wxMaService;
    }

    public WxMaMessageRouter getWxMaMessageRouter() {
        return wxMaMessageRouter;
    }

    public WxPayService getWxPayService() {
        return wxPayService;
    }
}
