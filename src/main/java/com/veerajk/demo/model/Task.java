package com.veerajk.demo.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.veerajk.demo.enums.TaskType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
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
//    @jakarta.persistence.Column(columnDefinition = "LONGTEXT")
    private String description;


    @ManyToOne
    @JoinColumn(name = "columnid", referencedColumnName = "id")
//    @JsonBackReference
	private Column column_id;

//    @JsonManagedReference
    @OneToMany(mappedBy = "taskid", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    private List<TaskComment> comments;

    @Enumerated(EnumType.STRING)
    private TaskType type;


    private Integer storyPoints=0;
    private boolean isvisible=true;

    @ManyToOne
    @JoinColumn(name="userid" )
    private User user;
    @jakarta.persistence.Column(columnDefinition = "boolean default false")
    private boolean iscompleted;

    private Date created;
    private Date updated;


}