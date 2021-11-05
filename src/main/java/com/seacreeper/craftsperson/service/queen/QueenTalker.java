package com.seacreeper.craftsperson.service.queen;

import java.io.IOException;
import java.util.concurrent.Future;
import lombok.NonNull;
import org.springframework.scheduling.annotation.Async;

public interface QueenTalker {

  @Async
  Future<String> send(@NonNull String url) throws IOException;
}
