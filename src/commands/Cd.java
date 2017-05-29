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

import exceptions.InvalidNumberOfArgumentsException;
import exceptions.InvalidPathException;
import files.Directory;
import handlers.ErrorHandler;
import handlers.Resource;
import handlers.Validator;
import output.Output;

/**
 * Cd
 * 
 * Command class that is responsible for the tasks of the shell command cd. Its
 * function is to change the current working directory
 */
public class Cd extends Command {

  /**
   * Changes the current working directory returning confirmation if action
   * worked
   *
   */
  public void execute(ArrayList<String> arguments, ErrorHandler handler,
      Validator validator, Resource resource) {

    Output out = resource.getOutput();

    try {
      Directory newDirectory = validator.validateCdAndGetDirectory(arguments);
      changeDirectory(newDirectory, validator);
    } catch (InvalidNumberOfArgumentsException e) {
      out.appendToStdError(e.getMessage());
      return;
    } catch (InvalidPathException e) {
      out.appendToStdError(e.getMessage());
      return;
    }

    return;
  }

  /**
   * Changes what the current directory is
   * 
   * @param newDirectory directory that will be the new current directory
   * @param validator
   */
  public void changeDirectory(Directory newDirectory, Validator validator) {
    validator.getDirectoryCursor().moveToDirectory(newDirectory);
  }
}
