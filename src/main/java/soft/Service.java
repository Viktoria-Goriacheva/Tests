package soft;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Service extends FileWrite {

  private List<Task> tasks;
  private String path;

  public Service(String path) {
    super(path);
    tasks = new ArrayList<>();
    this.path = path;
  }

  public void closeWrite() throws IOException {
    FileWrite.getWriter().close();
  }

  public void addTask(Task task) {
    tasks.add(task);
    FileWrite.write("add task " + task.toString() + "\n");

  }

  public void deleteTask(Task task) {
    tasks.remove(task);
    FileWrite.write("delete task " + task.toString() + "\n");

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
    List<Task> tasksForOneUser = new ArrayList<>();
    for (Task task : tasks) {
      if (task.getUser().getNameUser().equals(name)) {
        tasksForOneUser.add(task);
      }
    }
    return tasksForOneUser;
  }

  public List<Task> getTasks() {
    return tasks;
  }
}
