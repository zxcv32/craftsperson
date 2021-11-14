package com.seacreeper.craftsperson.service.scribe.postmannewman;

import com.seacreeper.craftsperson.service.scribe.postmannewman.model.Response;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.Future;
import org.springframework.scheduling.annotation.Async;

public interface ScribePostmanNewmanTalker {

  @Async
  Future<Response> readRecent(Optional<Integer> count) throws IOException;
}
