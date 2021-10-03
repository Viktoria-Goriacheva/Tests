import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import soft.FileWrite;
import soft.Priority;
import soft.Project;
import soft.Service;
import soft.Task;
import soft.User;

public class Main extends FileWrite {

  private static final Logger exLogger = LogManager.getLogger("ExLogger");
  public static String path;
  private static Connection connection;

  public Main(String path) {
    super(path);
  }

  public static void main(String[] args) {
    try {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Enter the path to the file to write");
      path = scanner.nextLine();
      Service service = new Service(path);
      getConnectionSQLite();
      Task task1 = new Task(new Project("job1"), new User("Jimm"), Priority.LOW, "gradle", "online",
          "write srttings");
      addTask(task1);
      addTask(task1);
      addTask(task1);
      addTask(task1);
      Task task2 = new Task(new Project("job9"), new User("Jon"), Priority.HIGH, "maven", "online",
          "write");
      updateTask(task2, 19);
      deleteTask(task2);
      showAllUsers();

      Task task3 = new Task(new Project("job1"), new User("Jimm"));
      service.addTask(task3);
      User user = new User("Jon");
      Task task4 = new Task(new Project("job2"), user);
      service.addTask(task4);
      service.addTask(new Task(new Project("job3"), new User("Jon")));
      service.addTask(new Task(new Project("job4"), new User("Jinni")));
      System.out.println(service.tasksForOneUser("Jon"));
      service.deleteTask(task3);
      System.out.println(service.allUsers());
      System.out.println(service.allProjects());
      System.out.println(service.allTasks());
      task4.setDescription("Make backend site");
      task4.setPriority(Priority.LOW);
      task4.setType("online");
      service.closeWrite();
    } catch (Exception e) {
      exLogger.error(e.getMessage());
    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException ex) {
          exLogger.error(ex.getMessage());
        }
      }
    }
  }

  public static void getConnectionSQLite() throws SQLException {
    connection = DriverManager.getConnection("jdbc:sqlite:test.db");
    Statement statement = connection.createStatement();
    statement.executeUpdate("CREATE TABLE IF NOT EXISTS projects ("
        + " id          INTEGER PRIMARY KEY AUTOINCREMENT,"
        + " project     TEXT NOT NULL,"
        + " user        TEXT NOT NULL,"
        + " priority    TEXT,"
        + " description TEXT,"
        + " subject     TEXT,"
        + " type        TEXT"
        + ")");
  }

  private static void addTask(Task task) throws SQLException {
    FileWrite.write("Add task " + task + "\n");
    PreparedStatement insertStmt = connection.prepareStatement(
        "INSERT INTO projects(project, user, priority,subject,type,description) VALUES(?,?,?,?,?,?)");
    insertStmt.setString(1, task.getProject().getNameProject());
    insertStmt.setString(2, task.getUser().getNameUser());
    insertStmt.setString(3, task.getPriority().toString());
    insertStmt.setString(4, task.getSubject());
    insertStmt.setString(5, task.getType());
    insertStmt.setString(6, task.getDescription());
    insertStmt.executeUpdate();
    exLogger.info("Add task " + task + "\n");
    ResultSet generatedKeys = insertStmt.getGeneratedKeys();
    if (generatedKeys.next()) {
      System.out.format("Проект %s добавлен. id: %d%n",
          task.getProject().getNameProject(), generatedKeys.getLong(1));
    }
  }

  private static void showAllUsers() throws SQLException {
    System.out.format("|%4s|%8s|%6s|%8s|%15s|%10s|%6s|%n", "id", "project", "user", "priority",
        "description", "subject", "type");
    System.out.println("|----|--------|------|--------|---------------|----------|------|");
    Statement statement = connection.createStatement();
    ResultSet rs = statement.executeQuery(
        "SELECT * FROM projects");
    while (rs.next()) {
      int id = rs.getInt(1);
      String project = rs.getString("project");
      String user = rs.getString("user");
      String priority = rs.getString("priority");
      String subject = rs.getString("subject");
      String type = rs.getString("type");
      String description = rs.getString("description");
      System.out
          .format("|%4d|%8s|%6s|%8s|%15s|%10s|%6s|%n", id, project, user, priority, description,
              subject, type);
    }
  }

  private static void deleteTask(Task task) throws SQLException {
    FileWrite.write("Delete task " + task + "\n");
    PreparedStatement updateStmt = connection.prepareStatement(
        "DELETE FROM projects WHERE user = ?");
    updateStmt.setString(1, task.getUser().getNameUser());
    updateStmt.executeUpdate();
    exLogger.info("Delete task " + task + "\n");
  }

  private static void updateTask(Task newTask, int id) throws SQLException {
    FileWrite.write("Changed task with ID = " + id + " on new " + newTask + "\n");
    PreparedStatement updateStmt = connection.prepareStatement(
        "UPDATE projects SET project = ?,user = ?,priority = ?,subject = ?,type = ?,description = ? WHERE id = ?");
    updateStmt.setString(1, newTask.getProject().getNameProject());
    updateStmt.setString(2, newTask.getUser().getNameUser());
    updateStmt.setString(3, newTask.getPriority().toString());
    updateStmt.setString(4, newTask.getSubject());
    updateStmt.setString(5, newTask.getType());
    updateStmt.setString(6, newTask.getDescription());
    updateStmt.setInt(7, id);
    updateStmt.executeUpdate();
    exLogger.info("Changed task with ID = " + id + " on new " + newTask + "\n");
  }
}


