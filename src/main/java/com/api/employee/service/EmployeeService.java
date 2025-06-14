package com.api.employee.service;

import com.api.employee.dto.EmployeeDTO;
import com.api.employee.dto.SalaryStatsDTO;
import com.api.employee.entity.Employee;
import com.api.employee.exceptions.ResourceNotFoundException;
import com.api.employee.repository.EmployeeRepository;
import com.api.employee.utility.EmployeeMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
public class EmployeeService implements EmployeeServiceInterface {

    @Autowired
    private EmployeeRepository repository;

    public EmployeeDTO save(EmployeeDTO dto) {

        if (dto == null) {
            throw new IllegalArgumentException("Employee DTO must not be null.");
        }

        if (dto.getId() != null) {
            throw new IllegalArgumentException("New employee should not have an ID. Use PUT for updates.");
        }

        Employee emp = EmployeeMapper.toEntity(dto);
        Employee saved = repository.save(emp);

        return EmployeeMapper.toDTO(saved);
    }

    public EmployeeDTO update(EmployeeDTO dto) {

        if (dto.getId() == null) {
            throw new IllegalArgumentException("Employee ID must not be null for update.");
        }

        Employee existingEmp = repository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee with ID " + dto.getId() + " not found."));

        existingEmp.setName(dto.getName());
        existingEmp.setDepartment(dto.getDepartment());
        existingEmp.setSalary(dto.getSalary());

        Employee updated = repository.save(existingEmp);
        return EmployeeMapper.toDTO(updated);
    }

    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Employee with ID " + id + " not found.");
        }
        repository.deleteById(id);
    }

    public List<Employee> getByName(String name) {

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

        List<Object[]> result = repository.findSalaryStatsByDepartment(department.trim());

        if (result == null || result.isEmpty() || result.get(0)[0] == null) {
            throw new ResourceNotFoundException("No salary data found for department: " + department);
        }

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
