package org.faze.meteorology.RestApp.controllers;

import static org.faze.meteorology.RestApp.util.ErrorsUtil.returnErrorsToClient;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.faze.meteorology.RestApp.dto.SensorDTO;
import org.faze.meteorology.RestApp.models.Sensor;
import org.faze.meteorology.RestApp.services.SensorService;
import org.faze.meteorology.RestApp.util.MeasurementErrorResponse;
import org.faze.meteorology.RestApp.util.MeasurementException;
import org.faze.meteorology.RestApp.util.SensorErrorResponse;
import org.faze.meteorology.RestApp.util.SensorNotFoundException;
import org.faze.meteorology.RestApp.util.SensorValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sensors")
public class SensorsController {

  private final SensorService sensorService;
  private final SensorValidator sensorValidator;
  private final ModelMapper modelMapper;

  @Autowired
  public SensorsController(SensorService sensorService,
      SensorValidator sensorValidator, ModelMapper modelMapper) {
    this.sensorService = sensorService;
    this.sensorValidator = sensorValidator;
    this.modelMapper = modelMapper;
  }

  @GetMapping()
  public List<SensorDTO> getSensors() {
    return sensorService.findAll().stream().map(this::convertToSensorDTO)
        .collect(Collectors.toList());
  }

  @GetMapping("/{name}")
  public SensorDTO getSensor(@PathVariable("name") String name) {

    Optional<Sensor> byName = sensorService.findByName(name);

    if(byName.isEmpty()) throw new SensorNotFoundException();

    return convertToSensorDTO(byName.get());
  }

  @PostMapping("/registration")
  public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO,
      BindingResult bindingResult) {

    Sensor sensorToAdd = convertToSensor(sensorDTO);

    sensorValidator.validate(sensorToAdd, bindingResult);

    if (bindingResult.hasErrors()) {
      returnErrorsToClient(bindingResult);
    }

    sensorService.register(sensorToAdd);

    return ResponseEntity.ok(HttpStatus.OK);
  }

  @ExceptionHandler
  private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementException e) {
    MeasurementErrorResponse response = new MeasurementErrorResponse(
        e.getMessage(),
        System.currentTimeMillis()
    );

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException e) {

    SensorErrorResponse response = new SensorErrorResponse(
        "Сенсор не найден!", System.currentTimeMillis());

    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  private Sensor convertToSensor(SensorDTO sensorDTO) {
    return modelMapper.map(sensorDTO, Sensor.class);
  }

  private SensorDTO convertToSensorDTO(Sensor sensor) {
    return modelMapper.map(sensor, SensorDTO.class);
  }
}