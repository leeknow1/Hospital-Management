package com.example.myhospital.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private long id;

    @Column(name = "room_type")
    @Length(max = 50)
    @NotBlank
    private String roomType;

    @Column(name = "room_number", unique = true)
    @NotNull
    @Positive
    private long roomNumber;

    @Column(name = "ward_type")
    @Length(max = 50)
    @NotBlank
    private String wardType;

    @Column
    @NotNull
    @Positive
    private long charge;
}
