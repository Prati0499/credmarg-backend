package com.task.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "sms_tracker")
public class Sms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, name = "vd_name")
    private String vendorName;

    @Column(nullable = false, name = "vd_email")
    private String vendorEmail;

    @Column(nullable = false, name = "vd_msg")
    private String vendorMesg;

    @Column(nullable = false, name = "msg_status")
    private String mesgStatus;

}
