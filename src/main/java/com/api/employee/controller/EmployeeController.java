package com.api.employee.controller;


import com.api.employee.dto.SalaryStatsDTO;
import com.api.employee.entity.Employee;
import com.api.employee.exceptions.ErrorObject;
import com.api.employee.exceptions.ResourceNotFoundException;
import com.api.employee.exceptions.ResponseObject;
import com.api.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @PostMapping
    public Employee add(@RequestBody Employee emp) {
        return service.save(emp);
    }

    @PutMapping
    public Employee update(@RequestBody Employee emp) {
        return service.update(emp);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

    @GetMapping("/by-name")
    public List<Employee> getByName(@RequestParam String name) {
        return service.getByName(name);
    }

    @GetMapping("/by-department")
    public List<Employee> getByDepartment(@RequestParam String department) {
        return service.getByDepartment(department);
    }

    @GetMapping("/avg-salary")
    public Double getAverageSalary(@RequestParam String department) {
        return service.getAverageSalary(department);
    }
    
    @GetMapping("/salary-stats")
    public ResponseEntity<SalaryStatsDTO> getSalaryStats(@RequestParam String department) {
		SalaryStatsDTO stats = service.getSalaryStats(department);
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/salary-stats-new")
    public ResponseEntity<SalaryStatsDTO> getSalaryStatsNew(@RequestParam String department) {
		SalaryStatsDTO stats = service.getSalaryStatsNew(department);
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getEmployeeById(@PathVariable Long id) {
        ResponseObject response = new ResponseObject();

        try {
            Employee employee = service.getEmployeeById(id);

            response.setStatusCode(200);
            response.setStatusMessage("SUCCESS");
            response.setData(employee); // make sure ResponseObject has a 'data' field
        } catch (ResourceNotFoundException ex) {
            ErrorObject error = new ErrorObject();
            error.setCode(404);
            error.setMessage("Not Found");
            error.setMoreInfo(ex.getMessage());

            response.setStatusCode(404);
            response.setStatusMessage("FAILURE");
            response.setErrorResponse(error);
        }

        response.setServerResponseTime(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.OK); // always 200 for outer HTTP, or use different code
    }

}

