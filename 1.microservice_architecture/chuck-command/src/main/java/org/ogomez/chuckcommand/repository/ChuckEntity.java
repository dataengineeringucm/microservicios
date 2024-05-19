package org.ogomez.chuckcommand.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "facts")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ChuckEntity {

  @Id
  @GeneratedValue
  private Long id;

  @Column
  private Long timestamp;

  @Column
  private String fact;

}
