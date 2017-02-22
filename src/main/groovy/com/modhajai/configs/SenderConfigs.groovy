package com.modhajai.configs

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.IntegerSerializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

/**
 * @author Jai Modha
 * @since 2017-02-17
 * This represents the configuration required for connecting to kafka.
 */
@EnableKafka
@Configuration
class SenderConfigs {

    @Value('${kafka.bootstrap.server}')
    String bootstrapServer

    @Bean
    Map producerConfigs() {
        def properties = [:]
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer)
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class)
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class)
        properties
    }

    @Bean
    ProducerFactory producerFactory() {
        new DefaultKafkaProducerFactory<>(producerConfigs())
    }

    @Bean
    KafkaTemplate kafkaTemplate() {
        new KafkaTemplate(producerFactory())
    }

    @Bean
    public Sender sender() {
        return new Sender();
    }

}
