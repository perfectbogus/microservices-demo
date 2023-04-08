package com.microservices.demo.analytics.service.business;

import com.microservices.demo.kafka.avro.model.TwitterAnalyticsAvroModel;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.List;

public interface KafkaConsumer<T extends SpecificRecordBase> {
    @KafkaListener(id = "twitterAnalyticsTopicListener", topics = "${kafka-config.topic-name}", autoStartup = "false")
    void receive(@Payload List<TwitterAnalyticsAvroModel> messages,
                 @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<Long> keys,
                 @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                 @Header(KafkaHeaders.OFFSET) List<Long> offsets);
}
