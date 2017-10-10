package com.sjony.MQ.impl;

import com.sjony.MQ.MQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MQProducerImpl implements MQProducer {
	@Autowired
	private AmqpTemplate amqpTemplate;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	private final static Logger LOGGER = LoggerFactory
			.getLogger(MQProducerImpl.class);

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.stnts.tita.rm.api.mq.MQProducer#sendDataToQueue(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public void sendDataToQueue(String queueKey, Object object) {
		try {
			UUID uuid = UUID.randomUUID();
			CorrelationData a = new CorrelationData(String.valueOf(uuid));

			rabbitTemplate.convertAndSend("topic_sjony", "test.11111", object, a);
		} catch (Exception e) {
			LOGGER.error("", e);
		}

	}
}
