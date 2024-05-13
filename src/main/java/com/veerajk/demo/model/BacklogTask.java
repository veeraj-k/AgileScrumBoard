package com.veerajk.demo.model;

import com.veerajk.demo.enums.TaskType;
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
public class BacklogTask {

    @Id
//    @GeneratedValue
    private Long id;

    private String title;

    @Lob
    @jakarta.persistence.Column(columnDefinition = "LONGTEXT")
    private String description;


    @Enumerated(EnumType.STRING)
    private TaskType type;

    private Integer storyPoints=0;

    @ManyToOne
    @JoinColumn(name="userid" )
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "backlog_id")
    private Backlog backlog;

    @OneToMany(mappedBy = "backlogTask", orphanRemoval = true,cascade = CascadeType.ALL)
    private List<BacklogTaskComment> backlogTaskComments = new ArrayList<>();

    private String sprint;
}
