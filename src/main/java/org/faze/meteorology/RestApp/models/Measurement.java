package org.faze.meteorology.RestApp.models;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Measurement")
public class Measurement {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "value")
  @NotNull(message = "Измерение температуры воздуха не должно быть пустым!")
  @Min(value = -100, message = "Температура воздуха не ниже -100 градусов!")
  @Max(value = 100, message = "Температура воздуха не выше +100 градусов!")
  private Double value;

  @Column(name = "raining")
  @NotNull(message = "Значение должно быть указано!")
  private Boolean raining;

  @NotNull
  @ManyToOne()
  @JoinColumn(name="sensor_name", referencedColumnName = "name")
  private Sensor sensor;

  @Column(name = "measured_at")
  private LocalDateTime measuredAt;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Double getValue() {
    return value;
  }

  public void setValue(Double value) {
    this.value = value;
  }

  // Jackson для Boolean смотрит на название геттера, отсекает is и оставляет название поля
  public Boolean isRaining() {
    return raining;
  }

  public void setRaining(Boolean raining) {
    this.raining = raining;
  }

  public Sensor getSensor() {
    return sensor;
  }

  public void setSensor(Sensor sensor) {
    this.sensor = sensor;
  }

  public LocalDateTime getMeasuredAt() {
    return measuredAt;
  }

  public void setMeasuredAt(LocalDateTime measuredAt) {
    this.measuredAt = measuredAt;
  }
}
