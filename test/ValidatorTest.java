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
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exceptions.InvalidDirectoryOrFileNameException;
import exceptions.InvalidNumberOfArgumentsException;
import files.Directory;
import files.DirectoryCursor;
import files.File;
import files.FileSystem;
import handlers.ErrorHandler;
import handlers.Validator;

public class ValidatorTest {

  private static Validator validator;
  private static FileSystem fs;
  private static Directory dirA;
  private static DirectoryCursor dc;
  private static ErrorHandler eh;
  private static ArrayList<String> arguments;
  private static File file;

  @BeforeClass
  public static void oneTimeSetup() {
    fs = FileSystem.getSystem();
    dirA = new Directory(fs, "dirA");
    file = new File("file");
    dirA.addFile(file);
    arguments = new ArrayList<String>();
  }

  @Before
  public void setup() {
    dc = new DirectoryCursor(fs);
    eh = new ErrorHandler();
    validator = new Validator(eh, dc);
    arguments.clear();
  }

  @Test
  public void testValidateNumberOfArgumentsIsZero() {
    try {
      validator.validateNumberOfArgumentsIsZero(arguments);
      // pass
    } catch (InvalidNumberOfArgumentsException e) {
      fail();
    }
  }

  @Test
  public void testValidateNumberOfArgumentsIsNotZero() {
    arguments.add("argument");
    try {
      validator.validateNumberOfArgumentsIsNotZero(arguments);
      // pass
    } catch (InvalidNumberOfArgumentsException e) {
      fail();
    }
  }

  @Test
  public void testValidateLs() {
    try {
      assertEquals(dirA, validator.validateLsAndGetContent("dirA"));
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testSeparateFileNameFromFullPath() {
    assertEquals("file", validator.separateFileNameFromFullPath("/dirA/file"));
  }

  @Test
  public void testSeparateFinalDirectory() {
    assertEquals("dirA", validator.separatePathAndFinalDirectory("/dirA"));
  }

  @Test
  public void testValidateNewDirectoryNameFromPath() {
    try {
      assertEquals("dirC",
          validator.validateNewDirectoryNameAndGetNameFromPath("/dirA/dirC"));
    } catch (InvalidDirectoryOrFileNameException e) {
      fail();
    }
  }

  @Test
  public void testValidateNewFileNameFromPath() {
    try {
      assertEquals("file2",
          validator.validateNewFileNameAndGetNameFromPath("/dirA/file2"));
    } catch (Exception e) {
      fail();
    }
  }


}
