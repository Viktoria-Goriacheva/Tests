package soft.servis.file;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileWrite implements AutoCloseable {

  protected static BufferedWriter writer;
  private static final Logger logger = LogManager.getLogger(FileWrite.class);
  public FileWrite(String path) {
    try {
      writer = Files.newBufferedWriter(Path.of(path));
    } catch (Exception e) {
      writer = null;
    }
  }

  public static void write(String toWrite) {
    if (writer != null) {
      try {
        writer.write(toWrite);
      } catch (IOException e) {
        logger.error("Error writing to file");
      }
    }
  }

  @Override
  public void close() throws IOException {
    if (!(FileWrite.writer == null)){
    writer.close();}
  }
}
