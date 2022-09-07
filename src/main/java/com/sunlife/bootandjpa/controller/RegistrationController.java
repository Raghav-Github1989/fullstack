package com.sunlife.bootandjpa.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sunlife.bootandjpa.model.Employee;
import com.sunlife.bootandjpa.service.EmployeeService;

@Controller
public class RegistrationController {
	
	private static final Logger logger = LogManager.getLogger(RegistrationController.class);
	
	@Autowired
	EmployeeService employeeService;

	@GetMapping("/registration")
	public ModelAndView register() {
		logger.info("Entering register");
		ModelAndView modelAndView = new ModelAndView("register");
		return modelAndView;
	}
	
	@PostMapping("/register")
	public String submitForm(@ModelAttribute("name") Employee employee, HttpServletRequest request) {
		Object botDetected = request.getAttribute("BOT_DETECTED");
		if(null != botDetected && ((String)request.getAttribute("BOT_DETECTED")).equals("TRUE")) {
			return "error";
		}
	     
	    System.out.println(employee);
	    System.out.println(request.getPathInfo());
	     
	    int empId = employeeService.addEmployee(employee);
	    System.out.println("Empid : "+empId);
	    return "register";
	}
	
	
	@PostMapping("/error")
	public String errorForm(@ModelAttribute("name") Employee employee) {
	    System.out.println(employee);	  
	    return "error";
	}
}
