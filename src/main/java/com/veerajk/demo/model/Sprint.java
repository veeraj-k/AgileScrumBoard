package com.veerajk.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;


    @OneToMany(mappedBy= "sprint",cascade = CascadeType.ALL)
    private List<Column> columns;


    @Temporal(TemporalType.DATE)
    private LocalDate startdate;
    @jakarta.persistence.Column(columnDefinition = "integer default 15")
    private int duration;

    @OneToOne
    @JoinColumn(name = "team_id")
    private Team team;

}