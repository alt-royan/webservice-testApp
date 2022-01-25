package org.altroyan.web.service.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Сохранение нового сотрудника
 */

@Data
public class EmployeeNewDto {

    @JsonProperty(required = true)
    private String name;
    @JsonProperty(required = true)
    private String surname;
    @JsonProperty(required = true)
    private String phone;
}

