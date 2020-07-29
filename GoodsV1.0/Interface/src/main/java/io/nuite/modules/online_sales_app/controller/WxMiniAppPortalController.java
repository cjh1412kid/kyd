package io.nuite.modules.online_sales_app.controller;

import cn.binarywang.wx.miniapp.bean.WxMaMessage;
import cn.binarywang.wx.miniapp.constant.WxMaConstants;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import io.nuite.modules.online_sales_app.entity.MiniAppEntity;
import io.nuite.modules.online_sales_app.service.OlsMiniAppService;
import io.nuite.modules.online_sales_app.wx.WxMyService;
import io.nuite.modules.online_sales_app.wx.WxServiceUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/online/miniapp")
public class WxMiniAppPortalController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OlsMiniAppService olsMiniAppService;

    @GetMapping(value = "portal/{miniId}", produces = "text/plain;charset=utf-8")
    public String authGet(@PathVariable(name = "miniId") String miniId,
                          @RequestParam(name = "signature", required = false) String signature,
                          @RequestParam(name = "timestamp", required = false) String timestamp,
                          @RequestParam(name = "nonce", required = false) String nonce,
                          @RequestParam(name = "echostr", required = false) String echostr) {
        this.logger.info("\n接收到来自微信服务器的认证消息：signature = [{}], timestamp = [{}], nonce = [{}], echostr = [{}]",
                signature, timestamp, nonce, echostr);

        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        MiniAppEntity miniAppEntity = olsMiniAppService.selectById(miniId);
        WxMyService wxMyService = WxServiceUtils.getWxMyService(miniAppEntity);
        if (wxMyService != null) {
            if (wxMyService.getWxMaService().checkSignature(timestamp, nonce, signature)) {
                return echostr;
            }
        }

        return "非法请求";
    }

    @PostMapping(value = "portal/{miniId}", produces = "application/xml; charset=UTF-8")
    public String post(@RequestBody String requestBody,
                       @PathVariable(name = "miniId") String miniId,
                       @RequestParam("msg_signature") String msgSignature,
                       @RequestParam("encrypt_type") String encryptType,
                       @RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce) {
        this.logger.info("\n接收微信请求：[msg_signature=[{}], encrypt_type=[{}], signature=[{}]," +
                        " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                msgSignature, encryptType, signature, timestamp, nonce, requestBody);

        MiniAppEntity miniAppEntity = olsMiniAppService.selectById(miniId);
        WxMyService wxMyService = WxServiceUtils.getWxMyService(miniAppEntity);
        if (wxMyService == null) {
            return "error";
        }
        final boolean isJson = Objects.equals(wxMyService.getWxMaService().getWxMaConfig().getMsgDataFormat(),
                WxMaConstants.MsgDataFormat.JSON);
        if (StringUtils.isBlank(encryptType)) {
            // 明文传输的消息
            WxMaMessage inMessage;
            if (isJson) {
                inMessage = WxMaMessage.fromJson(requestBody);
            } else {//xml
                inMessage = WxMaMessage.fromXml(requestBody);
            }

            this.route(inMessage, wxMyService.getWxMaMessageRouter());
            return "success";
        }

        if ("aes".equals(encryptType)) {
            // 是aes加密的消息
            WxMaMessage inMessage;
            if (isJson) {
                inMessage = WxMaMessage.fromEncryptedJson(requestBody, wxMyService.getWxMaService().getWxMaConfig());
            } else {//xml
                inMessage = WxMaMessage.fromEncryptedXml(requestBody, wxMyService.getWxMaService().getWxMaConfig(),
                        timestamp, nonce, msgSignature);
            }

            this.route(inMessage, wxMyService.getWxMaMessageRouter());
            return "success";
        }

        throw new RuntimeException("不可识别的加密类型：" + encryptType);
    }

    private void route(WxMaMessage message, WxMaMessageRouter router) {
        try {
            router.route(message);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
    }
}
