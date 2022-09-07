package com.sunlife.bootandjpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunlife.bootandjpa.model.Employee;
import com.sunlife.bootandjpa.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class MainController {
	
	@Autowired
	EmployeeService employeeService;
	
	@GetMapping("/status")
	public String getStatus() {
		return "App is running...";
	}
	
	@PostMapping("/addEmp")
	public int addEmployee(@RequestBody Employee employee) {
		return employeeService.addEmployee(employee);
	}
	
	@RequestMapping("/getEmp/{empId}")
	public Employee getEmployee(@PathVariable("empId") int empId) {
		return employeeService.getEmployee(empId);
	}
	
	@RequestMapping("/getAll")
	public List<Employee> getAllEmployee() {
		return employeeService.getAllEmployee();
	}
	
	@RequestMapping("/delete/{empId}")
	public String deleteEmployee(@PathVariable("empId") int empId) {
		employeeService.deleteEmployee(empId);
		return  "employee Deleted : " + empId;
	}
	
	@RequestMapping("/update")
	public String updateEmployee(@RequestBody Employee employee) {
		employeeService.updateEmployee(employee);
		return "Update is done for Employee : "+employee.getEmpid();
	}
	
}
