package com.microservices.demo.analytics.service.business;

import com.microservices.demo.kafka.avro.model.TwitterAnalyticsAvroModel;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.List;

public interface KafkaConsumer<T extends SpecificRecordBase> {
    void receive(List<TwitterAnalyticsAvroModel> messages, List<Long> keys, List<Integer> partitions,List<Long> offsets);
}
