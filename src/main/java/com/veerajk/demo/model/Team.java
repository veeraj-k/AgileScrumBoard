package com.veerajk.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToOne(mappedBy = "team", orphanRemoval = true)
    private Sprint sprint;

    @OneToOne(mappedBy = "team")
    private Backlog backlog;
}
