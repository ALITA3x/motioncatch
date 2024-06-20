package com.itheima.mqtt.client;

import com.itheima.mqtt.enums.QosEnum;
import com.itheima.mqtt.properties.MqttProperties;
import org.eclipse.paho.mqttv5.client.*;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by 传智播客*黑马程序员.
 */
@Component
public class EmqClient {

    private static final Logger log = LoggerFactory.getLogger(EmqClient.class);


    private IMqttClient mqttClient;

    @Autowired
    private MqttProperties mqttProperties;

    @Autowired
    private MqttCallback mqttCallback;


    @PostConstruct
    public void init(){
        MqttClientPersistence mempersitence = new MemoryPersistence();
        try {
            mqttClient = new MqttClient(mqttProperties.getBrokerUrl(),mqttProperties.getClientId(),mempersitence);
        } catch (MqttException e) {
            log.error("初始化客户端mqttClient对象失败,errormsg={},brokerUrl={},clientId={}",e.getMessage(),mqttProperties.getBrokerUrl(),mqttProperties.getClientId());
        }

    }

    /**
     * 连接broker
     * @param username
     * @param password
     */
    public void connect(String username,String password){
        MqttConnectionOptions options = new MqttConnectionOptions();
        options.setAutomaticReconnect(true);
        options.setUserName(username);
        options.setPassword(password.getBytes());
//        options.setCleanSession(true);
        options.setCleanStart(true);
        options.setKeepAliveInterval(10);
        mqttClient.setCallback(mqttCallback);

        try {
            mqttClient.connect(options);
        } catch (MqttException e) {
            log.error("mqtt客户端连接服务端失败,失败原因{}",e.getMessage());
        }
    }

    /**
     * 断开连接
     */
    @PreDestroy
    public void disConnect(){
        try {
            mqttClient.disconnect();
        } catch (MqttException e) {
            log.error("断开连接产生异常,异常信息{}",e.getMessage());
        }
    }

    /**
     * 重连
     */
    public void reConnect(){
        try {
            mqttClient.reconnect();
        } catch (MqttException e) {
            log.error("重连失败,失败原因{}",e.getMessage());
        }
    }

    /**
     * 发布消息
     * @param topic
     * @param msg
     * @param qos
     * @param retain
     */
    public void publish(String topic, String msg, QosEnum qos,boolean retain){

        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(msg.getBytes());
        mqttMessage.setQos(qos.value());
        mqttMessage.setRetained(retain);
        try {
            mqttClient.publish(topic,mqttMessage);
        } catch (MqttException e) {
            log.error("发布消息失败,errormsg={},topic={},msg={},qos={},retain={}",e.getMessage(),topic,msg,qos.value(),retain);
        }

    }

    /**
     * 订阅
     * @param topicFilter
     * @param qos
     */
    public void subscribe(String topicFilter,QosEnum qos){
        try {
            mqttClient.subscribe(topicFilter,qos.value());
        } catch (MqttException e) {
            log.error("订阅主题失败,errormsg={},topicFilter={},qos={}",e.getMessage(),topicFilter,qos.value());
        }

    }

    /**
     * 取消订阅
     * @param topicFilter
     */
    public void unSubscribe(String topicFilter){
        try {
            mqttClient.unsubscribe(topicFilter);
        } catch (MqttException e) {
            log.error("取消订阅失败,errormsg={},topicfiler={}",e.getMessage(),topicFilter);
        }
    }

}
