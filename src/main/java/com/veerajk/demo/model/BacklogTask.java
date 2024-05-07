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

    @OneToMany(mappedBy = "backlogTask", orphanRemoval = true)
    private List<BacklogTaskComment> backlogTaskComments = new ArrayList<>();

}
