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

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exceptions.EmptyStackException;
import files.Directory;
import files.DirectoryCursor;
import files.DirectoryStack;
import files.File;
import files.FileSystem;

public class DirectoryStackTest {

  private static DirectoryCursor dc;
  private static FileSystem fs;
  private static Directory directoryA, directoryB;
  private static File file;
  static DirectoryStack ds;

  @BeforeClass
  public static void oneTimeSetup() {
    fs = FileSystem.getSystem();
    directoryA = new Directory(fs, "dirA");
    directoryB = new Directory(fs, "dirB");
    new Directory(directoryA, "dirC");
    file = new File("file");
    file.append("this is the content of the file");
    dc = new DirectoryCursor(fs);
  }

  @Before
  public void setup() {
    dc.moveToDirectory(fs);
    ds = new DirectoryStack();
  }

  @Test
  public void testPush() {
    ds.push(directoryA);
    try {
      assertEquals("/dirA/", ds.pop());
    } catch (EmptyStackException e) {
      fail();
    }
  }

  @Test
  public void testPop() {
    ds.push(directoryA);
    ds.push(directoryB);
    try {
      assertEquals("/dirB/", ds.pop());
    } catch (EmptyStackException e) {
      fail();
    }
  }

  @Test
  public void testPopEmptyStack() {
    try {
      ds.pop();
      fail();
    } catch (EmptyStackException e) {
      // pass
    }
  }

}
