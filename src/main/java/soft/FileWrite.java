package soft;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWrite {

  protected static BufferedWriter writer;

  public FileWrite(String path) {
    try {
      writer = Files.newBufferedWriter(Path.of(path));
    } catch (Exception e) {
      writer = null;
      System.out.println(e.getStackTrace());
    }
  }

  public static BufferedWriter getWriter() {
    return writer;
  }

  public static void write(String toWrite) {
    if (writer != null) {
      try {
        writer.write(toWrite);
      } catch (IOException e) {
        System.out.println(e.getStackTrace());
      }
    }
  }
}
