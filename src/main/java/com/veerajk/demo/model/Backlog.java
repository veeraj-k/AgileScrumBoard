package com.veerajk.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Backlog {
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name="team_id")
    private Team team;

    @OneToMany
    private List<Task> tasks;

}
