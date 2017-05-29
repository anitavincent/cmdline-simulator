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

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import files.File;

public class FileTest {

  private static File file;

  @BeforeClass
  public static void oneTimeSetup() {

  }

  @Before
  public void setup() {
    file = new File("file");
    file.append("content of file");
  }

  @Test
  public void testGetContent() {
    assertEquals("content of file\n", file.getContent());
  }

  @Test
  public void testAppend() {
    file.append("appended line");
    assertEquals("content of file\nappended line\n", file.getContent());
  }

  @Test
  public void testOverwrite() {
    file.overwrite("overwritten line");
    assertEquals("overwritten line\n", file.getContent());
  }

  @Test
  public void getName() {
    assertEquals("file", file.getName());
  }

}
