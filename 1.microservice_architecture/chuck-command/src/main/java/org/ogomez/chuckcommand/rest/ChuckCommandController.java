package org.ogomez.chuckcommand.rest;

import com.github.javafaker.Faker;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ogomez.chuckcommand.repository.ChuckCommandRepository;
import org.ogomez.chuckcommand.repository.ChuckEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class ChuckCommandController {

  private final Faker faker = new Faker();

  private final ChuckCommandRepository repository;

  @PostMapping("/chuck-says")
  public ChuckFact createNewFact() {

    ChuckEntity entity = ChuckEntity.builder().timestamp(Timestamp.from(Instant.now()).getTime())
        .fact(faker.chuckNorris().fact()).build();

    repository.save(entity);

    return ChuckFact.builder().id(entity.getId()).timestamp(entity.getTimestamp())
        .fact(entity.getFact()).build();
  }

}
