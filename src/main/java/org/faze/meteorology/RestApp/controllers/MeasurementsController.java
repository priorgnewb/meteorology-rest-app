package org.faze.meteorology.RestApp.controllers;

import static org.faze.meteorology.RestApp.util.ErrorsUtil.returnErrorsToClient;

import java.util.stream.Collectors;
import javax.validation.Valid;
import org.faze.meteorology.RestApp.dto.MeasurementDTO;
import org.faze.meteorology.RestApp.dto.MeasurementsResponse;
import org.faze.meteorology.RestApp.models.Measurement;
import org.faze.meteorology.RestApp.services.MeasurementService;
import org.faze.meteorology.RestApp.util.MeasurementErrorResponse;
import org.faze.meteorology.RestApp.util.MeasurementException;
import org.faze.meteorology.RestApp.util.MeasurementNotFoundException;
import org.faze.meteorology.RestApp.util.MeasurementValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

  private final MeasurementService measurementService;
  private final MeasurementValidator measurementValidator;
  private final ModelMapper modelMapper;

  @Autowired
  public MeasurementsController(MeasurementService measurementService,
      MeasurementValidator measurementValidator,
      ModelMapper modelMapper) {
    this.measurementService = measurementService;
    this.measurementValidator = measurementValidator;
    this.modelMapper = modelMapper;
  }

  @GetMapping()
  public MeasurementsResponse getMeasurements() {
    // Обычно список из элементов оборачивается в один объект для пересылки
    return new MeasurementsResponse(
        measurementService.findAll().stream().map(this::convertToMeasurementDTO)
            .collect(Collectors.toList()));
  }

  @GetMapping("/rainyDaysCount")
  public Long getRainyDaysCount() {
    return measurementService.countRainyDays();
  }

  @PostMapping("/add")
  public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO,
      BindingResult bindingResult) {

    Measurement measurementToAdd = convertToMeasurement(measurementDTO);

    measurementValidator.validate(measurementToAdd, bindingResult);

    if (bindingResult.hasErrors()) {
      returnErrorsToClient(bindingResult);
    }

    measurementService.addMeasurement(measurementToAdd);
    return ResponseEntity.ok(HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public MeasurementDTO getMeasurement(@PathVariable("id") int id) {
    return convertToMeasurementDTO(measurementService.findOne(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<MeasurementErrorResponse> deleteMeasurement(@PathVariable("id") int id) {
    measurementService.delete(id);

//     TODO создание JSON при успешном удалении должно быть без Error
    MeasurementErrorResponse response = new MeasurementErrorResponse(
        "Показание с id = " + id + " удалено", System.currentTimeMillis());

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
    return modelMapper.map(measurementDTO, Measurement.class);
  }

  private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
    return modelMapper.map(measurement, MeasurementDTO.class);
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
  private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotFoundException e) {

    MeasurementErrorResponse response = new MeasurementErrorResponse(
        "Показания сенсора с таким id не обнаружено!", System.currentTimeMillis());

    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }
}
