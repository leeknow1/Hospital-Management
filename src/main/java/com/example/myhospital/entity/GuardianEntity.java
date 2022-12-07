package com.example.myhospital.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "guardians")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuardianEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
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

    @Column
    @NotBlank
    @Length(max = 50)
    private String relationship;

    @Column(name = "inpatient_id")
    @NotNull
    private long inpatientId;
}
