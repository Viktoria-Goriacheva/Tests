package soft;

public class User {

  private String nameUser;

  public User(String nameUser) {
    this.nameUser = nameUser;
  }

  public String getNameUser() {
    return nameUser;
  }

  public void setNameUser(String nameUser) throws Exception {
    if (nameUser != null) {
      this.nameUser = nameUser;
    } else {
      throw new Exception("You have entered the wrong name.");
    }
  }

  @Override
  public String toString() {
    return String
        .format("Name User: %s",
            this.nameUser);
  }
}
