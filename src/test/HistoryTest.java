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
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import commands.History;
import files.Directory;
import files.DirectoryCursor;
import files.DirectoryStack;
import files.File;
import files.FileSystem;
import handlers.ErrorHandler;
import handlers.Resource;
import handlers.Validator;
import output.Output;

public class HistoryTest {

  private static History history;
  private static FileSystem fs;
  private static Directory directoryA, directoryB, directoryC;
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
    file.append("this is the content of the file");
    out = new Output();
  }

  @Before
  public void setup() {
    history = new History();
    arguments.clear();
    resource = new Resource(ds, out);
  }

  @Test
  public void testNoCommands() {
    arguments.add("5");
    history.execute(arguments, eh, validator, resource);
    result = out.consumeStdOutput();
    assertNull(result);
  }

  @Test
  public void testOneCommand() {
    arguments.add("1");
    resource.addToInputLog("inputone");
    resource.addToInputLog("inputtwo");
    history.execute(arguments, eh, validator, resource);
    result = out.consumeStdOutput();
    assertEquals("2 inputtwo\n", result);
  }

  @Test
  public void testNotEnoughCommands() {
    arguments.add("4");
    resource.addToInputLog("inputone");
    resource.addToInputLog("inputtwo");
    history.execute(arguments, eh, validator, resource);
    result = out.consumeStdOutput();
    assertEquals("1 inputone\n2 inputtwo\n", result);
  }

  @Test
  public void testNegativeNumber() {
    arguments.add("-3");
    resource.addToInputLog("inputone");
    resource.addToInputLog("inputtwo");
    history.execute(arguments, eh, validator, resource);
    result = out.consumeStdError();
    assertEquals(
        "Invalid argument.Parameter for history command is a negative number",
        result);
  }

}
