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
import files.DirectoryCursor;
import handlers.ErrorHandler;
import handlers.Resource;
import handlers.Validator;
import output.Output;

/**
 * Pwd
 * 
 * Command class that is responsible for the tasks of the shell command pwd. Its
 * function is to retrieve the current working directory
 */

public class Pwd extends Command {

  /**
   * Returns current working directory
   * 
   */
  public void execute(ArrayList<String> arguments, ErrorHandler handler,
      Validator validator, Resource resource) {

    Output out = resource.getOutput();

    try {
      validator.validateNumberOfArgumentsIsZero(arguments);
      DirectoryCursor cursor = validator.getDirectoryCursor();
      out.appendToStdOutput(cursor.getFullPathOfWorkingDirectory());
      return;

    } catch (InvalidNumberOfArgumentsException e) {
      out.appendToStdError(e.getMessage()
          + "for the Pwd command. This command does not take arguments.\n");
      return;
    }


  }

}
