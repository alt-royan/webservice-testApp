package org.altroyan.web.service.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


/**
 * Обновление информации о сотруднике
 */

@Data
public class EmployeeUpdateDto {

    @JsonProperty(required = true)
    private Long id;
    private String name;
    private String surname;
    private String phone;
}
