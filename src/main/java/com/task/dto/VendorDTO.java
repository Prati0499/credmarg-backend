package com.task.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VendorDTO {
    @Email
    @NotBlank(message = "vd_email is mandatory")
    public String email;
    public String name;
    public String upi;
}

