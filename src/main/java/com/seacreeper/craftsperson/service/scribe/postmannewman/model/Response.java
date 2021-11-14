package com.seacreeper.craftsperson.service.scribe.postmannewman.model;

import java.util.ArrayList;
import java.util.Collection;

@lombok.Data
public class Response {

  private Collection<Data> data = new ArrayList<>();
}
