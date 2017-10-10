package com.sjony.MQ.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class QueueListenter2 implements MessageListener {

	@Override
	public void onMessage(Message msg) {
		try {
			byte[] s = msg.getBody();
			JSONObject json = JSON.parseObject(s, null);
			System.out.println(msg.toString());
			System.out.println("*********************************");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
