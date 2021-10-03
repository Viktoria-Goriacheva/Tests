package soft;

public class Project {

  private String nameProject;

  public Project(String nameProject) {
    this.nameProject = nameProject;
  }

  public String getNameProject() {
    return nameProject;
  }

  public void setNameProject(String nameProject) throws Exception {
    if (nameProject != null) {
      this.nameProject = nameProject;
    } else {
      throw new Exception("You have entered the wrong name.");

    }
  }

  @Override
  public String toString() {
    return String
        .format("Name Project: %s",
            this.nameProject);
  }
}
