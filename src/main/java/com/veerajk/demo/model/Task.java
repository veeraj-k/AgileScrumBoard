package com.veerajk.demo.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.veerajk.demo.enums.TaskType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task{

    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    @jakarta.persistence.Column(columnDefinition = "LONGTEXT")
    private String description;


    @ManyToOne
    @JoinColumn(name = "columnid", referencedColumnName = "id")
//    @JsonBackReference
	private Column column_id;

//    @JsonManagedReference
    @OneToMany(mappedBy = "taskid", cascade = CascadeType.ALL)
    private List<TaskComment> comments;

    @Enumerated(EnumType.STRING)
    private TaskType type;


    private Integer storyPoints=0;
    private boolean isvisible=true;

    @ManyToOne
    @JoinColumn(name="userid" )
    private User user;



}