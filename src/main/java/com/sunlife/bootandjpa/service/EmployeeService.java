package com.sunlife.bootandjpa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunlife.bootandjpa.dao.EmployeeRepository;
import com.sunlife.bootandjpa.model.Employee;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeRepository employeeRepository;

	public int addEmployee(Employee employee) {
		employeeRepository.save(employee);
		return employee.getEmpid();
	}
	
	public void updateEmployee(Employee employee) {
		employeeRepository.save(employee);
	}
	
	public Employee getEmployee(int empId) {
		return employeeRepository.findById(empId).get();
	}

	public List<Employee> getAllEmployee() {
		List<Employee> employees = new ArrayList<>();
		employeeRepository.findAll().forEach(e -> employees.add(e));
		return employees;
	}

	public int deleteEmployee(int empId) {
		employeeRepository.deleteById(empId);
		return empId;
	}


}
