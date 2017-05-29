package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import commands.Mv;
import files.Directory;
import files.DirectoryCursor;
import files.DirectoryStack;
import files.File;
import files.FileSystem;
import handlers.ErrorHandler;
import handlers.Resource;
import handlers.Validator;
import output.Output;

public class MvTest {

  private static Mv mv;
  private static Directory directoryA, directoryB, directoryC;
  private static FileSystem fs;
  private static File file;
  private static ArrayList<String> arguments;
  private static Validator validator;
  private static Resource resource;
  private static ErrorHandler eh;
  private static DirectoryCursor dc;
  private static DirectoryStack ds;
  private static Output out;

  @BeforeClass
  public static void oneTimeSetup() {
    fs = FileSystem.getSystem();
    directoryA = new Directory(fs, "dirA");
    directoryB = new Directory(fs, "dirB");
    directoryC = new Directory(directoryA, "dirC");
    file = new File("file");
    fs.addChild(directoryA);
    fs.addChild(directoryB);
    fs.addFile(file);
    directoryA.addChild(directoryC);
    arguments = new ArrayList<String>();
    eh = new ErrorHandler();
    dc = new DirectoryCursor(fs);
    validator = new Validator(eh, dc);
    out = new Output();
    resource = new Resource(ds, out);
    file.append("content of file");
  }

  @Before
  public void setup() {
    mv = new Mv();
    arguments.clear();

  }

  @Test
  public void testMoveFile() {
    arguments.add("file");
    arguments.add("dirA/dirC");
    mv.execute(arguments, eh, validator, resource);
    assertTrue(directoryC.getFiles().containsKey("file"));
    assertFalse(fs.getFiles().containsKey("file"));
    directoryC.removeFile(file);
    fs.addFile(file);
  }

  @Test
  public void testMoveDirectory() {
    arguments.add("dirB");
    arguments.add("dirA/dirC");
    mv.execute(arguments, eh, validator, resource);
    assertTrue(directoryC.getChildren().containsKey("dirB"));
    assertFalse(fs.getChildren().containsKey("dirB"));
    directoryB.changeParent(fs);
  }


}
