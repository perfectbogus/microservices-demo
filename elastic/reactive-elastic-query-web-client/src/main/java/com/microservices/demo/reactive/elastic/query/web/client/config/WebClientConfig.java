package com.microservices.demo.reactive.elastic.query.web.client.config;

import com.microservices.demo.config.ElasticQueryWebClientConfigData;
import com.microservices.demo.config.UserConfigData;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.apache.http.HttpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {
  private final ElasticQueryWebClientConfigData.WebClient webClientConfig;


  public WebClientConfig(ElasticQueryWebClientConfigData webClientConfigData) {
    this.webClientConfig = webClientConfigData.getWebClient();
  }

  @Bean("webClient")
  WebClient webClient() {
    return WebClient.builder()
            .baseUrl(webClientConfig.getBaseUrl())
            .defaultHeader(HttpHeaders.CONTENT_TYPE, webClientConfig.getContentType())
            .defaultHeader(HttpHeaders.ACCEPT, webClientConfig.getAcceptType())
            .clientConnector(new ReactorClientHttpConnector(HttpClient.from(getTcpClient()))).build();
  }

  private TcpClient getTcpClient() {
    return TcpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, webClientConfig.getConnectTimeoutMs())
            .doOnConnected(connection -> {
              connection.addHandlerLast(
                      new ReadTimeoutHandler(webClientConfig.getReadTimeoutMs(), TimeUnit.MILLISECONDS)
              );
              connection.addHandlerLast(
                      new WriteTimeoutHandler(webClientConfig.getWriteTimeoutMs(), TimeUnit.MILLISECONDS)
              );
            });
  }
}
