package com.seacreeper.craftsperson.service.scribe;

import com.seacreeper.craftsperson.model.influxdb.HttpScribe;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Future;
import org.springframework.scheduling.annotation.Async;

public interface ScribeTalker {

  @Async
  Future<Collection<HttpScribe>> readRecent(Optional<Integer> count) throws IOException;
}
