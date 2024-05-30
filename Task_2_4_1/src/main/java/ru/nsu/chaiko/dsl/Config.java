package ru.nsu.chaiko.dsl;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * config class.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Config extends GroovyConfigurable {
    private List<Task> tasks;
    private List<GroupMember> students;
    private String groupNumber;
}
