package com.example.myhospital.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.sql.Date;
import java.time.LocalTime;

@Entity
@Table(name = "schedule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_schedule")
    private long id;

    @Column(name = "id_doctor")
    @NotNull
    private long idDoctor;

    @Column(name = "name_doctor")
    @NotBlank
    private String nameDoctor;

    @Column(name = "type_doctor")
    @NotBlank
    private String typeDoctor;


    @Column(name = "id_patient")
    @NotNull
    private long idPatient;

    @Column(name = "name_patient")
    @NotBlank
    private String namePatient;

    @Column
    @NotNull
    private Date date;

    @Column
    @NotNull
    private LocalTime time;

    @Column(name = "room_number", unique = true)
    @NotNull
    @Positive
    private long roomNumber;
}
