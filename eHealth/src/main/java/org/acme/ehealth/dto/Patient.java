package org.acme.ehealth.dto;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Column;
@Entity
@Cacheable
public class Patient extends PanacheEntity {
    @Column(length = 40, unique = true)
    public String patientName;
    @Column(length = 40)
    public String patientAge;
    @Column(length = 150)
    public String patientAddress;
    @Column(length = 10)
    public String patientTel;
    public  Patient(){

    }

    public Patient(String patientName, String patientAge, String patientAddress, String patientTel) {
        this.patientName = patientName;
        this.patientAge = patientAge;
        this.patientAddress = patientAddress;
        this.patientTel = patientTel;
    }
}
