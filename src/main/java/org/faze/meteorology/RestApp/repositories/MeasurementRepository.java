package org.faze.meteorology.RestApp.repositories;

import org.faze.meteorology.RestApp.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

  @Query(
      value = "SELECT COUNT(*) FROM Measurement m WHERE m.raining = 'true'")
  Long countRainyDays();
}
