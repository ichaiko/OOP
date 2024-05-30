package ru.nsu.chaiko.dsl;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

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
