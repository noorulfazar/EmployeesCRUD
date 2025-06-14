package com.api.employee.service;

import com.api.employee.dto.EmployeeDTO;
import com.api.employee.dto.SalaryStatsDTO;
import com.api.employee.entity.Employee;

import java.util.List;

public interface EmployeeServiceInterface {
    EmployeeDTO save(EmployeeDTO dto);
    EmployeeDTO update(EmployeeDTO dto);

    void deleteById(Long id);
    List<Employee> getByName(String name);
    List<Employee> getByDepartment(String dept);
    Double getAverageSalary(String dept);
    SalaryStatsDTO getSalaryStats(String department);
    SalaryStatsDTO getSalaryStatsNew(String department);
    Employee getEmployeeById(Long id);
}
