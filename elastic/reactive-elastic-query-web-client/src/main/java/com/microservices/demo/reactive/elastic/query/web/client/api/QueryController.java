package com.microservices.demo.reactive.elastic.query.web.client.api;

import com.microservices.demo.elastic.query.web.client.common.model.ElasticQueryWebClientRequestModel;
import com.microservices.demo.elastic.query.web.client.common.model.ElasticQueryWebClientResponseModel;
import com.microservices.demo.reactive.elastic.query.web.client.service.ElasticQueryWebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import java.util.List;

@Controller
public class QueryController {

  private final static Logger LOG = LoggerFactory.getLogger(QueryController.class);

  private final ElasticQueryWebClient elasticQueryWebClient;


  public QueryController(ElasticQueryWebClient elasticQueryWebClient) {
    this.elasticQueryWebClient = elasticQueryWebClient;
  }

  @GetMapping("")
  private String index() {
    return "index";
  }

  @GetMapping("/error")
  public String error() {
    return "error";
  }

  @GetMapping("/home")
  public String home(Model model) {
    model.addAttribute("elasticQueryWebClientRequestModel",
            ElasticQueryWebClientRequestModel.builder().build());
    return "home";
  }

  @PostMapping(value = "/query-by-text")
  public String queryByText(@Valid ElasticQueryWebClientRequestModel requestModel, Model model) {
    LOG.info("Querying with text {}", requestModel.getText());
    Flux<ElasticQueryWebClientResponseModel> responseModels = elasticQueryWebClient.getDataByText(requestModel);
    responseModels = responseModels.log();
    IReactiveDataDriverContextVariable reactiveData =
            new ReactiveDataDriverContextVariable(responseModels, 1);

    model.addAttribute("elasticQueryWebClientResponseModels", reactiveData );
    model.addAttribute("searchText", requestModel.getText());
    model.addAttribute("elasticQueryWebClientRequestModel",
            ElasticQueryWebClientRequestModel.builder().build());
    LOG.info("Returning from reactive client controller for text: {}", requestModel.getText());
    return "home";
  }
}
