package ru.nsu.chaiko.dsl;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class TaskGrade {
    private int minimumForThree = 4;
    private int minimumForFour = 6;
    private int minimumForFive = 8;
}
