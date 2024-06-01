package ru.nsu.chaiko.utils;

import java.util.ArrayList;
import ru.nsu.chaiko.dsl.Config;

/**
 * class for printing.
 */
public class PrintResults {
    private final ArrayList<GroupMemberResult> results;
    private final Config config;

    /**
     * constr.
     *
     * @param results res.
     *
     * @param config conf.
     */
    public PrintResults(ArrayList<GroupMemberResult> results, Config config) {
        this.results = results;
        this.config = config;
    }

    /**
     * for printing.
     */
    public void printResults() {
        printHeader();
        var tasks = config.getTasks();

        for (GroupMemberResult gmResult : results) { // -10%s -9%s
            System.out.format("| %-17s|", gmResult.getGroupMember().getName());
            var tasksResults = gmResult.getTaskResults();
            boolean firstIt = true;

            for (var task : tasks) {
                if (!firstIt) {
                    System.out.format("|%-18s|", " ");
                }

                var subtotal = tasksResults.get(task);
                System.out.format(" %-11s|", task.getTaskName());

                if (subtotal.get(TaskCharacteristics.isBuild)) {
                    System.out.format("    %-5s|", "+");
                } else {
                    System.out.format("    %-5s|", "-");
                }

                if (subtotal.get(TaskCharacteristics.docksGenerated)) {
                    System.out.format("        %-9s|", "+");
                } else {
                    System.out.format("        %-9s|", "-");
                }

                if (subtotal.get(TaskCharacteristics.csPassed)) {
                    System.out.format("       %-8s|", "+");
                } else {
                    System.out.format("       %-8s|", "-");
                }

                if (subtotal.get(TaskCharacteristics.testsPassed)) {
                    System.out.format("    %-5s|", "+");
                } else {
                    System.out.format("    %-5s|", "-");
                }

                if (!firstIt) {
                    System.out.format("%-14s |", " ");
                } else {
                    System.out.format("   %-11f |", gmResult.getScore());
                }
                firstIt = false;

                System.out.print("\n");
            }

            System.out.println("|-------------------------------"
                    + "---------------------------------------------"
                    + "-------------------------|");
        }
    }

    /**
     * header.
     */
    private void printHeader() {
        System.out.println("|-------------------------"
                + "-------------------------------------"
                + "---------------------------------------|");
        System.out.format("|                        "
                        + "                    Group  %s     "
                        + "                                        |\n",
                config.getGroupNumber());
        System.out.println("|------------------|--"
                + "----------|---------|-----------------|"
                + "---------------|---------|---------------|");
        System.out.println("|   Group Member   |    Task  "
                + "  |  Build  |  Documentation  |  "
                + "Check Style  |  Tests  |  TOTAL SCORE  |");
        System.out.println("|------------------|----------"
                + "--|---------|-----------------|--"
                + "-------------|---------|---------------|");
    }
}
