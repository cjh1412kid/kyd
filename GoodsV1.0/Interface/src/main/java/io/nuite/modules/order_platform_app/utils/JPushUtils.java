package io.nuite.modules.order_platform_app.utils;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import io.nuite.modules.sr_base.utils.ConfigUtils;

/**
 * 极光推送工具类
 * API 文档: https://docs.jiguang.cn/jpush/server/push/rest_api_v3_push/
 * 源码: https://github.com/jpush/jpush-api-java-client
 * 例子源码: https://github.com/jpush/jpush-api-java-client/tree/master/example/main/java/cn/jpush/api/examples
 * @author yy
 * 
 */
@Component
public class JPushUtils {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ConfigUtils configUtils;

	
	/**
	 * 使用极光推送发送推送消息（notification和message必选其一，可同时存在）
	 * @param aliasList 要推送给的注册id的别名List（目前使用用户账号作别名）
	 * @param notification 目前是alert类型，可在app退出状态下，在手机通知栏显示
	 * @param message 自定义消息，必须手机在前台才能显示，
	 * 			包括:   msg_content	string	必填	消息内容本身
	 *					title	string	可选	消息标题
	 *					content_type	string	可选	消息内容类型（目前0~7表示订单，-1表示直播，-2表示订货会普通通知，-3表示订货会解除锁定）
	 *					extras	JSON Object	可选	额外参数JSON 
	 */
	public void sendPush(List<String> aliasList, String notification, String msgContent, String msgType) {
		try {
			// 极光对接参数
			String appKey = configUtils.getOrderPlatformApp().getjPushAppKey();
			String masterSecret = configUtils.getOrderPlatformApp().getjPushMasterSecret();
			boolean apnsProduction = configUtils.getOrderPlatformApp().isjPushApnsProduction();
			
			ClientConfig clientConfig = ClientConfig.getInstance();
			final JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, clientConfig);
			final PushPayload payload = buildPushObject_all_alias_alert(aliasList, notification, msgContent, msgType, apnsProduction);
			try {
			    PushResult result = jpushClient.sendPush(payload);
			    logger.info("极光推送成功: " + result);
			} catch (APIConnectionException e) {
				logger.error("极光推送，发送编号" + payload.getSendno() + "，连接失败，需要稍后重试", e);
				//极光推送时默认重试3次，如果都失败，用线程5分钟后重新发送一次
				retrySendPush(jpushClient, payload);
			} catch (APIRequestException e) {
				logger.error("极光推送，发送编号" + payload.getSendno() + "，消息有误，应当重新查看并修改: " + payload, e);
			}
		} catch (Exception e) {
			logger.error("极光推送发送推送消息异常:" + e.getMessage(), e);
		}
    }
	
	
    private PushPayload buildPushObject_all_alias_alert(List<String> aliasList, String notification, String msgContent, String msgType, boolean apnsProduction) {
        if(notification != null) {
	    	return PushPayload.newBuilder()
	                .setPlatform(Platform.all())
	                .setAudience(Audience.alias(aliasList))
	                .setNotification(Notification.alert(notification))
	                .setMessage(Message.newBuilder()
	                        .setMsgContent(msgContent)
	                        .setContentType(msgType)
	                        .addExtra("type", msgType)
	                        .build())
	                .setOptions(Options.newBuilder()
	                        .setApnsProduction(apnsProduction)
	                        .build())
	                .build();
        } else {
	        return PushPayload.newBuilder()
	                .setPlatform(Platform.all())
	                .setAudience(Audience.alias(aliasList))
	                .setMessage(Message.newBuilder()
	                        .setMsgContent(msgContent)
	                        .setContentType(msgType)
	                        .addExtra("type", msgType)
	                        .build())
	                .setOptions(Options.newBuilder()
	                        .setApnsProduction(apnsProduction)
	                        .build())
	                .build();
        }
    }
    
	private void retrySendPush(JPushClient jpushClient, PushPayload payload) {
		final ScheduledThreadPoolExecutor scheduledThreadPool = new ScheduledThreadPoolExecutor(1);
		scheduledThreadPool.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					PushResult result = jpushClient.sendPush(payload);
					logger.info("极光推送重试，推送成功: " + result);
				} catch (APIConnectionException e) {
					logger.error("极光推送重试，发送编号" + payload.getSendno() + "，连接失败", e);
				} catch (APIRequestException e) {
					logger.error("极光推送重试，发送编号" + payload.getSendno() + "，消息有误，应当重新查看并修改: " + payload, e);
				}
			}
		}, 5, TimeUnit.MINUTES);

		// 关闭线程池
		scheduledThreadPool.shutdown();
	}
	
}

