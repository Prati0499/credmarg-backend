package com.task.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Vendor")
public class Vendor {

    @Column(nullable = false,name = "vd_name")
    private String name;

    @Id
    @Column(nullable = false, unique = true,name = "vd_email")
    private String email;

    @Column(nullable = false,name = "vd_upi")
    private String upi;



}
