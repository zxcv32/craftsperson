package com.seacreeper.craftsperson.service.scribe.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.seacreeper.craftsperson.service.scribe.http.model.HttpScribe;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

@Slf4j
@Profile("!standalone")
@Service
public class HttpScribeTalkerImpl implements HttpScribeTalker {

  @Value("${seacreeper.scribes.baseUrl}")
  private String scribesAddress;

  private static final String scribesApiPath = "influxdb/recent";

  private static final Integer recentCount = 10;

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Async
  public Future<Collection<HttpScribe>> readRecent(Optional<Integer> count) throws IOException {
    val url = scribesAddress.concat(scribesApiPath);
    val requestFactory = new NetHttpTransport().createRequestFactory();
    val request = requestFactory.buildGetRequest(new GenericUrl(url));
    val rawResponse = request.execute().parseAsString();
    val results = objectMapper.readValue(rawResponse, new TypeReference<List<HttpScribe>>() {});
    return new AsyncResult<>(results);
  }
}
