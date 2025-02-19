package com.fin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateTestRequest {
    private Long taskId;
    private int count;
}
