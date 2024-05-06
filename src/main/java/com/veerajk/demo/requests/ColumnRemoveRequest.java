package com.veerajk.demo.requests;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ColumnRemoveRequest {
    Long columnId;
}
