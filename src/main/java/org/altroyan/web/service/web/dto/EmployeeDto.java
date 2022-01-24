package org.altroyan.web.service.web.dto;

import lombok.Data;
import org.altroyan.web.service.model.Department;
import org.altroyan.web.service.model.Employee;

/**
 * Информация о сотруднике
 */

@Data
public class EmployeeDto {

    private Long id;
    private String name;
    private String surname;
    private String phone;
    private Department.Type department;

    public static EmployeeDto fromEntity(Employee employee){
        EmployeeDto dto= new EmployeeDto();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setSurname(employee.getSurname());
        dto.setPhone(employee.getPhone());
        if(employee.getDepartment()!=null) {
            dto.setDepartment(employee.getDepartment().getDepartmentType());
        }else{
            dto.setDepartment(null);
        }
        return dto;
    }

}
