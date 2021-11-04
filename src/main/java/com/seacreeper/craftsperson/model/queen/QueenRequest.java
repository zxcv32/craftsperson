package com.seacreeper.craftsperson.model.queen;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

public class QueenRequest {

  @Value("${seacreeper.craftsperson.user.token}")
  private String token;

  @Setter private String url;
}
