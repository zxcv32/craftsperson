package com.seacreeper.craftsperson.service.queen;

import java.io.IOException;
import java.util.concurrent.Future;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Profile("standalone")
public class QueenTalkerMock implements QueenTalker {

  @Value("${seacreeper.queen.baseUrl}")
  private String queenAddress;

  @Override
  @Async
  public Future<String> send(@NonNull final String url) throws IOException {
    return new AsyncResult<>(url);
  }
}
