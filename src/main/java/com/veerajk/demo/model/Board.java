package com.veerajk.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    // Relationships
//    @JsonManagedReference
    @OneToMany(mappedBy="board")
    private List<Column> columns;


    @Temporal(TemporalType.DATE)
    private LocalDate startdate;
    @jakarta.persistence.Column(columnDefinition = "integer default 15")
    private int duration;


}