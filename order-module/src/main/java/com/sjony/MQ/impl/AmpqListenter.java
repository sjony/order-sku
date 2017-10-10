package com.sjony.MQ.impl;//package com.sjony.MQ.impl;
//
//import java.util.Map;
//
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.support.CorrelationData;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.alibaba.fastjson.JSON;
//
//@Component
//public class AmpqListenter {
//	@Autowired
//	private RabbitTemplate rabbitTemplate;
//
//	public void listenter(Map t) {
//		Message s = rabbitTemplate.receive("test_queue_key");
//
//		System.out.print(JSON.toJSONString(t));
//	}
//}
