package ru.nsu.chaiko.dsl;

import groovy.lang.GroovyObjectSupport;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GroupMember extends GroovyObjectSupport {
    private String name;
    private String group;
    private String repository;
}
