package com.thecodinginterface.kafkaprocessingsemantics;

import com.thecodinginterface.kafkaprocessingsemantics.models.Person;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.Map;

@SpringBootApplication
public class KafkaProcessingSemanticsApplication {
	static final Logger logger = LoggerFactory.getLogger(KafkaProcessingSemanticsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(KafkaProcessingSemanticsApplication.class, args);
	}


	@KafkaListener(topics = "#{'${kafka.topics.people.name}'}", containerFactory = "personListenerContainerFactory")
	public void listener(Person person) {
		logger.info("Consuming person " + person);
	}

}
