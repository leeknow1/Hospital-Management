package com.example.myhospital.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name = "outpatient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OutpatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotBlank
    @Length(max = 50)
    private String name;

    @Column
    @NotBlank
    @Length(max = 10)
    private String sex;

    @Column
    @NotBlank
    @Length(max = 15)
    private String phone;

    @Column(name = "id_medicine")
    @NotNull
    private long idMedicine;

    @Column(name = "name_medicine")
    @NotBlank
    @Length(max = 50)
    private String nameMedicine;

    @Column(name = "bought_date")
    @NotNull
    private Date boughtDate;
}
