package com.task.controller;

import com.task.dto.EmployeeDTO;
import com.task.entity.Employee;
import com.task.repo.EmployeeRepository;
import com.task.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/employees")
@Slf4j
@Validated
@CrossOrigin("*")
public class EmployeeController {

    @Autowired
    private final  EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;

    private final ModelMapper mapper = new ModelMapper();

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    //@Secured({"ADM"})--only accessible to the host
     @GetMapping("/getAllEmployees")
     public ResponseEntity<?> getAllEmployees() {
        log.info("Entering in {} and getAllEmployees", EmployeeController.class.getName());
        var employeeList = employeeRepository.findAll();
        if (!employeeList.isEmpty()) {
            var problemDetail = ProblemDetail.forStatus(HttpStatus.OK);
            problemDetail.setTitle("Employee List");
            problemDetail.setDetail("Employee List found and size is "+ employeeList.size());
            problemDetail.setStatus(100);
            problemDetail.setProperty("employeeList", employeeList);
            log.info("Exiting from {} and getAllEmployees", EmployeeController.class.getName());
            return ResponseEntity.ok(problemDetail);
        } else {
            log.info("Exiting from {} and getAllEmployees", EmployeeController.class.getName());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

    //@Secured({"ADM"})--only accessible to the host
    @PostMapping("/getEmployeeById")
    public ResponseEntity<?> getEmployeeById( @RequestBody @Valid EmployeeDTO employeeDTO) {
        log.info("Entering in {} and getEmployeeById", EmployeeController.class.getName());
        var employeeOptional = employeeRepository.findById(employeeDTO.getEmail());
        if (employeeOptional.isPresent()) {
            var problemDetail = ProblemDetail.forStatus(HttpStatus.OK);
            problemDetail.setTitle("Employee Data By Id");
            problemDetail.setDetail("Employee Data found by id "+ employeeDTO.getName());
            problemDetail.setStatus(100);
            problemDetail.setProperty("employeeData", employeeOptional.get());
            log.info("Exiting from {} and getEmployeeById", EmployeeController.class.getName());
            return ResponseEntity.ok(problemDetail);
        } else {
            log.info("Exiting from {} and getEmployeeById", EmployeeController.class.getName());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<?> createEmployee(@RequestBody @Valid EmployeeDTO employee) {
        log.info("Entering in {} and createEmployee", EmployeeController.class.getName());
        var map = mapper.map(employee, Employee.class);
        var result = employeeService.save(map,log);
        if(Objects.nonNull(result)){
            log.info("Exiting from {} and createEmployee", EmployeeController.class.getName());
            return ResponseEntity.ok(result);
        }else{
            log.info("Exiting from {} and createEmployee", EmployeeController.class.getName());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }




}
