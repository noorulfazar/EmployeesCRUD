package com.api.employee.controller;
 
import com.api.employee.dto.EmployeeDTO;
import com.api.employee.dto.SalaryStatsDTO;
import com.api.employee.entity.Employee;
import com.api.employee.exceptions.ErrorObject;
import com.api.employee.exceptions.ResourceNotFoundException;
import com.api.employee.exceptions.ResponseObject;
import com.api.employee.service.EmployeeService;
import com.api.employee.utility.EmployeeHandler;
import com.api.employee.utility.EmployeeMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;
    
    @Autowired
    private EmployeeHandler handler;

    @PostMapping
    public EmployeeDTO add(@RequestBody EmployeeDTO dto) {
    	
    	// calling method to test the cases
    	handler.method();
        return service.save(dto);
    }

    @PutMapping
    public EmployeeDTO update(@RequestBody EmployeeDTO dto) {
        return service.update(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

    @GetMapping("/by-name")
    public List<EmployeeDTO> getByName(@RequestParam String name) {
        List<Employee> list = service.getByName(name);
        return list.stream().map(EmployeeMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/by-department")
    public List<EmployeeDTO> getByDepartment(@RequestParam String department) {
        List<Employee> list = service.getByDepartment(department);
        return list.stream().map(EmployeeMapper::toDTO).collect(Collectors.toList());
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
            EmployeeDTO dto = EmployeeMapper.toDTO(employee);

            response.setStatusCode(200);
            response.setStatusMessage("SUCCESS");
            response.setData(dto);
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
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
