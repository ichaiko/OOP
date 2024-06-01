package ru.nsu.chaiko.dsl;

import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * task class.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Task extends GroovyConfigurable {
    String taskName;
    LocalDate softDeadline;
    LocalDate hardDeadline;
}
