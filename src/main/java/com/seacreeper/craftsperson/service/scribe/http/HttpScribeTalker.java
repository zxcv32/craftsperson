package com.seacreeper.craftsperson.service.scribe.http;

import com.seacreeper.craftsperson.service.scribe.http.model.HttpScribe;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Future;
import org.springframework.scheduling.annotation.Async;

public interface HttpScribeTalker {

  @Async
  Future<Collection<HttpScribe>> readRecent(Optional<Integer> count) throws IOException;
}
