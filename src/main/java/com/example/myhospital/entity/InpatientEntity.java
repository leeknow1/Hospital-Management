package com.example.myhospital.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "inpatient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InpatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(name = "room_number")
    @NotNull
    @Positive
    private long roomNumber;

    @Column(name = "ward_type")
    @NotBlank
    private String wardType;

    @Column(name = "doctor_name")
    @NotBlank
    private String doctorName;

    @Column(name = "doctor_type")
    @NotBlank
    private String doctorType;

    @Column(name = "patient_name")
    @NotBlank
    private String patientName;

    @Column(name = "medicine_name")
    @NotBlank
    private String medicineName;

    @Column(name = "duration")
    @NotNull
    @Positive
    private long duration;

    @Column(name = "total_charges")
    @NotNull
    @Positive
    private long totalCharges;

    @Column(name = "id_doctor")
    @NotNull
    private long idDoctor;
    @Column(name = "id_patient")
    @NotNull
    private long idPatient;
    @Column(name = "id_medicine")
    @NotNull
    private long idMedicine;
}
