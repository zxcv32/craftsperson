package com.seacreeper.craftsperson.service.scribe;

import com.seacreeper.craftsperson.model.influxdb.HttpScribe;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Future;

public interface ScribeTalker {

  Future<Collection<HttpScribe>> readRecent(Optional<Integer> count) throws IOException;
}
