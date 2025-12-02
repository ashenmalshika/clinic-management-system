package com.westminster.patient_service.model;

import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "patients")
public class Patient {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;


    @Column(unique = true)
    private String nic;


    private String phone;


    private LocalDate dob;


    private String address;

    private String username;


    // Constructors
    public Patient() {}


    public Patient(String name, String nic, String phone, LocalDate dob, String address, String username) {
        this.name = name;
        this.nic = nic;
        this.phone = phone;
        this.dob = dob;
        this.address = address;
        this.username = username;
    }


    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }


    public String getNic() { return nic; }
    public void setNic(String nic) { this.nic = nic; }


    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }


    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }


    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
