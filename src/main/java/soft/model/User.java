package soft.model;

public class User {

  private String nameUser;

  public User() {
  }

  public User(String nameUser) {
    this.nameUser = nameUser;
  }

  public String getNameUser() {
    return nameUser;
  }

  @Override
  public String toString() {
    return String
        .format("Name User: %s",
            this.nameUser);
  }
}
