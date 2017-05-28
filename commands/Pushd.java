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

import handlers.ErrorHandler;
import handlers.Resource;
import handlers.Validator;
import output.Output;

import java.util.ArrayList;

import exceptions.InvalidNumberOfArgumentsException;
import exceptions.InvalidPathException;
import files.Directory;
import files.DirectoryStack;

/**
 * Pushd
 * 
 * Command class that is responsible for the tasks of the shell command pushd.
 * Its function is to save the current working directory to the stack and change
 * it to a specified new directory
 */

public class Pushd extends Command {
  private DirectoryStack directoryStack;

  /**
   * Saves the current working directory to the stack and changes it to the
   * specified new directory
   * 
   */
  public void execute(ArrayList<String> arguments, ErrorHandler handler,
      Validator validator, Resource resource) {

    Output out = resource.getOutput();

    this.directoryStack = resource.getDirectoryStack();
    Command cd = new Cd();
    try {
      Directory newDirectory =
          validator.validatePushDAndReturnNewDirectory(arguments);
      Directory oldDirectory =
          validator.getDirectoryCursor().getWorkingDirectory();
      ((Cd) cd).changeDirectory(newDirectory, validator);
      directoryStack.push(oldDirectory);
    } catch (InvalidNumberOfArgumentsException e) {
      out.appendToStdError(e.getMessage());
      return;
    } catch (InvalidPathException e) {
      out.appendToStdError(e.getMessage());
      return;
    }
    return;
  }
}
