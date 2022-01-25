package org.altroyan.web.service.repository;

import org.altroyan.web.service.model.Department;
import org.altroyan.web.service.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByDepartment_DepartmentType(Department.Type type);

}
