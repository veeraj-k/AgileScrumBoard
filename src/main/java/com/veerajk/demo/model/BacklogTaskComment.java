package com.veerajk.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BacklogTaskComment {

    @Id
    private Long id;

    private String description;

    @ManyToOne
    @JoinColumn(name = "backlog_task_id")
    private BacklogTask backlogTask;

}
