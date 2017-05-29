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
package commands;

import java.util.ArrayList;

import exceptions.EmptyStackException;
import exceptions.InvalidNumberOfArgumentsException;
import exceptions.InvalidPathException;
import files.Directory;
import files.DirectoryStack;
import handlers.ErrorHandler;
import handlers.Resource;
import handlers.Validator;
import output.Output;

/**
 * Popd
 * 
 * Command class that is responsible for the tasks of the shell command popd.
 * Its function is to get the last directory saved to the stack and change the
 * current directory to it
 */
public class Popd extends Command {
  private DirectoryStack directoryStack;

  /**
   * Changes the current working directory to saved directory
   * 
   */
  public void execute(ArrayList<String> arguments, ErrorHandler handler,
      Validator validator, Resource resource) {

    Output out = resource.getOutput();

    String newDirectoryPath = "";
    this.directoryStack = resource.getDirectoryStack();
    Command cd = new Cd();
    try {
      validator.validateNumberOfArgumentsIsZero(arguments);
      newDirectoryPath = directoryStack.pop();
      Directory newDirectory =
          validator.validateAndRetrieveDirectory(newDirectoryPath);
      ((Cd) cd).changeDirectory(newDirectory, validator);
    } catch (InvalidNumberOfArgumentsException e) {
      out.appendToStdError(e.getMessage()
          + "for the Popd Command. This command does not take arguments. \n");
      return;
    } catch (EmptyStackException e) {
      out.appendToStdError(e.getMessage());
      return;
    } catch (InvalidPathException e) {
      out.appendToStdError(handler.getErrorMessage(26)
          + " The directory in path " + newDirectoryPath
          + " does not exist anymore.");
    }
    return;
  }

}
