package soft.model;

public class Task {

  private Project project;
  private User user;
  private Priority priority;
  private String subject;
  private String type;
  private String description;

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

  public String getSubject() {
    return subject;
  }

  public String getType() {
    return type;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return String
        .format("Project: %s | User: %s | Priority: %s | Subject: %s | Type: %s | Description: %s",
            this.project, this.user, this.priority, this.subject, this.type, this.description);
  }
}
