package com.veerajk.demo.requests;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class AddComment {
    String description;
    Long userid;
    Long taskid;

}
