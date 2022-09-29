package com.microservices.demo.elastic.query.service.api;

import com.microservices.demo.elastic.query.service.business.ElasticQueryService;
import com.microservices.demo.elastic.query.service.model.ElasticQueryServiceRequestModel;
import com.microservices.demo.elastic.query.service.model.ElasticQueryServiceResponseModel;
import com.microservices.demo.elastic.query.service.model.ElasticQueryServiceResponseModelV2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/documents")
public class ElasticDocumentController {
  private static final Logger LOG = LoggerFactory.getLogger(ElasticDocumentController.class);
  private final ElasticQueryService elasticQueryService;

  public ElasticDocumentController(ElasticQueryService elasticQueryService) {
    this.elasticQueryService = elasticQueryService;
  }

  @GetMapping("/v1")
  public @ResponseBody ResponseEntity<List<ElasticQueryServiceResponseModel>> getAllDocuments() {
    List<ElasticQueryServiceResponseModel> response = elasticQueryService.getAllDocuments();
    LOG.info("Elasticsearch return {} of documents", response.size());
    return ResponseEntity.ok(response);
  }

  @GetMapping("/v1/{id}")
  public @ResponseBody ResponseEntity<ElasticQueryServiceResponseModel> getDocumentById(@PathVariable @NotEmpty String id) {
    ElasticQueryServiceResponseModel elasticQueryServiceResponseModel = elasticQueryService.getDocumentById(id);
    LOG.debug("Elasticsearch return document with id {}", id);
    return ResponseEntity.ok(elasticQueryServiceResponseModel);
  }

  @GetMapping("/v2/{id}")
  public @ResponseBody ResponseEntity<ElasticQueryServiceResponseModelV2> getDocumentByIdV2(@PathVariable @NotEmpty String id) {
    ElasticQueryServiceResponseModel elasticQueryServiceResponseModel = elasticQueryService.getDocumentById(id);
    LOG.debug("Elasticsearch return document with id {}", id);
    ElasticQueryServiceResponseModelV2 responseModelV2 = getV2Model(elasticQueryServiceResponseModel);
    return ResponseEntity.ok(responseModelV2);
  }

  @PostMapping("/v1/get-document-by-text")
  public @ResponseBody ResponseEntity<List<ElasticQueryServiceResponseModel>> getDocumentsByText(@RequestBody @Valid ElasticQueryServiceRequestModel elasticQueryServiceRequestModel) {
    List<ElasticQueryServiceResponseModel> response =
            elasticQueryService.getDocumentByText(elasticQueryServiceRequestModel.getText());
    LOG.info("Elasticsearch return {} of documents", response.size());
    return ResponseEntity.ok(response);
  }

  private ElasticQueryServiceResponseModelV2 getV2Model(ElasticQueryServiceResponseModel responseModel) {
    ElasticQueryServiceResponseModelV2 responseModelV2 = ElasticQueryServiceResponseModelV2
            .builder()
            .id(Long.parseLong(responseModel.getId()))
            .text(responseModel.getText())
            .userId(responseModel.getUserId())
            .createAt(responseModel.getCreateAt())
            .build();
    responseModelV2.add(responseModel.getLinks());
    return responseModelV2;
  }

}
