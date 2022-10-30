package org.faze.meteorology.RestApp.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.faze.meteorology.RestApp.models.Sensor;

// Jackson отсекает is у булевых значений, поэтому здесь raining
@JsonPropertyOrder({ "id","value", "raining", "sensor", "measuredAt" })
public class MeasurementDTO {

  @NotNull(message = "Измерение температуры воздуха не должно быть пустым!")
  @Min(value = -100, message = "Температура воздуха не ниже -100 градусов!")
  @Max(value = 100, message = "Температура воздуха не выше +100 градусов!")
  private Double value;

  @NotNull(message = "Значение должно быть указано!")
  private Boolean isRaining;

  @NotNull(message = "Название сенсора должно быть указано!")
  private SensorDTO sensor;

  public Double getValue() {
    return value;
  }

  public void setValue(Double value) {
    this.value = value;
  }

  public Boolean getRaining() {
    return isRaining;
  }

  public void setRaining(Boolean raining) {
    isRaining = raining;
  }

  public SensorDTO getSensor() {
    return sensor;
  }

  public void setSensor(SensorDTO sensor) {
    this.sensor = sensor;
  }
}
