package org.altroyan.web.service.service;

import org.altroyan.web.service.EmployeeNotFoundException;
import org.altroyan.web.service.model.Department;
import org.altroyan.web.service.model.Employee;
import org.altroyan.web.service.repository.DepartmentRepository;
import org.altroyan.web.service.repository.EmployeeRepository;
import org.altroyan.web.service.web.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EmployeeService implements EmployeeServiceI{

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    // Поиск сотрудника по id
    public Employee getEmployeeById(Long id) throws EmployeeNotFoundException {
        Employee employee =employeeRepository.findById(id).orElseThrow(()->new EmployeeNotFoundException("Сотрудник с таким id не найден"));
        return employee;
    }

    //Поиск всех сотрудников с учётом фильтра
    @Override
    public List<EmployeeDto> getAllEmployees(@Nullable Department.Type type) {
        List<Employee> employees;
        if (type == null) {
            employees = employeeRepository.findAll();
        }else{

            employees=employeeRepository.findAllByDepartment_DepartmentType(type);
        }
        return employees.stream().map(EmployeeDto::fromEntity).collect(Collectors.toList());
    }

    //Сохранение нового сотрудника
    @Override
    public EmployeeDto saveNewEmployee(EmployeeNewDto employeeNewDto) {
        Employee employee =new Employee();
        employee.setName(employeeNewDto.getName());
        employee.setSurname(employeeNewDto.getSurname());
        employee.setPhone(employeeNewDto.getPhone());
        return EmployeeDto.fromEntity(employeeRepository.save(employee));
    }

    //Обновление существующего сотрудника
    @Override
    public EmployeeDto updateEmployee(EmployeeUpdateDto employeeUpdateDto) throws EmployeeNotFoundException {
        Employee employee = getEmployeeById(employeeUpdateDto.getId());
        if(employeeUpdateDto.getName()!=null) employee.setName(employeeUpdateDto.getName());
        if(employeeUpdateDto.getSurname()!=null) employee.setSurname(employeeUpdateDto.getSurname());
        if(employeeUpdateDto.getPhone()!=null) employee.setPhone(employeeUpdateDto.getPhone());
        return EmployeeDto.fromEntity(employeeRepository.save(employee));
    }

    //Удаление сотрудника
    @Override
    public void deleteEmployee(EmployeeIdDto idDto){
        try {
            employeeRepository.deleteById(idDto.getId());
        }catch (EmptyResultDataAccessException ignored) {
        }
    }

    //Назначение сотрудника в отдел
    @Override
    public EmployeeDto assignToDepartment(DepartmentAssignDto departmentDto) throws EmployeeNotFoundException {
        Employee employee = getEmployeeById(departmentDto.getEmployeeId());
        try {
            Department.Type type = Department.Type.fromValue(departmentDto.getDepartment());
            Department department =departmentRepository.findByDepartmentType(type).orElseThrow(()->new EmployeeNotFoundException("Неверно задан отдел"));
            employee.setDepartment(department);
            return EmployeeDto.fromEntity(employeeRepository.save(employee));
        }catch (IllegalArgumentException e){
            throw new EmployeeNotFoundException("Неверно задан отдел");
        }
    }

    //Удаление сотрудника из отдела
    @Override
    public EmployeeDto removeFromDepartment(EmployeeIdDto idDto) throws EmployeeNotFoundException {
        Employee employee = getEmployeeById(idDto.getId());
        employee.setDepartment(null);
        return EmployeeDto.fromEntity(employeeRepository.save(employee));
    }
}
