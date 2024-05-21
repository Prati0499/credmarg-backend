package com.task.service;

import com.task.entity.Employee;
import com.task.repo.EmployeeRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
@Service
public class EmployeeServiceIMPL implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Employee save(Employee employee, Logger log) {
        log.info("Entering in {} and save", EmployeeServiceIMPL.class.getName());
        if (validatedModel()) {
            employee.setCreated_by("ADMIN");
            employee.setCreated_dt(Timestamp.valueOf(LocalDate.now().atStartOfDay()));
            try {
                return employeeRepository.save(employee);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
      return employee;
    }

    private boolean validatedModel() {
        return true;
    }
}
