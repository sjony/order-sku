package com.sjony.MQ.impl;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class QueueListenter implements MessageListener {

	@Override
	public void onMessage(Message msg) {
		try {
			System.out.println(msg.toString());
			System.out.println("-------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
