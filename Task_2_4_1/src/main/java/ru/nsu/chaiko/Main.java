package ru.nsu.chaiko;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.nsu.chaiko.dsl.Config;
import ru.nsu.chaiko.dsl.GroupMember;
import ru.nsu.chaiko.dsl.Task;
import ru.nsu.chaiko.git.GitRepository;
import ru.nsu.chaiko.utils.GroupMemberResult;
import ru.nsu.chaiko.utils.Params;
import ru.nsu.chaiko.utils.PrintResults;
import ru.nsu.chaiko.utils.TaskCharacteristics;

/**
 * Main class of the checker.
 * import com.puppycrawl.tools.checkstyle.*;
 */
public class Main {

    /**
     * The main method of the checker.
     *
     * @param args command line arguments
     * @throws IOException               if an I/O error occurs
     * @throws NoSuchFieldException      if a field is not found
     * @throws InvocationTargetException if an exception occurs during method invocation
     * @throws InstantiationException    if an instantiation fails
     * @throws IllegalAccessException    if an attempt to access a field or method is illegal
     * @throws NoSuchMethodException     if a method is not found
     */
    public static void main(String[] args) throws IOException, NoSuchFieldException, InvocationTargetException,
            InstantiationException, IllegalAccessException, NoSuchMethodException {
        Config config = new Config();
        config.configureFromFile(new File(Params.CONFIGPATH));

        ArrayList<GroupMemberResult> results = new ArrayList<>();
        List<GroupMember> students = config.getStudents();
        List<Task> tasks = config.getTasks();

        for (var student : students) {
            GroupMemberResult groupMemberResult = new GroupMemberResult(student);
            GitRepository gitRepo = new GitRepository(student);
            gitRepo.cloneRepository(Params.PATHTOCLONE);
            double totalScore = 0;

            for (var task : tasks) {
                String taskName = task.getTaskName();
                HashMap<TaskCharacteristics, Boolean> currentTaskRes = new HashMap<>();

                boolean isBuild = gitRepo.buildProject(taskName);
                boolean docksGenerated = gitRepo.generateDocs(taskName);
                boolean csPassed = gitRepo.checkCodeStyle(taskName);
                boolean testsPassed = gitRepo.checkTestResults(taskName);

                currentTaskRes.put(TaskCharacteristics.isBuild, isBuild);
                currentTaskRes.put(TaskCharacteristics.docksGenerated, docksGenerated);
                currentTaskRes.put(TaskCharacteristics.csPassed, csPassed);
                currentTaskRes.put(TaskCharacteristics.testsPassed, testsPassed);

                groupMemberResult.updateTaskList(task, currentTaskRes);

                totalScore += gitRepo.calculateScore(task);
            }
            groupMemberResult.setScore(totalScore);

            results.add(groupMemberResult);
        }

        PrintResults print = new PrintResults(results, config);
        print.printResults();

        Path pathToBeDeleted = Paths.get(Params.PATHTOCLONE);
        deleteDirectory(pathToBeDeleted);
    }

    /**
     * Deletes the specified directory and its contents.
     *
     * @param path the path to the directory to be deleted
     * @throws IOException if an I/O error occurs
     */
    public static void deleteDirectory(Path path) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
