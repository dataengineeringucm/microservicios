package org.ogomez.chuckcommand.rest;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChuckFact {

  Long id;
  Long timestamp;
  String fact;
}
