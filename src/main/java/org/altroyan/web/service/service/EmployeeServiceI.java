package org.altroyan.web.service.service;

import org.altroyan.web.service.EmployeeNotFoundException;
import org.altroyan.web.service.model.Department;
import org.altroyan.web.service.model.Employee;
import org.altroyan.web.service.web.dto.*;

import java.util.List;

public interface EmployeeServiceI {

    Employee getEmployeeById(Long id) throws EmployeeNotFoundException;
    List<EmployeeDto> getAllEmployees(Department.Type type);
    EmployeeDto saveNewEmployee(EmployeeNewDto employeeNewDto);
    EmployeeDto updateEmployee(EmployeeUpdateDto employeeUpdateDto) throws EmployeeNotFoundException;
    void deleteEmployee(EmployeeIdDto idDto) throws EmployeeNotFoundException;
    EmployeeDto assignToDepartment(DepartmentAssignDto departmentDto) throws EmployeeNotFoundException;
    EmployeeDto removeFromDepartment(EmployeeIdDto idDto) throws EmployeeNotFoundException;

}
