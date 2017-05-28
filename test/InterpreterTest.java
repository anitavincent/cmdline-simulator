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

import files.Directory;
import files.DirectoryCursor;
import files.File;
import files.FileSystem;
import handlers.Interpreter;
import output.Output;

public class InterpreterTest {

  private static FileSystem fs;
  private static Directory dirA;
  private static File file;
  private static DirectoryCursor dc;
  private static Interpreter interpreter;
  private static Output out;

  @BeforeClass
  public static void oneTimeSetup() {
    fs = FileSystem.getSystem();
    fs.deleteItself();
    fs = FileSystem.getSystem();
    dirA = new Directory(fs, "dirA");
    file = new File("file");
    dc = new DirectoryCursor(fs);
    out = new Output();
    file.append("content of file");
    dirA.addFile(file);
  }

  @Before
  public void setup() {
    dc.moveToDirectory(fs);
    interpreter = new Interpreter(dc, out);
  }

  @Test
  public void testInterpretLs() {
    try {
      interpreter.interpret("ls");
      assertEquals("dirA \n", out.consumeStdOutput());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testInterpretCat() {
    try {
      interpreter.interpret("cat dirA/file");
      assertEquals("content of file\n", out.consumeStdOutput());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testInterpretCd() {
    try {
      interpreter.interpret("cd dirA");
    } catch (Exception e) {
      fail();
    }
    assertEquals(dirA, dc.getWorkingDirectory());
  }

  @Test
  public void testInterpretEcho() {
    try {
      interpreter.interpret("echo \"hello world\"");
      assertEquals("hello world", out.consumeStdOutput());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testInterpretExit() {
    try {
      interpreter.interpret("exit");
      assertEquals("exit", out.consumeStdOutput());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testInterpretHistory() {
    try {
      interpreter.interpret("history");
      assertEquals("1 history\n", out.consumeStdOutput());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testInterpretMan() {
    try {
      interpreter.interpret("man man");
      assertEquals("Print documentation for CMD", out.consumeStdOutput());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testInterpretMkdir() {
    try {
      interpreter.interpret("mkdir dirA/dirB");
    } catch (Exception e) {
      fail();
    }
    assertEquals("dirB", dirA.getChildren().get("dirB").getName());
  }

  @Test
  public void testInterpretPushdAndPopd() {
    try {
      interpreter.interpret("pushd dirA");
      assertEquals(dirA, dc.getWorkingDirectory());
      interpreter.interpret("popd");
      assertEquals(fs, dc.getWorkingDirectory());
    } catch (Exception e) {
      fail();
    }

  }

  @Test
  public void testInterpretPwd() {
    try {
      interpreter.interpret("pwd");
      assertEquals("/", out.consumeStdOutput());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testInterpretInvalidCommand() {
    try {
      interpreter.interpret("you used to call me on my shellphone");
      assertEquals("Invalid command", out.consumeStdError());
    } catch (Exception e) {
      fail();
    }
  }


}
