package com.itheima;

import com.itheima.mqtt.client.EmqClient;
import com.itheima.mqtt.enums.QosEnum;
import com.itheima.mqtt.properties.MqttProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class EmqDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmqDemoApplication.class, args);
	}


	@Autowired
	private EmqClient emqClient;

	@Autowired
	private MqttProperties properties;

	 @PostConstruct
	public void init(){
		//连接服务端
		emqClient.connect(properties.getUsername(),properties.getPassword());
		//订阅一个主题
		emqClient.subscribe("/topic/pub/sun/device", QosEnum.QoS0);
		//开启一个新的线程 每隔5秒去向 testtopic/123
		/*new Thread(()->{
			while (true){
				emqClient.publish("testtopic/123"," publish msg :"+ LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
						QosEnum.QoS2,false);
				try {
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}


			}
		}).start();*/
	}

}
