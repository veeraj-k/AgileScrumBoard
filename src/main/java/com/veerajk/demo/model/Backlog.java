package com.veerajk.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Backlog {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name="team_id")
    private Team team;

    @OneToMany(mappedBy = "backlog", orphanRemoval = true,cascade = CascadeType.ALL)
    private List<BacklogTask> backlogTasks = new ArrayList<>();

//    @OneToMany
//    private List<Task> tasks;

}
