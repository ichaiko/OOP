package ru.nsu.chaiko.dsl;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * group member tasks.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GroupMember extends GroovyConfigurable {
    private String name;
    private String group;
    private String repository;
}
