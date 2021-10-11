package soft.model;

public class Project {

  private String nameProject;

  public Project() {
  }

  public Project(String nameProject) {
    this.nameProject = nameProject;
  }

  public String getNameProject() {
    return nameProject;
  }

  @Override
  public String toString() {
    return String
        .format("Name Project: %s",
            this.nameProject);
  }
}
