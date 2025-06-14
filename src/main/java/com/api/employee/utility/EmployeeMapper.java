package com.api.employee.utility;

import com.api.employee.dto.EmployeeDTO;
import com.api.employee.entity.Employee;

public class EmployeeMapper {

    public static EmployeeDTO toDTO(Employee employee) {
        if (employee == null) return null;

        return new EmployeeDTO(
            employee.getId(),
            employee.getName(),
            employee.getDepartment(),
            employee.getSalary()
        );
    }

    public static Employee toEntity(EmployeeDTO dto) {
        if (dto == null) return null;

        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setName(dto.getName());
        employee.setDepartment(dto.getDepartment());
        employee.setSalary(dto.getSalary());
        return employee;
    }
}
