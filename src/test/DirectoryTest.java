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

import org.junit.BeforeClass;
import org.junit.Test;

import files.Directory;
import files.DirectoryStack;
import files.File;
import files.FileSystem;

public class DirectoryTest {

  private static FileSystem fs;
  private static Directory directoryA, directoryB, directoryC;
  private static File file;
  static DirectoryStack ds;

  @BeforeClass
  public static void oneTimeSetup() {
    fs = FileSystem.getSystem();
    directoryA = new Directory(fs, "dirA");
    directoryB = new Directory(fs, "dirB");
    directoryC = new Directory(directoryA, "dirC");
    file = new File("file");
    file.append("this is the content of the file");
  }

  @Test
  public void testAddChild() {
    fs.addChild(directoryA);
    assertEquals(fs.getChildren().get("dirA"), directoryA);
    assertEquals(directoryA.getParent(), fs);
  }

  @Test
  public void testAddFile() {
    fs.addFile(file);
    assertEquals(fs.getFiles().get("file"), file);
  }

  @Test
  public void testGetChildren() {
    directoryB.addChild(directoryC);
    assertEquals(directoryB.getChildren().keySet().toString(), "[dirC]");
  }

}
