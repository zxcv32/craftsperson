package com.seacreeper.craftsperson.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.query.FluxRecord;
import java.util.ArrayList;
import java.util.Collection;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class InfluxDbTalker {
  @Value("${com.seacreeper.scribes.influxdb.host}")
  private String influxdbHost;

  @Value("${com.seacreeper.scribes.influxdb.port}")
  private String influxdbPort;

  @Value("${com.seacreeper.scribes.influxdb.bucket}")
  private String influxdbBucket;

  @Value("${com.seacreeper.scribes.influxdb.token}")
  private String influxdbToken;

  @Value("${com.seacreeper.scribes.influxdb.org}")
  private String influxdbOrg;

  private InfluxDBClient influxDbClient;

//  @Async
  public Collection<String> read() {
    this.influxDbClient =
        InfluxDBClientFactory.create(
            influxdbHost + ":" + influxdbPort,
            influxdbToken.toCharArray(),
            influxdbOrg,
            influxdbBucket);
    val flux = "from(bucket:\"" + influxdbBucket + "\") |> range(start:0)";
    val queryApi = influxDbClient.getQueryApi();
    val tables = queryApi.query(flux);
    val results = new ArrayList<String>();
    for (val fluxTable : tables) {
      val fluxRecords = fluxTable.getRecords();
      for (FluxRecord fluxRecord : fluxRecords) {
        results.add(fluxRecord.getTime() + ": " + fluxRecord.getValueByKey("_value"));
      }
    }
    influxDbClient.close();
    return results;
  }
}
