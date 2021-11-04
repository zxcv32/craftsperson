package com.seacreeper.craftsperson.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

import com.seacreeper.craftsperson.model.influxdb.HttpScribe;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ScribeTalker {

  @Value("${seacreeper.scribes.baseUrl}")
  private String scribesAddress;

  private static final String scribesApiPath = "influxdb/recent";

  private static final Integer recentCount = 10;

  private static final ObjectMapper objectMapper = new ObjectMapper();

  //  @Async
  //  public Collection<String> read(@NonNull final Collection<String> ids) {
  //    ids.stream().forEachOrdered(id -> {
  //      val url = scribesAddress.concat();
  //
  //    });
  //  }
  @Async
  public Future<Collection<HttpScribe>> readRecent(
      /*Optional<Integer> count*/ ) throws IOException {
    val url = scribesAddress.concat(scribesApiPath);
    val requestFactory = new NetHttpTransport().createRequestFactory();
    val request = requestFactory.buildGetRequest(new GenericUrl(url));
    val rawResponse = request.execute().parseAsString();
    val results = objectMapper.readValue(rawResponse, new TypeReference<List<HttpScribe>>() {});
    return new AsyncResult<>(results);
  }
}
