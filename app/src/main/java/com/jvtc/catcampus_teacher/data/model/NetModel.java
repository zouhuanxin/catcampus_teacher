package com.jvtc.catcampus_teacher.data.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class NetModel<T> {
    public String message;
    public int code;
    public List<T> data;
}
