package com.example.myhospital.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.sql.Date;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorsEntity {

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
    @Length(max = 50)
    private String type;

    @Column
    @NotBlank
    @Length(max = 15)
    private String phone;

    @Column
    @NotNull
    @Past
    private Date birthday;

    @Column
    @NotNull
    @Positive
    private long charge;
}
