package com.seacreeper.craftsperson.service.queen;

import com.google.gson.Gson;
import com.seacreeper.craftsperson.model.queen.QueenRequest;
import java.io.IOException;
import java.util.concurrent.Future;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Profile("!standalone")
public class QueenTalkerImpl implements QueenTalker {

  @Value("${seacreeper.queen.baseUrl}")
  private String queenAddress;

  @Override
  @Async
  public Future<String> send(@NonNull final String url) throws IOException {
    val queenRequest = new QueenRequest();
    queenRequest.setUrl(url);
    val data = new Gson().toJson(queenRequest);
    val encodedUrl = queenAddress + "creeper/http/operation/";
    String result = "";
    try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
      HttpPut httpPut = new HttpPut(encodedUrl);
      httpPut.setEntity(new StringEntity(data));
      httpPut.addHeader("Content-Type", "application/json");
      try (CloseableHttpResponse response = httpclient.execute(httpPut)) {
        result =
            String.format(
                "[%d] %s" ,response.getStatusLine().getStatusCode(),
                response.getStatusLine().getReasonPhrase());
      }
    }
    return new AsyncResult<>(result);
  }
}
