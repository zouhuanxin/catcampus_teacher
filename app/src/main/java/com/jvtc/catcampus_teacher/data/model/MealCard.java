package com.jvtc.catcampus_teacher.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MealCard {
    private String JYSJ;
    private String JYJE;
    private String ZDMC;

}
