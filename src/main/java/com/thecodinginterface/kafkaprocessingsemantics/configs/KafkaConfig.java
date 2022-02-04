package com.thecodinginterface.kafkaprocessingsemantics.configs;

import com.thecodinginterface.kafkaprocessingsemantics.models.Person;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    String groupId;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    String autoOffsetReset;

    @Value("${kafka.topics.people.name}")
    String peopleTopicName;

    @Value("${kafka.topics.people.partitions}")
    int peopleTopicPartitions;

    @Value("${kafka.topics.people.replication-factor}")
    int peopleTopicReplicationFactor;

    @Bean
    public NewTopic peopleTopic() {
        return TopicBuilder.name(peopleTopicName)
                .partitions(peopleTopicPartitions)
                .replicas(peopleTopicReplicationFactor)
                .build();
    }

    @Bean
    public ConsumerFactory<String, Person> peopleConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                Map.of(
                        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset,
                        ConsumerConfig.GROUP_ID_CONFIG, groupId),
                new StringDeserializer(),
                new JsonDeserializer<>(Person.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Person> personListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, Person>();
        factory.setConsumerFactory(peopleConsumerFactory());
        return factory;
    }
}
