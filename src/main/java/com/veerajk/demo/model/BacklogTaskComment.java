package com.veerajk.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BacklogTaskComment {

    @Id
//    @GeneratedValue
    private Long id;

    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "backlog_task_id")
    private BacklogTask backlogTask;
    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private User user;
}
