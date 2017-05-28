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

import commands.Cat;
import files.DirectoryCursor;
import files.DirectoryStack;
import files.File;
import files.FileSystem;
import handlers.ErrorHandler;
import handlers.Resource;
import handlers.Validator;
import output.Output;

public class CatTest {

  private static Cat cat;
  private static FileSystem fs;
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
    file = new File("file");
    fs.addFile(file);
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
    cat = new Cat();
    arguments.clear();
  }

  @Test
  public void testFileWithContent() {
    arguments.add("file");
    cat.execute(arguments, eh, validator, resource);
    result = out.consumeStdOutput();
    assertEquals("this is the content of the file\n", result);
  }

  @Test
  public void testInvalidFile() {
    arguments.add("fakefile");
    cat.execute(arguments, eh, validator, resource);
    result = out.consumeStdError();
    assertEquals("File not found on the specified path", result);

  }

  @Test
  public void testInvalidPath() {
    arguments.add("dirA/dirD/file");
    cat.execute(arguments, eh, validator, resource);
    result = out.consumeStdError();
    assertEquals(" Invalid path. The file or directory does not exist", result);
  }

}
