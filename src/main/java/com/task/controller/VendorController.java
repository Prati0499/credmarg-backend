package com.task.controller;


import com.task.dto.VendorDTO;
import com.task.entity.Vendor;
import com.task.repo.SmsRepository;
import com.task.repo.VendorRepository;
import com.task.service.EmailService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/vendors")
@Slf4j
@Validated
@CrossOrigin("*")
public class VendorController {

    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    private final VendorRepository vendorRepository;
    @Autowired
    private final SmsRepository smsRepository;

    @Autowired
    EmailService emailService;

    public VendorController(VendorRepository vendorRepository, SmsRepository smsRepository) {
        this.vendorRepository = vendorRepository;
        this.smsRepository = smsRepository;
    }

    //@Secured({"HST"})--only accessible to the host
    @GetMapping("/getAllVendors")
    public ResponseEntity<?> getAllVendors() {
        log.info("Entering in {} and getAllVendors", VendorController.class.getName());
        var vendorList = vendorRepository.findAll();
        if (!vendorList.isEmpty()) {
            var problemDetail = ProblemDetail.forStatus(HttpStatus.OK);
            problemDetail.setTitle("Vendor List");
            problemDetail.setDetail("Vendor List found and size is "+vendorList.size());
            problemDetail.setStatus(100);
            problemDetail.setProperty("vendorList", vendorList);
            log.info("Exiting from {} and getAllVendors", VendorController.class.getName());
            return ResponseEntity.ok(problemDetail);
        } else {
            log.info("Exiting from {} and getAllVendors", VendorController.class.getName());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }
    @PostMapping("/getVendorById")
    public ResponseEntity<?> getVendorById( @RequestBody @Valid VendorDTO vendorDTO) {
        log.info("Entering in {} and getVendorById", VendorController.class.getName());
        var vendorOptional = vendorRepository.findById(vendorDTO.getEmail());
        if (vendorOptional.isPresent()) {
            var problemDetail = ProblemDetail.forStatus(HttpStatus.OK);
            problemDetail.setTitle("Vendor Data");
            problemDetail.setDetail("Vendor Data found by id "+vendorDTO.getName());
            problemDetail.setStatus(100);
            problemDetail.setProperty("vendorData", vendorOptional.get());
            log.info("Exiting from {} and getVendorById", VendorController.class.getName());
            return ResponseEntity.ok(problemDetail);
        } else {
            log.info("Exiting from {} and getVendorById", VendorController.class.getName());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

    }

    @PostMapping("/addVendor")
    public ResponseEntity<?> createVendor(@RequestBody @Valid VendorDTO vendorDTO) {
        log.info("Entering in {} and createVendor", EmployeeController.class.getName());
        var map = mapper.map(vendorDTO, Vendor.class);
        var result = vendorRepository.save(map);
        log.info("Exiting from {} and createEmployee", EmployeeController.class.getName());
        return ResponseEntity.ok(result);
    }
    @PostMapping("/sendEmails")
    public void sendEmailsToVendors(@RequestBody @Valid Vendor vendor) {
        log.info("Entering in {} and sendEmailsToVendors", EmployeeController.class.getName());
        vendorRepository.findAll()
                .forEach(x -> {
                    try {
                        emailService.sendEmailToVendors(x,log);
                    } catch (IOException e) {
                        e.printStackTrace();
                        log.error("Error in sending mails to vendor in {} and sendEmailsToVendors", VendorController.class.getName());
                    }
                });
        log.info("Exiting from {} and sendEmailsToVendors", EmployeeController.class.getName());
    }

    @GetMapping("/getAllVendorsSmsDetails")
    public ResponseEntity<?> getAllVendorsSmsDetails() {
        log.info("Entering..");
        var vendorList = smsRepository.findAll();
        if (!vendorList.isEmpty()) {
            var problemDetail = ProblemDetail.forStatus(HttpStatus.OK);
            problemDetail.setTitle("VendorEmail List");
            problemDetail.setDetail("VendorEmail List found and size is "+vendorList.size());
            problemDetail.setStatus(100);
            problemDetail.setProperty("vendorEmailList", vendorList);
            log.info("Exiting from VendorController {}", VendorController.class.getName());
            return ResponseEntity.ok(problemDetail);
        } else {
            log.info("Exiting from {} ", VendorController.class.getName());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }
}
