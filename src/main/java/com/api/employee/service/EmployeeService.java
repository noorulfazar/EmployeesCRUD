package com.api.employee.service;


import com.api.employee.dto.SalaryStatsDTO;
import com.api.employee.entity.Employee;
import com.api.employee.exceptions.ResourceNotFoundException;
import com.api.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
public class EmployeeService implements EmployeeServiceInterface {

    @Autowired
    private EmployeeRepository repository;

    public Employee save(Employee emp) {
    	
    	if (emp == null) {
            throw new IllegalArgumentException("Employee object must not be null.");
        }

        if (emp.getId() != null) {
            throw new IllegalArgumentException("New employee should not have an ID. Use PUT for updates.");
        }
        return repository.save(emp);
    }

    public Employee update(Employee emp) {
        
    	if (emp.getId() == null) {
            throw new IllegalArgumentException("Employee ID must not be null for update.");
        }

        Employee existingEmp = repository.findById(emp.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee with ID " + emp.getId() + " not found."));

        // Update fields
        existingEmp.setName(emp.getName());
        existingEmp.setDepartment(emp.getDepartment());
        existingEmp.setSalary(emp.getSalary());

        return repository.save(existingEmp);
    }

    public void deleteById(Long id) {
    	
    	if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Employee with ID " + id + " not found.");
        }
        repository.deleteById(id);
    }

    public List<Employee> getByName(String name) {
        //return repository.findByName(name);
    	
    	if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name must not be null or empty.");
        }

        List<Employee> employees = repository.findByName(name);
        
        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("No employees found with name: " + name);
        }

        return employees;
    }

    public List<Employee> getByDepartment(String dept) {
        //return repository.findByDepartment(dept);
    	
    	if (dept == null || dept.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name must not be null or empty.");
        }

        List<Employee> employees = repository.findByDepartment(dept);

        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("No employees found in department: " + dept);
        }

        return employees;
    }

    public Double getAverageSalary(String dept) {
        //return repository.findAverageSalaryByDepartment(dept);
    	
    	if (dept == null || dept.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name must not be null or empty.");
        }

        Double avgSalary = repository.findAverageSalaryByDepartment(dept);

        if (avgSalary == null) {
            throw new ResourceNotFoundException("No salary data found for department: " + dept);
        }

        return avgSalary;
    }
    
    public SalaryStatsDTO getSalaryStats(String department) {
        
    	if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name must not be null or empty.");
        }
    	
    	// Trim for safety (even though JPQL does it too)
        String cleanedDept = department.trim();

        List<Object[]> result = repository.findSalaryStatsByDepartment(cleanedDept);
        
        if (result == null || result.isEmpty() || result.get(0)[0] == null) {
            throw new ResourceNotFoundException("No salary data found for department: " + department);
        }
        
        System.out.println("result[0] = " + result.get(0)[0]);
        System.out.println("result[1] = " + result.get(0)[1]);
        System.out.println("result[2] = " + result.get(0)[2]);

        Object[] result1 = result.get(0);

        Double avg = (Double) result1[0];
        Double max = (Double) result1[1];
        Double min = (Double) result1[2];

        return new SalaryStatsDTO(department, avg, max, min);
    }
    
    public SalaryStatsDTO getSalaryStatsNew(String department) {
        
    	if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name must not be null or empty.");
        }

        List<Employee> employees = repository.findByDepartment(department.trim());

        if (employees == null || employees.isEmpty()) {
            throw new ResourceNotFoundException("No employees found in department: " + department);
        }

        DoubleSummaryStatistics stats = employees.stream()
                .mapToDouble(Employee::getSalary)
                .summaryStatistics();

        return new SalaryStatsDTO(
                department,
                stats.getAverage(),
                stats.getMax(),
                stats.getMin()
        );
    }
    
    public Employee getEmployeeById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee with ID " + id + " not found."));
    }

}

