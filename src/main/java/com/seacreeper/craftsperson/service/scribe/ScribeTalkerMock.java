package com.seacreeper.craftsperson.service.scribe;

import com.seacreeper.craftsperson.model.influxdb.HttpScribe;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

@Slf4j
@Profile("standalone")
@Service
public class ScribeTalkerMock implements ScribeTalker {

  @Override
  @Async
  public Future<Collection<HttpScribe>> readRecent(Optional<Integer> count) throws IOException {
    val scribes = new ArrayList<HttpScribe>();
    return new AsyncResult<>(scribes);
  }
}
