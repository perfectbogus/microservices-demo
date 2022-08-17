package com.microservices.demo.twitter.to.kafka.service.runner.impl;

import com.microservices.demo.config.TwitterToKafkaServiceConfigData;
import com.microservices.demo.twitter.to.kafka.service.runner.StreamRunner;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ConditionalOnExpression("${twitter-to-kafka-service.enable-v2-tweets} && not ${twitter-to-kafka-service.enable-mock-tweets}")
public class TwitterV2KafkaStreamRunner implements StreamRunner {
    private static final Logger LOG = LoggerFactory.getLogger(TwitterV2KafkaStreamRunner.class);
    private final TwitterToKafkaServiceConfigData twitterToKafkaServiceConfigData;
    private final TwitterV2StreamHelper twitterV2StreamHelper;

    public TwitterV2KafkaStreamRunner(TwitterToKafkaServiceConfigData twitterToKafkaServiceConfigData, TwitterV2StreamHelper twitterV2StreamHelper) {
        this.twitterToKafkaServiceConfigData = twitterToKafkaServiceConfigData;
        this.twitterV2StreamHelper = twitterV2StreamHelper;
    }

    @Override
    public void start() {
        final String bearerToken = twitterToKafkaServiceConfigData.getTwitterV2BearerToken();
        LOG.info("({})", twitterToKafkaServiceConfigData);
        if (bearerToken == null) {
            final String errorMessage = "There was a problem getting your bearer token " +
                    "please make sure you set the TWITTER_BEARER_TOKEN environment variable";
            LOG.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        try {
            twitterV2StreamHelper.setupRules(bearerToken, getRules());
            twitterV2StreamHelper.connectStream(bearerToken);
        } catch (IOException | URISyntaxException e) {
            final String errorMessage = "Error streaming tweets!";
            LOG.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> getRules() {
        List<String> keywords = twitterToKafkaServiceConfigData.getTwitterKeywords();
        Map<String, String> rules = new HashMap<>();
        for (String keyword : keywords) {
            rules.put(keyword, "Keyword: " + keyword);
        }
        LOG.info("Created filter for twitter stream for keywords {}", keywords);
        return rules;
    }
}
