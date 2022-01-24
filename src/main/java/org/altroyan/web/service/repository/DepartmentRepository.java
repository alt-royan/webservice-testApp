package org.altroyan.web.service.repository;

import org.altroyan.web.service.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByDepartmentType(Department.Type type);
}
