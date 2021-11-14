package com.seacreeper.craftsperson.service.scribe.postmannewman;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.seacreeper.craftsperson.service.scribe.postmannewman.model.Response;
import java.io.IOException;
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
public class ScribePostmanNewmanTalkerImpl implements ScribePostmanNewmanTalker {

  @Value("${seacreeper.scribe.postmannewman.baseUrl}")
  private String scribeAddress;

  private static final String scribesApiPath = "influxdb-go";

  private static final Integer limit = 10;

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Async
  public Future<Response> readRecent(Optional<Integer> count) throws IOException {
    val url = scribeAddress.concat(scribesApiPath);
    val requestFactory = new NetHttpTransport().createRequestFactory();
    val request = requestFactory.buildGetRequest(new GenericUrl(url));
    val rawResponse = request.execute().parseAsString();
    val results = objectMapper.readValue(rawResponse, new TypeReference<Response>() {});
    return new AsyncResult<>(results);
  }
}
