package ru.nsu.chaiko.utils;

import java.util.HashMap;
import ru.nsu.chaiko.dsl.GroupMember;
import ru.nsu.chaiko.dsl.Task;

/**
 * results of each student.
 */
public class GroupMemberResult {
    private final GroupMember groupMember;
    private final HashMap<Task,
            HashMap<TaskCharacteristics, Boolean>> taskResults = new HashMap<>();
    private double score;

    /**
     * constructor.
     *
     * @param groupMember arg.
     */
    public GroupMemberResult(GroupMember groupMember) {
        this.groupMember = groupMember;
    }

    /**
     * change state.
     *
     * @param task t.
     *
     * @param taskResult t.
     */

    public void updateTaskList(Task task, HashMap<TaskCharacteristics, Boolean> taskResult) {
        taskResults.put(task, taskResult);
    }

    /**
     * setter.
     *
     * @param score s.
     */
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * getter.
     *
     * @return score.
     */
    public double getScore() {
        return score;
    }

    /**
     * getter.
     *
     * @return member.
     */
    public GroupMember getGroupMember() {
        return groupMember;
    }

    /**
     * getter.
     *
     * @return state.
     */
    public HashMap<Task, HashMap<TaskCharacteristics, Boolean>> getTaskResults() {
        return taskResults;
    }
}
