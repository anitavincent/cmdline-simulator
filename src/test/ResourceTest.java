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
import org.junit.Test;

import files.DirectoryStack;
import handlers.Resource;
import output.Output;

public class ResourceTest {

  private Resource resource;
  private DirectoryStack ds;
  private Output out;

  @Before
  public void setup() {
    ds = new DirectoryStack();
    out = new Output();
    resource = new Resource(ds, out);
  }

  @Test
  public void testAddToInputLog() {
    resource.addToInputLog("added input");
    assertEquals("added input", resource.getInputLog().get(0));
  }

}
