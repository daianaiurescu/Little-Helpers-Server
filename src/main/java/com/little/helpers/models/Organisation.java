package com.little.helpers.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "organisations")
public class Organisation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "field")
    private String field;

    @Column(name = "description")
    private String description;

    @Column(name = "year")
    private int year;

    @Column(name = "photo")
    private String photo;
}
