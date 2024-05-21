package com.task.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployeeDTO {

    @NotBlank(message = "employee id is mandatory")
    @Email
    private String email;

    private String name;

    private String designation;

    private Double ctc;



}
