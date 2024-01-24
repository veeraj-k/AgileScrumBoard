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

    private String description;


    @ManyToOne
    @JoinColumn(name = "columnid", referencedColumnName = "id")//, nullable = false)
    @JsonBackReference //this one
	private Column column_id;

    @JsonManagedReference
    @OneToMany(mappedBy = "taskid", cascade = CascadeType.ALL)
    private List<TaskComment> comments;

    @jakarta.persistence.Column(columnDefinition = "VARCHAR_IGNORECASE")
    @Enumerated(EnumType.STRING)
    private TaskType type;

    private Integer storyPoints;




    public Task(Long id,String title,String description,Column column_id){
        this.id=id;
        this.title=title;
        this.description=description;
        this.column_id=column_id;
    }
}