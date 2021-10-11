package soft.servis.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import soft.model.Task;

public class ServisSQLite {

  private static final Logger logger = LogManager.getLogger(ServisSQLite.class);
  public static Connection connection;
  private static ServisSQLite INSTANCE;

  private ServisSQLite() {
  }

  static {
    try {
      INSTANCE = new ServisSQLite();
    } catch (Exception e) {
      logger.error("Exception occured while creating a Singleton Class");
    }
  }

  public static ServisSQLite getInstance() {
    return INSTANCE;
  }

  public void getConnectionSQLite() {
    try {
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
    } catch (SQLException e) {
      logger.error("The database connection is not established");
    }
  }

  public void addTask(Task task) {
    try {
      PreparedStatement insertStmt = connection.prepareStatement(
          "INSERT INTO projects(project, user, priority,subject,type,description) VALUES(?,?,?,?,?,?)");
      insertStmt.setString(1, task.getProject().getNameProject());
      insertStmt.setString(2, task.getUser().getNameUser());
      insertStmt.setString(3, task.getPriority().toString());
      insertStmt.setString(4, task.getSubject());
      insertStmt.setString(5, task.getType());
      insertStmt.setString(6, task.getDescription());
      insertStmt.executeUpdate();
      logger.info("Add task " + task + "\n");
      ResultSet generatedKeys = insertStmt.getGeneratedKeys();
      if (generatedKeys.next()) {
        System.out.format("Проект %s добавлен. id: %d%n",
            task.getProject().getNameProject(), generatedKeys.getLong(1));
      }
    } catch (
        java.sql.SQLException e) {
      logger.error("Error when adding a task to the database");
    }
  }

  public void showAllUsers() {
    try {
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
    } catch (SQLException e) {
      System.out.println("Error access to the database or waiting time has expired");
    }
  }

  public void deleteTask(String project) {
    try {
      PreparedStatement updateStmt = connection.prepareStatement(
          "DELETE FROM projects WHERE project = ?");
      updateStmt.setString(1, project);
      updateStmt.executeUpdate();
      logger.info("Delete task where project = " + project + "\n");
    } catch (SQLException e) {
      logger.error("Error access to the database");
    }
  }
}

