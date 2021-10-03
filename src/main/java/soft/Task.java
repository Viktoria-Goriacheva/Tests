package soft;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Task {

  private Project project;
  private User user;
  private Priority priority;
  private String subject;
  private String type;
  private String description;
  private static Logger logger = LogManager.getLogger("InfoLogger");

  public Task(Project project, User user) {
    this.project = project;
    this.user = user;
  }

  public Task(Project project, User user, Priority priority, String subject, String type,
      String description) {
    this.project = project;
    this.user = user;
    this.priority = priority;
    this.subject = subject;
    this.type = type;
    this.description = description;
  }

  public Project getProject() {
    return project;
  }

  public User getUser() {
    return user;
  }

  public Priority getPriority() {
    return priority;
  }

  public void setPriority(Priority priority) {
    this.priority = priority;
    logger.info(this.user.toString() + " Appointed new\n"
        + "a priority" + priority);
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
    logger.info(this.user.toString() + " Appointed new\n"
        + "a subject - " + subject);
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
    logger.info(this.user.toString() + " Appointed new a type - " + type);
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
    logger.info("Appointed new\n"
        + "a description" + this.user.toString());
  }

  @Override
  public String toString() {
    return String
        .format("Project: %s | User: %s | Priority: %s | Subject: %s | Type: %s | Description: %s",
            this.project, this.user, this.priority, this.subject, this.type, this.description);
  }
}
