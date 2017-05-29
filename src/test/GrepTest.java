package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import commands.Grep;
import files.Directory;
import files.DirectoryCursor;
import files.DirectoryStack;
import files.File;
import files.FileSystem;
import handlers.ErrorHandler;
import handlers.Resource;
import handlers.Validator;
import output.Output;

public class GrepTest {

  private static Grep grep;
  private static FileSystem fs;
  private static Directory directoryA, directoryB, directoryC;
  private static File file, file2;
  private static ArrayList<String> arguments;
  private static Validator validator;
  private static Resource resource;
  private static ErrorHandler eh;
  private static DirectoryCursor dc;
  private static String result;
  private static DirectoryStack ds;
  private static Output out;

  @BeforeClass
  public static void oneTimeSetup() {
    fs = FileSystem.getSystem();
    directoryA = new Directory(fs, "dirA");
    directoryB = new Directory(fs, "dirB");
    directoryC = new Directory(directoryA, "dirC");
    file = new File("file");
    file2 = new File("file2");
    fs.addChild(directoryA);
    fs.addChild(directoryB);
    fs.addFile(file);
    directoryA.addFile(file2);
    directoryA.addChild(directoryC);
    arguments = new ArrayList<String>();
    eh = new ErrorHandler();
    dc = new DirectoryCursor(fs);
    validator = new Validator(eh, dc);
    file.append("first line");
    file.append("second line");
    file2.append("first line file 2");
    out = new Output();
    resource = new Resource(ds, out);
  }

  @Before
  public void setup() {
    grep = new Grep();
    arguments.clear();

  }

  @Test
  public void testGrepOnOneLineOfOneFile() {
    arguments.add("second");
    arguments.add("file");
    grep.execute(arguments, eh, validator, resource);
    result = out.consumeStdOutput();
    assertEquals("file:\nsecond line\n", result);
  }

  @Test
  public void testGrepOnTwoLinesOfOneFile() {
    arguments.add("line");
    arguments.add("file");
    grep.execute(arguments, eh, validator, resource);
    result = out.consumeStdOutput();
    assertEquals("file:\nfirst line\nsecond line\n", result);
  }

  @Test
  public void testGrepOnTwoFiles() {
    arguments.add("first");
    arguments.add("file");
    arguments.add("/dirA/file2");
    grep.execute(arguments, eh, validator, resource);
    result = out.consumeStdOutput();
    assertEquals("file:\nfirst line\n\nfile2:\nfirst line file 2\n", result);
  }

  @Test
  public void testRecursiveGrep() {
    File file3 = new File("file3");
    file3.append("first line file 3");
    directoryC.addFile(file3);
    arguments.add("-R");
    arguments.add("line");
    arguments.add("/");
    grep.execute(arguments, eh, validator, resource);
    result = out.consumeStdOutput();
    assertEquals(
        "/file: \nfirst line\nsecond line\n\n/dirA/file2: \nfirst line file 2\n"
            + "\n/dirA/dirC/file3: \nfirst line file 3\n",
        result);
  }

}
