package com.codegym.repository;

import com.codegym.model.Department;
import com.codegym.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee,Long> {
    Page<Employee> findAllByDepartment(Department department,Pageable pageable);
    @Query("SELECT e FROM Employee e ORDER BY e.salary ASC")
    Page<Employee> findAll(Pageable pageable);
}
