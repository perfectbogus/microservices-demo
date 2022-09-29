package com.microservices.demo.elastic.query.service.model.assambler;

import com.microservices.demo.elastic.model.index.impl.TwitterIndexModel;
import com.microservices.demo.elastic.query.service.api.ElasticDocumentController;
import com.microservices.demo.elastic.query.service.model.ElasticQueryServiceResponseModel;
import com.microservices.demo.elastic.query.service.transformer.ElasticToResponseModelTransformer;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ElasticQueryServiceResponseModelAssembler extends RepresentationModelAssemblerSupport<TwitterIndexModel, ElasticQueryServiceResponseModel> {

  private final ElasticToResponseModelTransformer elasticToResponseModelTransformer;

  public ElasticQueryServiceResponseModelAssembler(ElasticToResponseModelTransformer elasticToResponseModelTransformer) {
    super(ElasticDocumentController.class, ElasticQueryServiceResponseModel.class);
    this.elasticToResponseModelTransformer = elasticToResponseModelTransformer;
  }


  @Override
  public ElasticQueryServiceResponseModel toModel(TwitterIndexModel entity) {
    return null;
  }
}
