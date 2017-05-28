package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import commands.Get;
import files.Directory;
import files.DirectoryCursor;
import files.DirectoryStack;
import files.File;
import files.FileSystem;
import handlers.ErrorHandler;
import handlers.Resource;
import handlers.Validator;
import output.Output;

public class GetTest {

  private static Get get;
  private static FileSystem fs;
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
    fs.deleteItself();
    fs = FileSystem.getSystem();
    arguments = new ArrayList<String>();
    eh = new ErrorHandler();
    dc = new DirectoryCursor(fs);
    validator = new Validator(eh, dc);
    out = new Output();
    resource = new Resource(ds, out);
  }

  @Before
  public void setup() {
    get = new Get();
    arguments.clear();
  }

  @Test
  public void testGetURL() {
    arguments.add("http://www.cs.cmu.edu/~spok/grimmtmp/073.txt");
    get.execute(arguments, eh, validator, resource);
    Directory workingDir = dc.getWorkingDirectory();
    File saved = workingDir.getFiles().get("073.txt");
    assertNotNull(saved);
    assertTrue(saved.getContent().contains("and with her forget"));
    workingDir.removeFile(saved);
  }

  @Test
  public void testBadURL() {
    arguments.add("online.com/fake.txt");
    get.execute(arguments, eh, validator, resource);
    Directory workingDir = dc.getWorkingDirectory();
    assertTrue(workingDir.getFiles().size() == 0);
  }

  @Test
  public void testContentAlreadyExists() {
    File file = new File("073.txt");
    Directory workingDir = dc.getWorkingDirectory();
    workingDir.addFile(file);
    arguments.add("http://www.cs.cmu.edu/~spok/grimmtmp/073.txt");
    get.execute(arguments, eh, validator, resource);
    assertTrue(workingDir.getFiles().size() == 1);
    workingDir.removeFile(file);
  }

}
