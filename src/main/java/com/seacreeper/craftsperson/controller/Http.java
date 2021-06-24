package com.seacreeper.craftsperson.controller;

import com.seacreeper.craftsperson.service.InfluxDbTalker;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/http/")
public class Http {

  @Autowired private InfluxDbTalker influxDbTalker;

  @GetMapping("recent")
  public Collection<String> getRecent() {
    Collection<String> read = influxDbTalker.read();
    return read;
  }
}
