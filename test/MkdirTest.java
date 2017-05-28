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
import java.util.HashMap;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import commands.Mkdir;
import files.Directory;
import files.DirectoryCursor;
import files.DirectoryStack;
import files.File;
import files.FileSystem;
import handlers.ErrorHandler;
import handlers.Resource;
import handlers.Validator;
import output.Output;

public class MkdirTest {

  private static Mkdir mkdir;
  private static FileSystem fs;
  private static Directory directoryA, directoryB, directoryC;
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
    file.append("this is the content of the file");
    out = new Output();
    resource = new Resource(ds, out);
  }

  @Before
  public void setup() {
    mkdir = new Mkdir();
    arguments.clear();

  }

  @Test
  public void testCreateTwoAtPwd() {
    arguments.add("dirE");
    arguments.add("dirF");
    mkdir.execute(arguments, eh, validator, resource);
    HashMap<String, Directory> children = fs.getChildren();
    assertEquals("[dirB, dirA, dirF, dirE]", children.keySet().toString());
  }

  @Test
  public void testCreateOneAtAbsolutePath() {
    arguments.add("/dirA/dirD");
    mkdir.execute(arguments, eh, validator, resource);
    HashMap<String, Directory> children = directoryA.getChildren();
    assertEquals("[dirD, dirC]", children.keySet().toString());
  }

  @Test
  public void testDirectoryAlreadyExists() {
    arguments.add("dirA");
    mkdir.execute(arguments, eh, validator, resource);
    HashMap<String, Directory> children = fs.getChildren();
    assertEquals("[dirB, dirA]", children.keySet().toString());
  }

}
