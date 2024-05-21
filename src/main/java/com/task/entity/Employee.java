package com.task.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "Employee")
public class Employee {


    @Column(nullable = false, name = "ep_name")
    private String name;

    @Column(nullable = false, name = "ep_designation")
    private String designation;

    @Column(nullable = false, name = "ep_ctc")
    private Double ctc;

    @Id
    @Column(nullable = false, unique = true, name = "ep_email")
    private String email;

    @Column(name = "ep_created_by")
    private String created_by;

    @Column(name = "ep_created_dt")
    private Timestamp created_dt;

}
