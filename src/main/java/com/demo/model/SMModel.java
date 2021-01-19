package com.demo.model;

import com.demo.core.data.States;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SMModel {
    private long id;
    private long  delta;
    private States state;
}
