package org.altroyan.web.service.web.dto;

import lombok.Data;

/**
 * Запрос на назначения сотрудника в отдел
 */

@Data
public class DepartmentAssignDto {
    private Long employeeId;
    private String department;
}
