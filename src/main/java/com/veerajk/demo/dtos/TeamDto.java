package com.veerajk.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {

    private Long id;
    private String name;
    private SprintDto sprint;

}
