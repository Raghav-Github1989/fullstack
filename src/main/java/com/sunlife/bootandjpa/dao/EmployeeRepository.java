package com.sunlife.bootandjpa.dao;

import org.springframework.data.repository.CrudRepository;

import com.sunlife.bootandjpa.model.Employee;


public interface EmployeeRepository extends CrudRepository<Employee, Integer>{
}
