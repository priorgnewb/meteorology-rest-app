package org.faze.meteorology.RestApp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SensorDTO {

  @NotEmpty(message = "Название сенсора не должно быть пустым")
  @Size(min = 2, max = 30, message = "Длина имени должна быть от 2 до 30 символов")
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
