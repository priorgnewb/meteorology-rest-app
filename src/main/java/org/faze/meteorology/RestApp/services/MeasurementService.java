package org.faze.meteorology.RestApp.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.faze.meteorology.RestApp.models.Measurement;
import org.faze.meteorology.RestApp.repositories.MeasurementRepository;
import org.faze.meteorology.RestApp.util.MeasurementNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

  private final MeasurementRepository measurementRepository;
  private final SensorService sensorService;

  @Autowired
  public MeasurementService(MeasurementRepository measurementRepository,
      SensorService sensorService) {
    this.measurementRepository = measurementRepository;
    this.sensorService = sensorService;
  }

  public List<Measurement> findAll() {
    return measurementRepository.findAll();
  }

  public Measurement findOne(int id) {
    Optional<Measurement> foundMeasurement = measurementRepository.findById(id);
    return foundMeasurement.orElseThrow(MeasurementNotFoundException::new);
  }

  @Transactional
  public void addMeasurement(Measurement measurement) {
    enrichMeasurement(measurement);
    measurementRepository.save(measurement);
  }

  // заполняем дополнительные поля объекта Measurement (время добавления показания)
  public void enrichMeasurement(Measurement measurement) {
    // мы должны сами найти сенсор из БД по имени и вставить объект из Hibernate persistence context'а
    measurement.setSensor(sensorService.findByName(measurement.getSensor().getName()).get());

    measurement.setMeasuredAt(LocalDateTime.now());
  }

  @Transactional
  public void delete(int id) {
    Optional<Measurement> foundMeasurement = measurementRepository.findById(id);

    if (foundMeasurement.isPresent()) {
      measurementRepository.deleteById(id);
    } else throw new MeasurementNotFoundException();

  }

  public Long countRainyDays() {
    return measurementRepository.countRainyDays();
  }
}
