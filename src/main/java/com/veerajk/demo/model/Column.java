package com.veerajk.demo.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Column {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int location;
    
//    @JsonManagedReference
    @OneToMany(mappedBy = "column_id", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "boardid", referencedColumnName = "id")//, nullable = false)
    @JsonBackReference
    private Board board;

    private boolean isvisible=true;
     
}
