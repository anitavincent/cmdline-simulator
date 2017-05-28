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

import org.junit.BeforeClass;
import org.junit.Test;

import exceptions.InvalidPathException;
import files.Directory;
import files.DirectoryCursor;
import files.File;
import files.FileSystem;
import handlers.PathChecker;

public class PathCheckerTest {

  private static PathChecker checker;
  private static FileSystem fs;
  private static Directory dirA;
  private static DirectoryCursor dc;
  private static File file;

  @BeforeClass
  public static void oneTimeSetup() {
    fs = FileSystem.getSystem();
    dirA = new Directory(fs, "dirA");
    file = new File("file");
    file.append("content of file");
    dirA.addFile(file);
    dc = new DirectoryCursor(fs);
    checker = new PathChecker(dc);
  }

  @Test
  public void testCheckValidPathForParent() {
    try {
      assertEquals(fs,
          checker.checkIfValidPathForParentAndRetrieveParentDirectory("/dirA"));
    } catch (InvalidPathException e) {
      fail();
    }
  }

  @Test
  public void testCheckValidPathDirectory() {
    try {
      assertEquals(dirA, checker.checkIfValidPathAndRetrieveDirectory("/dirA"));
    } catch (InvalidPathException e) {
      fail();
    }
  }

  @Test
  public void testCheckValidPathFile() {
    try {
      assertEquals(file, checker.checkIfValidPathAndRetrieveFile("dirA/file"));
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testCheckValidPathContent() {
    try {
      assertEquals(file,
          checker.checkValidPathAndRetrieveContent("/dirA/file"));
    } catch (Exception e) {
      fail();
    }
  }



}
