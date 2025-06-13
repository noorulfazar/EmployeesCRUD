package com.api.employee.service;

import com.api.employee.dto.SalaryStatsDTO;
import com.api.employee.entity.Employee;

import java.util.List;

public interface EmployeeServiceInterface {

    Employee save(Employee emp);
    
    Employee update(Employee emp);
    
    void deleteById(Long id);
    
    Employee getEmployeeById(Long id);

    List<Employee> getByName(String name);
    
    List<Employee> getByDepartment(String dept);
    
    Double getAverageSalary(String dept);
    
    SalaryStatsDTO getSalaryStats(String department);

    SalaryStatsDTO getSalaryStatsNew(String department);
}
