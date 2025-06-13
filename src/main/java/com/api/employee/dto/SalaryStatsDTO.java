package com.api.employee.dto;

public class SalaryStatsDTO {
    private String department;
    private Double averageSalary;
    private Double maxSalary;
    private Double minSalary;

    // Constructor
    public SalaryStatsDTO(String department, Double averageSalary, Double maxSalary, Double minSalary) {
        this.department = department;
        this.averageSalary = averageSalary;
        this.maxSalary = maxSalary;
        this.minSalary = minSalary;
    }
    
    // Getters and Setters

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Double getAverageSalary() {
		return averageSalary;
	}

	public void setAverageSalary(Double averageSalary) {
		this.averageSalary = averageSalary;
	}

	public Double getMaxSalary() {
		return maxSalary;
	}

	public void setMaxSalary(Double maxSalary) {
		this.maxSalary = maxSalary;
	}

	public Double getMinSalary() {
		return minSalary;
	}

	public void setMinSalary(Double minSalary) {
		this.minSalary = minSalary;
	}

    
    
    
}

