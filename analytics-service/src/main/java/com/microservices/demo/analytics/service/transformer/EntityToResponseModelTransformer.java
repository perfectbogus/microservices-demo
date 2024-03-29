package com.microservices.demo.analytics.service.transformer;

import com.microservices.demo.analytics.service.dataaccess.entity.impl.AnalyticsEntity;
import com.microservices.demo.analytics.service.model.AnalyticsResponseModel;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EntityToResponseModelTransformer {

    public Optional<AnalyticsResponseModel> getResponseModel(AnalyticsEntity twitterAnalyticsEntity) {
        if (twitterAnalyticsEntity == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(
                AnalyticsResponseModel.builder()
                        .id(twitterAnalyticsEntity.getId())
                        .word(twitterAnalyticsEntity.getWord())
                        .wordCount(twitterAnalyticsEntity.getWordCount())
                        .build()
        );
    }
}
