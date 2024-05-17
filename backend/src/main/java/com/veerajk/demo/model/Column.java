package com.veerajk.demo.model;


import java.util.List;

import jakarta.persistence.*;

import lombok.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "bcolumn")
public class Column {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private int location;
    
//    @JsonManagedReference
//    @OneToMany(mappedBy = "column_id", cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "column_id")
    private List<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "sprintid")//, nullable = false)
//    @JoinColumn(name = "sprintid", referencedColumnName = "id")//, nullable = false)
//    @JsonBackReference
    private Sprint sprint;


    @jakarta.persistence.Column(columnDefinition = "boolean default false")
    private boolean isdone;
     
}
