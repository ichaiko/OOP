package ru.nsu.chaiko.git;

import com.puppycrawl.tools.checkstyle.*;
import com.puppycrawl.tools.checkstyle.api.AuditListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.gradle.tooling.BuildException;
import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;
import org.gradle.tooling.TestExecutionException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import ru.nsu.chaiko.dsl.GroupMember;
import ru.nsu.chaiko.dsl.Task;

import static java.nio.file.FileVisitOption.FOLLOW_LINKS;
import static com.puppycrawl.tools.checkstyle.api.AutomaticBean.OutputStreamOptions.NONE;

/**
 * repo class.
 */
public class GitRepository {
    static int MAXWARNINGS = 7;
    private final String repoUrl;
    private final GroupMember groupMember;
    private String rootDirPath;

    /**
     * constr.
     *
     * @param groupMember g.
     */
    public GitRepository(GroupMember groupMember) {
        this.repoUrl = groupMember.getRepository();
        this.groupMember = groupMember;
    }

    /**
     * for cloning.
     */
    public void cloneRepository(String dirPath) {
        this.rootDirPath = dirPath + "/" + groupMember.getName();
        File cloneDirectory = new File(this.rootDirPath);

        try {
//            System.out.println("Cloning repository from " + repoUrl + " to " + this.rootDirPath);
            Git.cloneRepository()
                    .setURI(repoUrl)
                    .setDirectory(cloneDirectory)
                    .call();
            System.out.println("Repository cloned successfully!");
        } catch (GitAPIException e) {
            e.printStackTrace();
            System.err.println("Failed to clone repository: " + e.getMessage());
        }
    }

    /**
     * build.
     */
    public boolean buildProject(String taskName) {
        File projectDir = new File(rootDirPath, taskName);

        if (!projectDir.exists()) {
            System.err.println("Project directory does not exist: " + projectDir.getAbsolutePath());
            return false;
        }

        ProjectConnection connection = GradleConnector
                .newConnector()
                .forProjectDirectory(projectDir)
                .connect();

        return runGradleTask(connection, "assemble");
    }

    /**
     * for docks.
     */
    public boolean generateDocs(String taskName) {
        File projectDir = new File(rootDirPath, taskName);

        if (!projectDir.exists()) {
            System.err.println("Project directory does not exist: " + projectDir.getAbsolutePath());
            return false;
        }

        ProjectConnection connection = GradleConnector
                .newConnector()
                .forProjectDirectory(projectDir)
                .connect();

        return runGradleTask(connection, "javadoc");
    }

    /**
     * for cs.
     */
    @SneakyThrows
    public boolean checkCodeStyle(String taskName) {
        String pathToRepo = rootDirPath;

        DefaultConfiguration config = (DefaultConfiguration) ConfigurationLoader
                .loadConfiguration(String.format("%s/.github/google_checks.xml", pathToRepo),
                        new PropertiesExpander(new Properties()));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        AuditListener listener = new DefaultLogger(byteArrayOutputStream, NONE);

        Checker checker = new Checker();
        checker.setModuleClassLoader(Checker.class.getClassLoader());
        checker.configure(config);
        checker.addListener(listener);

        List<File> javaFiles = Files.walk(Paths.get(String.format("%s/src",
                                pathToRepo + "/" + taskName))
                        , FOLLOW_LINKS)
                .filter(Files::isRegularFile)
                .filter(file -> file.toFile().getPath().endsWith(".java"))
                .map(Path::toFile)
                .collect(Collectors.toList());

        checker.process(javaFiles);
        checker.destroy();

        String results = byteArrayOutputStream.toString();
//        System.out.println("CheckStyle results:\n" + results);
        return StringUtils.countMatches(results, "[WARN]") < MAXWARNINGS;
    }

    /**
     * for gradle tasks.
     */
    private boolean runGradleTask(ProjectConnection connection, String taskName) {
        BuildLauncher build = connection.newBuild().forTasks(taskName);
        try {
            build.run();
//            System.out.println("Task " + taskName + " successful");
            connection.close();
            return true;
        } catch (BuildException | TestExecutionException e) {
            e.printStackTrace();
            System.err.println("Task " + taskName + " failed - " + e.getMessage());
            connection.close();
            return false;
        }
    }

    /**
     * for tests.
     */
    private boolean runTests(String taskName) {
        File projectDir = new File(rootDirPath, taskName);

        if (!projectDir.exists()) {
            System.err.println("Project directory does not exist: " + projectDir.getAbsolutePath());
            return false;
        }

        ProjectConnection connection = GradleConnector
                .newConnector()
                .forProjectDirectory(projectDir)
                .connect();

        return runGradleTask(connection, "test");
    }

    /**
     * check tests.
     */
    public boolean checkTestResults(String taskName) {
        if (!runTests(taskName)) {
            return false;
        }

        try {
            Path testResultsDir = Paths.get(rootDirPath, taskName, "build", "test-results", "test");
            if (!Files.exists(testResultsDir)) {
                System.err.println("Test results directory does not exist: " + testResultsDir.toAbsolutePath());
                return false;
            }

            List<Path> resultFiles = Files.walk(testResultsDir)
                    .filter(Files::isRegularFile)
                    .filter(file -> file.toString().endsWith(".xml"))
                    .collect(Collectors.toList());

            for (Path resultFile : resultFiles) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(resultFile.toFile());

                doc.getDocumentElement().normalize();
                NodeList testsuites = doc.getElementsByTagName("testsuite");

                for (int i = 0; i < testsuites.getLength(); i++) {
                    if (Integer.parseInt(testsuites.item(i).getAttributes().getNamedItem("failures").getNodeValue()) > 0 ||
                            Integer.parseInt(testsuites.item(i).getAttributes().getNamedItem("errors").getNodeValue()) > 0) {
                        System.err.println("Test failures or errors found in: " + resultFile.toAbsolutePath());
                        return false;
                    }
                }
            }
//            System.out.println("All tests passed successfully.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * calc score.
     */
    public double calculateScore(Task currentTask) {
        LocalDate mergeDate = getMergeCommitDate(currentTask.getTaskName());
        LocalDate softDeadline = currentTask.getSoftDeadline();
        LocalDate hardDeadline = currentTask.getHardDeadline();
        System.out.println(mergeDate);

        if (mergeDate == null) {
            System.err.println("Could not determine the merge date.");
            return 0;
        }

        if (mergeDate.isBefore(softDeadline) || mergeDate.isEqual(softDeadline)) {
            return 1.0;
        } else if (mergeDate.isBefore(hardDeadline) || mergeDate.isEqual(hardDeadline)) {
            return 0.5;
        } else {
            return 0;
        }
    }

    /**
     * get date.
     */
    @SneakyThrows
    private LocalDate getMergeCommitDate(String taskBranchName) {
        try (Git git = Git.open(new File(rootDirPath))) {
            Iterable<RevCommit> commits = git.log().add(git.getRepository().resolve("refs/heads/main")).call();
            for (RevCommit commit : commits) {
                if (commit.getParents().length > 1) { // это коммит слияния
                    for (RevCommit parent : commit.getParents()) {
                        RevWalk revWalk = new RevWalk(git.getRepository());
                        RevCommit parentCommit = revWalk.parseCommit(parent);
                        revWalk.dispose();
                        if (parentCommit.getFullMessage().contains(taskBranchName)) {
                            Instant commitInstant = Instant.ofEpochSecond(commit.getCommitTime());
                            return LocalDate.ofInstant(commitInstant, ZoneId.systemDefault());
                        }
                    }
                }
            }

        } catch (GitAPIException e) {
            e.printStackTrace();
        }

        return null;
    }
}
