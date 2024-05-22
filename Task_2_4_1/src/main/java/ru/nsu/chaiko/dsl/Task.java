package ru.nsu.chaiko.dsl;

import java.time.LocalDate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Task {
    String taskName;
    LocalDate softDeadline;
    LocalDate hardDeadline;
}
