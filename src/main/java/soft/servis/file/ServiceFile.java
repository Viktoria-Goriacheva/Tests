package soft.servis.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import soft.model.Project;
import soft.model.Task;
import soft.model.User;

public class ServiceFile extends FileWrite {

  private static final Logger logger = LogManager.getLogger(ServiceFile.class);
  private List<Task> tasks;
  private String path;

  public ServiceFile(String path) throws IOException {
    super(path);
    tasks = new ArrayList<>();
    this.path = path;
  }

  public void addTask(Task task) {
    tasks.add(task);
    FileWrite.write("add task " + task.toString() + "\n");
    logger.info("Add task " + task + "\n");
  }

  public void deleteTask(String project) {
    try {
      Task task = tasks.stream().filter(p -> p.getProject().getNameProject().equals(project))
          .findFirst().orElseThrow();
      tasks.remove(task);
      FileWrite.write("Delete task " + task + "\n");
      logger.info("Delete task " + task + "\n");
    } catch (NoSuchElementException e) {
      System.out.println("There is no such project in the list");
    }
  }

  public List<User> allUsers() {
    List<User> users = new ArrayList<>();
    for (Task task : tasks) {
      users.add(task.getUser());
    }
    return users;
  }

  public List<Project> allProjects() {
    List<Project> projects = new ArrayList<>();
    for (Task task : tasks) {
      projects.add(task.getProject());
    }
    return projects;
  }

  public List<Task> allTasks() {
    return tasks;
  }

  public List<Task> tasksForOneUser(String name) {
    List<Task> tasksForOneUser = tasks.stream().filter(p -> p.getUser().getNameUser().equals(name))
        .collect(
            Collectors.toList());
    return tasksForOneUser;
  }
}
