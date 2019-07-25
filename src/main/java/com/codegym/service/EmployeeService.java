package com.codegym.service;

import com.codegym.model.Department;
import com.codegym.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface EmployeeService {
    Page<Employee> findAll(Pageable pageable);
    void save(Employee employee);
    Employee findById(Long id);
    Page<Employee> findAllByDepartment(Department department,Pageable pageable);
    void remove(Long id);
}
