package com.dgsme.dgsmeclone.dto;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee_loginCredential")
public class EmployeeLoginCredentialsDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private long empId;
    private String employeeEmail;
    private String employeePassword;
    private String employeerole;
    
    
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public long getEmpId() {
		return empId;
	}
	public void setEmpId(long empId) {
		this.empId = empId;
	}
	public String getEmployeeEmail() {
		return employeeEmail;
	}
	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}
	public String getEmployeePassword() {
		return employeePassword;
	}
	public void setEmployeePassword(String employeePassword) {
		this.employeePassword = employeePassword;
	}
	public String getEmployeerole() {
		return employeerole;
	}
	public void setEmployeerole(String employeerole) {
		this.employeerole = employeerole;
	}

   
}
