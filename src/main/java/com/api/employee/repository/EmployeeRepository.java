package com.api.employee.repository;

import com.api.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    List<Employee> findByName(String name);
    List<Employee> findByDepartment(String department);
    
    @Query("SELECT AVG(e.salary) FROM Employee e WHERE e.department = ?1")
    Double findAverageSalaryByDepartment(String department);
    
    //@Query("SELECT AVG(e.salary), MAX(e.salary), MIN(e.salary) FROM Employee e WHERE LOWER(TRIM(e.department)) = LOWER(TRIM(?1))")
//    @Query("SELECT AVG(e.salary), MAX(e.salary), MIN(e.salary) FROM Employee e WHERE e.department = '?1'")
//    Object[] findSalaryStatsByDepartment(String department);
    
//    @Query("SELECT AVG(e.salary), MAX(e.salary), MIN(e.salary) FROM Employee e WHERE LOWER(TRIM(e.department)) = LOWER(TRIM(:dept))")
//    Object[] findSalaryStatsByDepartment(@Param("dept") String department);
    
    @Query("SELECT AVG(e.salary), MAX(e.salary), MIN(e.salary) FROM Employee e WHERE e.department = :dept")
    List<Object[]> findSalaryStatsByDepartment(@Param("dept") String department);
}
