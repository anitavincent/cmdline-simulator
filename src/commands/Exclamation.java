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
import handlers.Interpreter;
import handlers.Resource;
import handlers.Validator;
import output.Output;

public class Exclamation extends Command {

  /**
   * Gets a certain past input from the inputLog and calls that input again
   */
  public void execute(ArrayList<String> arguments, ErrorHandler handler,
      Validator validator, Resource resource) {

    Output out = resource.getOutput();

    int index;

    try {
      // validates arguments
      index = validator.validateExclamationAndGetNumber(arguments);
    } catch (InvalidArgumentException e) {
      out.appendToStdError(e.getMessage());
      return;
    } catch (NumberFormatException e) {
      out.appendToStdError(e.getMessage());
      return;
    } catch (InvalidNumberOfArgumentsException e) {
      out.appendToStdError(e.getMessage());
      return;
    }

    // gets past input from log
    String command = resource.getInputLog().get(index - 1);

    // calls past input again, the results will be stored in Output
    Interpreter i = new Interpreter(validator.getDirectoryCursor(), out);
    i.interpret(command);

  }

}
