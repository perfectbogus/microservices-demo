package com.microservices.demo.elastic.query.service.api;

import com.microservices.demo.elastic.query.service.business.ElasticQueryService;
import com.microservices.demo.elastic.query.service.common.model.ElasticQueryServiceRequestModel;
import com.microservices.demo.elastic.query.service.common.model.ElasticQueryServiceResponseModel;
import com.microservices.demo.elastic.query.service.model.ElasticQueryServiceAnalyticsResponseModel;
import com.microservices.demo.elastic.query.service.model.ElasticQueryServiceResponseModelV2;
import com.microservices.demo.elastic.query.service.security.TwitterQueryUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping(value = "/documents" , produces = "application/vnd.api.v1+json")
public class ElasticDocumentController {
  private static final Logger LOG = LoggerFactory.getLogger(ElasticDocumentController.class);
  private final ElasticQueryService elasticQueryService;

  public ElasticDocumentController(ElasticQueryService elasticQueryService) {
    this.elasticQueryService = elasticQueryService;
  }

  @Value("${server.port}")
  private String port;

  @PostAuthorize("hasPermission(returnObject, 'READ')")
  @Operation(summary = "Get all elastic documents")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "success", content = {
                  @Content(mediaType = "application/vnd.api.v1+json", schema = @Schema(implementation = ElasticQueryServiceResponseModel.class))
          }),
          @ApiResponse(responseCode = "400", description = "Not found"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")
  })
  @GetMapping("/")
  public @ResponseBody ResponseEntity<List<ElasticQueryServiceResponseModel>> getAllDocuments() {
    List<ElasticQueryServiceResponseModel> response = elasticQueryService.getAllDocuments();
    LOG.info("Elasticsearch return {} of documents", response.size());
    return ResponseEntity.ok(response);
  }

  @PreAuthorize("hasPermission(#id, 'ElasticQuerySerivceResponseModel', 'READ')")
  @Operation(summary = "Get all elastic By Id")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "success", content = {
                  @Content(mediaType = "application/vnd.api.v1+json", schema = @Schema(implementation = ElasticQueryServiceResponseModel.class))
          }),
          @ApiResponse(responseCode = "400", description = "Not found"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")
  })
  @GetMapping("/{id}")
  public @ResponseBody ResponseEntity<ElasticQueryServiceResponseModel> getDocumentById(@PathVariable @NotEmpty String id) {
    ElasticQueryServiceResponseModel elasticQueryServiceResponseModel = elasticQueryService.getDocumentById(id);
    LOG.debug("Elasticsearch return document with id {}", id);
    return ResponseEntity.ok(elasticQueryServiceResponseModel);
  }

  @Operation(summary = "Get all elastic By Id")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "success", content = {
                  @Content(mediaType = "application/vnd.api.v2+json", schema = @Schema(implementation = ElasticQueryServiceResponseModelV2.class))
          }),
          @ApiResponse(responseCode = "400", description = "Not found"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")
  })
  @GetMapping(value = "/{id}", produces = "application/vnd.api.v2+json")
  public @ResponseBody ResponseEntity<ElasticQueryServiceResponseModelV2> getDocumentByIdV2(@PathVariable @NotEmpty String id) {
    ElasticQueryServiceResponseModel elasticQueryServiceResponseModel = elasticQueryService.getDocumentById(id);
    ElasticQueryServiceResponseModelV2 responseModelV2 = getV2Model(elasticQueryServiceResponseModel);
    LOG.debug("Elasticsearch return document with id {}", id);
    return ResponseEntity.ok(responseModelV2);
  }

  @PreAuthorize("hasRole('APP_USER_ROLE') || hasRole('APP_SUPER_USER_ROLE') || hasAuthority('SCOPE_APP_USER_ROLE')")
  @PostAuthorize("hasPermission(returnObject, 'READ')")
  @Operation(summary = "Get all elastic By text")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "success", content = {
                  @Content(mediaType = "application/vnd.api.v1+json", schema = @Schema(implementation = ElasticQueryServiceResponseModel.class))
          }),
          @ApiResponse(responseCode = "400", description = "Not found"),
          @ApiResponse(responseCode = "500", description = "Internal Server Error")
  })
  @PostMapping("/get-document-by-text")
  public @ResponseBody
  ResponseEntity<ElasticQueryServiceAnalyticsResponseModel>
  getDocumentsByText(@RequestBody @Valid ElasticQueryServiceRequestModel elasticQueryServiceRequestModel,
                     @AuthenticationPrincipal TwitterQueryUser principal,
                     @RegisteredOAuth2AuthorizedClient("keycloak")OAuth2AuthorizedClient oAuth2AuthorizedClient) {
    ElasticQueryServiceAnalyticsResponseModel response =
            elasticQueryService.getDocumentByText(elasticQueryServiceRequestModel.getText(),
                    oAuth2AuthorizedClient.getAccessToken().getTokenValue());
    LOG.info("Elasticsearch return {} of documents on port {}", response.getQueryServiceResponseModels().size(), port);
    return ResponseEntity.ok(response);
  }

  private ElasticQueryServiceResponseModelV2 getV2Model(ElasticQueryServiceResponseModel elasticQueryServiceResponseModel) {
    ElasticQueryServiceResponseModelV2 responseModelV2 = ElasticQueryServiceResponseModelV2
            .builder()
            .id(Long.parseLong(elasticQueryServiceResponseModel.getId()))
            .userId(elasticQueryServiceResponseModel.getUserId())
            .text(elasticQueryServiceResponseModel.getText())
            .text2("Version 2 Text")
            .build();
    responseModelV2.add(elasticQueryServiceResponseModel.getLinks());
    return responseModelV2;
  }

}
