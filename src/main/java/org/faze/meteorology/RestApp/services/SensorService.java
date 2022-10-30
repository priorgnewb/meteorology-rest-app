package org.faze.meteorology.RestApp.services;

import java.util.List;
import java.util.Optional;
import org.faze.meteorology.RestApp.models.Sensor;
import org.faze.meteorology.RestApp.repositories.SensorRepository;
import org.faze.meteorology.RestApp.util.SensorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SensorService {

  SensorRepository sensorRepository;

  @Autowired
  public SensorService(SensorRepository sensorRepository) {
    this.sensorRepository = sensorRepository;
  }

  public List<Sensor> findAll() {
    return sensorRepository.findAll();
  }

  public Optional<Sensor> findByName(String name) {

    return sensorRepository.findByName(name);

  }

  @Transactional
  public void register(Sensor sensor) {
    sensorRepository.save(sensor);
  }
}
