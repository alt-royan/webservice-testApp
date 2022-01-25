package org.altroyan.web.service.web.dto;

import lombok.Data;
import org.altroyan.web.service.model.Department;


/**
 * Фильтр поиска сотрудников по отделу
 */

@Data
public class EmployeeSearchDto {

    private Department.Type department;
}
