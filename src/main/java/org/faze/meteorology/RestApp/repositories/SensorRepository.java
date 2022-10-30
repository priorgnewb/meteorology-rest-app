package org.faze.meteorology.RestApp.repositories;

import java.util.Optional;
import org.faze.meteorology.RestApp.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, String> {

  Optional<Sensor> findByName(String name);
}
