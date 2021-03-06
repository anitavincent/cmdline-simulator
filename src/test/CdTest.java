// **********************************************************
// Assignment2:
// Student1: Anita Paes Vincent
// UTOR user_name: paesvinc
// UT Student #: 1002812049
// Author: Anita Paes Vincent
//
// Student2:
// UTOR user_name: dinizroc
// UT Student #: 1002813459
// Author: Ives Levi Diniz Rocha
//
// Student3: Jessica Provenciano Silverio
// UTOR user_name: provenci
// UT Student #: 1002813486
// Author: Jessica Provenciano Silverio
//
// Student4:
// UTOR user_name:
// UT Student #:
// Author:
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC 207 and understand the consequences.
// *********************************************************
package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import commands.Cd;
import files.Directory;
import files.DirectoryCursor;
import files.DirectoryStack;
import files.File;
import files.FileSystem;
import handlers.ErrorHandler;
import handlers.Resource;
import handlers.Validator;
import output.Output;

public class CdTest {

  private static Cd cd;
  private static FileSystem fs;
  private static Directory directoryA, directoryB, directoryC, working;
  private static File file;
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
    file.append("this is the content of the file");

  }

  @Before
  public void setup() {
    cd = new Cd();
    arguments.clear();
    dc.moveToDirectory(fs);
  }

  @Test
  public void testMoveDown() {
    arguments.add("dirA");
    cd.execute(arguments, eh, validator, resource);
    working = dc.getWorkingDirectory();
    assertEquals(directoryA, working);
  }

  @Test
  public void testMoveUp() {
    arguments.add("dirB");
    cd.execute(arguments, eh, validator, resource);
    working = dc.getWorkingDirectory();
    assertEquals(directoryB, working);
    arguments.clear();
    arguments.add("..");
    cd.execute(arguments, eh, validator, resource);
    working = dc.getWorkingDirectory();
    assertEquals(fs, working);
  }

  @Test
  public void testInvalidPath() {
    arguments.add("dirD");
    cd.execute(arguments, eh, validator, resource);
    result = out.consumeStdError();
    assertEquals(" Invalid path. The file or directory does not exist", result);
  }

}
