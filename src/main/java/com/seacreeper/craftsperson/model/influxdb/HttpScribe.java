package com.seacreeper.craftsperson.model.influxdb;

import lombok.Data;

@Data
public class HttpScribe {
  private String dateTime;
  private String data;
}
