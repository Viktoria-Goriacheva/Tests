import java.io.IOException;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import soft.model.Priority;
import soft.model.Project;
import soft.model.Task;
import soft.model.User;
import soft.servis.database.ServisSQLite;
import soft.servis.file.FileWrite;
import soft.servis.file.ServiceFile;

public class Main {

  private static final Logger logger = LogManager.getLogger(Main.class);
  public static String path;
  public static String command;

  public static void main(String[] args) {
    try (FileWrite fileWrite = new FileWrite(path)) {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Enter the number: 1 - Working with a file; 2 - Working with a database");
      int command0 = scanner.nextInt();
      if (command0 == 1) {
        System.out.println("Enter the path to the file to write");
        path = scanner.next();
        ServiceFile serviceFile = new ServiceFile(path);
        for (; ; ) {
          System.out.println(
              "Enter the number of command: 1- Add; 2- Delete; 3- Show list; 4- Show all of Users; 5- Show all of Projects; 6- Show tasks for one user; 7- Exit");
          command = scanner.next();
          if (Integer.parseInt(command) == 7) {
            break;
          }
          switch (Integer.parseInt(command)) {
            case 1:
              System.out.println("Enter the data in the specified order through ; " + "\n"
                  + "Name project;Name user;Priority (HIGH,MEDIUM or LOW);Subject;Type;Description");
              Scanner scanner1 = new Scanner(System.in);
              String line0 = scanner1.nextLine();
              String[] result = line0.split(";");
              if (result.length == 6 && !result[0].trim().isEmpty() && !result[1].trim()
                  .isEmpty()) {
                try {
                  Task task1 = new Task(new Project(result[0]), new User(result[1]),
                      Priority.valueOf(result[2]), result[3],
                      result[4],
                      result[5]);

                  serviceFile.addTask(task1);
                } catch (IllegalArgumentException il) {
                  logger.error("Arguments should not be empty");
                  break;
                }
              } else {
                logger.info("Data incorrectly entered");
              }
              break;
            case 2:
              System.out.println("Enter the name of the project to delete");
              Scanner scanner2 = new Scanner(System.in);
              serviceFile.deleteTask(scanner2.nextLine());
              break;
            case 3:
              System.out.println(serviceFile.allTasks());
              break;
            case 4:
              System.out.println(serviceFile.allUsers());
              break;
            case 5:
              System.out.println(serviceFile.allProjects());
              break;
            case 6:
              Scanner scanner5 = new Scanner(System.in);
              System.out.println("Enter the name of user");
              String nameUser = scanner5.nextLine();
              System.out.println(serviceFile.tasksForOneUser(nameUser));
              break;
            case 7:
              break;
            default:
              logger.error("Wrong number command");
              break;
          }
        }
      } else if (command0 == 2) {
        ServisSQLite servisSQLite = ServisSQLite.getInstance();
        servisSQLite.getConnectionSQLite();
        for (; ; ) {
          System.out.println(
              "Enter the number of command: 1- Add; 2- Delete; 3- Show list; 4- Exit");
          command = scanner.next();
          if (Integer.parseInt(command) == 4) {
            break;
          }
          switch (Integer.parseInt(command)) {
            case (1):
              System.out.println("Enter the data in the specified order through ; " + "\n"
                  + "Name project;Name user;Priority (HIGH,MEDIUM or LOW);Subject;Type;Description");
              Scanner scanner3 = new Scanner(System.in);
              String line = scanner3.nextLine();
              String[] result = line.split(";");
              if (result.length == 6 && !result[0].trim().isEmpty() && !result[1].trim()
                  .isEmpty()) {
                try {
                  Task task1 = new Task(new Project(result[0]), new User(result[1]),
                      Priority.valueOf(result[2]), result[3],
                      result[4],
                      result[5]);
                  servisSQLite.addTask(task1);
                } catch (IllegalArgumentException il) {
                  logger.error("Arguments should not be empty");
                  break;
                }
              } else {
                logger.info("Data incorrectly entered");
              }
              break;
            case (2):
              System.out.println("Enter the name of the project to delete");
              Scanner scanner4 = new Scanner(System.in);
              servisSQLite.deleteTask(scanner4.nextLine());
              break;
            case (3):
              servisSQLite.showAllUsers();
              break;
            case (4):
              break;
            default:
              logger.error("Wrong number command");
              break;
          }
        }
      } else {
        System.out.println("Wrong command");
      }
    } catch (IOException e) {
      logger.error("Error writing to file");
    }
  }
}


