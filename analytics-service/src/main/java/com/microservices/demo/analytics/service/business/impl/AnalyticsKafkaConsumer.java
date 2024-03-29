package com.microservices.demo.analytics.service.business.impl;

import com.microservices.demo.analytics.service.business.KafkaConsumer;
import com.microservices.demo.analytics.service.dataaccess.entity.impl.AnalyticsEntity;
import com.microservices.demo.analytics.service.dataaccess.entity.repository.AnalyticsRepository;
import com.microservices.demo.analytics.service.transformer.AvroToDbEntityModelTransformer;
import com.microservices.demo.config.KafkaConfigData;
import com.microservices.demo.kafka.admin.client.KafkaAdminClient;
import com.microservices.demo.kafka.avro.model.TwitterAnalyticsAvroModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AnalyticsKafkaConsumer implements KafkaConsumer<TwitterAnalyticsAvroModel> {

    private static final Logger LOG = LoggerFactory.getLogger(AnalyticsKafkaConsumer.class);
    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    private final KafkaAdminClient kafkaAdminClient;
    private final KafkaConfigData kafkaConfig;
    private final AvroToDbEntityModelTransformer avroToDbEntityModelTransformer;
    private final AnalyticsRepository analyticsRepository;

    public AnalyticsKafkaConsumer(KafkaListenerEndpointRegistry registry,
                                  KafkaAdminClient adminClient,
                                  KafkaConfigData config, AvroToDbEntityModelTransformer avroToDbEntityModelTransformer, AnalyticsRepository analyticsRepository) {
        this.kafkaListenerEndpointRegistry = registry;
        this.kafkaAdminClient = adminClient;
        this.kafkaConfig = config;
        this.avroToDbEntityModelTransformer = avroToDbEntityModelTransformer;
        this.analyticsRepository = analyticsRepository;
    }

    @EventListener
    public void onAppStarted(ApplicationStartedEvent event) {
        kafkaAdminClient.checkTopicsCreated();
        LOG.info("Topics with name {} is ready for operations!", kafkaConfig.getTopicNamesToCreate().toArray());
        Objects.requireNonNull(kafkaListenerEndpointRegistry.getListenerContainer("twitterAnalyticsTopicListener")).start();
    }

    @Override
    @KafkaListener(id = "twitterAnalyticsTopicListener", topics = "${kafka-config.topic-name}", autoStartup = "false")
    public void receive(@Payload List<TwitterAnalyticsAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        LOG.info("{} number of message received with keys {}, partitions {} and offsets {}, sending it to database: Thread id {}",
                messages.size(), keys.toString(), partitions.toString(), offsets.toString(), Thread.currentThread().getName());
        List<AnalyticsEntity> twitterAnalyticsEntities = avroToDbEntityModelTransformer.getEntityModel(messages);
        LOG.info("twitterAnalyticsEntities {}", twitterAnalyticsEntities.toString());
        analyticsRepository.batchPersist(twitterAnalyticsEntities);
        LOG.info("{} number of messages send to database", twitterAnalyticsEntities.size());
    }

}