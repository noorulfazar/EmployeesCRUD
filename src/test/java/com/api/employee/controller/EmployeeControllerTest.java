package com.api.employee.controller;

import com.api.employee.dto.EmployeeDTO;
import com.api.employee.service.EmployeeService;
import com.api.employee.utility.EmployeeHandler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
 
@ExtendWith(Moc kitoExtension.class)
public class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService service;
    
    @Mock
    private EmployeeHandler handler;

    @Test
    public void addTest() {
        EmployeeDTO dto = new EmployeeDTO(1L, "John", "HR", 20000D);

        employeeController.add(dto);

        // Optionally verify if the service method was called
        //Mockito.verify(service).save(dto);
    }
}
