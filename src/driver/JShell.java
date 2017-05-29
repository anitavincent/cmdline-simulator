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
package driver;

import java.util.Scanner;

import files.DirectoryCursor;
import files.FileSystem;
import handlers.Interpreter;
import output.Output;
import output.OutputToScreen;

/**
 * JShell
 * 
 * Main class
 * 
 */
public class JShell {
  private DirectoryCursor dc;
  private FileSystem fileSystem;
  private Output output;

  /**
   * Initializes file system
   * 
   */
  public JShell() {
    this.fileSystem = FileSystem.getSystem();
    this.dc = new DirectoryCursor(fileSystem);
    this.output = new Output();
  }

  public DirectoryCursor getDirectoryCursor() {
    return this.dc;
  }

  public Output getOutput() {
    return this.output;
  }

  public void execute() {
    Scanner sc = new Scanner(System.in);
    String line;
    Interpreter i =
        new Interpreter(this.getDirectoryCursor(), this.getOutput());
    OutputToScreen outputToScreen = new OutputToScreen(output);
    boolean runShell = true;
    do {
      System.out.print("/");
      System.out
          .print(this.getDirectoryCursor().getWorkingDirectory().getName());
      System.out.print(":~$");
      line = sc.nextLine();
      // call function that identifies commands
      // and separates, counts and deals with arguments
      i.interpret(line);
      if (this.output.getStdOutput() != null
          && this.output.getStdOutput().equals("exit")) {
        runShell = false;
      } else {
        outputToScreen.displayOutput();
      }
    } while (runShell);
    sc.close();
  }

  /**
   * Gets input from the user and sends it to the parser. Prints current path
   * before waiting for new input. Stops execution when its appropriate.
   * 
   * @throws Exception
   */
  public static void main(String[] args) {
    FileSystem.getSystem();
    JShell j = new JShell();
    j.execute();
  }

}
