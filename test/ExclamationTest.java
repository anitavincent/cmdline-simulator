package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import commands.Exclamation;
import files.DirectoryCursor;
import files.DirectoryStack;
import files.FileSystem;
import handlers.ErrorHandler;
import handlers.Interpreter;
import handlers.Resource;
import handlers.Validator;
import output.Output;

public class ExclamationTest {

  private static FileSystem fs;
  private static ArrayList<String> arguments;
  private static ErrorHandler eh;
  private static DirectoryCursor dc;
  private static DirectoryStack ds;
  private static Output out;
  private static Interpreter interpreter;

  @BeforeClass
  public static void oneTimeSetup() {
    fs = FileSystem.getSystem();
    arguments = new ArrayList<String>();
    eh = new ErrorHandler();
    out = new Output();
    ds = new DirectoryStack();
    new Resource(ds, out);
  }

  @Before
  public void setup() {
    new Exclamation();
    arguments.clear();
    fs.deleteItself();
    fs = FileSystem.getSystem();
    dc = new DirectoryCursor(fs);
    new Validator(eh, dc);
    interpreter = new Interpreter(dc, out);

  }

  @Test
  public void testExecuteWithTwoCommands() {
    interpreter.interpret("mkdir dirA dirB");
    interpreter.interpret("ls");
    out.consumeStdOutput();
    interpreter.interpret("!2");
    assertEquals("dirB \ndirA \n", out.consumeStdOutput());
  }

  @Test
  public void testExecuteWithOneCommand() {
    interpreter.interpret("pwd");
    out.consumeStdOutput();
    interpreter.interpret("!1");
    assertEquals("/", out.consumeStdOutput());
  }

  @Test
  public void testExecuteWithoutNumber() {
    interpreter.interpret("pwd");
    out.consumeStdOutput();
    interpreter.interpret("!");
    assertEquals("Invalid argument. The argument provided to ! is"
        + " not an acceptable number", out.consumeStdError());
  }

}
