package org.altroyan.web.service.web.controller;

import org.altroyan.web.service.EmployeeNotFoundException;
import org.altroyan.web.service.model.Department;
import org.altroyan.web.service.service.EmployeeServiceI;
import org.altroyan.web.service.web.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    @Qualifier("employeeService")
    private EmployeeServiceI employeeService;

    // Поиск сотрудника по id
    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") Long id) throws EmployeeNotFoundException {
        EmployeeDto employeeDto =EmployeeDto.fromEntity(employeeService.getEmployeeById(id));
        return ResponseEntity.ok(employeeDto);
    }

    // Поиск всех сотрудников с необязательным фильтром по отделам
    @GetMapping("/employee")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(@RequestParam(value = "department", required = false) String departmentName) throws EmployeeNotFoundException {
        Department.Type department=null;
        if(departmentName !=null) {
            try {
                department = Department.Type.fromValue(departmentName);
            } catch (IllegalArgumentException ignored) {
                throw new EmployeeNotFoundException("Неверный отдел");
            }
        }
        List<EmployeeDto> employeeDtos = employeeService.getAllEmployees(department);
        return ResponseEntity.ok(employeeDtos);
    }

    //Сохранение нового сотрудника
    @PostMapping("/employee")
    public ResponseEntity<EmployeeDto> addNewEmployee (@RequestBody EmployeeNewDto employeeNew){
        EmployeeDto employeeDto =employeeService.saveNewEmployee(employeeNew);
        return ResponseEntity.ok(employeeDto);
    }

    //Обновление существующего сотрудника
    @PutMapping("/employee")
    public ResponseEntity<EmployeeDto> updateEmployee (@RequestBody EmployeeUpdateDto employeeUpdateDto) throws EmployeeNotFoundException {
        EmployeeDto employeeDto =employeeService.updateEmployee(employeeUpdateDto);
        return ResponseEntity.ok(employeeDto);
    }

    //Удвление сотрудника
    @DeleteMapping("/employee")
    public ResponseEntity<EmployeeDto> deleteEmployee(@RequestBody EmployeeIdDto idDto) throws EmployeeNotFoundException {
        employeeService.deleteEmployee(idDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    //Назначение сотрудника в отдел
    @PostMapping("/employee/department")
    public ResponseEntity<EmployeeDto> assignToDepartment(@RequestBody DepartmentAssignDto departmentAssignDto) throws EmployeeNotFoundException {
        EmployeeDto employeeDto = employeeService.assignToDepartment(departmentAssignDto);
        return ResponseEntity.ok(employeeDto);
    }

    //Удаление сотрудника из отдела
    @DeleteMapping("/employee/department")
    public ResponseEntity<EmployeeDto> removeFromDepartment (@RequestBody EmployeeIdDto employeeIdDto) throws EmployeeNotFoundException {
        EmployeeDto employeeDto = employeeService.removeFromDepartment(employeeIdDto);
        return ResponseEntity.ok(employeeDto);
    }
}
