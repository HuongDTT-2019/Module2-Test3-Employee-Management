package com.codegym.service;

import com.codegym.model.Department;
import com.codegym.model.Employee;

public interface DepartmentService {
    Iterable<Department> findAll();
    Department findById(Long id);
    void save(Department department);
    void remove(Long id);
}
