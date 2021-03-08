package com.jvtc.catcampus_teacher.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class NetModel2<T> {
    public String message;
    public int code;
    public T data;
}
