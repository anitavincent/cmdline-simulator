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

import exceptions.InvalidArgumentException;
import exceptions.InvalidNumberOfArgumentsException;
import handlers.ErrorHandler;
import handlers.Resource;
import handlers.Validator;
import output.Output;
import output.OutputToFile;

/**
 * Echo
 * 
 * Command class that is responsible for the tasks of the shell command echo.
 * Its function is to write a string either to the screen or to a specified file
 */
public class Echo extends Command {

  /**
   * Identifies what type of echo was called and appends or overwrites string to
   * a file or to the screen
   * 
   */
  public void execute(ArrayList<String> entireInputUser, ErrorHandler handler,
      Validator validator, Resource resource) {
    Output out = resource.getOutput();

    ArrayList<String> organizedArguments;
    try {
      String inputUser = entireInputUser.get(0);
      organizedArguments =
          validator.validateAndRetriveOrganizedEchoArguments(inputUser);
    } catch (InvalidNumberOfArgumentsException e) {
      out.appendToStdError(e.getMessage());
      return;
    } catch (InvalidArgumentException e) {
      out.appendToStdError(e.getMessage());
      return;
    }
    if (organizedArguments.size() == 1) {
      out.appendToStdOutput(organizedArguments.get(0));
      return;
    }
    try {
      validator.validateEchoOperator(organizedArguments);
      String message = organizedArguments.get(0);
      String path = organizedArguments.get(2);
      String operator = organizedArguments.get(1);
      OutputToFile outputToFile = new OutputToFile(out);
      outputToFile.saveMessageToFile(message, path, operator, validator,
          handler);
    } catch (InvalidArgumentException e) {
      out.appendToStdError(e.getMessage());
    }

  }


}
