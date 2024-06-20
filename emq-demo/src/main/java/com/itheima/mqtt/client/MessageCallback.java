package com.itheima.mqtt.client;

import com.alibaba.fastjson2.JSONObject;
import com.itheima.mqtt.enums.QosEnum;
import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttCallback;
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;

/**
 * Created by 传智播客*黑马程序员.
 */
@Component
public class MessageCallback implements MqttCallback {

    private static final Logger log = LoggerFactory.getLogger(MessageCallback.class);

    EmqClient emqClient;

    @Autowired
    public MessageCallback(EmqClient emqClient) {
        this.emqClient = emqClient;
    }

    /**
     * 丢失了对服务端的连接后触发的回调
     * @param
     */
    /*@Override
    public void connectionLost(Throwable cause) {
        // 资源的清理  重连
        log.info("丢失了对服务端的连接");
    }*/

    @Override
    public void disconnected(MqttDisconnectResponse mqttDisconnectResponse) {
        log.info("丢失了对服务端的连接");
    }

    @Override
    public void mqttErrorOccurred(MqttException e) {
        log.info("mqtt错误{}" + e);
    }

    /**
     * 应用收到消息后触发的回调
     * @param topic
     * @param message
     * @throws Exception
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String data = new String(message.getPayload());
        log.info("订阅者订阅到了消息,topic={},messageid={},qos={},payload={}",
                topic,
                message.getId(),
                message.getQos(),
                data);
        JSONObject jsonObject = JSONObject.parseObject(data);
        String base64EncodedString = jsonObject.getJSONObject("payload").getString("heartbreath");
        byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedString);
        log.info("decodedBytes: {}", decodedBytes);
    }

    @Override
    public void deliveryComplete(IMqttToken token) {
        int messageId = token.getMessageId();
        String[] topics = token.getTopics();
        log.info("消息发布完成,messageid={},topics={}",messageId,topics);
    }

    @Override
    public void connectComplete(boolean b, String s) {
        log.info("连接完成,b={},s={}",b,s);
        emqClient.subscribe("/topic/pub/sun/device", QosEnum.QoS0);
    }

    @Override
    public void authPacketArrived(int i, MqttProperties mqttProperties) {
        log.info("authPacketArrived,mqttProperties={}",mqttProperties);
    }

    /**
     * 消息发布者消息发布完成产生的回调
     * @param token
     */
    /*@Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        int messageId = token.getMessageId();
        String[] topics = token.getTopics();
        log.info("消息发布完成,messageid={},topics={}",messageId,topics);
    }*/
}
