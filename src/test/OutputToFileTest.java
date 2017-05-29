package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import files.DirectoryCursor;
import files.File;
import files.FileSystem;
import handlers.ErrorHandler;
import handlers.Validator;
import output.Output;
import output.OutputToFile;

public class OutputToFileTest {

  private Output out;
  private OutputToFile otf;
  private Validator validator;
  private ErrorHandler handler;
  private FileSystem fs;
  private DirectoryCursor dc;
  private File file;

  @Before
  public void setup() {
    out = new Output();
    otf = new OutputToFile(out);
    fs = FileSystem.getSystem();
    fs.deleteItself();
    fs = FileSystem.getSystem();
    dc = new DirectoryCursor(fs);
    validator = new Validator(handler, dc);
    file = new File("file");
    file.append("initial content");
    fs.addFile(file);
  }

  @Test
  public void testSaveOutputToFileCreatingNewFile() {
    out.appendToStdOutput("output");
    otf.saveOutputToFile("file2", ">>", validator, handler);
    String content = fs.getFiles().get("file2").getContent();
    assertEquals("output\n", content);
  }

  @Test
  public void testSaveMessageToFileOverwrite() {
    otf.saveMessageToFile("message", "file", ">", validator, handler);
    String content = fs.getFiles().get("file").getContent();
    assertEquals("message\n", content);
  }

  @Test
  public void testSaveMessageToFileAppend() {
    otf.saveMessageToFile("message", "file", ">>", validator, handler);
    String content = fs.getFiles().get("file").getContent();
    assertEquals("initial content\nmessage\n", content);
  }

}
