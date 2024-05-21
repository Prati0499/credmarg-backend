package com.task.service;


import com.task.entity.Employee;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {
    Employee save(Employee employee, Logger log);


}
